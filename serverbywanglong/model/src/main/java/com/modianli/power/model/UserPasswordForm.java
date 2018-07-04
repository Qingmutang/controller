package com.modianli.power.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    private SmsCaptchaRequest captchaResponse;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20, message = "密码必须由6到20个字符组成")
    private String newPassword;

}
