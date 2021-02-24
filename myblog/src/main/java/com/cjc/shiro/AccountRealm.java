package com.cjc.shiro;

import com.cjc.entity.User;
import com.cjc.service.UserService;
import com.cjc.util.JwtUtils;
import jdk.nashorn.internal.parser.Token;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2021/2/22
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 **/
@Component
public class AccountRealm extends AuthorizingRealm {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(AccountRealm.class);

    /**
     * 授權
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 認證
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        logger.info("--------------------- 进入AuthenticationInfo() ");

        JwtToken jwtToken = (JwtToken) authenticationToken;

        String subject = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();


        User user = userService.getById(Long.valueOf(subject));

        if(user == null){
            throw new UnknownAccountException("账号不存在");
        }
        if(user.getStatus()==-1){
            throw new LockedAccountException("账号已被冻结");
        }

        AccountProfile accountProfile = new AccountProfile();
        BeanUtils.copyProperties(user,accountProfile);

        return new SimpleAuthenticationInfo(accountProfile,jwtToken.getCredentials(),getName());
    }
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

}
