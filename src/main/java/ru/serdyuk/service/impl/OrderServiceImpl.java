package ru.serdyuk.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.serdyuk.exception.EntityNotFoundException;
import ru.serdyuk.model.Order;
import ru.serdyuk.repo.OrderRepository;
import ru.serdyuk.service.OrderService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository inMemoryOrderRepository;


    @Override
    public List<Order> findAll() {
        return inMemoryOrderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return inMemoryOrderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Order from ID {0} not found", id)));
    }

    @Override
    public Order save(Order order) {
        return inMemoryOrderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        checkForUpdate(order.getId());
        return inMemoryOrderRepository.update(order);
    }

    @Override
    public void deleteById(Long id) {
        inMemoryOrderRepository.removeById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        inMemoryOrderRepository.deleteByIdIn(ids);
    }


}
