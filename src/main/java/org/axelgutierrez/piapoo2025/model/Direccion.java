package org.axelgutierrez.piapoo2025.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter //Getters y setters y constructor sin argumentos usando Lombok
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "El nombre de la calle es obligatorio")
    private String calle;

    @NotNull(message = "El número de casa es obligatorio")
    @Min(value = 1, message = "El número de casa debe ser mayor a 0")
    private Integer numCasa;

    @NotBlank(message = "El nombre de la colonia es obligatorio")
    private String colonia;

    @NotBlank(message = "El nombre del municipio es obligatorio")
    private String municipio;

    @NotBlank(message = "El nombre del estado es obligatorio")
    private String estado;

    @NotNull(message = "El código postal es obligatorio")
    private Integer codigoPostal;
}
