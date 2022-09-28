package app.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    public Date timestamp;

    public int status;

    public String error;

    public String message;
}
