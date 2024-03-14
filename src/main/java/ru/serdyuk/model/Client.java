package ru.serdyuk.model;

import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
//@Builder
@Entity(name = "clients")
public class Client {
    public Client() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name")
    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        } else orders.add(order);
    }

    public void removeOrder(Long orderId) {
        orders = orders.stream().filter(
                o -> !o.getId().equals(orderId)
        ).collect(Collectors.toList());
    }

    public List<Order> getOrders() {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        return orders;
    }
}