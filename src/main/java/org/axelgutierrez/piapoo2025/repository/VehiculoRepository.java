package org.axelgutierrez.piapoo2025.repository;

import org.axelgutierrez.piapoo2025.model.Vehiculo;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface VehiculoRepository extends CrudRepository<Vehiculo, Long> {
    //Mostrar los vehiculos dependiendo de su estado (DISPONIBLE O EN_MANTENIMIENTO)
    List<Vehiculo> findByEstado(String estado);
    //Mostrar los vehiculos con un precio menor a x Ordenados de mayor a menor
    List<Vehiculo> findByPrecioPorDiaLessThanEqualOrderByPrecioPorDiaDesc(Double precioPorDia);
}
