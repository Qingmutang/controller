/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CategorizedPermission implements Serializable {

    private String category;
    private List<String> permissions = new ArrayList<>();

    public CategorizedPermission() {
    }

    public CategorizedPermission(String category, List<String> permissions) {
        this.category = category;
        this.permissions = permissions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "CategorizedPermission{" + "category=" + category + ", permissions=" + permissions + '}';
    }
}
