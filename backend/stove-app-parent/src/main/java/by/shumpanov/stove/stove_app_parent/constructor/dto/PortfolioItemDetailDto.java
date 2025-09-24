package by.shumpanov.stove.stove_app_parent.constructor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PortfolioItemDetailDto {
    private Long id;
    private String title;
    private String description;
    @JsonProperty("main_image_url")
    private String mainImageUrl;
    @JsonProperty("completion_date")
    private LocalDate completionDate;

    private ConfigurationResponse configuration;
}
