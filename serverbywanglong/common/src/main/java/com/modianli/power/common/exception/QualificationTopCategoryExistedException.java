package com.modianli.power.common.exception;

/**
 * Created by dell on 2017/2/25.
 */
public class QualificationTopCategoryExistedException extends RuntimeException{
  private static final long serialVersionUID = 1L;
  private String qualificationTopName;

  public String getQualificationTopName() {
    return qualificationTopName;
  }

  public void setQualificationTopName(String qualificationTopName) {
    this.qualificationTopName = qualificationTopName;
  }
  public QualificationTopCategoryExistedException(String qualificationTopName) {
    this.qualificationTopName = qualificationTopName;
  }
}
