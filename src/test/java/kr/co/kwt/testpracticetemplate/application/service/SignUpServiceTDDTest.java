package kr.co.kwt.testpracticetemplate.application.service;

import kr.co.kwt.testpracticetemplate.application.port.in.SignUpCommand;
import kr.co.kwt.testpracticetemplate.application.port.out.SaveUserPort;
import kr.co.kwt.testpracticetemplate.domain.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class SignUpServiceTDDTest {

    // Step 1: 가장 단순한 실패하는 테스트부터 시작
    @Test
    void 이메일이_없으면_회원가입에_실패한다() {
        // 1단계: 실패하는 테스트
        SignUpCommand command = new SignUpCommand(
                null,
                "password123",
                "John Doe"
        );

        SaveUserPort saveUserPort = Mockito.mock(SaveUserPort.class);
        SignUpService signUpService = new SignUpService(saveUserPort);

        assertThrows(IllegalArgumentException.class,
                () -> signUpService.signUp(command));
    }

    // Step 2: 이메일 유효성 검증 추가
    @Test
    void 이메일_형식이_올바르지_않으면_회원가입에_실패한다() {
        SignUpCommand command = new SignUpCommand(
                "invalid-email",
                "password123",
                "John Doe"
        );

        SaveUserPort saveUserPort = Mockito.mock(SaveUserPort.class);
        SignUpService signUpService = new SignUpService(saveUserPort);

        assertThrows(IllegalArgumentException.class,
                () -> signUpService.signUp(command));
    }

    // Step 3: 성공 케이스 추가
    @Test
    void 올바른_이메일_형식이면_회원가입에_성공한다() {
        SignUpCommand command = new SignUpCommand(
                "test@example.com",
                "password123",
                "John Doe"
        );

        SaveUserPort saveUserPort = Mockito.mock(SaveUserPort.class);
        SignUpService signUpService = new SignUpService(saveUserPort);

        assertDoesNotThrow(() -> signUpService.signUp(command));
        verify(saveUserPort).saveUser(any(User.class));
    }
}