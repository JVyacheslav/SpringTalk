package com.jvyacheslav.messenger.interfaces;

import com.jvyacheslav.messenger.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPass(String username, String pass);
    User findByUsernameAndEmail(String username, String email);
    User findByUsername(String username);
    User findByEmail(String email);
}
