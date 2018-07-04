/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.model;

public class BooleanValue {

  private boolean result = false;

  public BooleanValue() {
  }

  public BooleanValue(boolean result) {
	this.result = result;
  }

  public boolean isResult() {
	return result;
  }

  public void setResult(boolean result) {
	this.result = result;
  }

  @Override
  public String toString() {
	return "BooleanValue{" + "result=" + result + '}';
  }

}
