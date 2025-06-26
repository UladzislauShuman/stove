package by.shumpanov.stove.stove_app_parent.constructor.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComponentOption extends AbstractPersistable<Long> {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price_modifier", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceModifier;

    @Column(name = "image_url", nullable = false, unique = true)
    private String imageUrl;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false; // может перенести в БД?
}
