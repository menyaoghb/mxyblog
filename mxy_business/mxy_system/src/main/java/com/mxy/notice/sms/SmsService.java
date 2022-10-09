package com.mxy.notice.sms;

import com.mxy.common.core.utils.DateUtils;
import com.mxy.common.core.utils.RedisUtil;
import com.mxy.common.core.utils.ServiceResult;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class SmsService {

    @Value("${sms.max.limit}")
    private Integer maxLimit;

    @Value("${sms.sendError.content}")
    private String sendErrorContent;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SmsSend smsSend;

    private static final String MESSAGE_LIMIT = "messager_limit:";
    private static final String SEND_LIMIT = "sendmsg_limit_count:";
    private static final String PHONE_NO = "message_phone_no:";

    private static final String EMAIL_MESSAGE_LIMIT = "email_messager_limit:";
    private static final String EMAIL_SEND_LIMIT = "email_sendmsg_limit_count:";
    private static final String EMAIL_NO = "message_email_no:";

    private static final String CAPTCHA_NO = "message_captcha_no:";

    private Random random = new Random();

    public String sendSmsMessage(String phoneNo) {
        // 非空检验
        if (StringUtils.isEmpty(phoneNo)) {
            return ServiceResult.error("电话号码不能为空哦");
        }
        String sendMsgKey = SEND_LIMIT + phoneNo;
        if (redisUtil.hasKey(sendMsgKey) && (int) redisUtil.get(sendMsgKey) > maxLimit) {
            return ServiceResult.error(sendErrorContent);
        }
        String phoneNoKey = PHONE_NO + phoneNo;
        // 如果验证码已发送 作废之前的验证码
        if (redisUtil.hasKey(phoneNoKey)) {
            redisUtil.del(phoneNoKey);
        }
        String limitKey = MESSAGE_LIMIT + phoneNo;
        if (redisUtil.hasKey(limitKey)) {
            return ServiceResult.error("一分钟只能请求一次哦");
        }
        // 随机生成6位验证码
        String verifyCode = String.valueOf(random.nextInt(899999) + 100000);
        // 限制60秒只能生成一次验证码
        redisUtil.set(limitKey, verifyCode, 60);
        // 短信验证码 5分钟失效
        redisUtil.set(phoneNoKey, verifyCode, 300);
        int count = (int) (redisUtil.get(sendMsgKey) == null ? 0 : redisUtil.get(sendMsgKey));
        // 计算当天23点59分59秒的秒数
        long expireTime = DateUtils.getDifferentTimes();
        // 当日0点过期
        redisUtil.set(sendMsgKey, count + 1, expireTime);
        // 异步请求下发短信
        smsSend.sendSmsMessage(phoneNo, verifyCode, 2);
        return ServiceResult.success("短信发送成功");
    }

    public String sendEmailMessage(String email) {
        // 非空检验
        if (StringUtils.isEmpty(email)) {
            return ServiceResult.error("邮箱地址不能为空哦");
        }
        String sendMsgKey = EMAIL_SEND_LIMIT + email;
        if (redisUtil.hasKey(sendMsgKey) && (int) redisUtil.get(sendMsgKey) > maxLimit) {
            return ServiceResult.error(sendErrorContent);
        }
        String emailNoKey = EMAIL_NO + email;
        // 如果验证码已发送 作废之前的验证码
        if (redisUtil.hasKey(emailNoKey)) {
            redisUtil.del(emailNoKey);
        }
        String limitKey = EMAIL_MESSAGE_LIMIT + email;
        if (redisUtil.hasKey(limitKey)) {
            return ServiceResult.error("一分钟只能请求一次哦");
        }
        // 随机生成6位验证码
        String verifyCode = String.valueOf(random.nextInt(899999) + 100000);
        // 限制60秒只能生成一次验证码
        redisUtil.set(limitKey, verifyCode, 60);
        // 短信验证码 5分钟失效
        redisUtil.set(emailNoKey, verifyCode, 300);
        int count = (int) (redisUtil.get(sendMsgKey) == null ? 0 : redisUtil.get(sendMsgKey));
        // 计算当天23点59分59秒的秒数
        long expireTime = DateUtils.getDifferentTimes();
        // 当日0点过期
        redisUtil.set(sendMsgKey, count + 1, expireTime);
        // 异步请求下发邮件
        smsSend.sendEmailMessage(email, verifyCode);
        return ServiceResult.success("邮件发送成功");
    }

    public String captcha(HttpServletRequest request, HttpServletResponse response) {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为300秒
        redisUtil.set(CAPTCHA_NO + key, verCode, 300);
        // 将key和base64返回给前端
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("image", specCaptcha.toBase64());
        return ServiceResult.success(map);
    }

}
