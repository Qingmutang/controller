package com.modianli.power.common.exception;

/**
 * Created by dell on 2017/2/25.
 */
public class QualificationMiddleCategoryExistedException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private String qualificationMiddleName;

  public String getQualificationMiddleName() {
    return qualificationMiddleName;
  }

  public void setQualificationMiddleName(String qualificationMiddleName) {
    this.qualificationMiddleName = qualificationMiddleName;
  }

  public QualificationMiddleCategoryExistedException(String qualificationMiddleName) {
    this.qualificationMiddleName = qualificationMiddleName;
  }
}
