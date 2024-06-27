package rca.ne.java.v1;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import rca.ne.java.v1.models.Role;
import rca.ne.java.v1.models.enums.ERole;
import rca.ne.java.v1.repositories.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class StarterTemplatesApplication  extends SpringBootServletInitializer {
	private final IRoleRepository roleRepository;


	public static void main(String[] args) {
		SpringApplication.run(StarterTemplatesApplication.class, args);
	}
	@Bean
	public void  initializeRoles(){
		Role roleEntity = new Role();
		List<ERole> roles = new ArrayList<>();
		roles.add(ERole.ADMIN);
		roles.add(ERole.CUSTOMER);
		for(ERole role : roles){
			if(!roleRepository.existsByRoleName(role.name())){
				roleEntity.setRoleName(role.name());
				roleRepository.save(roleEntity);
			}
		}

	}
}