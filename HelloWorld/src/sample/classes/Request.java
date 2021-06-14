package sample.classes;

public enum Request {
    INVALID_PASSWORD("Invalid Password"),
    INVALID_CREDENTIALS("Invalid Credentials"),
    SUCCESS("Successfully");

    private final String message;

    Request(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
