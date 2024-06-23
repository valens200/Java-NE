package com.templates.valens.v1.utils;


import com.templates.valens.v1.models.Product;
import com.templates.valens.v1.models.User;
import org.modelmapper.ModelMapper;

public class Mapper {
    public static ModelMapper modelMapper = new ModelMapper();

    public static Product getProductFromDTO(Object object){
        return modelMapper.map(object, Product.class);
    }

    public static User getUserFromDTO(Object object){
        return modelMapper.map(object,User.class);
    }
}
