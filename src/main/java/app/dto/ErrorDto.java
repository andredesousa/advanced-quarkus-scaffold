package app.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;
}
