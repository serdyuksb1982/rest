package ru.serdyuk.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.serdyuk.model.Client;

public interface DatabaseClientRepository extends JpaRepository<Client, Long> {
}