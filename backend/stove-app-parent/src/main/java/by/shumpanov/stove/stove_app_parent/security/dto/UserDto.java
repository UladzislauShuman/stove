package by.shumpanov.stove.stove_app_parent.security.dto;

import by.shumpanov.stove.stove_app_parent.security.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable {

    private Long id;

    @NotBlank(message = "email не может быть пустым")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "номер телефона не может быть пустым")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "full_name не может быть пустым")
    @JsonProperty("full_name")
    private String fullName;

    @NotBlank(message = "user_role не может быть пустой")
    @JsonProperty("user_role")
    private User.UserRole role;
}
