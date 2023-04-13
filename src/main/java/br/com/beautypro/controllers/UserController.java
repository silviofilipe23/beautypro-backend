package br.com.beautypro.controllers;

import br.com.beautypro.models.Address;
import br.com.beautypro.models.User;
import br.com.beautypro.payload.request.UserRequest;
import br.com.beautypro.payload.response.MessageResponse;
import br.com.beautypro.payload.response.PageableResponse;
import br.com.beautypro.services.repository.UserRepository;
import br.com.beautypro.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableResponse> getUsers(@Valid @RequestParam int page, @RequestParam int size) {
        PageableResponse users = userDetailsService.listUsers(page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserDetails(@Valid @PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@Valid @PathVariable Long userId, @RequestBody UserRequest userRequest) {

        Optional<User> userExists = userRepository.findById(userId);

        if (userExists.isPresent()) {
            User user = userExists.get();

            Address address = new Address();

            address.setCep(userRequest.getCep());
//            address.setCity(userRequest.getCity());
            address.setComplement(userRequest.getComplement());
            address.setDistrict(userRequest.getDistrict());
            address.setNumber(userRequest.getNumber());
            address.setStreet(userRequest.getStreet());
//            address.setState(userRequest.getState());

            user.setAddress(address);
            user.setName(userRequest.getName());
            user.setEmail(user.getEmail());
            user.setPasswordResetToken(null);
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteClient(@Valid @PathVariable("id") Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            userDetailsService.deleteUser(id);
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Usuário inativado com sucesso!"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
