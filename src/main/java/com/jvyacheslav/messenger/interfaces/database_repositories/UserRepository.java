package com.jvyacheslav.messenger.interfaces.database_repositories;

import com.jvyacheslav.messenger.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//repository for working with the database users
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPass(String username, String pass);
    User findByUsernameAndEmail(String username, String email);
    User findByUsername(String username);
    User findByEmail(String email);
}
