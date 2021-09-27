package com.taxi.services;

import com.taxi.dto.UserDTO;
import com.taxi.repositories.UserRepository;
import com.taxi.tools.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    @Autowired
    public UserService(UserRepository repo)
    {
        this.repo = repo;
    }

    public UserDTO findUserByUserName(String userName) {
        return repo.findByUserName(userName).orElse(null);
    }

    public UserDTO findUserById(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<UserDTO> listAll()
    {
        return (List<UserDTO>) repo.findAll();
    }

    public List<UserDTO> listUsers()
    {
        return repo.findByRoles("ROLE_USER");
    }

    public void save(UserDTO user) {
        repo.save(user);
    }

    public UserDTO get(Integer id) throws UserNotFoundException {
        Optional<UserDTO> result = repo.findById(id);
        if (result.isPresent())
            return result.get();
        else
            throw new UserNotFoundException("Could not find any users with ID " + id);

    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any users with ID " + id);
        }
        repo.deleteById(id);
    }
}
