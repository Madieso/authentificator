package com.lma.authentificator.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lma.authentificator.hibernate.entity.UserEntity;
import com.lma.authentificator.hibernate.repository.IUserRepository;
import com.lma.authentificator.hibernate.service.IUserService;

/**
 * User Service: for CRUD operation to database
 *
 */
@Service
public class UserService implements IUserService {

	/** The repository. */
	@Autowired
	private IUserRepository repository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Override
	public List<UserEntity> findAll() {
		return (List<UserEntity>) this.repository.findAll();
	}

	/**
	 * Save.
	 *
	 * @param workflow the workflow
	 * @return the user entity
	 */
	@Override
	public UserEntity save(final UserEntity user) {
		return this.repository.save(user);
	}


	@Override
	public UserEntity findByEmail(String mail) {

		final Optional<UserEntity> opt = this.repository.findByEmail(mail);

		if(opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	@Override
	public void delete(UserEntity user) {
		this.repository.delete(user);
	}

	@Override
	public UserEntity findByPseudo(String pseudo) {

		final Optional<UserEntity> opt = this.repository.findByPseudo(pseudo);

		if(opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	@Override
	public UserEntity findByUuid(String uuid) {

		final Optional<UserEntity> opt = this.repository.findByUuid(uuid);

		if(opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

}