package com.example.wechat.Utils;

import android.text.TextUtils;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/11 0011 16:35
 */

public class StringUtils {

    //用户名的规则：字母开头，后面任意，总长度3-20
    public static final String USERNAME_REGEX = "^[a-zA-Z]{1}\\w{2,19}$";
    //密码的规则：只能是数字，总长度为3-20
    public static final String PWD_REGEX = "^[0,9]{3,20}$";

    //定义一个校验用户名的方法
    public static boolean checkUsername (String username) {
        if (TextUtils.isEmpty(username)) {
            return false;
        }

        return username.matches(USERNAME_REGEX);
    }

    //定义一个校验密码的方法
    public static boolean checkPwd(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            return false;
        }
        return pwd.matches(PWD_REGEX);
    }
}
