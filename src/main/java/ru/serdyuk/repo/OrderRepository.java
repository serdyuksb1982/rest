package ru.serdyuk.repo;

import ru.serdyuk.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findAll();

    Optional<Order> findById(Long id);

    Order save(Order order);

    Order update(Order order);

    void removeById(Long id);

    void deleteByIdIn(List<Long> ids);
}
