package org.axelgutierrez.piapoo2025.controller;

import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.model.Cliente;
import org.axelgutierrez.piapoo2025.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    //Guardar cliente en la base de datos
    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteService.guardarCliente(cliente);
    }

    //Mostrar la lista de los clientes en la base de datos
    @GetMapping
    public List<Cliente> mostrarClientes() {
        return clienteService.listarClientes();
    }

    //mostrar un cliente buscado por ID
    @GetMapping("/{id}")
    public Cliente mostrarCliente(@PathVariable Long id) throws RecursoNoEncontradoException {
        return clienteService.buscarClientePorId(id);
    }

    //actualizar la informacion de un cliente
    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) throws RecursoNoEncontradoException{
        return clienteService.actualizarCliente(id, clienteActualizado);
    }

    //eliminamos al Cliente de la base de datos
    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) throws RecursoNoEncontradoException {
        clienteService.eliminarCliente(id);
    }
}
