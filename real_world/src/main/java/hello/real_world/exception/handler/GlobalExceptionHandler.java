package hello.real_world.exception.handler;

import hello.real_world.exception.CommonErrorCode;
import hello.real_world.exception.CustomException;
import hello.real_world.exception.ErrorCode;
import hello.real_world.exception.dto.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * 스프링은 `스프링 예외`를 미리 처리해둔 `ResponseEntityExceptionHandler`를 추상 클래스를 제공함
 * 해당 클래스엔 스프링 예외에 대한 `ExceptionHandler`가 모두 구현되어 있음
 * 에러 메시지를 반환하지 않기에 스프링 예외에 대한 에러 응답을 보내기 위해서는 `handleExceptionInternal()`을 오버로딩 하여 사용한다.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // RuntimeException 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e) {
        log.warn("handleRuntimeException: ", e);
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    // IllegalArgumentException 처리
    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("handleIllegalArgumentException: ", e);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    // NoSuchElementException 처리
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e) {
        log.warn("handleNoSuchElementException", e);
        ErrorCode errorCode = CommonErrorCode.RESOURCE_NOT_FOUND;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    // 대부분의 에러 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        log.warn("handleAllException: ", e);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeResponseError(errorCode));
    }

    private ResponseError makeResponseError(ErrorCode errorCode) {
        return ResponseError.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeResponseError(errorCode, message));
    }

    private ResponseError makeResponseError(ErrorCode errorCode, String message) {
        return ResponseError.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }

    /**
     * `@Valid` 검증 시 발생하는 예외 = MethodArgumentNotValidException
     * 해당 예외 처리 메서드
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.warn("handleValidError: ", e);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(e, errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeResponseError(e, errorCode));
    }

    private ResponseError makeResponseError(BindException e, ErrorCode errorCode) {
        List<ResponseError.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ResponseError.ValidationError::of)
                .collect(Collectors.toList());

        return ResponseError.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .errors(validationErrorList)
                .build();
    }

}
