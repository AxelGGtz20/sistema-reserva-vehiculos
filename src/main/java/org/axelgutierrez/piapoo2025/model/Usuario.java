package org.axelgutierrez.piapoo2025.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter //Getters y setters usando Lombok
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres")
    private String apellidos;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    //relación con la tabla dirección, un cliente/empleado una dirección
    @NotNull(message = "La dirección es obligatoria")
    @OneToOne(cascade = CascadeType.ALL)//si elimino al cliente/empleado, se elimina su direccion tambien
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;
}
