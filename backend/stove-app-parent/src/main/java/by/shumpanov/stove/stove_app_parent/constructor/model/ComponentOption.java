package by.shumpanov.stove.stove_app_parent.constructor.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "component_options")
public class ComponentOption implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "component_options_generator")
    @SequenceGenerator(name = "component_options_generator", sequenceName = "component_options_id_seq", allocationSize = 1)
    private Long id;

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
    private boolean isDefault = false;
}
