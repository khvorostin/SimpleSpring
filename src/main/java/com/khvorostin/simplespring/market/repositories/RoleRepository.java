package com.khvorostin.simplespring.market.repositories;

import com.khvorostin.simplespring.market.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository< Role, Long> {
}
