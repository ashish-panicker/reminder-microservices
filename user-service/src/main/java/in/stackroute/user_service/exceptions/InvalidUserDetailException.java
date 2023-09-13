package in.stackroute.user_service.exceptions;

public class InvalidUserDetailException extends RuntimeException {

    public InvalidUserDetailException(String message) {
        super(message);
    }
}
