package com.hnp.testingspringboot.redisrepo;


import com.hnp.testingspringboot.model.TokenStore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenStoreRepository extends CrudRepository<TokenStore, String> {


    Optional<TokenStore> findByUsername(String s);
}
