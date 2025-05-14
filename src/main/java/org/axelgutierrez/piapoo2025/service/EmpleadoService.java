package org.axelgutierrez.piapoo2025.service;

import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.model.Direccion;
import org.axelgutierrez.piapoo2025.model.Empleado;
import org.axelgutierrez.piapoo2025.repository.EmpleadoRepository;
import org.axelgutierrez.piapoo2025.repository.RolEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService implements IFuncionesCompartidas<Empleado> {
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private RolEmpleadoRepository rolEmpleadoRepository;

    /**
     * Guarda un empleado en la base de datos.
     * <br>
     * El método recibe un objeto "Empleado" y lo guarda en la base de datos usando el repositorio.
     *
     * @param empleado el empleado que se va a guardar
     * @return el empleado guardado con su id generado automaticamente
     * @throws RecursoNoEncontradoException si no existe el rol del empleado en la base de datos.
     */
    @Override
    public Empleado guardar(Empleado empleado) throws RecursoNoEncontradoException{
        //busca si existe el rol del empleado en la base de datos antes de guardar
        if(!rolEmpleadoRepository.existsById(empleado.getRol().getId())) {
            throw new RecursoNoEncontradoException("Rol no encontrado con id: " + empleado.getRol().getId());
        }

        return empleadoRepository.save(empleado);
    }

    /**
     * Obtiene una lista de todos los empleados en la base de datos.
     * <br>
     * El metodo consulta la base de datos a través del repositorio y devuelve una lista con todos los empleados.
     *
     * @return una lista con todos los empleados almacenados en la base de datos.
     */
    @Override
    public List<Empleado> listar() {
        return (List<Empleado>) empleadoRepository.findAll();
    }

    /**
     * Obtiene un empleado de la base de datos.
     * <br>
     * El metodo consulta la base de datos a través del repositorio y devuelve el empleado con el id dado.
     *
     * @param id el id del empleado que se quiere obtener de la base de datos.
     * @return el empleado con el id dado.
     * @throws RecursoNoEncontradoException si no existe el empleado con el id dado en la base de datos.
    */
    @Override
    public Empleado buscarPorId(Long id) throws RecursoNoEncontradoException {
        //guardamos en un optional el empleado si existe
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if(empleado.isEmpty()) { //si no existe (vacío el optional) lanzamos excepcion
            throw new RecursoNoEncontradoException("Empleado no encontrado con id: " + id);
        }
        return empleado.get(); //si existe, obtenemos al empleado
    }

    /**
     * Actualiza un empleado en la base de datos.
     * <br>
     * El metodo recibe el id del empleado que se desea actualizar y un objeto "Empleado" con la información que se desea cambiar y lo actualiza en la base de datos a través del repositorio.
     * <br>
     * Solo se actualizan los datos que no sean null.
     *
     * @param id el id del empleado que se desea actualizar.
     * @param empleadoActualizado el objeto "Empleado" con la información que se desea cambiar.
     * @return el empleado con los cambios realizados.
     * @throws RecursoNoEncontradoException si no existe el empleado con el id dado en la base de datos.
    */
    @Override
    public Empleado actualizar(Long id, Empleado empleadoActualizado) throws RecursoNoEncontradoException {
        Empleado empleado = buscarPorId(id); //buscamos si existe

        //si no es null actualiza ese campo
        if(empleadoActualizado.getNombre() != null) {
            empleado.setNombre(empleadoActualizado.getNombre());
        }
        if(empleadoActualizado.getApellidos() != null) {
            empleado.setApellidos(empleadoActualizado.getApellidos());
        }
        if(empleadoActualizado.getEmail() != null) {
            empleado.setEmail(empleadoActualizado.getEmail());
        }
        if(empleadoActualizado.getTelefono() != null) {
            empleado.setTelefono(empleadoActualizado.getTelefono());
        }
        if(empleadoActualizado.getDireccion() != null) {
            Direccion direccionNueva = empleadoActualizado.getDireccion();
            if(direccionNueva.getCalle() != null) {
                empleado.getDireccion().setCalle(direccionNueva.getCalle());
            }
            if(direccionNueva.getNumCasa() != null) {
                empleado.getDireccion().setNumCasa(direccionNueva.getNumCasa());
            }
            if(direccionNueva.getColonia() != null) {
                empleado.getDireccion().setColonia(direccionNueva.getColonia());
            }
            if(direccionNueva.getMunicipio() != null) {
                empleado.getDireccion().setMunicipio(direccionNueva.getMunicipio());
            }
            if(direccionNueva.getEstado() != null) {
                empleado.getDireccion().setEstado(direccionNueva.getEstado());
            }
            if(direccionNueva.getCodigoPostal() != null) {
                empleado.getDireccion().setCodigoPostal(direccionNueva.getCodigoPostal());
            }
        }
        if(empleadoActualizado.getRol() != null) { //valida que exista el nuevo rol
            if(!rolEmpleadoRepository.existsById(empleadoActualizado.getRol().getId())) {
                throw new RecursoNoEncontradoException("Rol no encontrado con id: " + empleadoActualizado.getRol().getId());
            }
            empleado.setRol(empleadoActualizado.getRol());
        }

        return empleadoRepository.save(empleado);
    }

    /**
     * Elimina un empleado de la base de datos.
     * <br>
     * El metodo recibe el id del empleado que se desea eliminar y lo elimina de la base de datos a través del repositorio.
     *
     * @param id el id del empleado que se desea eliminar.
     * @throws RecursoNoEncontradoException si no existe el empleado con el id dado en la base de datos.
    */
    @Override
    public void eliminar(Long id) throws RecursoNoEncontradoException {
        if(!empleadoRepository.existsById(id)) { //revisa que existe el empleado
            throw new RecursoNoEncontradoException("Empleado no encontrado con id: " + id);
        }
        empleadoRepository.deleteById(id); //lo elimina
    }
}
