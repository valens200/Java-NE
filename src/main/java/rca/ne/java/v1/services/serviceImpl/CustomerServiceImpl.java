package rca.ne.java.v1.services.serviceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rca.ne.java.v1.dtos.requests.CreateCustomerDTO;
import rca.ne.java.v1.exceptions.BadRequestException;
import rca.ne.java.v1.models.Account;
import rca.ne.java.v1.models.Customer;
import rca.ne.java.v1.repositories.IAccountRepository;
import rca.ne.java.v1.repositories.ICustomerRepository;
import rca.ne.java.v1.repositories.IUserRepository;
import rca.ne.java.v1.services.ICustomerService;
import rca.ne.java.v1.services.IRoleService;
import rca.ne.java.v1.utils.ExceptionsUtils;
import rca.ne.java.v1.utils.Hash;
import rca.ne.java.v1.utils.Mapper;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends ServiceImpl implements ICustomerService {
    private final ICustomerRepository customerRepository;
    private final IAccountRepository accountRepository;
    private final IUserRepository userRepository;
    private final IRoleService roleService;

    /**
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public Customer create(CreateCustomerDTO dto) {
        try{
            if(customerRepository.existsByEmail(dto.getEmail())) throw  new BadRequestException("The customer with the provided email already exists");
            customer = Mapper.getCustomerFromDTO(dto);
            role = roleService.getByRoleName("CUSTOMER");
            roles = new HashSet<>();
            roles.add(role);
            profile = Mapper.getUserFromDTO(dto);
            profile.setRoles(roles);
            profile.setPassword(Hash.hashPassword(profile.getPassword()));
            profile = userRepository.save(profile);
            customer.setProfile(profile);
            customer = customerRepository.save(customer);
            account = new Account(customer);
            account = accountRepository.save(account);
            customer.setAccount(account);
            return customerRepository.save(customer);
        }catch (Exception exception){
            ExceptionsUtils.handleServiceExceptions(exception);
            return null;
        }
    }
}
