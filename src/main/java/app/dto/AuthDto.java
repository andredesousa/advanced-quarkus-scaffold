package app.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RegisterForReflection
public class AuthDto {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
