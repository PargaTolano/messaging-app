package com.parga.messagingapp.user;

import com.google.common.collect.Lists;
import com.parga.messagingapp.dto.UpdateUserDTO;
import com.parga.messagingapp.exception.FieldNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers( int page, int size){
        Pageable paging = PageRequest.of(page, size);
        return userRepository.findAll(paging).getContent();
    }

    public List<User> searchUsersByUsername(String username, int page, int size){
        Pageable paging = PageRequest.of(page, size);
        Page<User> pageUser;
        if(username == null){
            pageUser = userRepository.findAll(paging);
        }else {
            pageUser = userRepository.findByUsernameContainingIgnoreCase(username, paging);
        }

        return pageUser.getContent();
    }

    public List<User> getAllById(List<String> ids){
        Iterable<User> iterable = userRepository.findAllById(ids);
        return Lists.newArrayList(iterable);
    }

    public User getUserById(String id) throws Exception{

        if( id == null || id.isEmpty() )
            throw new FieldNotFoundException("id must not be null or empty");

        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }

    public User createUser(User user) throws Exception{

        if( user.getUsername() == null || user.getUsername().isEmpty())
            throw new FieldNotFoundException("Username must not be null or empty");

        if( user.getPassword() == null || user.getPassword().isEmpty())
            throw new FieldNotFoundException("Password must not be null or empty");

        return  userRepository.insert(user);
    }

    public User updateUser(String id, UpdateUserDTO user) throws Exception{

        if( id == null || id.isEmpty() )
            throw new FieldNotFoundException("id must not be null or empty");

        if (user.getUsername() == null || user.getUsername().isEmpty())
            throw new FieldNotFoundException("username must not be null or empty");

        User userToUpdate = userRepository.findById(id).get();
        if( userToUpdate == null )
            return null;

        userToUpdate.setUsername(user.getUsername());

        return userRepository.save(userToUpdate);
    }

    public void deleteUser(String id) throws Exception{

        if( id == null || id.isEmpty() )
            throw new FieldNotFoundException("id must not be null or empty");

        User user = userRepository.findById(id).orElse(null);

        if(user == null)
            throw new FieldNotFoundException("user must not be null or empty");

        userRepository.deleteById(id);
    }
}
