package app.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RegisterForReflection
public class ErrorDto {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;
}
