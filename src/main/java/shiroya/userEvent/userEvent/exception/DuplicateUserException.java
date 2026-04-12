package shiroya.userEvent.userEvent.exception;

public class DuplicateUserException extends RuntimeException  {

    public DuplicateUserException(String message){
        super(message);
    }
}
