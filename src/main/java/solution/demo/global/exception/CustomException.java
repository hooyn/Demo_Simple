package solution.demo.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private ErrorCode errorCode;

    public CustomException(Exception e){
        super(e.getMessage(), e);

        if(e instanceof CustomException)
            this.errorCode = ((CustomException)e).errorCode;
    }

    public CustomException(ErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }
}
