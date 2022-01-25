package pojos;

import lombok.Data;

@Data
public class ErrorPojo {
    private String path;
    private String error;
    private String message;
    private String timestamp;
    private int status;
}
