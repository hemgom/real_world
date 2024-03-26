package hello.real_world.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 발생한 예외를 처리해줄 예외 클래스
 * `RuntimeException`(언체크 예외) 을 상속 받음
 * ------------------------------------------
 * 언체크 예외를 상속받게 한 이유
 * 비즈니스로직에서 따로 `try-catch`문을 사용해 처리할 예외가 없음 (전역적으로 예외를 처리하기 때문)
 * 오히려 체크 예외를 상속하면 불필요하게 `throws`가 전파될 우려가 있음
 * Spring 은 내부적으로 예외를 확인해 `언체크 예외`거나 `에러`라면 자동으로 롤백하도록 처리함
 * 이는 개발자가 체크 예외를 처리할 거란 무언의 기대가 있기 때문
 */
@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}