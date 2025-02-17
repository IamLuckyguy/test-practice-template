package kr.co.kwt.testpracticetemplate.adapter.out.persistence;

import kr.co.kwt.testpracticetemplate.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
}