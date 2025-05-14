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

    /**
     * Endpoint para crear una nueva reserva.
     * <br>
     * Este metodo recibe un objeto "Reserva", lo pasa al servicio para guardarlo
     * en la base de datos y devuelve la reserva creada.
     *
     * @param reserva el objeto "Reserva" que se va a crear
     * @return la reserva creada y guardada en la base de datos.
     * @throws RecursoNoEncontradoException si el vehiculo o el cliente no existen en la base de datos.
     * @throws ReservaInvalidaException si el vehiculo ya tiene una reserva para esas fechas.
     */
    @PostMapping
    public Reserva crearReserva(@RequestBody Reserva reserva) throws RecursoNoEncontradoException, ReservaInvalidaException {
        return reservaService.guardar(reserva);
    }

    /**
     * Endpoint para obtener la lista de todas las reservas almacenadas en la base de datos.
     * <br>
     * El metodo llama al servicio para obtener la lista de reservas y devuelve la lista.
     *
     * @return una lista con todas las reservas almacenadas en la base de datos.
     */
    @GetMapping
    public List<Reserva> mostrarReservas() {
        return reservaService.listar();
    }

    /**
     * Endpoint para obtener una reserva almacenada en la base de datos.
     * <br>
     * El metodo llama al servicio para obtener una reserva por medio de su id y devuelve la información de la reserva.
     *
     * @param id el id de la reserva que se quiere obtener de la base de datos.
     * @return la reserva con el id dado.
     * @throws RecursoNoEncontradoException si no existe la reserva con el id dado en la base de datos.
    */
    @GetMapping("/{id}")
    public Reserva mostrarReserva(@PathVariable Long id) throws RecursoNoEncontradoException {
        return reservaService.buscarPorId(id);
    }

    /**
     * Endpoint para actualizar una reserva almacenada en la base de datos.
     * <br>
     * El metodo recibe el id de la reserva que se desea actualizar y un objeto "Reserva" con la información que se desea cambiar y llama al servicio para actualizar la reserva.
     * <br>
     * Solo se actualizan los datos que no sean null.
     *
     * @param id el id de la reserva que se desea actualizar.
     * @param reservaActualizada el objeto "Reserva" con la información que se desea cambiar.
     * @return la reserva con los cambios realizados.
     * @throws RecursoNoEncontradoException si no existe la reserva o el vehiculo con el id dado en la base de datos.
     * @throws ReservaInvalidaException si el vehiculo ya tiene una reserva para esas fechas.
    */
    @PutMapping("/{id}")
    public Reserva actualizarReserva(@PathVariable Long id, @RequestBody Reserva reservaActualizada) throws RecursoNoEncontradoException, ReservaInvalidaException {
        return reservaService.actualizar(id, reservaActualizada);
    }

    /**
     * Endpoint para eliminar una reserva almacenada en la base de datos.
     * <br>
     * El metodo recibe el id de la reserva que se desea eliminar y llama al servicio para eliminar la reserva.
     *
     * @param id el id de la reserva que se desea eliminar.
     * @throws RecursoNoEncontradoException si no existe la reserva con el id dado en la base de datos.
    */
    @DeleteMapping("/{id}") //eliminamos una reserva
    public void eliminarReserva(@PathVariable Long id) throws RecursoNoEncontradoException {
        reservaService.eliminar(id);
    }
}
