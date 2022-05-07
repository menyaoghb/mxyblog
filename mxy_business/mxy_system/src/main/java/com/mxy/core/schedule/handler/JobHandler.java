package com.mxy.core.schedule.handler;

import com.mxy.common.core.entity.SysUser;
import com.mxy.common.core.utils.DateUtils;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 孟耀
 */
//@Component
public class JobHandler {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @XxlJob("jobHandler")
    public void jobHandler() throws Exception {
        for (int i = 0; i < 1000; i++) {
            SysUser sysUser = new SysUser();

            sysUser.setStatus("0");
            sysUser.setCreateUser("系统");
            sysUser.setPassword(bCryptPasswordEncoder.encode("12345678Az@"));
            sysUser.setAvatar("http://mxy.mxyit.com/0ae9a7ef-6254-4117-be07-0c3bb7c0d8a7");
            sysUser.setUsername(DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS) + i);
            sysUser.setUserType("1483417117871259649");
            sysUser.setEmail("2533180033@qq.com");
            sysUser.setPhoneNumber("13156582153");
            sysUser.setSex("0");
            sysUser.setLoginIp("110.42.135.120");
            sysUser.setLoginDate(new Date());
            sysUser.setRemark("备注" + i);
            sysUser.setNickName("自动注册MY" + i + "号");
            sysUser.insert();
            XxlJobHelper.log("当前用户：" + sysUser.getUsername());
        }
    }

    @XxlJob("jobHandlerA")
    public void jobHandlerA() throws Exception {
        for (int i = 0; i < 1000; i++) {
            SysUser sysUser = new SysUser();
            sysUser.setStatus("0");
            sysUser.setCreateUser("系统");
            sysUser.setPassword(bCryptPasswordEncoder.encode("12345678Az@"));
            sysUser.setAvatar("http://mxy.mxyit.com/0ae9a7ef-6254-4117-be07-0c3bb7c0d8a7");
            sysUser.setUsername(DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS) + i);
            sysUser.setUserType("1483417117871259649");
            sysUser.setEmail("2533180033@qq.com");
            sysUser.setPhoneNumber("13156582153");
            sysUser.setSex("0");
            sysUser.setLoginIp("110.42.135.120");
            sysUser.setLoginDate(new Date());
            sysUser.setRemark("备注" + i);
            sysUser.setNickName("自动调度A" + (i + 1000) + "号");
            sysUser.insert();
            XxlJobHelper.log("当前用户：" + sysUser.getUsername());
        }
    }


    @XxlJob("jobHandlerB")
    public void jobHandlerB() throws Exception {
        for (int i = 0; i < 1000; i++) {
            SysUser sysUser = new SysUser();
            sysUser.setStatus("0");
            sysUser.setCreateUser("系统");
            sysUser.setPassword(bCryptPasswordEncoder.encode("12345678Az@"));
            sysUser.setAvatar("http://mxy.mxyit.com/0ae9a7ef-6254-4117-be07-0c3bb7c0d8a7");
            sysUser.setUsername(DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS) + i);
            sysUser.setUserType("1483417117871259649");
            sysUser.setEmail("2533180033@qq.com");
            sysUser.setPhoneNumber("13156582153");
            sysUser.setSex("0");
            sysUser.setLoginIp("110.42.135.120");
            sysUser.setLoginDate(new Date());
            sysUser.setRemark("备注" + i);
            sysUser.setNickName("自动注册B" + (i + 10000) + "号");
            sysUser.insert();
            XxlJobHelper.log("当前用户：" + sysUser.getUsername());
        }
    }


    @XxlJob("jobHandlerC")
    public void jobHandlerC() throws Exception {
        for (int i = 0; i < 1000; i++) {
            SysUser sysUser = new SysUser();
            sysUser.setStatus("0");
            sysUser.setCreateUser("系统");
            sysUser.setPassword(bCryptPasswordEncoder.encode("12345678Az@"));
            sysUser.setAvatar("http://mxy.mxyit.com/0ae9a7ef-6254-4117-be07-0c3bb7c0d8a7");
            sysUser.setUsername(DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS) + i);
            sysUser.setUserType("1483417117871259649");
            sysUser.setEmail("2533180033@qq.com");
            sysUser.setPhoneNumber("13156582153");
            sysUser.setSex("0");
            sysUser.setLoginIp("110.42.135.120");
            sysUser.setLoginDate(new Date());
            sysUser.setRemark("备注" + i);
            sysUser.setNickName("自动注册C" + (i + 100000) + "号");
            sysUser.insert();
            XxlJobHelper.log("当前用户：" + sysUser.getUsername());
        }
    }


    @XxlJob("jobHandlerD")
    public void jobHandlerD() throws Exception {
        for (int i = 0; i < 1000; i++) {
            SysUser sysUser = new SysUser();
            sysUser.setStatus("0");
            sysUser.setCreateUser("系统");
            sysUser.setPassword(bCryptPasswordEncoder.encode("12345678Az@"));
            sysUser.setAvatar("http://mxy.mxyit.com/0ae9a7ef-6254-4117-be07-0c3bb7c0d8a7");
            sysUser.setUsername(DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS) + i);
            sysUser.setUserType("1483417117871259649");
            sysUser.setEmail("2533180033@qq.com");
            sysUser.setPhoneNumber("13156582153");
            sysUser.setSex("0");
            sysUser.setLoginIp("110.42.135.120");
            sysUser.setLoginDate(new Date());
            sysUser.setRemark("备注" + i);
            sysUser.setNickName("自动注册D" + (i + 1000000) + "号");
            sysUser.insert();
            XxlJobHelper.log("当前用户：" + sysUser.getUsername());
        }
    }

    @XxlJob("jobHandlerE")
    public void jobHandlerE() throws Exception {
        for (int i = 0; i < 1000; i++) {
            SysUser sysUser = new SysUser();
            sysUser.setStatus("0");
            sysUser.setCreateUser("系统");
            sysUser.setPassword(bCryptPasswordEncoder.encode("12345678Az@"));
            sysUser.setAvatar("http://mxy.mxyit.com/0ae9a7ef-6254-4117-be07-0c3bb7c0d8a7");
            sysUser.setUsername(DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS) + i);
            sysUser.setUserType("1483417117871259649");
            sysUser.setEmail("2533180033@qq.com");
            sysUser.setPhoneNumber("13156582153");
            sysUser.setSex("0");
            sysUser.setLoginIp("110.42.135.120");
            sysUser.setLoginDate(new Date());
            sysUser.setRemark("备注" + i);
            sysUser.setNickName("自动注册E" + (i + 10000000) + "号");
            sysUser.insert();
            XxlJobHelper.log("当前用户：" + sysUser.getUsername());
        }
    }
}
