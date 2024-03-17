package ru.serdyuk.service;

import ru.serdyuk.exception.UpdateStateException;
import ru.serdyuk.model.Order;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findById(Long id);

    Order save(Order order);

    Order update(Order order);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);

    default void checkForUpdate(Long orderId) {
        Order currentOrderDb = findById(orderId);
        Instant now = Instant.now();
        Duration duration = Duration.between(currentOrderDb.getUpdateAt(), now);
        if (duration.getSeconds() > 5) {
            throw new UpdateStateException("Невозможно обновить заказ.");
        }
    }
}
