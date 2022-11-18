package com.mxy.security.justauth;

import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthQqRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthUtil {

    @Value("${justauth.qq.clientId}")
    private String qqClientId;

    @Value("${justauth.qq.clientSecret}")
    private String qqClientSecret;

    @Value("${justauth.qq.redirectUri}")
    private String qqRedirectUri;

    @Value("${justauth.gitee.clientId}")
    private String giteeClientId;

    @Value("${justauth.gitee.clientSecret}")
    private String giteeClientSecret;

    @Value("${justauth.gitee.redirectUri}")
    private String giteeRedirectUri;

    /**
     * 鉴权
     */
    public AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source) {
            case "qq":
                authRequest = new AuthQqRequest(AuthConfig.builder()
                        .clientId(qqClientId)
                        .clientSecret(qqClientSecret)
                        .redirectUri(qqRedirectUri)
                        .build());
                break;
            case "gitee":
                authRequest = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId(giteeClientId)
                        .clientSecret(giteeClientSecret)
                        .redirectUri(giteeRedirectUri)
                        .build());
                break;
            default:
                break;
        }
        return authRequest;
    }

}
