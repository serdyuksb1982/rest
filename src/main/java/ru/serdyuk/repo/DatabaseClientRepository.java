package ru.serdyuk.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.serdyuk.model.Clients;

import java.util.List;

public interface DatabaseClientRepository extends JpaRepository<Clients, Long> {

    @Override
    @EntityGraph(attributePaths = {"orders"})
    List<Clients> findAll();
}