package com.sebn.spring.login.controllers;

import com.sebn.spring.login.models.ERole;
import com.sebn.spring.login.models.Role;
import com.sebn.spring.login.models.User;
import com.sebn.spring.login.payload.request.LoginRequest;
import com.sebn.spring.login.payload.request.SignupRequest;
import com.sebn.spring.login.payload.response.MessageResponse;
import com.sebn.spring.login.payload.response.UserInfoResponse;
import com.sebn.spring.login.repository.RoleRepository;
import com.sebn.spring.login.repository.UserRepository;
import com.sebn.spring.login.security.jwt.JwtUtils;
import com.sebn.spring.login.security.services.UserDetailsImpl;
import com.sebn.spring.login.models.*;
import com.sebn.spring.login.payload.request.*;
import com.sebn.spring.login.payload.response.*;
import com.sebn.spring.login.repository.*;
import com.sebn.spring.login.security.jwt.*;
import com.sebn.spring.login.security.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.crypto.password.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        Optional<String> roleOpt = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getName(),
                        roleOpt.orElse(null)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erreur: Cet adresse courriel est déjà utilisé!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName());

        Set<String> strRoles = signUpRequest.getRole();
        List<Role> roles = new ArrayList<>();

        if (strRoles == null) {
            Role managerRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                    .orElseThrow(() -> new RuntimeException("Error: Le rôle est introuvable."));
            roles.add(managerRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Erreur: Le rôle est introuvable."));
                        roles.add(adminRole);

                        break;
                    case "rh":
                        Role rhRole = roleRepository.findByName(ERole.ROLE_RH)
                                .orElseThrow(() -> new RuntimeException("Erreur: Le rôle est introuvable."));
                        roles.add(rhRole);

                        break;
                    default:
                        Role managerRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Erreur: Le rôle est introuvable."));
                        roles.add(managerRole);
                }
            });
            userRepository.save(user);

        }

        user.setRole(roles.get(0));
        return ResponseEntity.ok(new MessageResponse("Enregistré avec succès!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("Votre session a été fermée!"));
    }
}
