package com.modianli.power.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SigninForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @Valid
    private ImgCaptchaRequest captchaResponse;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ImgCaptchaRequest getCaptchaResponse() {
        return captchaResponse;
    }

    public void setCaptchaResponse(ImgCaptchaRequest captchaResponse) {
        this.captchaResponse = captchaResponse;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupForm{" + "username=" + username + ", password=" + password + ", captchaResponse=" + captchaResponse + '}';
    }

}
