package com.modianli.power.api.user;

import com.modianli.power.domain.jpa.UserAccount;
import com.modianli.power.model.ApiConstants;
import com.modianli.power.model.AuthenticationRequest;
import com.modianli.power.model.AuthenticationToken;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = ApiConstants.URI_API)
public class AuthenticationController {

  @Inject
  private AuthenticationManager authenticationManager;

  @Inject
  private UserDetailsService userDetailsService;

  public AuthenticationController() {
  }

  @RequestMapping(value = "/authenticate", method = {RequestMethod.POST})
  public AuthenticationToken authorize(@RequestBody AuthenticationRequest authenticationRequest,
									   HttpServletRequest request) {

	final UsernamePasswordAuthenticationToken token
		= new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
												  authenticationRequest.getPassword());

	final UserAccount details = (UserAccount) this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

	if (!details.isStaff()) {
	  throw new AccessDeniedException("");
	}

	final Authentication authentication = this.authenticationManager.authenticate(token);

	SecurityContextHolder.getContext().setAuthentication(authentication);

	final HttpSession session = request.getSession(true);

	session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
						 SecurityContextHolder.getContext());

	final Map<String, Boolean> roles = new HashMap<>();

	details.getAuthorities().stream().forEach((authority) -> {
	  roles.put(authority.toString(), Boolean.TRUE);
	});

	return new AuthenticationToken(details.getUsername(), roles, session.getId());
  }
}
