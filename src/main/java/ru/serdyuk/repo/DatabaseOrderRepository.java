package ru.serdyuk.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.serdyuk.model.Orders;

public interface DatabaseOrderRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {

    //Page<Orders> findAllByProduct(String product, Pageable pageable);

}
