package org.springframework.samples.petclinic.storage;

/**
 *  Handles the StorageException
 * @author Shen Chen
 * @author Marc Issac 
 */
public class StorageException extends RuntimeException {

	/**
	 * Handles StorageException with a message  
	 * @param message The warning 
	 */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Handles StorageException with a message and cause 
     * @param message The warning  
     * @param cause The cause of the exception
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}