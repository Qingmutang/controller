/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.persistence.repository.jpa;

import com.modianli.power.domain.jpa.Role;

import java.util.List;

public interface PermissionRepositoryCustom {

  public List<Role> findByActiveAndFetchResource(boolean active);


}
