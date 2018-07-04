package com.modianli.power.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gao on 17-4-21.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Sitemap {

  private String url;

  private LocalDateTime lastModified;
}
