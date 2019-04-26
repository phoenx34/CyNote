package org.springframework.samples.petclinic.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
 
/**
 * The class handles the excption 
 * @author Shen Chen
 * @author Marc Issac
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "NotFoundException not found")
public class NotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  /**
   * The not found exception with just the message waring 
   * @param message The waring 
   */
    public NotFoundException(String message) {
        super(message);
    }
 
    
    /**
     * The not found exception with  the message waring and the cause 
     * @param message The warning 
     * @param cause The cause of the exception 
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}