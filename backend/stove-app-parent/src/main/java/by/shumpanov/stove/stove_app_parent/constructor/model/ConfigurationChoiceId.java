package by.shumpanov.stove.stove_app_parent.constructor.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ConfigurationChoiceId implements Serializable {
    @Column(name = "configuration_id")
    private Long configurationId;

    @Column(name = "option_id")
    private Long optionId;
}