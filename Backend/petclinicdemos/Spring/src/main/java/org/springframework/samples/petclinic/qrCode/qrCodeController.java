package org.springframework.samples.petclinic.qrCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.NotFoundException;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

import java.io.IOException;


@RestController
@RequestMapping("/qrcode")
public class qrCodeController {

	
	 @Autowired
	 qrCodeService qrCodeService;

	 

	 	/**
	 	 * The message of the code should be passed in by front end
	 	 * create a QR code with that message
	 	 * Return the QR code image in the string base64 format 
	 	 * @param message
	 	 * @return
	 	 * @throws IOException
	 	 */
	    @RequestMapping(value="/getQRCode")
	    public String getQRCode(@RequestBody String message) throws IOException {
	        return qrCodeService.crateQRCode(message,200,200);
	    }
	
	    
	    
	    
	    /**
	     * With a given image
	     * read and return the message hidden in the graph 
	     * @param imageInBase The image with the form of base64
	     * @return The message hidden inside 
	     * @throws IOException
	     * @throws NotFoundException
	     * @throws Base64DecodingException
	     */
	    @RequestMapping(value="/readQRCode")
	    public void readQRCode(@RequestBody String imageInBase) throws IOException, NotFoundException, Base64DecodingException {
	        String hiddenMessage =  qrCodeService.decodeQRCode(imageInBase);
	        
	    }
	
	    
	    
	    
	    
	
	
}
