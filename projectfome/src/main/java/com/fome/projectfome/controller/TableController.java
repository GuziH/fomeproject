package com.fome.projectfome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fome.projectfome.dto.ApiResponseDTO;
import com.fome.projectfome.dto.Exception.HandlerException;
import com.fome.projectfome.dto.enums.EUserRole;
import com.fome.projectfome.model.Order;
import com.fome.projectfome.model.User;
import com.fome.projectfome.service.TableService;
import com.fome.projectfome.service.ValidationTokenService;
import com.fome.projectfome.dto.mappers.Password;
import com.fome.projectfome.dto.mappers.DoubleApiResponse;
import com.fome.projectfome.dto.mappers.ListProductApiResponse;
import com.fome.projectfome.dto.mappers.DateApiResponse;

import javax.servlet.http.HttpServletRequest;


import java.util.List;

@RestController
@RequestMapping("/table")
public class TableController {

    @Autowired
    TableService tableService;

    @Autowired
    ValidationTokenService validationToken;

    
    // Fazer um pedido
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path = "/make-order")
    public ApiResponseDTO makeOrderCtrl(@RequestBody Order order){

        tableService.controllerOrder(order);
       
        return  new ApiResponseDTO(true, "Pedido Finalizado Com Sucesso!");
    
    }

    // achar um pedido especifico
    @GetMapping(path = "/find-order/{id}")
    public Order findOrderCtrl(@PathVariable int id, HttpServletRequest tokenAuth){

        String userId = validationToken.execute(tokenAuth, EUserRole.KITCHEN_ROLE);
        
        if(userId.equals("Permisson denied")){
            
            userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        }else if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

        return tableService.findById(id);
    
    }

    // find all
    @GetMapping(path = "/find-all-orders/find-all")
    public Iterable<Order> findAllOrdersCtrl(HttpServletRequest tokenAuth){


        String userId = validationToken.execute(tokenAuth, EUserRole.KITCHEN_ROLE);
        
        if(userId.equals("Permisson denied")){
            
            userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        }else if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }
    
        return tableService.findAll();
    
    }

    @GetMapping(path = "/find-new-orders/find-all")
    public Iterable<Order> findNewProductOrdersCtrl(HttpServletRequest tokenAuth){


		String userId = validationToken.execute(tokenAuth, EUserRole.KITCHEN_ROLE);
        
        if(userId.equals("Permisson denied")){
            
            userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        }else if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }
        
        return tableService.findNewOrders();
    
    }

    @PostMapping(path = "/find-table-orders/find-all")
    public List<Order> findTableOrdersCtrl(@RequestBody User table, HttpServletRequest tokenAuth){

        String userId = validationToken.execute(tokenAuth, EUserRole.ATTENDANT_ROLE);
        
        if(userId.equals("Permisson denied")){
            
            userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        }else if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

        return tableService.findTableOrders(table);
    
    }


    // ATUALIZAR UM PEDIDO CASO NECESSARIO
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(path = "/update-order")
    public Order updateOrderCtrl( @RequestBody Order order, HttpServletRequest tokenAuth){
    
        String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        if(userId.equals("Permisson denied")){

            throw new HandlerException("Permisson denied");
        }

        return tableService.updateOrder(order);
    
    }

    //PAGAR E FINALIZAR UM PEDIDO
    @PostMapping(path = "payorder")
    public ApiResponseDTO payOrderCtrl(@RequestBody Order order,  HttpServletRequest tokenAuth){
        
        String userId = validationToken.execute(tokenAuth, EUserRole.ATTENDANT_ROLE);
        
        if(userId.equals("Permisson denied")){
            
            userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        }else if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }
         
        return tableService.payOrder(order);
    }

    // deletar um pedido caso tenha sido errado
    @DeleteMapping(path = "/delete-order/{id}")
    public Order deleteOrderCtrl(@PathVariable int id, HttpServletRequest tokenAuth){

        //kit
        String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);

        if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

        return tableService.deleteOrder(id);

    }

    // gerar a senha para a mesa   
    @GetMapping(value="/gen-password/{id}")
    public Password genPasswordCtrl(@PathVariable int id, HttpServletRequest tokenAuth) {
        
        String userId = validationToken.execute(tokenAuth, EUserRole.WAITER_ROLE);
       
        if(userId.equals("Permisson denied")){
                
            userId = validationToken.execute(tokenAuth, EUserRole.ATTENDANT_ROLE);
        
        }else if(userId.equals("Permisson denied")){
            
            userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        }else if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

        String genPasssword = tableService.genPassword(id);
        
        return new Password(true, genPasssword);
    
    }


    // mudar o STATUS PARA TRUE significa que o pedido esta pronto a ser levado a mesa
    @GetMapping(value="/finish-order/{id}")
    public ApiResponseDTO finishOrderCtrl(@PathVariable int id, HttpServletRequest tokenAuth) {
        
        String userId = validationToken.execute(tokenAuth, EUserRole.KITCHEN_ROLE);
        
        if(userId.equals("Permisson denied")){
            
            userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        }else if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }
        
        return tableService.finishOrder(id);
    }

    @PostMapping(value="/balance-sheet")
    public DoubleApiResponse showBalanceSheetCtrl(@RequestBody DateApiResponse date,HttpServletRequest tokenAuth) {
        
        String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

       return new DoubleApiResponse(true, tableService.showBalanceSheet(date));

    }


    @GetMapping(value="/most-sold-products")
    public List<ListProductApiResponse> mostSoldProductsCtrl(HttpServletRequest tokenAuth){
        
        String userId = validationToken.execute(tokenAuth, EUserRole.ADMIN_ROLE);
        
        if(userId.equals("Permisson denied")){
        
            throw new HandlerException("Permisson denied");
        
        }

        return tableService.mostSoldsProducts();

    }

}
    



    