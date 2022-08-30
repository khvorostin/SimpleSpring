package com.khvorostin.simplespring.market.repositories;

import com.khvorostin.simplespring.market.models.Privilege;
import org.springframework.data.repository.CrudRepository;

public interface PrivilegeRepository extends CrudRepository< Privilege, Long > {
}
