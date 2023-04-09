package br.com.beautypro.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;

import br.com.beautypro.models.Address;
import br.com.beautypro.payload.request.*;
import br.com.beautypro.services.PasswordResetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.beautypro.models.ERole;
import br.com.beautypro.models.Role;
import br.com.beautypro.models.User;
import br.com.beautypro.payload.response.JwtResponse;
import br.com.beautypro.payload.response.MessageResponse;
import br.com.beautypro.services.repository.RoleRepository;
import br.com.beautypro.services.repository.UserRepository;
import br.com.beautypro.security.jwt.JwtUtils;
import br.com.beautypro.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
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

  @Autowired
  PasswordResetService passwordResetService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        roles));
  }

  @PostMapping("/create-user")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Usuário já cadastrado!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Email já cadastrado!"));
    }

    Address address = new Address(signUpRequest.getStreet(), signUpRequest.getNumber(), signUpRequest.getComplement(), signUpRequest.getDistrict(), signUpRequest.getCity(), signUpRequest.getState(), signUpRequest.getCep());

    // Create new user's account
    User user = new User();
    user.setUsername(signUpRequest.getUsername());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(encoder.encode(signUpRequest.getPassword()));
    user.setName(signUpRequest.getName());
    user.setAddress(address);

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        if ("admin".equals(role)) {
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);
        } else {
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Usuário cadastrado com sucesso!"));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO) throws MessagingException {


    if (userRepository.existsByEmail(passwordResetDTO.getEmail())) {

      passwordResetService.sendEmailResetPassword(passwordResetDTO.getEmail());
      return ResponseEntity.ok()
              .body(new MessageResponse("Email de recuperação enviado com sucesso!"));
    } else {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Dados inválidos!"));
    }
  }

  @PostMapping("/validate-token")
  public ResponseEntity<?> validatePasswordResetToken(@Valid @RequestBody PasswordResetTokenDTO tokenDTO) {

    System.out.println(tokenDTO.getEmail());
    System.out.println(tokenDTO.getToken());
    boolean isValidToken = passwordResetService.validatePasswordResetToken(tokenDTO.getEmail(), tokenDTO.getToken());
    System.out.println(isValidToken);
    if (isValidToken) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/new-password")
  public ResponseEntity<?> resetPassword(@RequestBody NewPasswordDTO passwordDTO) {
    boolean success = passwordResetService.saveNewPassword(passwordDTO.getEmail(), passwordDTO.getNewPassword(), passwordDTO.getToken());

    if (success) {
      return ResponseEntity.ok().body(new MessageResponse("Senha alterada com sucesso!"));
    } else {
      return ResponseEntity.badRequest().body(new MessageResponse("Erro ao alterar a senha. Tente novamente!"));
    }
  }

}