package ru.serdyuk.repo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.serdyuk.exception.EntityNotFoundException;
import ru.serdyuk.model.Client;
import ru.serdyuk.model.Order;
import ru.serdyuk.repo.ClientRepository;
import ru.serdyuk.repo.OrderRepository;
import ru.serdyuk.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class InMemoryClientRepository implements ClientRepository {

    private  OrderRepository inMemoryOrderRepository;

    private final Map<Long, Client> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1L);

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Client save(Client client) {
        Long clientId = currentId.getAndIncrement();
        client.setId(clientId);
        repository.put(clientId, client);
        return client;
    }

    @Override
    public Client update(Client client) {
        Long clientID = client.getId();
        Client currentClient = repository.get(clientID);
        if (currentClient == null) {
            throw new EntityNotFoundException(MessageFormat.format("Client with Id {0} not found.", clientID));
        }
        BeanUtils.copyNotNullProperties(client, currentClient);
        currentClient.setId(clientID);
        repository.put(clientID, currentClient);

        return currentClient;
    }

    @Override
    public void deleteById(Long id) {
        Client client = repository.get(id);
        if (client == null) {
            throw new EntityNotFoundException(MessageFormat.format("Client with Id {0} not found.", id));
        }
        inMemoryOrderRepository.deleteByIdIn(client.getOrders().stream().map(Order::getId).collect(Collectors.toList()));
        repository.remove(id);
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.inMemoryOrderRepository = orderRepository;
    }
}
