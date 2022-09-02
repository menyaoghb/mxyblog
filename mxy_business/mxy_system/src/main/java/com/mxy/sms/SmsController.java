package com.mxy.sms;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/foreign/sms")
public class SmsController {

    @Resource
    private SmsService smsService;


    /**
     * 发送短信验证码
     * @return
     */
    @ApiOperation(value="发送短信验证码", httpMethod = "GET", notes = "发送短信验证码")
    @GetMapping(value = "/customer/sendMessageCode")
    public String sendMessageCode(@RequestParam(value = "phoneNo") String phoneNo){
        return  smsService.sendMessageCode(phoneNo);
    }



}
