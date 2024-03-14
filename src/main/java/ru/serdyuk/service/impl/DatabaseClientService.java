package ru.serdyuk.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.serdyuk.exception.EntityNotFoundException;
import ru.serdyuk.model.Client;
import ru.serdyuk.repo.ClientRepository;
import ru.serdyuk.repo.DatabaseClientRepository;
import ru.serdyuk.service.ClientService;
import ru.serdyuk.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseClientService implements ClientService {

    private final DatabaseClientRepository databaseClientRepository;

    @Override
    public List<Client> findAll() {
        return databaseClientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return databaseClientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format(
                                "Client ID {0} not found!", id
                        )
                ));
    }

    @Override
    public Client save(Client client) {
        return databaseClientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        Client currentClient = findById(client.getId());
        BeanUtils.copyNotNullProperties(client, currentClient);
        return databaseClientRepository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        databaseClientRepository.deleteById(id);
    }
}
