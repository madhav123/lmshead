/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
package org.mifosplatform.billing.message.service;

import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.mifosplatform.billing.message.data.BillingMessageDataForProcessing;
import org.mifosplatform.billing.message.domain.BillingMessage;
import org.mifosplatform.billing.message.domain.MessageDataRepository;
import org.mifosplatform.infrastructure.core.domain.EmailDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageGmailBackedPlatformEmailService implements MessagePlatformEmailService {

private final BillingMesssageReadPlatformService billingMesssageReadPlatformService;
 private final MessageDataRepository messageDataRepository;

 @Autowired
    public MessageGmailBackedPlatformEmailService(BillingMesssageReadPlatformService billingMesssageReadPlatformService,
    		MessageDataRepository messageDataRepository){
	
	 this.billingMesssageReadPlatformService=billingMesssageReadPlatformService;
	 this.messageDataRepository=messageDataRepository;
	 
 }

 @Override
 public void sendToUserEmail() {
     Email email = new SimpleEmail();

     String authuserName = "info@hugotechnologies.com";
     //String authusername="hugotechnologies";

     String authuser = "ashokcse556@gmail.com";
     String authpwd = "9989720715";

     // Very Important, Don't use email.setAuthentication()
     email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
     email.setDebug(true); // true if you want to debug
     email.setHostName("smtp.gmail.com");
     try {
         email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
         email.setFrom(authuserName, authuser);
         List<BillingMessageDataForProcessing> billingMessageDataForProcessings=this.billingMesssageReadPlatformService.retrieveMessageDataForProcessing();
   	    for(BillingMessageDataForProcessing emailDetail : billingMessageDataForProcessings){
          
         StringBuilder subjectBuilder = new StringBuilder().append(" ").append(emailDetail.getSubject()).append("  ");

         email.setSubject(subjectBuilder.toString());
  
        String sendToEmail = emailDetail.getMessageTo();

         StringBuilder messageBuilder = new StringBuilder().append(emailDetail.getHeader()).append(".").append(emailDetail.getBody()).append(",").append(emailDetail.getFooter());

         email.setMsg(messageBuilder.toString());

         email.addTo(sendToEmail, emailDetail.getMessageFrom());
         email.setSmtpPort(587);
         email.send();
         BillingMessage billingMessage=this.messageDataRepository.findOne(emailDetail.getId());
         if(billingMessage.getStatus().contentEquals("N"))
         {
         	billingMessage.updateStatus();
         }
         this.messageDataRepository.save(billingMessage);
         
         
        }
   	 } catch (EmailException e) {
         throw new MessagePlatformEmailSendException(e);
     }
 }
}*/

