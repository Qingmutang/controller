/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modianli.power.api.user;

import com.modianli.power.common.service.UserService;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.BooleanValue;
import com.modianli.power.model.RoleDetails;
import com.modianli.power.model.RoleForm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

@RestController()
@RequestMapping(value = ApiConstants.URI_API_MGT)
@Slf4j
public class RoleMgtController {

    @Inject
    private UserService userService;

    @RequestMapping(value = {ApiConstants.URI_ROLES}, method = RequestMethod.GET)
    @ResponseBody
    public Page<RoleDetails> allRoles(
            @RequestParam(value = "q",required = false) String q,
            @RequestParam("active") boolean active,
            @PageableDefault(page = 0, size = 10) Pageable page) {

        if (log.isDebugEnabled()) {
            log.debug("fetch all role...@ q:" + q + ", page:" + page);
        }

        Page<RoleDetails> users = userService.findRoles(q, active, page);

        if (log.isDebugEnabled()) {
            log.debug("count of users @" + users.getTotalElements());
            log.debug(" users @" + users.getContent());
        }

        return users;
    }

    @RequestMapping(value = {ApiConstants.URI_ROLES + "/{id}/permissions"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<String>> getPermissionOfRole(@PathVariable("id") Long id) {
        if (log.isDebugEnabled()) {
            log.debug("udpate user @" + id);
        }

        RoleDetails roleDetails = userService.findRoleById(id);

        List<String> permissions = userService.findGrantedPermissionNamesByRoleName(roleDetails.getName());

        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    @RequestMapping(value = {ApiConstants.URI_ROLES + "/{id}/permissions"}, method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> savePermissionsOfRole(@PathVariable("id") Long id, @RequestBody String[] permissions) {
        if (log.isDebugEnabled()) {
            log.debug("udpate user @" + id + ", permisisons length@" + permissions.length + ", @" + permissions);
        }

        RoleDetails roleDetails = userService.findRoleById(id);
        final String roleName = roleDetails.getName();

        List<String> grantedPerms = userService.findGrantedPermissionNamesByRoleName(roleName);

        List<String> added = new ArrayList<>();

        List<String> removed = new ArrayList<>();

        //found new added
        for (String newPerm : permissions) {
            boolean found = false;

            for (String existedPerm : grantedPerms) {
                if (newPerm.equals(existedPerm)) {
                    found = true;
                    break;
                }
            }

            if (found == false) {
                added.add(newPerm);
            }
        }

        //found removed
        for (String existedPerm : grantedPerms) {
            boolean found = false;

            for (String newPerm : permissions) {
                if (newPerm.equals(existedPerm)) {
                    found = true;
                    break;
                }
            }

            if (found == false) {
                removed.add(existedPerm);
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("added @" + added);
            log.debug("removed @" + removed);
        }

        if (!added.isEmpty()) {
            userService.grantPermissions(roleName, added.toArray(new String[added.size()]));
        }

        if (!removed.isEmpty()) {
            userService.revokePermissions(roleName, removed.toArray(new String[removed.size()]));
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = {ApiConstants.URI_ROLES}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> createRole(
            @RequestBody RoleForm form,
            UriComponentsBuilder uriComponentsBuilder) {
        if (log.isDebugEnabled()) {
            log.debug("save user data @" + form);
        }

        RoleDetails saved = userService.saveRole(form);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path(ApiConstants.URI_API_MGT + ApiConstants.URI_ROLES + "/{id}")
                .buildAndExpand(saved.getId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = {ApiConstants.URI_ROLES + "/{id}"}, method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> updateRole(@PathVariable("id") Long id, @RequestBody RoleForm form) {
        if (log.isDebugEnabled()) {
            log.debug("udpate user @" + id + " data @" + form);
        }

        userService.updateRole(id, form);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = {ApiConstants.URI_ROLES + "/{id}"}, method = RequestMethod.PUT, params = "action=UNLOCK")
    @ResponseBody
    public ResponseEntity<Void> unlockRole(@PathVariable("id") Long id) {
        if (log.isDebugEnabled()) {
            log.debug("unlock roler @" + id);
        }

        userService.activateRole(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = {ApiConstants.URI_ROLES + "/{id}"}, method = RequestMethod.DELETE, params = "!action")
    @ResponseBody
    public ResponseEntity<Void> deactivate(@PathVariable("id") Long id) {
        if (log.isDebugEnabled()) {
            log.debug("udpate user @" + id);
        }

        userService.deactivateRole(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = {ApiConstants.URI_ROLES + "/{id}"}, method = RequestMethod.GET, params = "!action")
    @ResponseBody
    public ResponseEntity<RoleDetails> getRole(@PathVariable("id") Long id) {

        if (log.isDebugEnabled()) {
            log.debug("get user data by id @" + id);
        }

        RoleDetails user = userService.findRoleById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = {ApiConstants.URI_ROLES + "/{name}"}, method = RequestMethod.GET, params = "action=CHECK_EXISTENCE")
    @ResponseBody
    public ResponseEntity<BooleanValue> getRole(@PathVariable("name") String name) {

        if (log.isDebugEnabled()) {
            log.debug("get user data by name@" + name);
        }

        RoleDetails user = userService.findRoleByName(name);

        return new ResponseEntity<>(new BooleanValue(true), HttpStatus.OK);
    }

}
