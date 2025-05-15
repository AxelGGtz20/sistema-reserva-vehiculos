package org.axelgutierrez.piapoo2025.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) //no se ingresa, se calcula y se lee
    private Double precioTotal; //precioPorDia de vehiculo x cantidadDias(fecha fin - fecha inicio)

    @NotNull(message = "El cliente es obligatorio")
    @ManyToOne //una reserva tiene un cliente, un cliente puede tener varias reservas
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @NotNull(message = "El vehiculo es obligatorio")
    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;
}
