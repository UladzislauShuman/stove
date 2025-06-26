package by.shumpanov.stove.stove_app_parent.constructor.config;

import by.shumpanov.stove.stove_app_parent.constructor.model.Addon;
import by.shumpanov.stove.stove_app_parent.constructor.model.Component;
import by.shumpanov.stove.stove_app_parent.constructor.model.ComponentOption;
import by.shumpanov.stove.stove_app_parent.constructor.model.StoveType;
import by.shumpanov.stove.stove_app_parent.constructor.repository.AddonRepository;
import by.shumpanov.stove.stove_app_parent.constructor.repository.ComponentOptionRepository;
import by.shumpanov.stove.stove_app_parent.constructor.repository.ComponentRepository;
import by.shumpanov.stove.stove_app_parent.constructor.repository.StoveTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@Profile("dev")
public class DataInitializer {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(
            StoveTypeRepository stoveTypeRepository,
            ComponentRepository componentRepository,
            ComponentOptionRepository componentOptionRepository,
            AddonRepository addonRepository
    ) {
        return args -> {
            if (stoveTypeRepository.count() > 0) {
                System.out.println("Data already exists. Skipping initialization.");
                return;
            }

            System.out.println("--- Initializing Test Data ---");

            // 1. Создаем ТИП СТРОЕНИЯ
            StoveType pompeiStove = StoveType.builder()
                    .name("Помпейская печь")
                    .description("Классическая дровяная печь для пиццы и выпечки.")
                    .basePrice(new BigDecimal("5000.00"))
                    .imageUrl("https://example.com/images/pompei.jpg")
                    .build();
            stoveTypeRepository.save(pompeiStove);
            
            // 2. Создаем КОМПОНЕНТЫ для этого типа
            Component baseComponent = componentRepository.save(Component.builder().stoveType(pompeiStove).name("Основание").description("На чем будет стоять ваша печь.").isRequired(true).build());
            Component domeComponent = componentRepository.save(Component.builder().stoveType(pompeiStove).name("Купол").description("Материал основной камеры горения.").isRequired(true).build());
            Component insulationComponent = componentRepository.save(Component.builder().stoveType(pompeiStove).name("Утепление купола").description("Как долго печь будет держать тепло.").isRequired(true).build());
            Component facingComponent = componentRepository.save(Component.builder().stoveType(pompeiStove).name("Облицовка").description("Внешний вид вашей печи.").isRequired(false).build());

            // 3. Создаем ВАРИАНТЫ (Options) для каждого компонента
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

            // 4. Создаем общие ДОП. УСЛУГИ (Addons)
            addonRepository.saveAll(List.of(
                    Addon.builder().name("Доставка в пределах 50 км").description("Привезем все материалы на ваш участок.").price(new BigDecimal("100.00")).build(),
                    Addon.builder().name("Строительство дымохода (за метр)").description("Цена указана за 1 метр кирпичного дымохода.").price(new BigDecimal("250.00")).build()
            ));

            System.out.println("--- Test Data Initialized ---");
        };
    }
}