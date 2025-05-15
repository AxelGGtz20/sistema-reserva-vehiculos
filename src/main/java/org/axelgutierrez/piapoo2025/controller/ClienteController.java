package org.axelgutierrez.piapoo2025.controller;

import jakarta.validation.Valid;
import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.model.Cliente;
import org.axelgutierrez.piapoo2025.model.Reserva;
import org.axelgutierrez.piapoo2025.service.ClienteService;
import org.axelgutierrez.piapoo2025.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ReservaService reservaService;

    /**
     * Endpoint para crear un nuevo cliente.
     * <br>
     * Este método recibe un objeto "Cliente", lo pasa al servicio para guardarlo
     * en la base de datos y devuelve el cliente creado.
     *
     * @param cliente el objeto "Cliente" que se va a crear
     * @return el cliente creado y guardado en la base de datos.
     */
    @PostMapping
    public Cliente crearCliente(@RequestBody @Valid Cliente cliente) {
        return clienteService.guardar(cliente);
    }

    /**
     * Endpoint para obtener la lista de todos los clientes almacenados en la base de datos.
     * <br>
     * El metodo llama al servicio para obtener la lista de clientes y devuelve la lista.
     *
     * @return una lista con todos los clientes almacenados en la base de datos.
     */
    @GetMapping
    public List<Cliente> mostrarClientes() {
        return clienteService.listar();
    }


    /**
     * Endpoint para obtener un cliente almacenado en la base de datos.
     * <br>
     * El metodo llama al servicio para obtener un cliente por medio de su id y devuelve la información del cliente.
     *
     * @param id el id del cliente que se quiere obtener de la base de datos.
     * @return el cliente con el id dado.
     * @throws RecursoNoEncontradoException si no existe el cliente con el id dado en la base de datos.
     */
    @GetMapping("/{id}")
    public Cliente mostrarCliente(@PathVariable Long id) throws RecursoNoEncontradoException {
        return clienteService.buscarPorId(id);
    }

    /**
     * Endpoint para obtener la lista de todas las reservas de un cliente.
     * <br>
     * El metodo llama al servicio de reservas para obtener la lista de reservas que tiene un cliente y devuelve la lista.
     *
     * @param id el id del cliente que se desea conocer su historial.
     * @return una lista con todos los clientes que tienen una reserva en la base de datos.
     * @throws RecursoNoEncontradoException si no existe el cliente con el id dado en la base de datos.
     */
    @GetMapping("/{id}/reservas")
    public List<Reserva> historialReservasCliente(@PathVariable Long id) throws RecursoNoEncontradoException{
        return reservaService.obtenerReservasPorCliente(id);
    }

    /**
     * Endpoint para actualizar un cliente almacenado en la base de datos.
     * <br>
     * El metodo recibe el id del cliente que se desea actualizar y un objeto "Cliente" con la información que se desea cambiar y llama al servicio para actualizar el cliente.
     * <br>
     * Solo se actualizan los datos que no sean null.
     *
     * @param id el id del cliente que se desea actualizar.
     * @param clienteActualizado el objeto "Cliente" con la información que se desea cambiar.
     * @return el cliente con los cambios realizados.
     * @throws RecursoNoEncontradoException si no existe el cliente con el id dado en la base de datos.
     */
    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) throws RecursoNoEncontradoException{
        return clienteService.actualizar(id, clienteActualizado);
    }

    /**
     * Endpoint para eliminar un cliente almacenado en la base de datos.
     * <br>
     * El metodo recibe el id del cliente que se desea eliminar y llama al servicio para eliminar el cliente.
     *
     * @param id el id del cliente que se desea eliminar.
     * @throws RecursoNoEncontradoException si no existe el cliente con el id dado en la base de datos.
     */
    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) throws RecursoNoEncontradoException {
        clienteService.eliminar(id);
    }
}
