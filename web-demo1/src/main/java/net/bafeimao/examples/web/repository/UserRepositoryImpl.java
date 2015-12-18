package net.bafeimao.examples.web.repository;

import net.bafeimao.examples.web.domain.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<User, Long> implements UserRepository {

	@Override
	public boolean checkExistenceByEmail(String email) {
		return count("where email=?", email) != 0;
	}

	@Override
	public boolean checkExistenceByName(String name) {
		return count("where name=?", name) != 0;
	}

}
