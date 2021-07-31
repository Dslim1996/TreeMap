package com.spring.treemap.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.spring.treemap.domain.MemberVO;

public class EmailSender {
	public static void gmailSend(MemberVO member) {
        String user = ""; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
        String password = "";   // 패스워드

        // SMTP 서버 정보를 설정한다.
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com"); 
        prop.put("mail.smtp.port", 465); 
        prop.put("mail.smtp.auth", "true"); 
        prop.put("mail.smtp.ssl.enable", "true"); 
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));

            //수신자메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(member.getUserEmail())); 

            // Subject
            message.setSubject("Treemap 임시 비밀번호 입니다."); //메일 제목을 입력

            // Text
            message.setContent("<h1>TreeMap 임시 비밀번호 입니다.</h1><br>"+member.getUserName()+"님의 임시 비밀번호: "+member.getUserPW() +" 사이트에서 비밀번호를 변경하시기 바랍니다.","text/html;charset=utf-8");
            // send the message
            Transport.send(message); ////전송
            System.out.println("message sent successfully...");
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	public static void naverMailSend(MemberVO member) { 
		String host = "smtp.naver.com"; 
		// 네이버일 경우 네이버 계정, gmail경우 gmail 계정 
		String user = ""; 
		// 패스워드 
		String password = "";      
		
		// SMTP 서버 정보를 설정한다. 
		Properties props = new Properties(); 
		props.put("mail.smtp.host", host); 
		props.put("mail.smtp.port", 587); 
		props.put("mail.smtp.auth", "true"); 
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() { 
			protected PasswordAuthentication getPasswordAuthentication() { 
				return new PasswordAuthentication(user, password); 
				} 
			}); 
		
		try { 
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user)); 
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(member.getUserEmail())); 
			
			// 메일 제목 
			message.setSubject("Treemap 임시 비밀번호 입니다."); 
			// 메일 내용 
			message.setContent("<h1>TreeMap 임시 비밀번호 입니다.</h1><br>"+member.getUserName()+"님의 임시 비밀번호: "+member.getUserPW() +" 사이트에서 비밀번호를 변경하시기 바랍니다.","text/html;charset=utf-8");
			
			// send the message 		
			Transport.send(message); 
			System.out.println("Success Message Send"); 
			} 
		catch (MessagingException e) {
			e.printStackTrace(); 
			} 
		}

}
