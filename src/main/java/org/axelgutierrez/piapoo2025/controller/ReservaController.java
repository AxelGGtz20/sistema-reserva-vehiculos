package org.axelgutierrez.piapoo2025.controller;

import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.exception.ReservaInvalidaException;
import org.axelgutierrez.piapoo2025.model.Reserva;
import org.axelgutierrez.piapoo2025.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @PostMapping //guardamos la reserva en la base de datos
    public Reserva crearReserva(@RequestBody Reserva reserva) throws RecursoNoEncontradoException, ReservaInvalidaException {
        return reservaService.guardarReserva(reserva);
    }

    @GetMapping //mostramos la lista de reservas en la base de datos
    public List<Reserva> mostrarReservas() {
        return reservaService.listarReservas();
    }

    @GetMapping("/{id}") //mostramos una reserva buscada por su id
    public Reserva mostrarReserva(@PathVariable Long id) throws RecursoNoEncontradoException {
        return reservaService.buscarReservaPorId(id);
    }

    @PutMapping("/{id}") //actualizamos una reserva
    public Reserva actualizarReserva(@PathVariable Long id, @RequestBody Reserva reservaActualizada) throws RecursoNoEncontradoException, ReservaInvalidaException {
        return reservaService.actualizarReserva(id, reservaActualizada);
    }

    @DeleteMapping("/{id}") //eliminamos una reserva
    public void eliminarReserva(@PathVariable Long id) throws RecursoNoEncontradoException {
        reservaService.eliminarReserva(id);
    }
}
