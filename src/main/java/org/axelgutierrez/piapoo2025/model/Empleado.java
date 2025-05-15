package org.axelgutierrez.piapoo2025.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Empleado extends Usuario{
    //atributos basicos de Usuario
    @NotNull(message = "El rol es obligatorio")
    @ManyToOne //Un empleado tiene un rol, un rol tiene muchos empleados.
    private RolEmpleado rol;
}
