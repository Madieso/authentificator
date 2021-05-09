package com.lma.authentificator.hibernate.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lma.authentificator.hibernate.entity.UserEntity;

/**
 * Interface Project Repository to allow CRUD operations to User Entity table
 */
@Repository
public interface IUserRepository extends CrudRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String mail);

	Optional<UserEntity> findByPseudo(String pseudo);

	Optional<UserEntity> findByUuid(String uuid);

}