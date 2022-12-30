package solution.demo.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import solution.demo.global.response.ResponseWrapper;

import javax.servlet.http.HttpServletRequest;

import static solution.demo.global.util.CustomExceptionUtil.checkErrorCode;
import static solution.demo.global.util.CustomExceptionUtil.checkMessage;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ResponseWrapper> errorHandlerException(CustomException e, HttpServletRequest request){

        ErrorCode errorCode;
        String cause = "[none]";
        String message;

        if (e.getErrorCode() == null) {
            cause = e.getCause().getClass().getSimpleName();
            message = e.getCause().getMessage();
            errorCode = checkErrorCode(cause);
        } else {
            errorCode = e.getErrorCode();
            message = e.getMessage();
        }

        return ResponseEntity
                .status(errorCode.getStatus().value())
                .body(new ResponseWrapper(
                        request, errorCode.getStatus(), false, message, "cause-" + cause));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseWrapper> handlerException(Exception e, HttpServletRequest request){

        ErrorCode errorCode;
        String cause = e.getClass().getSimpleName();
        String message = checkMessage(cause, e);

        errorCode = checkErrorCode(cause);

        return ResponseEntity
                .status(errorCode.getStatus().value())
                .body(new ResponseWrapper(
                        request, errorCode.getStatus(), false, message, "cause-" + cause));
    }
}
