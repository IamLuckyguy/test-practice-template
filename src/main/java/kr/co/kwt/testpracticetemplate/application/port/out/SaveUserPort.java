package kr.co.kwt.testpracticetemplate.application.port.out;

import kr.co.kwt.testpracticetemplate.domain.User;

public interface SaveUserPort {
    void saveUser(User user);
}