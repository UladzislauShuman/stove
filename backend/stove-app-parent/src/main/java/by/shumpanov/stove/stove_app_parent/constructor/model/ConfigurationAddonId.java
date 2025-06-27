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
public class ConfigurationAddonId implements Serializable {
    @Column(name = "configuration_id")
    private Long configurationId;

    @Column(name = "addon_id")
    private Long addonId;
}