package shiroya.userEvent.userEvent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionable {

    @ExceptionHandler(UserNotCreatedException.class)
    public ResponseEntity<String> UserNotCreatedException(RuntimeException ex){
        return new ResponseEntity<>("SomeThing is Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> duplicateUserException(RuntimeException ex){
        return new ResponseEntity<>("Duplicate User", HttpStatus.CONFLICT);
    }


    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> RoleNotFoundException(RuntimeException ex) {
        return new ResponseEntity<>("Roles Not Found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsUserOrPass.class)
    public ResponseEntity<String> BadCredentialsUserOrPass(RuntimeException ex) {
        return new ResponseEntity<>("Bad Credentials", HttpStatus.BAD_REQUEST);
    }
}
