package be.howest.ti.mars.logic.exceptions;

public class ProductException extends RuntimeException{
    public ProductException() {
        super();
    }
    public ProductException(String message){
        super(message);
    }
    public ProductException(String message, Throwable ex) {
        super(message,ex);
    }
}
