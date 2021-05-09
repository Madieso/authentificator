package com.lma.authentificator.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

	@Value("${mail.transport.protocol}")
	private String protocol;

	@Value("${mail.smtp.auth}")
	private String auth;

	@Value("${mail.smtp.starttls.enable}")
	private String starttls;

	@Value("${mail.debug}")
	private String debug;

	@Value("${mail.smtp.host}")
	private String host;

	@Value("${mail.smtp.port}")
	private String port;

	@Value("${mail.sender.email}")
	private String email;

	@Value("${mail.sender.password}")
	private String password;

	@Value("${frontend.url}")
	private String frontEndUrl;

	@Bean
	public JavaMailSender emailSender() {
		final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(this.host);
		mailSender.setPort(Integer.valueOf(this.port));

		mailSender.setUsername(this.email);
		mailSender.setPassword(this.password);

		final Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", this.protocol);
		props.put("mail.smtp.auth", this.auth);
		props.put("mail.smtp.starttls.enable", this.starttls);
		props.put("mail.debug", this.debug);

		return mailSender;
	}

}
