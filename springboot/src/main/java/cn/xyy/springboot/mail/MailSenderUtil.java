package cn.xyy.springboot.mail;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderUtil {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	/**
	 * 发送一封简单的邮件
	 * @param from 发件人
	 * @param to 收件人
	 * @param subject 主题
	 * @param text 内容
	 */
	public void sendSimpleMail(String from,String to,String subject,String text) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(from);
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		javaMailSender.send(simpleMailMessage);
	}
	
	/**
	 * 发送一封带有附件的邮件
	 * @param from	发件人
	 * @param to	收件人
	 * @param subject	主题
	 * @param text	内容
	 * @param attachmentFilename 文件原始名称
	 * @param attachmentRealResource	附件真实路径信息
	 * @throws Exception
	 */
	public void sendAttachmentsMail(String from,String to,String subject,String text,String attachmentFilename,String attachmentRealResource) throws Exception{
		try{
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(text);
			
			FileSystemResource fileSystemResource = new FileSystemResource(new File(attachmentRealResource));
			messageHelper.addAttachment(attachmentFilename, fileSystemResource);
			
			javaMailSender.send(mimeMessage);
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception("附件邮件发送失败！！！");
		}
	}
	/**
	 * 发送静态资源邮件
	 * @param from
	 * @param to
	 * @param subject
	 * @param text
	 * @param attachmentFilename
	 * @param attachmentRealResource
	 */
	public void sendInlineMail(String from,String to,String subject,String text,String attachmentFilename,String attachmentRealResource){
		try{
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(text,true);
			
			FileSystemResource fileSystemResource = new FileSystemResource(new File(attachmentRealResource));
			messageHelper.addInline(attachmentFilename, fileSystemResource);
			
			javaMailSender.send(mimeMessage);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}