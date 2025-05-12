package org.axelgutierrez.piapoo2025.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter //Getters y setters usando Lombok
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;

    //relación con la tabla dirección, un cliente/empleado una dirección
    @OneToOne(cascade = CascadeType.ALL)//si elimino al cliente/empleado, se elimina su direccion tambien
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;
}
