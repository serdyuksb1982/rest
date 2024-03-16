package ru.serdyuk.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
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


    private String name;

    @OneToMany(mappedBy = "clients", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Orders> orders = new ArrayList<>();


}