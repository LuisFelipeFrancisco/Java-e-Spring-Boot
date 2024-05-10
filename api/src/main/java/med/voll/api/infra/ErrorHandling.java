package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandling {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity notFound404(){
        return ResponseEntity.notFound().build(); // 404 NOT FOUND
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity notValid400(MethodArgumentNotValidException err){

        List<FieldError> errors = err.getFieldErrors();

        return ResponseEntity.badRequest().body(errors.stream().map(DataValidationError::new).toList()); // 400 BAD REQUEST
    }

    private record DataValidationError(String field, String message){
        public DataValidationError(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}
