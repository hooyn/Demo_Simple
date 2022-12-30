package hooyn.base.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ResponseWrapper {

    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    private String path;

    private String status;

    private Integer code;

    private Boolean isSuccess;

    private String message;

    private Object responseData;

    public ResponseWrapper(HttpServletRequest request, HttpStatus status, Boolean isSuccess, String message, Object responseData){
        this.path = request.getRequestURI() + " [" + request.getMethod() + "]";
        this.status = status.name();
        this.code = status.value();
        this.isSuccess = isSuccess;
        this.message = message;
        this.responseData = responseData;
    }
}
