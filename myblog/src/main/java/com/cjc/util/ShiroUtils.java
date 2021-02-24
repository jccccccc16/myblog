package com.cjc.util;

import com.cjc.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2021/2/23
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 **/
public class ShiroUtils {

    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}
