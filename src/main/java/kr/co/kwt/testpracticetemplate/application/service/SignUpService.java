package kr.co.kwt.testpracticetemplate.application.service;

import kr.co.kwt.testpracticetemplate.application.port.in.SignUpCommand;
import kr.co.kwt.testpracticetemplate.application.port.in.SignUpUseCase;
import kr.co.kwt.testpracticetemplate.application.port.out.SaveUserPort;
import kr.co.kwt.testpracticetemplate.domain.User;
import org.springframework.stereotype.Service;

@Service
public class SignUpService implements SignUpUseCase {
    private final SaveUserPort saveUserPort;

    public SignUpService(SaveUserPort saveUserPort) {
        this.saveUserPort = saveUserPort;
    }

    @Override
    public void signUp(SignUpCommand command) {
        // 1. 비즈니스 검증 로직
        validateSignUpCommand(command);

        // 2. 도메인 객체 생성
        User user = new User(
                command.email(),
                command.password(),
                command.name()
        );

        // 3. 저장
        saveUserPort.saveUser(user);
    }

    private void validateSignUpCommand(SignUpCommand command) {
        // 이메일 형식 검증
        if (!isValidEmail(command.email())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // 비밀번호 규칙 검증
        if (!isValidPassword(command.password())) {
            throw new IllegalArgumentException("Invalid password format");
        }

        // 이름 길이 검증
        if (command.name() == null || command.name().trim().length() < 2) {
            throw new IllegalArgumentException("Name should be at least 2 characters");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }
}