package org.springframework.samples.petclinic.storage;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * StorageProperties that contains the information for storage
 * @author Shen Chen 
 * @author Marc Issac
 */
@ConfigurationProperties("storage")
public class StorageProperties {

  
    private String location = "upload-dir";

    /**
     * Getter for location
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for location
     * @param location Given location
     */
    public void setLocation(String location) {
        this.location = location;
    }

}