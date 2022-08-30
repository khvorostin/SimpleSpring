package com.khvorostin.simplespring.market.repositories;

import com.khvorostin.simplespring.market.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository< User, Long> {
    Optional<User> findByUsername(String username);
}
