package com.fome.projectfome.service;

import java.util.ArrayList;
import java.util.List; 
import java.util.Random;
import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fome.projectfome.dao.OrderRepository;
import com.fome.projectfome.dao.ProductRepository;
import com.fome.projectfome.dao.UserRepository;
import com.fome.projectfome.dto.ApiResponseDTO;
import com.fome.projectfome.dto.enums.EStatusOrder;
import com.fome.projectfome.dto.mappers.DateApiResponse;
import com.fome.projectfome.dto.mappers.ListProductApiResponse;
import com.fome.projectfome.model.Order;
import com.fome.projectfome.model.User;
import com.fome.projectfome.model.Product;

@Transactional
@Service
public class TableService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    
   
    // CRIAR UM PEDIDO NOVO
    public Order createOrder(Order order) {
        
        orderRepository.save(order);
        
        return order;
    }

    // INCREMENTAR PRODUTOS EM UM PEDIDO JA CRIADO
    public Order addProductOrder(Order order){

        List<Order> listOrder = null;
        listOrder = orderRepository.findByCpfCliente(order.getCpfCliente());

        Order newOrder = new Order();

        for(Order ord : listOrder){
            
            if(ord.getStatus() != EStatusOrder.PAID_OUT){

                newOrder = ord;

            }

        }

        newOrder.setTotalValue(newOrder.getTotalValue() + order.getTotalValue());

      
        newOrder.getNewIdProductOrder().addAll(order.getNewIdProductOrder());

        newOrder.setStatus(EStatusOrder.PREPARING);

        orderRepository.save(newOrder);
               
        return order;
    }
    
    // saber se a order ja existe e faz um novo ou incrementa
    public Order controllerOrder(Order order){

       
        List<Order> listOrder = null;

        listOrder = orderRepository.findByCpfCliente(order.getCpfCliente());

        Order newOrder = null;

        if(listOrder == null){

            createOrder(order);

        }else{

            for(Order ord : listOrder){
            
                if(ord.getStatus() != EStatusOrder.PAID_OUT){

                    System.out.println("\n\n\n\n\n\n\n\n " +ord.getCpfCliente() + "\n\n\n\n\n\n");
                    newOrder = ord;

                }

            }

        }

        if(newOrder == null){
            
            return createOrder(order);
 
        }else{
            
            return addProductOrder(order);

        }
       
    }
    
    // retornar os pedidos para ser feito pela cozinha
    public List<Order> findNewOrders(){

        List<Order> allOrders = orderRepository.findAll();

        List<Order> newOrders = new ArrayList<Order>();

        for(Order ord : allOrders){

            if(!ord.getNewIdProductOrder().isEmpty()){
                
                
                //ord.setCreatedAt(ord.getCreatedAt());

                newOrders.add(ord);

            }

        }

        return newOrders;

    }

    public List<Order> findTableOrders(User table){

        List<Order> allOrders = orderRepository.findAll();
        
        List<Order> deskOrders = new ArrayList<Order>();

        for(Order ord : allOrders){

            if(ord.getTable_id().getId() == table.getId() && ord.getStatus() != EStatusOrder.PAID_OUT){

                deskOrders.add(ord);

            }

        }

        return deskOrders;
        
    }

    // find By ID
    public Order findById(int id){

        return orderRepository.findById(id);

    }

    // find all
    public List<Order> findAll(){

        return orderRepository.findAll();

    }

    // update Order
    public Order updateOrder(Order order){

        Order newOrder = orderRepository.findById(order.getId());
        newOrder.setTotalValue(order.getTotalValue());
        newOrder.setIdProductOrder(order.getIdProductOrder());
        newOrder.setPaymentMethod(order.getPaymentMethod());
        newOrder.setCpfCliente(order.getCpfCliente());
        newOrder.setStatus(order.getStatus());
        newOrder.setTable_id(order.getTable_id());
        
        
        orderRepository.save(newOrder);

        return newOrder;

    }

    // update Order
    public ApiResponseDTO finishOrder(int id){

        Order newOrder = orderRepository.findById(id);
        
        if(newOrder.getStatus() == EStatusOrder.PAID_OUT){

            return new ApiResponseDTO(false, "Este pedido já foi pago!");

        }

        newOrder.setStatus(EStatusOrder.DONE);

        newOrder.getIdProductOrder().addAll(newOrder.getNewIdProductOrder());
        
        ArrayList<String> newList = new ArrayList<String>();

        newOrder.setNewIdProductOrder(newList);
        
        orderRepository.save(newOrder);

        return new ApiResponseDTO(true, "Pedido pago!");

    }

    public ApiResponseDTO payOrder(Order order){

        Order newOrder = orderRepository.findById(order.getId());
        
        newOrder.setPaymentMethod(order.getPaymentMethod());

        newOrder.setStatus(EStatusOrder.PAID_OUT);

        newOrder.getIdProductOrder().addAll(newOrder.getNewIdProductOrder());
        
        ArrayList<String> newList = new ArrayList<String>();

        newOrder.setNewIdProductOrder(newList);
        
        orderRepository.save(newOrder);

        return new ApiResponseDTO(true, "Peido pago!");

    }

    // delete Order
    public Order deleteOrder(int id){
        Order order = orderRepository.findById(id);
        orderRepository.delete(order);
        return order;
    }

    // gerar senha para liberar a mesa
    public String genPassword(int id){
        
        String AB = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        
        // atualizando a senha do usuario no banco de dados
        userService.updateTablePassword(id, sb.toString());

        return sb.toString();
    }

        public double showBalanceSheet(DateApiResponse sDate){

        double totalValue = 0;

        List <Order> allOrders = orderRepository.findAll();
        
        for(Order ord : allOrders){
            if(ord.getStatus() == EStatusOrder.PAID_OUT){
                 if(ord.getCreatedAt().getMonth() >= sDate.getIniDate().getMonth() && ord.getCreatedAt().getMonth() <= sDate.getFinalDate().getMonth()) {
                      if(ord.getCreatedAt().getMonth() == sDate.getIniDate().getMonth()){         
                         if(ord.getCreatedAt().getDate() >= sDate.getIniDate().getDate()){
                        System.out.println("\n\n\n\n\n\n mesPed = iniMes \n Data do pedido:"+ord.getCreatedAt()+"\n Data inicial: "+sDate.getIniDate()+"\n data final:"+sDate.getFinalDate());
                            totalValue+= ord.getTotalValue();
                          
                         }
                     }else if(ord.getCreatedAt().getMonth() == sDate.getFinalDate().getMonth()){
                                if(ord.getCreatedAt().getDate()<= sDate.getFinalDate().getDate()){
                                    System.out.println("\n\n\n\n\n\n mesPed = finalMes \n Data do pedido:"+ord.getCreatedAt()+"\n Data inicial: "+sDate.getIniDate()+"\n data final:"+sDate.getFinalDate());
                                    totalValue+= ord.getTotalValue();
                                }
                     }else{
                        System.out.println("\n\n\n\n\n\n mesAleatorio \n Data do pedido:"+ord.getCreatedAt()+"\n Data inicial: "+sDate.getIniDate()+"\n data final:"+sDate.getFinalDate());
                        totalValue+= ord.getTotalValue();
                     }
                 }
                 
               
            }
        }
        System.out.println("\n=========================================================\n");
		return totalValue;
    }


    public List<ListProductApiResponse> mostSoldsProducts(){
        List<Order> allOrders = orderRepository.findAll();
       
        List<Product> listProducts = new ArrayList<>();
     
        for(Order ord: allOrders){

            if(ord.getStatus() == EStatusOrder.PAID_OUT ){

                for(int i =0; i<ord.getIdProductOrder().size();i++){

                    int id_product =Integer.parseInt(ord.getIdProductOrder().get(i));

                    Product auxProduct = productRepository.findById(id_product);

                    listProducts.add(auxProduct);
                }
            }
        }

        List<ListProductApiResponse> newListProduct = new ArrayList<>();

        int productAlredyExists = 0;
        int indice =0;

        for(Product prod : listProducts){
            productAlredyExists = 0;

            for(int i =0;i < newListProduct.size();i++){
                if(prod.getId() ==  newListProduct.get(i).getProduct().getId()){
                    productAlredyExists = 1;
                    indice = i;
                }
            }
            if(productAlredyExists == 1){
                newListProduct.get(indice).setAmount( newListProduct.get(indice).getAmount() +1);
            }else{
                newListProduct.add(new ListProductApiResponse(prod, 1));
            }
        }

        Collections.sort(newListProduct);

        return newListProduct;
    }

}