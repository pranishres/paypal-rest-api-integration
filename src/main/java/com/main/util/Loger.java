package com.main.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Loger {
	static Logger logger = Logger.getLogger("MyLog");  
	static FileHandler 	fh;
	
	public static void addLog(String message){

		    try {  

		  
		        SimpleFormatter formatter = new SimpleFormatter();  
		        fh.setFormatter(formatter);  

		        // the following statement is used to log any messages  
		        logger.info("My first log");  
		        logger.info(message);

		    } catch (SecurityException e) {  
		        e.printStackTrace();  
		    }
	}
	
	public static void startLogger(){
		try {
			fh = new FileHandler("/media/pranishres/510712721F638E31/Dropbox/ekbana files/Work/Other files/Tasks/Paypal-REST-API/logs/MyLogFile.log");
		      // This block configure the logger with handler and formatter  
	        logger.addHandler(fh);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
