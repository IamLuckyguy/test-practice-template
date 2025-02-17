package kr.co.kwt.testpracticetemplate.application.port.in;

public record SignUpCommand(String email, String password, String name) {
}