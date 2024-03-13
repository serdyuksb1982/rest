package ru.serdyuk.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.serdyuk.exception.EntityNotFoundException;
import ru.serdyuk.model.Client;
import ru.serdyuk.model.Order;
import ru.serdyuk.repo.DatabaseOrderRepository;
import ru.serdyuk.service.ClientService;
import ru.serdyuk.service.OrderService;
import ru.serdyuk.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseOrderService implements OrderService {

    private final DatabaseOrderRepository orderRepository;

    private final ClientService databaseClientService;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Order ID {0} not found", id)
                ));
    }

    @Override
    public Order save(Order order) {
        Client client = databaseClientService.findById(order.getId());
        order.setClient(client);
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        checkForUpdate(order.getId());
        Client client = databaseClientService.findById(order.getClient().getId());
        Order currentOrder = findById(order.getId());

        BeanUtils.copyNotNullProperties(order, currentOrder);
        currentOrder.setClient(client);
        return orderRepository.save(currentOrder);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        orderRepository.deleteAllById(ids);
    }
}
