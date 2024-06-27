package rca.ne.java.v1.utils;


import rca.ne.java.v1.models.Customer;
import rca.ne.java.v1.models.User;
import org.modelmapper.ModelMapper;

public class Mapper {
    public static ModelMapper modelMapper = new ModelMapper();

    public static User getUserFromDTO(Object object){
        return modelMapper.map(object,User.class);
    }
    public static Customer getCustomerFromDTO(Object object){
        return modelMapper.map(object, Customer.class);
    }

}
