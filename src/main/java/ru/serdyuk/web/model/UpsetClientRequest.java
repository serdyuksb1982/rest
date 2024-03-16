package ru.serdyuk.web.model;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpsetClientRequest {


    @NotBlank(message = "The client's name must be filled!")
    @Size(min = 3, max = 30, message = "The client's name cannot be less than {min} and more {max}!")
    private String name;

}
