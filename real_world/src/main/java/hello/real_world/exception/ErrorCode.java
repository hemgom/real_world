package hello.real_world.exception;

import org.springframework.http.HttpStatus;

/**
 * 클라이언트에게 보낼 에러코드 정의
 * 에러명, HTTP 상태, 에러 메시지
 */
public interface ErrorCode {

    String name();
    HttpStatus getHttpStatus();
    String getMessage();

}
