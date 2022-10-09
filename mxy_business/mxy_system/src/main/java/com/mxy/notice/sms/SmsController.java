package com.mxy.notice.sms;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信验证码
 *
 * @author mengyao
 */
@RestController
@CrossOrigin
@RequestMapping("/api/foreign/sms")
public class SmsController {

    @Resource
    private SmsService smsService;

    /**
     * 发送短信验证码
     *
     * @return
     */
    @ApiOperation(value = "发送短信验证码", httpMethod = "GET", notes = "发送短信验证码")
    @GetMapping(value = "/customer/sendMessageCode")
    public String sendSmsMessage(@RequestParam(value = "phoneNo") String phoneNo) {
        return smsService.sendSmsMessage(phoneNo);
    }

    /**
     * 发送邮箱验证码
     *
     * @return
     */
    @ApiOperation(value = "发送邮箱验证码", httpMethod = "GET", notes = "发送邮箱验证码")
    @GetMapping(value = "/customer/sendEmailMessage")
    public String sendEmailMessage(@RequestParam(value = "email") String email) {
        return smsService.sendEmailMessage(email);
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @ApiOperation(value = "获取验证码", httpMethod = "GET", notes = "获取验证码")
    @GetMapping(value = "/get/captcha")
    public String captcha(HttpServletRequest request, HttpServletResponse response) {
        return smsService.captcha(request,response);
    }

}
