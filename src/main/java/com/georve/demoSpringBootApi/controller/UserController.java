package com.georve.demoSpringBootApi.controller;

import com.georve.demoSpringBootApi.model.ERole;
import com.georve.demoSpringBootApi.model.Role;
import com.georve.demoSpringBootApi.payload.request.LoginRequest;
import com.georve.demoSpringBootApi.payload.request.SignupRequest;
import com.georve.demoSpringBootApi.error.CustomException;
import com.georve.demoSpringBootApi.error.ResourceAlreadyExists;
import com.georve.demoSpringBootApi.model.User;
import com.georve.demoSpringBootApi.payload.response.JwtResponse;
import com.georve.demoSpringBootApi.security.jwt.JwtUtils;
import com.georve.demoSpringBootApi.security.services.UserDetailsImpl;
import com.georve.demoSpringBootApi.services.RoleService;
import com.georve.demoSpringBootApi.services.UserService;
import com.georve.demoSpringBootApi.utils.ExceptionDefinitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    ResponseEntity<User> create(@Valid @RequestBody SignupRequest signUpRequest) {

        if (service.existsByUsername(signUpRequest.getUsername())) {
            throw new ResourceAlreadyExists(ExceptionDefinitions.ALREADY_EXIST);
        }

        if (service.existsByEmail(signUpRequest.getEmail())) {
            throw new ResourceAlreadyExists(ExceptionDefinitions.ALREADY_EXIST);
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();

        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = this.roleService.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = this.roleService.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = this.roleService.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = this.roleService.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        return new ResponseEntity<>(service.saveOrUpdate(user), HttpStatus.CREATED);
    }


    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
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
