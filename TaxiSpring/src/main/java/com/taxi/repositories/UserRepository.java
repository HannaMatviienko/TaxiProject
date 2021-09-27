package com.taxi.repositories;

import com.taxi.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends CrudRepository<UserDTO, Integer> {
    Long countById(Integer id);
    Optional<UserDTO> findByUserName(String userName);
    List<UserDTO> findByRoles(String roles);
}
