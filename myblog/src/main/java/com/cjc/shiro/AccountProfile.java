package com.cjc.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2021/2/22
 * Time: 18:04
 * To change this template use File | Settings | File Templates.
 **/
@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;
}
