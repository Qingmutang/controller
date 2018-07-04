package com.modianli.power.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @NotEmpty
    private String oldPassword;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20, message = "密码必须由6到20个字符组成")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "PasswordForm [oldPassword=" + oldPassword + ", newPassword=" + newPassword + "]";
    }

}
