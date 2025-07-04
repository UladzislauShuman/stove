package by.shumpanov.stove.stove_app_parent.constructor.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "components")
public class Component implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "components_generator")
    @SequenceGenerator(name = "components_generator", sequenceName = "components_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "stove_type_id", nullable = false)
    private StoveType stoveType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_required", nullable = false)
    private boolean isRequired = true;

    @Column(name = "allow_multiple_choices", nullable = false)
    private boolean allowMultipleChoices = false;

    @OneToMany(mappedBy = "component", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComponentOption> componentOptions;
}
