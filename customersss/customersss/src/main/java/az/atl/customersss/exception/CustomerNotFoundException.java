package az.atl.customersss.exception;

public class CustomerNotFoundException extends RuntimeException {
    private String message;


    public CustomerNotFoundException() {

    }

    public CustomerNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
