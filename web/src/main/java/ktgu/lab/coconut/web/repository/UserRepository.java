package ktgu.lab.coconut.web.repository;

import ktgu.lab.coconut.web.domain.User;

public interface UserRepository extends GenericRepository<User, Long> {
 
	boolean checkExistenceByName(String name);

	boolean checkExistenceByEmail(String email);
}
