package by.shumpanov.stove.stove_app_parent.constructor.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "configuration_choices")
public class ConfigurationChoice implements Serializable{

    @EmbeddedId
    private ConfigurationChoiceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("configurationId")
    @JoinColumn(name = "configuration_id")
    private Configuration configuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("optionId")
    @JoinColumn(name = "option_id")
    private ComponentOption option;

    @Column(name = "quantity")
    private Integer quantity;
}
