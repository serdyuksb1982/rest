package ru.serdyuk.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.serdyuk.model.Orders;
import ru.serdyuk.web.model.OrderFilter;

import java.math.BigDecimal;
import java.time.Instant;

public interface OrderSpecification {

    static Specification<Orders> withFilter(OrderFilter orderFilter) {
        return Specification.where(byProductName(orderFilter.getProductName()))
                .and(byCostRange(orderFilter.getMinCost(), orderFilter.getMaxCost()))
                .and(byClientId(orderFilter.getClientId()))
                .and(byCreatedAtBefore(orderFilter.getCreatedBefore()))
                .and(byUpdatedAtBefore(orderFilter.getUpdatedBefore()));
    }

    static Specification<Orders> byProductName(String productName) {
        return ((root, query, criteriaBuilder) -> {
            if (productName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("product"), productName);
        });
    }

    static Specification<Orders> byCostRange(BigDecimal minCost, BigDecimal maxCost) {
        return ((root, query, criteriaBuilder) -> {
            if (minCost == null && maxCost == null) {
                return null;
            }
            if (minCost == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("cost"), maxCost);
            }
            if (maxCost == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minCost);
            }
            return criteriaBuilder.between(root.get("cost"), minCost, maxCost);
        });
    }

    static Specification<Orders> byClientId(Long clientId) {
        return (root, query, criteriaBuilder) -> {
            if (clientId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("clients").get("id"), clientId);
        };
    }

    static Specification<Orders> byCreatedAtBefore(Instant createdBefore) {
        return (root, query, criteriaBuilder) -> {
            if (createdBefore == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("createAt"), createdBefore);
        };
    }

    static Specification<Orders> byUpdatedAtBefore(Instant updatedBefore) {
        return (root, query, criteriaBuilder) -> {
            if (updatedBefore == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("updateAt"), updatedBefore);
        };
    }
}
