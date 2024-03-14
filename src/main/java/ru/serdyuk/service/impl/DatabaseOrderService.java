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

    private final DatabaseOrderRepository databaseOrderRepository;

    private final DatabaseClientService databaseClientService;

    @Override
    public List<Order> findAll() {
        return databaseOrderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return databaseOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Order ID {0} not found", id)
                ));
    }

    @Override
    public Order save(Order order) {
        Client client = databaseClientService.findById(order.getClient().getId());
        order.setClient(client);
        return databaseOrderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        checkForUpdate(order.getId());
        Client client = databaseClientService.findById(order.getClient().getId());
        Order currentOrder = findById(order.getId());

        BeanUtils.copyNotNullProperties(order, currentOrder);
        currentOrder.setClient(client);
        return databaseOrderRepository.save(currentOrder);
    }

    @Override
    public void deleteById(Long id) {
        databaseOrderRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        databaseOrderRepository.deleteAllById(ids);
    }
}
