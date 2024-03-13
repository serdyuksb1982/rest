package ru.serdyuk.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.serdyuk.model.Order;

public interface DatabaseOrderRepository extends JpaRepository<Order, Long> {
}
