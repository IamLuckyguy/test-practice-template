package kr.co.kwt.testpracticetemplate.application.service;

import kr.co.kwt.testpracticetemplate.application.port.in.SignUpCommand;
import kr.co.kwt.testpracticetemplate.application.port.out.SaveUserPort;
import kr.co.kwt.testpracticetemplate.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@DisplayName("회원 가입 시나리오")
public class SignUpServiceBDDTest {
    @Nested
    @DisplayName("회원가입을 시도할 때")
    class SignUpScenario {

        @Test
        @DisplayName("모든 정보가 올바르면 회원가입에 성공한다")
        void successfulSignUp() {
            // Given: 올바른 회원가입 정보가 주어졌을 때
            SignUpCommand command = new SignUpCommand(
                    "test@example.com",
                    "password123",
                    "John Doe"
            );
            SaveUserPort saveUserPort = Mockito.mock(SaveUserPort.class);
            SignUpService signUpService = new SignUpService(saveUserPort);

            // When: 회원가입을 시도하면
            signUpService.signUp(command);

            // Then: 사용자가 저장되어야 한다
            verify(saveUserPort).saveUser(any(User.class));
        }

        @Test
        @DisplayName("이메일 형식이 잘못되면 가입에 실패한다")
        void failureWithInvalidEmail() {
            // Given: 잘못된 이메일 형식이 주어졌을 때
            SignUpCommand command = new SignUpCommand(
                    "invalid-email",
                    "password123",
                    "John Doe"
            );
            SaveUserPort saveUserPort = Mockito.mock(SaveUserPort.class);
            SignUpService signUpService = new SignUpService(saveUserPort);

            // When & Then: 회원가입 시도 시 예외가 발생해야 한다
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> signUpService.signUp(command)
            );

            assertEquals("Invalid email format", exception.getMessage());
        }
    }
}
