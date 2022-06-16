package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	//buscar no banco um usuario por email
	User findByEmail(String email);
	
	//Exemplo de query method
	//List<User> findByFirstName(String firstName);

}
