package org.axelgutierrez.piapoo2025.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String marca;
    private String placa;
    private String modelo;
    private String color;
    private String estado; //DISPONIBLE o EN MANTENIMIENTO
    private Double precioPorDia; //precio por cada día de renta

}
