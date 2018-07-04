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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SignupForm implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  @NotEmpty
  @Size(min = 6, message = "用户名必须由 6 到 20 位字符组成")
  private String username;

  @NotNull
  @NotEmpty
  @Size(min = 6, message = "密码必须由 6 到 20 位字符组成")
  private String password;

  @Valid
  private SmsCaptchaRequest captchaResponse;


}
