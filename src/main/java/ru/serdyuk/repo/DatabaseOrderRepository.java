package ru.serdyuk.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.serdyuk.model.Orders;

public interface DatabaseOrderRepository extends JpaRepository<Orders, Long> {
}
