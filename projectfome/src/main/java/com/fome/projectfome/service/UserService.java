package com.fome.projectfome.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fome.projectfome.dao.UserRepository;
import com.fome.projectfome.dto.mappers.IdApiResponse;
import com.fome.projectfome.model.User;
import com.fome.projectfome.utils.HashManagerUtils;

@Transactional
@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    public User createUser(User newUser){

        newUser.setPassword(HashManagerUtils.generateCrypt(newUser.getPassword()));
        userRepository.save(newUser);

        return newUser;
    }

    public User findById(int id){

        return userRepository.findById(id);
 
     }
 
     // mostrar todos os produtos
     public List<User> findAll(){
 
         return userRepository.findAll();
     }

    public String deleteUser(int id){

        userRepository.delete(userRepository.findById(id)); 

        return "user deleted successfully";
    }
   
    public User updateUser(User user){

        User newUser = userRepository.findById(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setUserRole(user.getUserRole());
        newUser.setCpf(user.getCpf());
        newUser.setSalary(user.getSalary());
        newUser.setName(user.getName());

        userRepository.save(newUser);

        return newUser;

    }

    public User updateTablePassword(int id, String password){

        User newUser = userRepository.findById(id);

        newUser.setPassword(HashManagerUtils.generateCrypt(password));

        userRepository.save(newUser);

        return newUser;

    }

    public IdApiResponse getIdByUsername(String userName){

        Iterable<User> usersFound = userRepository.findByUsername(userName);
        
        User userFound = new User();

        for(User user : usersFound){
            userFound = user;
        }

        int idUser = userFound.getId();

        return new IdApiResponse(true, idUser);
    }


}