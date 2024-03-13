package ru.serdyuk.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.serdyuk.exception.EntityNotFoundException;
import ru.serdyuk.model.Client;
import ru.serdyuk.repo.ClientRepository;
import ru.serdyuk.service.ClientService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository inMemoryClientRepository;

    @Override
    public List<Client> findAll() {
        return inMemoryClientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return inMemoryClientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Client from ID {0} not found", id)));
    }

    @Override
    public Client save(Client client) {
        return inMemoryClientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        return inMemoryClientRepository.update(client);
    }

    @Override
    public void deleteById(Long id) {
        inMemoryClientRepository.deleteById(id);
    }
}