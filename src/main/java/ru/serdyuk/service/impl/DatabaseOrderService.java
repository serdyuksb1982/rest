package ru.serdyuk.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.serdyuk.exception.EntityNotFoundException;
import ru.serdyuk.model.Clients;
import ru.serdyuk.model.Orders;
import ru.serdyuk.repo.DatabaseOrderRepository;
import ru.serdyuk.service.ClientServiceDb;
import ru.serdyuk.service.OrderServiceDb;
import ru.serdyuk.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseOrderService implements OrderServiceDb {

    private final DatabaseOrderRepository databaseOrderRepository;

    private final ClientServiceDb databaseClientService;

    @Override
    public List<Orders> findAll() {
        return databaseOrderRepository.findAll();
    }

    @Override
    public Orders findById(Long id) {
        return databaseOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Order ID {0} not found", id)
                ));
    }

    @Override
    public Orders save(Orders orders) {
        Clients clients = databaseClientService.findById(orders.getClients().getId());
        orders.setClients(clients);
        return databaseOrderRepository.save(orders);
    }

    @Override
    public Orders update(Orders orders) {
        checkForUpdate(orders.getId());
        Clients clients = databaseClientService.findById(orders.getClients().getId());
        Orders currentOrders = findById(orders.getId());

        BeanUtils.copyNotNullProperties(orders, currentOrders);
        currentOrders.setClients(clients);
        return databaseOrderRepository.save(currentOrders);
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
