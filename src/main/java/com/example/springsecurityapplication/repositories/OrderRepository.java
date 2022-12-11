package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    //лист заказов пользователя
    List<Order> findByPerson(Person person);
    @Query(value = "select * from orders where ((lower(number) LIKE %?1%) or (lower(number) LIKE '?1%') or (lower(number) LIKE '%?1'))", nativeQuery = true)
    List<Order> findByNumber(String number);
}
