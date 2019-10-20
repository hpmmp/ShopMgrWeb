package com.shop.web.utils;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author zhangchuyan
 * @date 2015-04-01
 */

public class ThreeDES {
	
	private static Logger logger = LoggerFactory.getLogger(ThreeDES.class);

	public static byte[] decryptMode(byte keybyte[], byte src[]){

		try{
			SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
	        Cipher c1 = Cipher.getInstance("DESede");
	        c1.init(2, deskey);
	        return c1.doFinal(src);
	            
		 }catch(NoSuchAlgorithmException e1){
	         logger.error(e1.getMessage(), e1);
		 }catch(NoSuchPaddingException e2){
			 logger.error(e2.getMessage(), e2);
		 }catch(Exception e3){
			 logger.error(e3.getMessage(), e3);
		 }
		 return null;
	 }
	 
	 public static byte[] encryptMode(byte[] keybyte,byte[] src){ 
		 
         try {  
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");  
            Cipher c1 = Cipher.getInstance("DESede");  
            c1.init(Cipher.ENCRYPT_MODE, deskey); 
            return c1.doFinal(src);
            
        } catch (NoSuchAlgorithmException e1) {    
        	logger.error(e1.getMessage(), e1); 
        }catch(NoSuchPaddingException e2){  
        	logger.error(e2.getMessage(), e2);
        }catch(Exception e3){  
        	logger.error(e3.getMessage(), e3);  
        }  
        return null;  
    }  
}
