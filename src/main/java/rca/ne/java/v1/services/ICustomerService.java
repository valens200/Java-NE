package rca.ne.java.v1.services;

import rca.ne.java.v1.dtos.requests.CreateCustomerDTO;
import rca.ne.java.v1.models.Customer;

public interface ICustomerService {

    public Customer create(CreateCustomerDTO dto);
}
