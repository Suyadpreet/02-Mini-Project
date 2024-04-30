package com.suyad.Emails;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils 
{
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendEmail(File file)
	{
		boolean status = false;
		try
		{
			Integer myid = 10;
			String myname = "Suyadpreet Singh";
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg,true);// use true to Send Multipart(attached,files) with mail
			helper.setTo("suyadgill@gmail.com");
			helper.setSubject("Your Report");
			helper.setText("<h2>Please download your report Mr. "+myname+
					"</h2><br><p><a href=http://localhost:8081/wel?id="+myid+"&name="+myname+">To visit Welcome Page click here</a></p>", true);
			helper.addAttachment(file.getName(), file);
			mailSender.send(msg);
			status = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
}
