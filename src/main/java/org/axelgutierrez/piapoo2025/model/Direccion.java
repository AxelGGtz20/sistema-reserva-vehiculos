package org.axelgutierrez.piapoo2025.model;

import jakarta.persistence.*;
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
    private String calle;
    private Integer numCasa;
    private String colonia;
    private String municipio;
    private String estado;
    private Integer codigoPostal;
}
