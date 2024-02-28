package com.hnust.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class VerificationCodeUtil {
    public static final String FROM_EMAIL = "15700799965@163.com";
    public static final String HOST = "smtp.163.com";

    /**
     * 验证码发送
     * @param email
     * @return
     */
    public static String send(String email) {
        String verificationCodeStr = "";
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", HOST);
        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(FROM_EMAIL, "MDFYNTQLGYHQAEPK"); //发件人邮件用户名、授权码
            }
        });
        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(FROM_EMAIL));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // Set Subject: 头部头字段
            message.setSubject("验证码");
            //验证码获取
            int verificationCode = 1000 + new Random().nextInt(9000);
            verificationCodeStr = Integer.toString(verificationCode);
            // 设置消息体
            message.setText(verificationCodeStr);
            // 发送消息
            Transport.send(message);
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return verificationCodeStr;
    }
}
