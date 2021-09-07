package com.parga.messagingapp.user;

import com.google.common.collect.Lists;
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

    public User getUserById(String id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }

    public User createUser(User user){
        return  userRepository.insert(user);
    }

    public User updateUser(String id, User user) {

        User userToUpdate = userRepository.findById(id).get();
        if( userToUpdate == null )
            return null;

        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());

        return userRepository.save(userToUpdate);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
