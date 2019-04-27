package org.springframework.samples.petclinic.qrCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;



@Service
public class qrCodeService {
	
	
	/**
	 * Encoder
	 * Create a new QR Code with it's content and width and height of the code
	 * And return the front end with the base64 string
	 * @param content The content you want for the code 
	 * @param width The width of the code 
	 * @param height The height of the code 
	 * @return The image in the format of base64 format 
	 * @throws IOException
	 */
    public String crateQRCode(String content, int width, int height) throws IOException {

        String resultImage = "";
        if (!StringUtils.isEmpty(content)) {
            ServletOutputStream stream = null;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            @SuppressWarnings("rawtypes")
            HashMap<EncodeHintType, Comparable> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 指定字符编码为“utf-8”
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // 指定二维码的纠错等级为中级
            hints.put(EncodeHintType.MARGIN, 2); // 设置图片的边距

            try {
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ImageIO.write(bufferedImage, "png", os);
                /**
                 * 原生转码前面没有 data:image/png;base64 这些字段，返回给前端是无法被解析，可以让前端加，也可以在下面加上
                 */
                //byte[] encodedArray = Base64.getEncoder().encode(someArrayToEncode);
                resultImage = new String("data:image/png;base64," + Base64.encode(os.toByteArray()));
              //  resultImage = new String("data:image/png;base64," + Base64.encode(os.toByteArray()));

                return resultImage;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    stream.flush();
                    stream.close();
                }
            }
        }
        return null;
    }
	
    
	
    
    
    
    /**
     * 
     * Decode the Base64 String in a array of bytes
     * Pass the decoded String to ImageIO.read parameter. 
     * Here you need to instantiate a new ByteArrayInputStream
     * Return the message 
     * @param base64QRCode The image with the message in the format of base64
     * @return The message within the message 
     * @throws IOException
     * @throws NotFoundException
     * @throws Base64DecodingException
     */
    public String decodeQRCode(String base64QRCode) throws IOException, NotFoundException, Base64DecodingException {
    	byte[] decoded = Base64.decode(base64QRCode);
    	BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(decoded));
    	    LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
    	    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

    	    Result result = new MultiFormatReader().decode(bitmap);
    	    return result.getText();
    	}
	
	
	
	
	
	
	
}
