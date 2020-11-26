package be.howest.ti.mars.logic.exceptions;

public class SeedException extends RuntimeException{
    public SeedException() {
        super();
    }
    public SeedException(String message) {
        super(message);
    }
    public SeedException(String message, Throwable ex) {
        super(message,ex);
    }
}
