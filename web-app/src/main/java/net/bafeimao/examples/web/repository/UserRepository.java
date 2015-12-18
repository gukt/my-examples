package net.bafeimao.examples.web.repository;

import net.bafeimao.examples.web.domain.User;

public interface UserRepository extends GenericRepository<User, Long> {
 
	boolean checkExistenceByName(String name);

	boolean checkExistenceByEmail(String email);
}
