package by.shumpanov.stove.stove_app_parent.constructor.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponentOptionDto implements Serializable {

    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price_modifier")
    private BigDecimal priceModifier;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("is_default")
    private boolean isDefault = false;

}
