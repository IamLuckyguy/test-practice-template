package kr.co.kwt.testpracticetemplate.adapter.out.persistence;

import kr.co.kwt.testpracticetemplate.application.port.out.SaveUserPort;
import kr.co.kwt.testpracticetemplate.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserPersistenceAdapter implements SaveUserPort {
    private final UserRepository userRepository;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}