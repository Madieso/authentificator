package com.lma.authentificator.hibernate.service;

import java.util.List;

import com.lma.authentificator.hibernate.entity.UserEntity;

/**
 * Interface IUserService.
 */
public interface IUserService {

	/**
	 * Find all steps
	 *
	 * @return the step list
	 */
	List<UserEntity> findAll();

	/**
	 * Save a user
	 *
	 * @param user the user
	 * @return the user entity
	 */
	UserEntity save(final UserEntity user);

	UserEntity findByEmail(String mail);

	UserEntity findByPseudo(String pseudo);

	UserEntity findByUuid(String uuid);

	void delete(final UserEntity user);

}
