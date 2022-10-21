package com.xxx.crm.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Tony on 2016/8/23.
 */
public class LoginUserUtil {

    /**
     * 从cookie中获取userId
     * @param request
     * @return
     */
    public static int releaseUserIdFromCookie(HttpServletRequest request) {
        String userIdString = CookieUtil.getCookieValue(request, "userIdStr");
        if (StringUtils.isBlank(userIdString)) {
            return 0;
        }
        // 使用base64解析前端传递过来的 id
        Integer userId = UserIDBase64.decoderUserID(userIdString);
        return userId;
    }
}
