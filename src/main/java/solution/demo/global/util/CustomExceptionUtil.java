package solution.demo.global.util;

import solution.demo.global.exception.ErrorCode;

public class CustomExceptionUtil {

    public static ErrorCode checkErrorCode(String code){

        return switch (code) {
            case "AccessDeniedException" -> ErrorCode.FORBIDDEN;
            case "HttpMessageNotReadableException", "MissingServletRequestParameterException",
                    "JsonParseException", "FileSizeLimitExceededException",
                    "MethodArgumentTypeMismatchException", "HttpMediaTypeNotSupportedException" -> ErrorCode.BAD_REQUEST;
            default -> ErrorCode.INTERNAL_SERVER_ERROR;
        };
    }

    public static String checkMessage(String code, Exception e){

        return switch (code) {
            case "HttpMessageNotReadableException", "MissingServletRequestParameterException" -> "요청 데이터가 필요합니다.";
            case "JsonParseException" -> "데이터 포맷이 맞지 않습니다.";
            case "MethodArgumentTypeMismatchException" -> "요청 파라미터의 타입이 올바르지 않습니다.";
            case "HttpMediaTypeNotSupportedException" -> "Content-Type 이 맞지 않습니다.";
            case "MethodArgumentNotValidException" ->  "필수 값의 제약 조건을 확인해주세요.";
            default -> e.getMessage();
        };
    }
}
