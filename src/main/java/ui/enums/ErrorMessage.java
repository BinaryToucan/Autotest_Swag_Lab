package ui.enums;

///  Сообщения об ошибках
public enum ErrorMessage {
    LOCKED_USER("Epic sadface: Sorry, this user has been locked out."),
    INVALID_PASSWORD("Epic sadface: Username and password do not match any user in this service"),
    REQUIRED_USER("Epic sadface: Username is required"),
    REQUIRED_PASSWORD("Epic sadface: Password is required");;

    private final String text;

    ErrorMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
