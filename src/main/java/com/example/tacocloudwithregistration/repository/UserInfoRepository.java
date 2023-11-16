package com.example.tacocloudwithregistration.repository;

import com.example.tacocloudwithregistration.data.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
}
