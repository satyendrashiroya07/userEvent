package shiroya.userEvent.userEvent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionable extends RuntimeException {

    @ExceptionHandler(UserNotCreatedException.class)
    public ResponseEntity<String> UserNotCreatedException(RuntimeException ex){
        return new ResponseEntity<>("SomeThing is Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> duplicateUserException(RuntimeException ex){
        return new ResponseEntity<>("Duplicate User", HttpStatus.CONFLICT);
    }
}
