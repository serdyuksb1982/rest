package ru.serdyuk.web.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsetClientRequest {

    @NotBlank(message = "Имя клиента должно быть заполнено!")
    private String name;

}
