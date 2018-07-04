package com.modianli.power.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String mobileNumber;

    private String email;

    private String headImage;


}
