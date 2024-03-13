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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryOrderRepository implements OrderRepository {

    private ClientRepository inMemoryClientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.inMemoryClientRepository = clientRepository;
    }

    private final Map<Long, Order> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Order> findById(Long id) {

        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Order save(Order order) {
        Long orderId = currentId.incrementAndGet();
        Long clientId = order.getClient().getId();
        Client client = inMemoryClientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("User not "));
        order.setClient(client);
        order.setId(clientId);
        Instant now = Instant.now();
        order.setCreateAt(now);
        order.setUpdateAt(now);
        repository.put(orderId, order);
        client.addOrder(order);
        inMemoryClientRepository.update(client);
        return order;
    }

    @Override
    public Order update(Order order) {
        Long orderId = order.getId();
        Instant now = Instant.now();
        Order currentOrder = repository.get(orderId);
        if (currentOrder == null) {
            throw new EntityNotFoundException(MessageFormat.format("Order from ID {0} not found", orderId));
        }
        BeanUtils.copyNotNullProperties(order, orderId);
        currentOrder.setUpdateAt(now);
        currentOrder.setId(orderId);
        repository.put(orderId, currentOrder);
        return currentOrder;
    }

    @Override
    public void removeById(Long id) {
        repository.remove(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        ids.forEach(repository::remove);
    }
}
