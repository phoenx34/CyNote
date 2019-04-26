package org.springframework.samples.petclinic.storage;

/**
 *  Handles the StorageFileNotFoundException
 * @author Shen Chen
 * @author Marc Issac 
 */
public class StorageFileNotFoundException extends StorageException {

	/**
	 * Handles StorageFileNotFoundException with a message  
	 * @param message The warning 
	 */
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    /**
     * Handles StorageFileNotFoundException with a message and cause 
     * @param message The warning  
     * @param cause The cause of the exception
     */
    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}