package br.com.beautypro.services.repository;

import java.util.Optional;

import br.com.beautypro.models.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.beautypro.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Page<User> findByActive(boolean active, Pageable pageable);

  Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

  Page<User> findByNameContainingIgnoreCaseAndActive(String name, boolean active, Pageable pageable);
}
