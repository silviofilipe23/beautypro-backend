package br.com.beautypro.security.services;

import br.com.beautypro.models.Supplier;
import br.com.beautypro.payload.response.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.beautypro.models.User;
import br.com.beautypro.services.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

  public PageableResponse listUsers(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<User> userRep = userRepository.findAll(pageable);
    List<User> userDTO = userRep.stream().collect(Collectors.toList());

    PageableResponse response = new PageableResponse();

    response.setData(userDTO);
    response.setPages(userRep.getTotalPages());
    response.setSize(size);
    response.setTotal(userRep.getTotalElements());
    return response;
  }

  public PageableResponse listUsersFilterNameAndActive(int page, int size, String name, boolean active) {
    Pageable pageable = PageRequest.of(page, size);
    Page<User> userResponse = userRepository.findByNameContainingIgnoreCaseAndActive(name, active, pageable);
    List<User> userList = userResponse.stream()
            .collect(Collectors.toList());

    PageableResponse response = new PageableResponse();

    response.setData(userList);
    response.setPages(userResponse.getTotalPages());
    response.setSize(size);
    response.setTotal(userResponse.getTotalElements());

    return response;
  }

  public PageableResponse listUsersFilterActive(int page, int size, boolean active) {
    Pageable pageable = PageRequest.of(page, size);
    Page<User> userResponse = userRepository.findByActive(active,  pageable);
    List<User> userList = userResponse.stream()
            .collect(Collectors.toList());

    PageableResponse response = new PageableResponse();

    response.setData(userList);
    response.setPages(userResponse.getTotalPages());
    response.setSize(size);
    response.setTotal(userResponse.getTotalElements());

    return response;
  }

  public PageableResponse listUsersFilterName(int page, int size, String name) {
    Pageable pageable = PageRequest.of(page, size);
    Page<User> userResponse = userRepository.findByNameContainingIgnoreCase(name,  pageable);
    List<User> userList = userResponse.stream()
            .collect(Collectors.toList());

    PageableResponse response = new PageableResponse();

    response.setData(userList);
    response.setPages(userResponse.getTotalPages());
    response.setSize(size);
    response.setTotal(userResponse.getTotalElements());

    return response;
  }




  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

}
