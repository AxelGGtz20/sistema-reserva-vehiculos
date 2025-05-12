package org.axelgutierrez.piapoo2025.service;

import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.model.Cliente;
import org.axelgutierrez.piapoo2025.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Long id) throws RecursoNoEncontradoException {
        //guardamos en un optional el cliente si existe
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()) { //si no existe (vacío el optional) lanzamos excepcion
            throw new RecursoNoEncontradoException("Cliente no encontrado con id: " + id);
        }
        return cliente.get(); //si existe, obtenemos al cliente
    }

    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) throws RecursoNoEncontradoException {
        Cliente cliente = buscarClientePorId(id); //buscamos si existe

        //si es null no lo quiere actualizar, si NO es null, actualizamos el atributo
        if (clienteActualizado.getNombre() != null) {
            cliente.setNombre(clienteActualizado.getNombre());
        }
        if (clienteActualizado.getApellidos() != null) {
            cliente.setApellidos(clienteActualizado.getApellidos());
        }
        if (clienteActualizado.getEmail() != null) {
            cliente.setEmail(clienteActualizado.getEmail());
        }
        if (clienteActualizado.getTelefono() != null) {
            cliente.setTelefono(clienteActualizado.getTelefono());
        }
        if (clienteActualizado.getDireccion() != null) { //la dirección tiene que actualizarse completamente
            cliente.setDireccion(clienteActualizado.getDireccion());
        }

        return clienteRepository.save(cliente); //sobreescribimos (solo los datos nuevos)
    }

    public void eliminarCliente(Long id) throws RecursoNoEncontradoException {
        if(!clienteRepository.existsById(id)) {//si no existe el id en la bdd lanza excepcion
            throw new RecursoNoEncontradoException("Cliente no encontrado con id: " + id);
        }
        clienteRepository.deleteById(id);//si no existe lo elimina
    }
}
