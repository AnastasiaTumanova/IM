package org.IM2.magazine.service;

import org.IM2.magazine.dto.UserDTO;
import org.IM2.magazine.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean save (UserDTO userDTO);
    void save (User user);
    List<UserDTO> getAll();

    User findByName(String name);

    void updateProfile(UserDTO dto);


}
