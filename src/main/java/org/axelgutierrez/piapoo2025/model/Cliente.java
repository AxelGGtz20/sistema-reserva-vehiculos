package org.axelgutierrez.piapoo2025.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor //constructor sin argumentos usando Lombok
public class Cliente extends Usuario{
    //atributos heredados de Usuario
}
