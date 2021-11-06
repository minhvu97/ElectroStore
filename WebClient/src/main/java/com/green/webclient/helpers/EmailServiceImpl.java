package com.green.webclient.helpers;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.green.webmodels.entities.Customer;
import com.green.webmodels.entities.Order;
import com.green.webmodels.entities.OrderDetail;

public class EmailServiceImpl {
	
	private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "email/email-simple";
	
	private static final String EMAIL_ORDER = "email/email-order";
  
	public static void sendSimpleMessage(JavaMailSender emailSender, String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@baeldung.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public static void sendHTMLEmail(JavaMailSender mailSender, String from, String to, String subject,
			String message) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

		try {
			helper.setSubject(subject);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText("<h1>ELECTRO-SHOP</h1><BR>" + message);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
    public static void sendSimpleMail( JavaMailSender mailSender, TemplateEngine htmlTemplateEngine,
            final String recipientName, final String recipientEmail, final Locale locale)
            throws MessagingException {

            // Prepare the evaluation context
            final Context ctx = new Context(locale);
            ctx.setVariable("name", recipientName);
            ctx.setVariable("subscriptionDate", new Date());
            ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

            // Prepare message using a Spring helper
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject("Example HTML email (simple)");
            message.setFrom("thymeleaf@example.com");
            message.setTo(recipientEmail);

            // Create the HTML body using Thymeleaf
            final String htmlContent = htmlTemplateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);
            message.setText(htmlContent, true /* isHtml */);

            // Send email
           mailSender.send(mimeMessage);
        }
    
    public static void sendVerificationEmail(String verificationCode, String recipientName, String recipientEmail,
    		JavaMailSender mailSender) throws UnsupportedEncodingException, MessagingException
    {
		String subject = "Please verify your registration";
		String senderName = "Electro Team";
		
		String mailContent = "<p>Dear " + recipientName + ",</p>";
		mailContent += "<p>Please click the link below to verify to your registration:</p>";
		
		String verifyURL = "http://localhost:8082/verify?code=" + verificationCode;
		
		mailContent += "<h3><a href='" + verifyURL + "'>VERIFY</a></h3>";	
		mailContent += "<p>Thank you<br>The Electro Team</p>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("noreply@gmail.com", senderName);
		helper.setTo(recipientEmail);
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		
		mailSender.send(message);
    }
    
	public static void sendOrderEmail(JavaMailSender mailSender, Customer customer,
			String subject, TemplateEngine htmlTemplateEngine, final Locale locale, Order newestOrder) 
			throws UnsupportedEncodingException, MessagingException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		String senderName = "Electro Team";

		helper.setFrom("noreply@gmail.com", senderName);
		helper.setTo(customer.getEmail());
		helper.setSubject(subject);
		
		List<OrderDetail> listOd = new ArrayList<>(newestOrder.getOrderDetails());
		
        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", customer.getName());
        ctx.setVariable("subject", subject);
        ctx.setVariable("listOrderDetail", listOd);
        ctx.setVariable("totalPrice", newestOrder.getTotalPrice());
        
        // Create the HTML body using Thymeleaf
        final String htmlContent = htmlTemplateEngine.process(EMAIL_ORDER, ctx);
        helper.setText(htmlContent, true /* isHtml */);

		mailSender.send(mimeMessage);
		
	}
}
