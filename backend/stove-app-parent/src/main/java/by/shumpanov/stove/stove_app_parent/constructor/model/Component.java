package by.shumpanov.stove.stove_app_parent.constructor.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "components")
public class Component extends AbstractPersistable<Long> {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "stove_type_id", nullable = false)
    private StoveType stoveType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_required", nullable = false)
    private boolean isRequired = true; // или перенести в другое место дефолтное значение

    @Column(name = "allow_multiple_choices", nullable = false)
    private boolean allowMultipleChoices = false; // или перенести в БД

    @OneToMany(mappedBy = "component", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComponentOption> componentOptions;
}
