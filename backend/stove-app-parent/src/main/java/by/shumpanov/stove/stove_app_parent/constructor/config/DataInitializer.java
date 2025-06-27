package by.shumpanov.stove.stove_app_parent.constructor.config;

import by.shumpanov.stove.stove_app_parent.constructor.model.Addon;
import by.shumpanov.stove.stove_app_parent.constructor.model.Component;
import by.shumpanov.stove.stove_app_parent.constructor.model.ComponentOption;
import by.shumpanov.stove.stove_app_parent.constructor.model.StoveType;
import by.shumpanov.stove.stove_app_parent.constructor.repository.AddonRepository;
import by.shumpanov.stove.stove_app_parent.constructor.repository.ComponentOptionRepository;
import by.shumpanov.stove.stove_app_parent.constructor.repository.ComponentRepository;
import by.shumpanov.stove.stove_app_parent.constructor.repository.StoveTypeRepository;
import by.shumpanov.stove.stove_app_parent.security.model.User;
import by.shumpanov.stove.stove_app_parent.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@Profile("dev") // Этот бин будет создан только при активном профиле "dev"
public class DataInitializer {

    @Bean
    @Transactional // Оборачиваем все в одну транзакцию для надежности
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            StoveTypeRepository stoveTypeRepository,
            ComponentRepository componentRepository,
            ComponentOptionRepository componentOptionRepository,
            AddonRepository addonRepository
    ) {
        return args -> {
            // Проверяем, есть ли уже данные, чтобы не создавать дубликаты при каждом перезапуске
            if (userRepository.count() > 0) {
                System.out.println("Data already exists. Skipping initialization.");
                return;
            }

            System.out.println("--- Initializing Test Data ---");

            // =============================================
            //      ШАГ 0: СОЗДАЕМ ТЕСТОВЫХ ПОЛЬЗОВАТЕЛЕЙ
            // =============================================
            User customer = User.builder()
                    .fullName("Тестовый Клиент")
                    .email("customer@example.com")
                    .phoneNumber("+375291112233")
                    .passwordHash(passwordEncoder.encode("password"))
                    .userRole(User.UserRole.CUSTOMER)
                    .build();
            userRepository.save(customer);

            User stovemaker = User.builder()
                    .fullName("Мастер Печник")
                    .email("stovemaker@example.com")
                    .phoneNumber("+375294445566")
                    .passwordHash(passwordEncoder.encode("password"))
                    .userRole(User.UserRole.STOVEMAKER)
                    .build();
            userRepository.save(stovemaker);

            System.out.println("--- Users created: customer@example.com, stovemaker@example.com (password: 'password') ---");

            // =============================================
            //      ШАГ 1: СОЗДАЕМ ТИП СТРОЕНИЯ
            // =============================================
            StoveType pompeiStove = StoveType.builder()
                    .name("Помпейская печь")
                    .description("Классическая дровяная печь для пиццы и выпечки.")
                    .basePrice(new BigDecimal("5000.00"))
                    .imageUrl("https://example.com/images/pompei.jpg")
                    .build();
            stoveTypeRepository.save(pompeiStove);

            // =============================================
            //      ШАГ 2: СОЗДАЕМ КОМПОНЕНТЫ
            // =============================================
            Component baseComponent = componentRepository.save(Component.builder().stoveType(pompeiStove).name("Основание").description("На чем будет стоять ваша печь.").isRequired(true).build());
            Component domeComponent = componentRepository.save(Component.builder().stoveType(pompeiStove).name("Купол").description("Материал основной камеры горения.").isRequired(true).build());
            Component insulationComponent = componentRepository.save(Component.builder().stoveType(pompeiStove).name("Утепление купола").description("Как долго печь будет держать тепло.").isRequired(true).build());
            Component facingComponent = componentRepository.save(Component.builder().stoveType(pompeiStove).name("Облицовка").description("Внешний вид вашей печи.").isRequired(false).build());

            // =============================================
            //      ШАГ 3: СОЗДАЕМ ВАРИАНТЫ (OPTIONS)
            // =============================================
            // Для Основания
            componentOptionRepository.saveAll(List.of(
                    ComponentOption.builder().component(baseComponent).name("На кирпичном постаменте").priceModifier(new BigDecimal("2500.00")).imageUrl("https://example.com/images/base_brick.jpg").isDefault(true).build(),
                    ComponentOption.builder().component(baseComponent).name("На металлическом каркасе").priceModifier(new BigDecimal("3500.00")).imageUrl("https://example.com/images/base_metal.jpg").isDefault(false).build(),
                    ComponentOption.builder().component(baseComponent).name("На готовом прицепе").priceModifier(new BigDecimal("7000.00")).imageUrl("https://example.com/images/base_trailer.jpg").isDefault(false).build()
            ));

            // Для Купола
            componentOptionRepository.saveAll(List.of(
                    ComponentOption.builder().component(domeComponent).name("Из красного огнеупорного кирпича").priceModifier(new BigDecimal("1500.00")).imageUrl("https://example.com/images/dome_red.jpg").isDefault(true).build(),
                    ComponentOption.builder().component(domeComponent).name("Из желтого шамотного кирпича").priceModifier(new BigDecimal("1800.00")).imageUrl("https://example.com/images/dome_yellow.jpg").isDefault(false).build()
            ));

            // Для Утепления
            componentOptionRepository.saveAll(List.of(
                    ComponentOption.builder().component(insulationComponent).name("Керамическое волокно (стандарт)").priceModifier(new BigDecimal("800.00")).imageUrl("https://example.com/images/ins_ceramic.jpg").isDefault(true).build(),
                    ComponentOption.builder().component(insulationComponent).name("Вермикулит + цемент").priceModifier(new BigDecimal("600.00")).imageUrl("https://example.com/images/ins_vermiculite.jpg").isDefault(false).build()
            ));

            // Для Облицовки
            componentOptionRepository.saveAll(List.of(
                    ComponentOption.builder().component(facingComponent).name("Простая штукатурка под покраску").priceModifier(new BigDecimal("500.00")).imageUrl("https://example.com/images/face_plaster.jpg").isDefault(true).build(),
                    ComponentOption.builder().component(facingComponent).name("Декоративная мозаика").priceModifier(new BigDecimal("2500.00")).imageUrl("https://example.com/images/face_mosaic.jpg").isDefault(false).build()
            ));

            // =============================================
            //      ШАГ 4: СОЗДАЕМ ОБЩИЕ ДОП. УСЛУГИ (ADDONS)
            // =============================================
            addonRepository.saveAll(List.of(
                    Addon.builder().name("Доставка в пределах 50 км").description("Привезем все материалы на ваш участок.").price(new BigDecimal("100.00")).build(),
                    Addon.builder().name("Строительство дымохода (за метр)").description("Цена указана за 1 метр кирпичного дымохода.").price(new BigDecimal("250.00")).build()
            ));

            System.out.println("--- Test Data Initialized ---");
        };
    }
}