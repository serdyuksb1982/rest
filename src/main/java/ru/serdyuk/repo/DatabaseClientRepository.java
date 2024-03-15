package ru.serdyuk.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.serdyuk.model.Clients;

public interface DatabaseClientRepository extends JpaRepository<Clients, Long> {
}