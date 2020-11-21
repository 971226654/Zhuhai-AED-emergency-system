package com.bnuz.aed.common.tools.utils;

import cn.hutool.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leia Liang
 */
public class WechatUtils {

    /** 小程序的APP_ID */
    private static final String APP_ID_MINI = "wx7e09b09b4e03a623";

    /** 小程序的SECRET */
    private static final String SECRET_MINI = "169574d4167cd24781e12b1f9918b950";

    /** 小程序的URL */
    private static final String URL_MINI = "https://api.weixin.qq.com/sns/jscode2session";

    /** 小程序获取access_token */
    private static final String URL_ACCESS_MINI = "https://api.weixin.qq.com/cgi-bin/token";

    /** 网页的APP_ID */
    private static final String APP_ID_WEB = "wx6c0238d7aa237f78";

    /** 网页的SECRET */
    private static final String SECRET_WEB = "40a565ac2046e3b857df3c5641d6a88e";

    /** 网页获取code */
    private static final String CODE_WEB = "https://open.weixin.qq.com/connect/oauth2/authorize";

    /** 网页获取access_token */
    private static final String URL_WEB = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /** 网页刷新access_token */
    private static final String URL_REFRESH_WEB = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

    /** 网页检查access_token是否有效 */
    private static final String URL_CHECK_WEB = "https://api.weixin.qq.com/sns/auth";

    /** 网页获取个人信息 */
    private static final String URL_INFO_WEB = "https://api.weixin.qq.com/sns/userinfo";

    /** 网页小程序统一grant_type */
    private static final String GRANT_TYPE = "authorization_code";

    /**
     * 小程序用code换取openid
     * (openid, session_key, unionid, errcode, errmsg)
     * @param code
     * @return
     */
    public static JSONObject getOpenIdByMini(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("appid", APP_ID_MINI);
        params.put("secret", SECRET_MINI);
        params.put("js_code", code);
        params.put("grant_type", GRANT_TYPE);
        return HttpsClientUtils.doGet(URL_MINI, params);
    }

    /**
     * 小程序获取AccessToken
     * (access_token, expires_in, errcode, errmsg)
     * @return
     */
    public static JSONObject getAccessTokenByMini() {
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "client_credential");
        params.put("appid", APP_ID_MINI);
        params.put("secret", SECRET_MINI);
        return HttpsClientUtils.doGet(URL_ACCESS_MINI, params);
    }

    /**
     * WEB用code换取access_token
     * (access_token, expires_in, refresh_token, openid, scope, unionid)
     * @param code
     * @return
     */
    public static JSONObject getAccessTokenByWeb(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("appid", APP_ID_WEB);
        params.put("secret", SECRET_WEB);
        params.put("code", code);
        params.put("grant_type", GRANT_TYPE);
        return HttpsClientUtils.doGet(URL_WEB, params);
    }

    /**
     * WEB刷新access_token
     * (access_token, expires_in, refresh_token, openid, scope)
     * @param refreshToken
     * @return
     */
    public static JSONObject refreshAccessTokenByWeb(String refreshToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("appid", APP_ID_WEB);
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
        return HttpsClientUtils.doGet(URL_REFRESH_WEB, params);
    }

    /**
     * 检查access_token是否过期
     * (errcode, errmsg)
     * @param accessToken
     * @param openid
     * @return
     */
    public static JSONObject checkAccessTokenByWeb(String accessToken, String openid) {
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("openid", openid);
        return HttpsClientUtils.doGet(URL_CHECK_WEB, params);
    }

    /**
     * 获取个人信息
     * (openid, nickname, sex, province, city, country, headimgurl, privilege, unionid)
     * @param accessToken
     * @param openid
     * @return
     */
    public static JSONObject getInfoByWeb(String accessToken, String openid) {
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("openid", openid);
        params.put("lang", "zh_CN");
        return HttpsClientUtils.doGet(URL_INFO_WEB, params);
    }

    /**
     * 生成二维码用的url
     * @param state sessionID
     * @return 拼接好的URL
     */
    public static String getCode(String state) {
        StringBuilder url = new StringBuilder();
        url.append(CODE_WEB);
        url.append("?appid=" + APP_ID_WEB);
        String redirectUri = "https://zhuhaiaed.xyz:9090/login/callback";
        url.append("&redirect_uri=").append(URLEncoder.encode(redirectUri));
        url.append("&response_type=code");
        url.append("&scope=snsapi_userinfo");
        url.append("&state=").append(state);
        url.append("#wechat_redirect");
        return url.toString();
    }

}
