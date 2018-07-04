package com.modianli.power.domain.jpa;

import com.modianli.power.domain.jpa.support.PersistableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by dell on 2017/2/22.
 */
@Entity
@Table(name = "certification_image")
public class CertificationImage extends PersistableEntity<Long> {


  @OneToOne
  @JoinColumn(name = "certification_id")
  private  EnterpriseCertification enterpriseCertification;

  @Column(name = "certification_image_url", length = 100, nullable = false)
  private String imageUrl;

  public CertificationImage(EnterpriseCertification enterpriseCertification, String imageUrl) {
    this.enterpriseCertification = enterpriseCertification;
    this.imageUrl = imageUrl;
  }

  public CertificationImage() {

  }

  public EnterpriseCertification getEnterpriseCertification() {
    return enterpriseCertification;
  }

  public void setEnterpriseCertification(EnterpriseCertification enterpriseCertification) {
    this.enterpriseCertification = enterpriseCertification;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public String toString() {
    return "CertificationImage{" +
        "enterpriseCertification=" + enterpriseCertification +
        ", imageUrl='" + imageUrl + '\'' +
        '}';
  }
}
