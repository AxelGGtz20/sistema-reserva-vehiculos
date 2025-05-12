package org.axelgutierrez.piapoo2025.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter @Setter //Getters y setters usando Lombok
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) //no se ingresa, se calcula y se lee
    private Double precioTotal; //precioPorDia de vehiculo x cantidadDias(fecha fin - fecha inicio)

    @ManyToOne //una reserva tiene un cliente, un cliente puede tener varias reservas
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;
}
