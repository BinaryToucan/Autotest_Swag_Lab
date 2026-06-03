package utils;

public enum TestUser {

    STANDARD_USER("standard_user", "secret_sauce"),
    WRONG_PASS_USER("standard_user", "public_sauce"),
    LOCKED_USER("locked_out_user", "secret_sauce"),
    PROBLEM_USER("problem_user", "secret_sauce");

    private final String username;
    private final String password;

    TestUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}