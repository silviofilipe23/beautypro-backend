package br.com.beautypro.dataloader;

import br.com.beautypro.models.ERole;
import br.com.beautypro.models.Role;
import br.com.beautypro.models.User;
import br.com.beautypro.repository.RoleRepository;
import br.com.beautypro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public void run(String... args) {

//        Role roleAdmin = new Role();
//        roleAdmin.setName(ERole.ROLE_ADMIN);
//        roleRepository.save(roleAdmin);
//
//        Role roleUser = new Role();
//        roleUser.setName(ERole.ROLE_USER);
//        roleRepository.save(roleUser);
//        User userAdmin = new User();
//        userAdmin.setPassword("$2a$10$o5lKkrbmtrb237UbJ573fOLZ2414FK1kb0xTci96BabFYH20B.qwa");
//        userAdmin.setEmail("silvio.dionizio23@gmail.com");
//        userAdmin.setUsername("silvio");
//        userAdmin.setName("SILVIO FILIPE DIONIZIO JUNIOR");
//
//        Set<Role> roles = new HashSet<>();
//        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//        roles.add(userRole);
//
//        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//        roles.add(adminRole);
//
//        userAdmin.setRoles(roles);
//
//        userRepository.save(userAdmin);

    }
}
