package ru.serdyuk.service;

import ru.serdyuk.model.Clients;

import java.util.List;

public interface ClientServiceDb {
    List<Clients> findAll();

    Clients findById(Long id);

    Clients save(Clients client);

    Clients update(Clients client);

    void deleteById(Long id);
}
