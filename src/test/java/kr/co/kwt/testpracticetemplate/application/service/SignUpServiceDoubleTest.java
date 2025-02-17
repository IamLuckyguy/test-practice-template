package kr.co.kwt.testpracticetemplate.application.service;

import kr.co.kwt.testpracticetemplate.application.port.in.SignUpCommand;
import kr.co.kwt.testpracticetemplate.application.port.out.SaveUserPort;
import kr.co.kwt.testpracticetemplate.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SignUpServiceDoubleTest {
    // Dummy 객체 예시
    class DummyEmailService {
        void sendEmail(String to, String subject, String body) {
            // 아무 동작도 하지 않음
        }
    }

    // Stub 객체 예시
    class StubUserValidator {
        boolean isValidEmail(String email) {
            return email != null && email.contains("@");
        }
    }

    // Spy 객체 예시
    class SpyUserRepository implements SaveUserPort {
        private User savedUser;
        private int saveCount = 0;

        @Override
        public void saveUser(User user) {
            this.savedUser = user;
            this.saveCount++;
        }

        public User getSavedUser() {
            return savedUser;
        }

        public int getSaveCount() {
            return saveCount;
        }
    }

    // Fake 객체 예시
    class FakeUserRepository implements SaveUserPort {
        private final List<User> users = new ArrayList<>();

        @Override
        public void saveUser(User user) {
            users.add(user);
        }

        public List<User> findAll() {
            return new ArrayList<>(users);
        }
    }

    @Test
    @DisplayName("Spy를 사용한 회원가입 테스트")
    void signUpWithSpy() {
        // Given
        SpyUserRepository spyRepository = new SpyUserRepository();
        SignUpService signUpService = new SignUpService(spyRepository);
        SignUpCommand command = new SignUpCommand(
                "test@example.com",
                "password123",
                "John Doe"
        );

        // When
        signUpService.signUp(command);

        // Then
        assertEquals(1, spyRepository.getSaveCount());
        assertNotNull(spyRepository.getSavedUser());
        assertEquals("test@example.com", spyRepository.getSavedUser().getEmail());
    }

    @Test
    @DisplayName("Fake를 사용한 회원가입 테스트")
    void signUpWithFake() {
        // Given
        FakeUserRepository fakeRepository = new FakeUserRepository();
        SignUpService signUpService = new SignUpService(fakeRepository);
        SignUpCommand command = new SignUpCommand(
                "test@example.com",
                "password123",
                "John Doe"
        );

        // When
        signUpService.signUp(command);

        // Then
        assertEquals(1, fakeRepository.findAll().size());
        assertEquals("test@example.com", fakeRepository.findAll().get(0).getEmail());
    }
}
