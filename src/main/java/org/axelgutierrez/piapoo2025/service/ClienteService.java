package org.axelgutierrez.piapoo2025.service;

import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.model.Cliente;
import org.axelgutierrez.piapoo2025.model.Direccion;
import org.axelgutierrez.piapoo2025.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IFuncionesCompartidas<Cliente> {
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Guarda un cliente en la base de datos.
     * <br>
     * El método recibe un objeto "Cliente" y lo guarda en la base de datos usando el repositorio.
     *
     * @param cliente el cliente que se va a guardar
     * @return el cliente guardado con su id generado automaticamente
     */
    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Obtiene una lista de todos los clientes en la base de datos.
     * <br>
     * El metodo consulta la base de datos a través del repositorio y devuelve una lista con todos los clientes.
     *
     * @return una lista con todos los clientes almacenados en la base de datos.
     */
    @Override
    public List<Cliente> listar() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    /**
     * Obtiene un cliente de la base de datos.
     * <br>
     * El metodo consulta la base de datos a través del repositorio y devuelve el cliente con el id dado.
     *
     * @param id el id del cliente que se quiere obtener de la base de datos.
     * @return el cliente con el id dado.
     * @throws RecursoNoEncontradoException si no existe el cliente con el id dado en la base de datos.
     */
    @Override
    public Cliente buscarPorId(Long id) throws RecursoNoEncontradoException {
        //guardamos en un optional el cliente si existe
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()) { //si no existe (vacío el optional) lanzamos excepcion
            throw new RecursoNoEncontradoException("Cliente no encontrado con id: " + id);
        }
        return cliente.get(); //si existe, obtenemos al cliente
    }

    /**
     * Actualiza un cliente en la base de datos.
     * <br>
     * El metodo recibe el id del cliente que se desea actualizar y un objeto "Cliente" con la información que se desea cambiar y lo actualiza en la base de datos a través del repositorio.
     *<br>
     * Solo se actualizan los datos que no sean null.
     *
     * @param id el id del cliente que se desea actualizar.
     * @param clienteActualizado el objeto "Cliente" con la información que se desea cambiar.
     * @return el cliente con los cambios realizados.
     * @throws RecursoNoEncontradoException si no existe el cliente con el id dado en la base de datos.
     */
    @Override
    public Cliente actualizar(Long id, Cliente clienteActualizado) throws RecursoNoEncontradoException {
        Cliente cliente = buscarPorId(id); //buscamos si existe

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
        if (clienteActualizado.getDireccion() != null) {
            Direccion direccionNueva = clienteActualizado.getDireccion();
            if(direccionNueva.getCalle() != null) {
                cliente.getDireccion().setCalle(direccionNueva.getCalle());
            }
            if(direccionNueva.getNumCasa() != null) {
                cliente.getDireccion().setNumCasa(direccionNueva.getNumCasa());
            }
            if(direccionNueva.getColonia() != null) {
                cliente.getDireccion().setColonia(direccionNueva.getColonia());
            }
            if(direccionNueva.getMunicipio() != null) {
                cliente.getDireccion().setMunicipio(direccionNueva.getMunicipio());
            }
            if(direccionNueva.getEstado() != null) {
                cliente.getDireccion().setEstado(direccionNueva.getEstado());
            }
            if(direccionNueva.getCodigoPostal() != null) {
                cliente.getDireccion().setCodigoPostal(direccionNueva.getCodigoPostal());
            }
        }

        return clienteRepository.save(cliente); //sobreescribimos (solo los datos nuevos)
    }

    /**
     * Elimina un cliente de la base de datos.
     * <br>
     * El metodo recibe el id del cliente que se desea eliminar y lo elimina de la base de datos a través del repositorio.
     *
     * @param id el id del cliente que se desea eliminar.
     * @throws RecursoNoEncontradoException si no existe el cliente con el id dado en la base de datos.
    */
    @Override
    public void eliminar(Long id) throws RecursoNoEncontradoException {
        if(!clienteRepository.existsById(id)) {//si no existe el id en la bdd lanza excepcion
            throw new RecursoNoEncontradoException("Cliente no encontrado con id: " + id);
        }
        clienteRepository.deleteById(id);//si no existe lo elimina
    }
}
