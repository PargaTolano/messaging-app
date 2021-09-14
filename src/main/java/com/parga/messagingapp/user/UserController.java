package com.parga.messagingapp.user;

import com.parga.messagingapp.dto.UpdateUserDTO;
import com.parga.messagingapp.dto.ResponseDTO;
import com.parga.messagingapp.exception.FieldNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<ResponseDTO> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        try {
            List users = userService.getUsers(page, size);
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Users retrieved successfully", users), HttpStatus.OK);
        }  catch (Exception e){
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ResponseDTO>  getUser(@PathVariable String id){
        try{
            User user = userService.getUserById(id);
            return new ResponseEntity(new ResponseDTO("User retrieved successfully", user), HttpStatus.OK);
        } catch ( FieldNotFoundException e){
            return new ResponseEntity(new ResponseDTO(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity(new ResponseDTO("", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/search")
    public ResponseEntity<ResponseDTO> searchUsersByUsername(
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        try{
            List users = userService.searchUsersByUsername( username, page, size);
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Search retrieved successfully", users), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody User user){
        try{
            User created = userService.createUser(user);
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Created user successfully", created), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable String id, @RequestBody UpdateUserDTO user){
        try{
            User updated = userService.updateUser(id,user);
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Updated user successfully", updated), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String id){
        try{
            userService.deleteUser(id);
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("Deleted User Successfully", null), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<ResponseDTO>(new ResponseDTO("", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
