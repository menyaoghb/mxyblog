package com.mxy.security.security.jwt;

import com.alibaba.fastjson.JSONObject;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.security.common.config.JWTConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JWT接口请求校验拦截器
 * 请求接口时会进入这里验证Token是否合法和过期
 *
 * @Author Mxy
 * @CreateTime 2022/01/5 16:41
 */
@Slf4j
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中JWT的Token
        String tokenHeader = request.getHeader(JWTConfig.tokenHeader);
        log.info("--------------Token校验开始tokenHeader：{}", tokenHeader);
        if (null != tokenHeader && tokenHeader.startsWith(JWTConfig.tokenPrefix)) {
            try {
                // 截取JWT前缀
                String token = tokenHeader.replace(JWTConfig.tokenPrefix, "");
                log.info("--------------Token校验开始token:{}", token);
                // 解析JWT
                Claims claims = Jwts.parser()
                        .setSigningKey(JWTConfig.secret)
                        .parseClaimsJws(token)
                        .getBody();
                log.info("--------------解析JWT:{}", claims);
                // 获取用户名
                String userName = claims.getSubject();
                String userId = claims.getId();
                log.info("--------------获取用户名:{},{}", userName, userId);
                if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userId)) {
                    // 获取角色
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    String authority = claims.get("authorities").toString();
                    if (!StringUtils.isEmpty(authority)) {
                        List<Map<String, String>> authorityMap = JSONObject.parseObject(authority, List.class);
                        for (Map<String, String> role : authorityMap) {
                            if (!StringUtils.isEmpty(role)) {
                                authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                            }
                        }
                    }
                    log.info("--------------组装参数");
                    //组装参数
                    SelfUserEntity selfUserEntity = new SelfUserEntity();
                    selfUserEntity.setUsername(claims.getSubject());
                    selfUserEntity.setRelName(String.valueOf(claims.get("relName")));
                    selfUserEntity.setUserId(claims.getId());
                    selfUserEntity.setAuthorities(authorities);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(selfUserEntity, userId, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("--------------Token校验结束");
                }
            } catch (ExpiredJwtException e) {
                log.info("Token过期");
            } catch (Exception e) {
                log.info("Token无效");
            }
        }
        filterChain.doFilter(request, response);
        return;
    }
}