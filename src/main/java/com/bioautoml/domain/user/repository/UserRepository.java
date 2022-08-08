package com.bioautoml.domain.user.repository;

import com.bioautoml.domain.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findById(UUID uuid);

    Optional<UserModel> findByEmail(String email);

}
