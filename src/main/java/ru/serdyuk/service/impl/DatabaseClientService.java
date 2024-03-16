package ru.serdyuk.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.serdyuk.exception.EntityNotFoundException;
import ru.serdyuk.model.Clients;
import ru.serdyuk.model.Orders;
import ru.serdyuk.repo.DatabaseClientRepository;
import ru.serdyuk.repo.DatabaseOrderRepository;
import ru.serdyuk.service.ClientServiceDb;
import ru.serdyuk.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseClientService implements ClientServiceDb {

    private final DatabaseClientRepository databaseClientRepository;

    private final DatabaseOrderRepository databaseOrderRepository;

    @Override
    public List<Clients> findAll() {
        return databaseClientRepository.findAll();
    }

    @Override
    public Clients findById(Long id) {
        return databaseClientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format(
                                "Client ID {0} not found!", id
                        )
                ));
    }

    @Override
    public Clients save(Clients clients) {
        return databaseClientRepository.save(clients);
    }

    @Override
    public Clients update(Clients clients) {
        Clients currentClients = findById(clients.getId());
        BeanUtils.copyNotNullProperties(clients, currentClients);
        return databaseClientRepository.save(clients);
    }

    @Override
    public void deleteById(Long id) {
        databaseClientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Clients saveWithOrders(Clients client, List<Orders> orders) {
        Clients saveClient = databaseClientRepository.save(client);

        for (Orders order : orders) {
            order.setClients(saveClient);
            var saveOrder = databaseOrderRepository.save(order);
            saveClient.addOrder(saveOrder);
        }
        return saveClient;
    }
}
