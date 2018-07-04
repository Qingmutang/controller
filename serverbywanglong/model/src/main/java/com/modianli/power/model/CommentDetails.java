package com.modianli.power.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dell on 2017/2/24.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CommentDetails implements Serializable {

  private static final long serialVersionUID = 3213232812274677998L;

  private String description;

  private String userUsername;

  private LocalDateTime createdDate;

  private String url;

  private String enterpriseName;

  private Boolean active;

  private Long id;




}
