package com.mxy.system.security.security.service;

import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysUser;
import com.mxy.system.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * SpringSecurity用户的业务实现
 * @Author Mxy
 * @CreateTime 2022/01/1 17:21
 */
@Component
public class SelfUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询用户信息
     * @Author Mxy
     * @CreateTime 2022/01/13 17:23
     * @Param  userName  用户名
     * @Return UserDetails SpringSecurity用户信息
     */
    @Override
    public SelfUserEntity loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 查询用户信息
        SysUser sysUserEntity =sysUserService.selectUserByName(userName);
        if (sysUserEntity!=null){
            // 组装参数
            SelfUserEntity selfUserEntity = new SelfUserEntity();
            BeanUtils.copyProperties(sysUserEntity,selfUserEntity);
            return selfUserEntity;
        }
        return null;
    }
}