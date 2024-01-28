package org.IM2.magazine.dao;

import org.IM2.magazine.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByUsername(String username);

}
