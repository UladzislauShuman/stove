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
@Table(name = "configuration_addons")
public class ConfigurationAddon {

    @EmbeddedId
    private ConfigurationAddonId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("configurationId")
    @JoinColumn(name = "configuration_id")
    private Configuration configuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("addonId")
    @JoinColumn(name = "addon_id")
    private Addon addon;
}
