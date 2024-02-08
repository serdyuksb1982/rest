package ru.serdyuk.web.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsetClientRequest {

    @NotBlank(message = "Имя клиента должно быть заполнено!")
    @Size(min = 3, max = 30, message = "Имя клиента не может быть меньше {min} и больше {max}!")
    private String name;

}
