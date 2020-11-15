package be.howest.ti.mars.logic.exceptions;

public class UsersException extends RuntimeException {
    public UsersException() {
        super();
    }
    public UsersException(String message) {
        super(message);
    }
    public UsersException(String message, Throwable cause) {
        super(message, cause);
    }
}