/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.portfolio.message.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.joda.time.LocalDate;
import org.mifosplatform.portfolio.message.data.BillingMessageDataForProcessing;
import org.mifosplatform.portfolio.message.domain.BillingMessage;
import org.mifosplatform.portfolio.message.domain.MessageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageGmailBackedPlatformEmailService implements
		MessagePlatformEmailService {
	private final MessageDataRepository messageDataRepository;

	@Autowired
	public MessageGmailBackedPlatformEmailService(
			MessageDataRepository messageDataRepository) {

		this.messageDataRepository = messageDataRepository;

	}

	@Override
	public String sendToUserEmail(BillingMessageDataForProcessing emailDetail) {
		
		Email email = new SimpleEmail();
		String authuserName = "info@hugotechnologies.com";

		String authuser = "madhav@hugotechnologies.com";
		String authpwd = "madhav123";

		// Very Important, Don't use email.setAuthentication()
		email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
		email.setDebug(false); // true if you want to debug
		email.setHostName("smtp.gmail.com");
		try {
			email.getMailSession().getProperties()
					.put("mail.smtp.starttls.enable", "true");
			email.setFrom(authuserName, authuser);
			// email.setSmtpPort(587);
			StringBuilder subjectBuilder = new StringBuilder().append(" ")
					.append(emailDetail.getSubject()).append("  ");

			email.setSubject(subjectBuilder.toString());

			String sendToEmail = emailDetail.getMessageTo();
			StringBuilder messageBuilder = new StringBuilder()
					.append(emailDetail.getHeader()).append(".")
					.append(emailDetail.getBody()).append(",")
					.append(emailDetail.getFooter());
			email.addTo(sendToEmail, emailDetail.getMessageFrom());
			// email.setHtmlMsg("<html>"+messageBuilder.toString()+"</html>");
			email.setMsg(messageBuilder.toString());
			email.send();
			BillingMessage billingMessage = this.messageDataRepository
					.findOne(emailDetail.getId());
			if (billingMessage.getStatus().contentEquals("N")) {
				billingMessage.updateStatus();
			}
			this.messageDataRepository.save(billingMessage);
            return "success";
		} catch (Exception e) {
			handleCodeDataIntegrityIssues(null, e);
			return e.getMessage();
		}
	}

	private void handleCodeDataIntegrityIssues(Object object, Exception dve) {
		// TODO Autogenerated method stub

	}

	@Override
	public String sendToUserMobile(String message, Long id) {
		// TODO Autogenerated method stub
		try {
			
			String retval = "";
			URL url = new URL("http://smscountry.com/SMSCwebservice_Bulk.aspx");

			HttpURLConnection urlconnection = (HttpURLConnection) url
					.openConnection();

			urlconnection.setRequestMethod("POST");
			urlconnection.setRequestProperty("ContentType",
					"application/xwwwformurlencoded");
			urlconnection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(
					urlconnection.getOutputStream());
			out.write(message);
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlconnection.getInputStream()));
			String decodedString;
			while ((decodedString = in.readLine()) != null) {
				retval += decodedString;
			}
			in.close();
			System.out.println(retval);
			String k = retval.substring(0, 3);

			if (k.equalsIgnoreCase("OK:")) {
				BillingMessage billingMessage = this.messageDataRepository
						.findOne(id);
				if (billingMessage.getStatus().contentEquals("N")) {
					billingMessage.updateStatus();
				}
				this.messageDataRepository.save(billingMessage);
			}
			return "success";

		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException : "
					+ e.getMessage() + " . encoding pattern not supported.");
			return e.getMessage();
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException : " + e.getMessage()
					+ " . URL is not located.");
			return e.getMessage();
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage() + ".");
			return e.getMessage();
		}
	}

	@Override
	public String createEmail(String pdfFileName, String emailId) {
		
		 Date date=new Date();
		 String dateTime=date.getHours()+""+date.getMinutes();
	     String fileLocation="ReportEmail_"+new LocalDate().toString().replace("-","")+"_"+dateTime+".pdf";
		final String username = "ashokreddy556@gmail.com";
		final String password = "9989720715";

		    Properties props = new Properties();

		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.host", "smtp.gmail.com");
		    props.put("mail.smtp.port", "587");

		    Session session = Session.getInstance(props,new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, password);
		      }
		      });

		    try {

		      Message message = new MimeMessage(session);
		      message.setFrom(new InternetAddress(emailId));
		      message.setRecipients(Message.RecipientType.TO,
		        InternetAddress.parse(emailId));
		      message.setSubject("hello, For Testing");
				message.setText("Dear Mail Crawler,"
						+ "\n\n No spam to my email, please!");
				
				MimeBodyPart messageBodyPart = new MimeBodyPart();

		        Multipart multipart = new MimeMultipart();

		        messageBodyPart = new MimeBodyPart();
		        String file = pdfFileName;
		        String fileName = fileLocation;
		        DataSource source = new FileDataSource(file);
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(fileName);
		        multipart.addBodyPart(messageBodyPart);
		        message.setContent(multipart);
		        System.out.println("Sending");
				Transport.send(message);
		      System.out.println("Done");
		      return "Success";
		    } catch (MessagingException e) {
		      throw new RuntimeException(e);
		    }
		    
	}
}