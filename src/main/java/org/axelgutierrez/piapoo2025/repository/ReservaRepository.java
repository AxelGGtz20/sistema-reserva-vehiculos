package org.axelgutierrez.piapoo2025.repository;

import org.axelgutierrez.piapoo2025.model.Reserva;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends CrudRepository<Reserva, Long> {
    /*Para cada reserva de ese vehiculo revisamos si
      la nueva fecha de inicio no este entre las fechas de una reserva ya hecha.
      la nueva fecha de fin no este entre las fechas de una reserva ya hecha
      la fecha de inicio de una reserva no este entre las fechas de la nueva reserva
    */
    @Query("SELECT r FROM Reserva r WHERE r.vehiculo.id = :vehiculoId AND " +
            "(:nuevaFechaInicio BETWEEN r.fechaInicio AND r.fechaFin OR " +
            ":nuevaFechaFin BETWEEN r.fechaInicio AND r.fechaFin OR " +
            "r.fechaInicio BETWEEN :nuevaFechaInicio AND :nuevaFechaFin)")
    List<Reserva> buscarReservasEnConflicto(@Param("vehiculoId") Long vehiculoId, @Param("nuevaFechaInicio") LocalDate nuevaFechaInicio, @Param("nuevaFechaFin") LocalDate nuevaFechaFin);

    /*Para cada reserva de ese vehiculo excepto la reserva que se quiere actualizar,
      si no, siempre se solaparían con la nueva fecha revisamos que:
      la nueva fecha de inicio no este entre las fechas de una reserva ya hecha.
      la nueva fecha de fin no este entre las fechas de una reserva ya hecha
      la fecha de inicio de una reserva no este entre las fechas de la nueva reserva
     */
    @Query("SELECT r FROM Reserva r WHERE r.vehiculo.id = :vehiculoId AND r.id <> :reservaId AND " +
            "(:nuevaFechaInicio BETWEEN r.fechaInicio AND r.fechaFin OR " +
            ":nuevaFechaFin BETWEEN r.fechaInicio AND r.fechaFin OR " +
            "r.fechaInicio BETWEEN :nuevaFechaInicio AND :nuevaFechaFin)")
    List<Reserva> buscarReservasEnConflictoAlActualizar(@Param("vehiculoId") Long vehiculoId,
                                                        @Param("nuevaFechaInicio") LocalDate nuevaFechaInicio,
                                                        @Param("nuevaFechaFin") LocalDate nuevaFechaFin,
                                                        @Param("reservaId") Long reservaId);

    //Obtiene el historial de reservas para un cliente en específico
    List<Reserva> findByClienteId(Long clienteId);
}
