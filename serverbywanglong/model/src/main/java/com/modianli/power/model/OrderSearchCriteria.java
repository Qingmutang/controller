/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author hansy
 */
public class OrderSearchCriteria implements Serializable {

    private LocalDateTime start;
    private LocalDateTime end;
    private String type;
    private String status;
    private String active;
 

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderSearchCriteria{" + "start=" + start + ", end=" + end + ", type=" + type + ", status=" + status + ", active=" + active + '}';
    }



}
