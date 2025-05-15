package org.axelgutierrez.piapoo2025.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter //Getters y setters usando Lombok
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "La placa es obligatoria") //validacion de como debe ser, depende de la ubicación de la empresa
    private String placa;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotBlank(message = "El color es obligatorio")
    private String color;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "DISPONIBLE|EN MANTENIMIENTO", message = "El estado debe ser DISPONIBLE o EN MANTENIMIENTO")
    private String estado;

    @NotNull(message = "El precio por día es obligatorio")
    @Positive(message = "El precio por día debe ser un número positivo")
    private Double precioPorDia; //precio por cada día de renta

}
