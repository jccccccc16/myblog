package com.cjc.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2021/2/23
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 **/
@Data
public class LoginVO implements Serializable {

    @NotBlank(message = "用户名不能为空")
    private String username;

    private String password;
}
