package com.fome.projectfome.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fome.projectfome.dto.ApiResponseDTO;
import com.fome.projectfome.dto.AuthenticationUserDTO;
import com.fome.projectfome.dto.Exception.HandlerException;
import com.fome.projectfome.dto.enums.EUserRole;
import com.fome.projectfome.dto.mappers.IdApiResponse;
import com.fome.projectfome.dto.mappers.TokenResponseDTO;
import com.fome.projectfome.model.User;
import com.fome.projectfome.service.AuthenticationService;
import com.fome.projectfome.service.UserService;
import com.fome.projectfome.service.ValidationTokenService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationService handleUser;

    @Autowired
    ValidationTokenService validationToken;

    @PostMapping(value= "/login", produces = {"application/json"})
    @ResponseBody
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ApiResponseDTO login(@RequestBody AuthenticationUserDTO user) {
        

            String token;
            token = handleUser.execute(user);

            if(token.equals("Permisson denied")){
                 return new ApiResponseDTO("Access denied");
            }

            return new ApiResponseDTO(new TokenResponseDTO(token));
       
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/get-role")
    public ApiResponseDTO getUserRole(HttpServletRequest request) {
        try{
            String role = validationToken.getRoleFromToken(request);
            return new ApiResponseDTO(role);
        } catch (Exception e){
            throw new HandlerException(e.getMessage());
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/create")
    public User createUserCtrl(@RequestBody User user/*,HttpServletRequest tokenAuth*/){//add token
        
        //String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        //if(userId.equals("Permisson denied")){throw new HandlerException("Permisson denied");}

        return userService.createUser(user);
        
    }

    @GetMapping(value = "/find-by-id/{id}")
    public User findById(@PathVariable int id, @RequestBody HttpServletRequest tokenAuth){

        String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        if(userId.equals("Permisson denied")){throw new HandlerException("Permisson denied");}

        return userService.findById(id);
        
    }


    @GetMapping(value = "/find-all")
    public List<User> findAllUsersCtrl( HttpServletRequest tokenAuth){

        String userId = validationToken.execute(tokenAuth, EUserRole.WAITER_ROLE);
       
        if(userId.equals("Permisson denied")){
                
            userId = validationToken.execute(tokenAuth, EUserRole.ATTENDANT_ROLE);
        
        }else if(userId.equals("Permisson denied")){
            
            userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        }else if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

        return userService.findAll();
        
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResponseDTO deleteUserCtrl (@PathVariable int id, HttpServletRequest tokenAuth) {

        String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);

        if(userId.equals("Permisson denied")){throw new HandlerException("Permisson denied");}

        userService.deleteUser(id);

        return new ApiResponseDTO(true, "Usuario deletado!");
        
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(path = "/update")
    public User  updateUserCtrl(@RequestBody User user, HttpServletRequest tokenAuth){
        
        String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        if(userId.equals("Permisson denied")){throw new HandlerException("Permisson denied");}

        return userService.updateUser(user);   
    }

    @GetMapping(path = "/find-id/{username}")
    public IdApiResponse findOrderCtrl(@PathVariable String username, HttpServletRequest tokenAuth){

        return userService.getIdByUsername(username);
    
    }
   
}
