package com.georve.demoSpringBootApi.controller;

import com.georve.demoSpringBootApi.config.JwtTokenUtil;
import com.georve.demoSpringBootApi.error.CustomException;
import com.georve.demoSpringBootApi.error.ResourceAlreadyExists;
import com.georve.demoSpringBootApi.error.ResourceNotFoundException;
import com.georve.demoSpringBootApi.model.JwtResponse;
import com.georve.demoSpringBootApi.model.Speaker;
import com.georve.demoSpringBootApi.model.User;
import com.georve.demoSpringBootApi.services.JwtUserDetailsService;
import com.georve.demoSpringBootApi.services.SpeakerService;
import com.georve.demoSpringBootApi.services.UserService;
import com.georve.demoSpringBootApi.utils.ExceptionDefinitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired(required=true)
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    ResponseEntity<User> create(@RequestBody User user) {

        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        if (service.exists(user)) {
            throw new ResourceAlreadyExists(ExceptionDefinitions.ALREADY_EXIST);
        }
        return new ResponseEntity<>(service.saveOrUpdate(user), HttpStatus.CREATED);
    }


    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody JwtRequest user) {

        authenticate(user.getUsername(), user.getPassword());

      UserDetails found= jwtUserDetailsService.loadUserByUsername(user.getUsername());

      final String token = jwtTokenUtil.generateToken(found);


      return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }


    private void authenticate(String username, String password) throws CustomException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new CustomException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new CustomException("INVALID_CREDENTIALS");
        }
    }
}
