package br.com.beautypro.services;

import br.com.beautypro.models.Client;
import br.com.beautypro.models.User;
import br.com.beautypro.payload.request.UserRequest;
import br.com.beautypro.services.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

}
