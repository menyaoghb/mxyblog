package com.mxy.notice.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.mxy.common.core.entity.SysSmsSendLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 短信发送
 *
 * @author mengyao
 */
@Slf4j
@Component
public class SmsSend {

    @Value("${sms.accessKeyId}")
    private String accessKeyId;

    @Value("${sms.secret}")
    private String secret;

    @Resource
    private JavaMailSender sender;

    /**
     * @description: 短信验证码发送
     * @param: phoneNo-接收号码、verifyCode-验证码、type-短信模板类型（默认通用模板）（1-注册、2-登录、0-通用）
     * 签名说明：
     * 个人日常博客
     * 模板说明：
     * 通用模板：SMS_250730005   您的验证码${code}，该验证码5分钟内有效，请勿泄漏于他人！
     * 登录验证码模板：SMS_250730255    验证码为：${code}，您正在登录，若非本人操作，请勿泄露。
     * 注册验证码模板：SMS_250750257    您正在申请手机注册，验证码为：${code}，5分钟内有效！
     **/
    @Async("threadPoolTaskExecutor")
    public void sendSmsMessage(String phoneNo, String verifyCode, int type) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);
        //组装请求对象
        CommonRequest request = new CommonRequest();
        //短信API产品域名（接口地址固定，无需修改）
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        //接收号码
        request.putQueryParameter("PhoneNumbers", phoneNo);
        //短信签名
        request.putQueryParameter("SignName", "个人日常博客");
        //短信模板
        String smsTemplate = "";
        switch (type) {
            case 1:
                request.putQueryParameter("TemplateCode", "SMS_250750257");
                smsTemplate = "【个人日常博客】您正在申请手机注册，验证码为：" + verifyCode + "，5分钟内有效！";
                break;
            case 2:
                request.putQueryParameter("TemplateCode", "SMS_250730255");
                smsTemplate = "【个人日常博客】验证码为：" + verifyCode + "，您正在登录，若非本人操作，请勿泄露。";
                break;
            default:
                request.putQueryParameter("TemplateCode", "SMS_250730005");
                smsTemplate = "【个人日常博客】您的验证码" + verifyCode + "，该验证码5分钟内有效，请勿泄漏于他人！";
                break;
        }
        //验证码
        request.putQueryParameter("TemplateParam", "{code:" + verifyCode + "}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            SysSmsSendLog sendLog = new SysSmsSendLog();
            sendLog.setPhone(phoneNo);
            sendLog.setRequest(smsTemplate);
            sendLog.setResponse(String.valueOf(response.getData()));
            sendLog.insert();
            log.info(phoneNo + "短信发送：" + response.getData());
        } catch (Exception e) {
            e.printStackTrace();
            log.info(phoneNo + "短信发送失败：" + e.getMessage());
        }
    }

    @Async("threadPoolTaskExecutor")
    public void sendEmailMessage(String email, String verifyCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("my.mxy@qq.com");
            mailMessage.setTo(email);
            mailMessage.setSubject("个人日常博客验证码");
            mailMessage.setText("您的验证码" + verifyCode + "，该验证码5分钟内有效，请勿泄漏于他人！");
            sender.send(mailMessage);
            SysSmsSendLog sendLog = new SysSmsSendLog();
            sendLog.setPhone(email);
            sendLog.setRequest(mailMessage.getText());
            sendLog.setResponse("OK");
            log.info(email + "->邮件发送：" + mailMessage.getText());
            sendLog.insert();
        } catch (MailException e) {
            e.printStackTrace();
            log.info(email + "邮件发送失败：" + e.getMessage());
        }
    }

}
