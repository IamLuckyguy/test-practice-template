package kr.co.kwt.testpracticetemplate.adapter.in.web;

import kr.co.kwt.testpracticetemplate.application.port.in.SignUpCommand;
import kr.co.kwt.testpracticetemplate.application.port.in.SignUpUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {
    private final SignUpUseCase signUpUseCase;

    public SignUpController(SignUpUseCase signUpUseCase) {
        this.signUpUseCase = signUpUseCase;
    }

    @PostMapping("/api/signup")
    public void signUp(@RequestBody SignUpRequest signUpRequest) {
        SignUpCommand command = new SignUpCommand(
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getName()
        );
        signUpUseCase.signUp(command);
    }
}