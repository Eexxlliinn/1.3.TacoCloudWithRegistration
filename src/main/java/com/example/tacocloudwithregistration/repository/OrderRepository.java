package com.example.tacocloudwithregistration.repository;

import com.example.tacocloudwithjpa.data.TacoOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    //Override save SQL Statement with insert data in taco
    @Override
    <S extends TacoOrder> S save(S entity);
}
