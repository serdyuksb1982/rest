package ru.serdyuk.service;

import ru.serdyuk.exception.UpdateStateException;
import ru.serdyuk.model.Orders;
import ru.serdyuk.web.model.OrderFilter;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public interface OrderServiceDb {

    List<Orders> filterBy(OrderFilter filter);

    List<Orders> findAll();

    Orders findById(Long id);

    Orders save(Orders order);

    Orders update(Orders order);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);

    default void checkForUpdate(Long orderId) {
        Orders currentOrders = findById(orderId);
        Instant now = Instant.now();
        Duration duration = Duration.between(currentOrders.getUpdateAt(), now);
        if (duration.getSeconds() > 5) {
            throw new UpdateStateException("Невозможно обновить заказ.");
        }
    }
}
