package ru.serdyuk.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity(name = "clients")
public class Clients {
    public Clients() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name")
    private String name;

    @OneToMany(mappedBy = "clients", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Orders> orders = new ArrayList<>();

    public List<Orders> getOrders() {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        return orders;
    }
}