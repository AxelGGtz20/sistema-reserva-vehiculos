package org.axelgutierrez.piapoo2025.service;

import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.exception.ReservaInvalidaException;
import org.axelgutierrez.piapoo2025.model.Reserva;
import org.axelgutierrez.piapoo2025.model.Vehiculo;
import org.axelgutierrez.piapoo2025.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private VehiculoService vehiculoService;

    public Reserva guardarReserva(Reserva reserva) throws ReservaInvalidaException, RecursoNoEncontradoException {

        Vehiculo vehiculo = vehiculoService.buscarVehiculoPorId(reserva.getVehiculo().getId());
        //validar que el cliente exista?

        //Buscamos que el vehiculo no este reservado para esa fecha
        List<Reserva> reservasEnConflicto = reservaRepository.buscarReservasEnConflicto(reserva.getVehiculo().getId(), reserva.getFechaInicio(), reserva.getFechaFin());

        //si la lista tiene elementos ya hay una reserva para esa fecha
        if(!reservasEnConflicto.isEmpty()) {
            throw new ReservaInvalidaException("EL vehiculo ya esta reservado para esa fecha");
        }

        //Calcula los dias de la reserva (se le suma 1 por que no cuenta el ultimo)
        long dias = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin()) + 1;
        Double precioTotal = vehiculo.getPrecioPorDia() * dias;
        reserva.setPrecioTotal(precioTotal);

        return reservaRepository.save(reserva);
    }

    public List<Reserva> listarReservas() {
        return (List<Reserva>) reservaRepository.findAll();
    }

    public Reserva buscarReservaPorId(Long Id) throws RecursoNoEncontradoException {
        Optional<Reserva> reserva = reservaRepository.findById(Id);
        if(reserva.isEmpty()) {
            throw new RecursoNoEncontradoException("Reserva no encontrada con id: " + Id);
        }
        return reserva.get();
    }

    public Reserva actualizarReserva(Long Id, Reserva reservaActualizada) throws RecursoNoEncontradoException, ReservaInvalidaException {
        Reserva reserva = buscarReservaPorId(Id); //buscamos si existe, lanza excepcion si no

        //Guardamos los valores anteriores para facilitar la validacion
        LocalDate nuevaFechaInicio = reserva.getFechaInicio();
        LocalDate nuevaFechaFin = reserva.getFechaFin();
        Vehiculo nuevoVehiculo = reserva.getVehiculo();
        boolean validar = false;

        //si no es null, actualiza ese campo
        if (reservaActualizada.getFechaInicio() != null) {
            nuevaFechaInicio = reservaActualizada.getFechaInicio();
            validar = true;
        }
        if (reservaActualizada.getFechaFin() != null) {
            nuevaFechaFin = reservaActualizada.getFechaFin();
            validar = true;
        }
        if (reservaActualizada.getVehiculo() != null) {
            nuevoVehiculo = reservaActualizada.getVehiculo();
            vehiculoService.buscarVehiculoPorId(nuevoVehiculo.getId());
            validar = true;
        }

        //si actualizan algun campo debemos re validar los datos y posibles solapamientos
        List<Reserva> reservasEnConflictos = new ArrayList<>();
        if (validar) {
            reservasEnConflictos = reservaRepository.buscarReservasEnConflictoAlActualizar(
                    nuevoVehiculo.getId(), nuevaFechaInicio, nuevaFechaFin, reserva.getId());
        }
        if (!reservasEnConflictos.isEmpty()) {
            throw new ReservaInvalidaException("No se puede actualizar, ya hay una reserva para el mismo vehiculo en esas fechas");
        }

        //después de la validación, actualizamos los campos que mando el usuario
        // Solo actualizamos los campos que llegaron
        if (reservaActualizada.getFechaInicio() != null) {
            reserva.setFechaInicio(nuevaFechaInicio);
        }
        if (reservaActualizada.getFechaFin() != null) {
            reserva.setFechaFin(nuevaFechaFin);
        }
        if (reservaActualizada.getVehiculo() != null) {
            reserva.setVehiculo(nuevoVehiculo);
        }

        //Calcula los dias de la reserva (se le suma 1 por que no cuenta el ultimo)
        long dias = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin()) + 1;
        Double precioTotal = reserva.getVehiculo().getPrecioPorDia() * dias;
        reserva.setPrecioTotal(precioTotal);

        return reservaRepository.save(reserva);
    }

    public void eliminarReserva(Long Id) throws RecursoNoEncontradoException {
        if(!reservaRepository.existsById(Id)) { //si no existe lanza excepcion
            throw new RecursoNoEncontradoException("Reserva no encontrada con id: " + Id);
        }
        reservaRepository.deleteById(Id); //si existe la eliminamos
    }
}
