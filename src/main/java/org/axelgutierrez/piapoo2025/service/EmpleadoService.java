package org.axelgutierrez.piapoo2025.service;

import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.model.Empleado;
import org.axelgutierrez.piapoo2025.repository.EmpleadoRepository;
import org.axelgutierrez.piapoo2025.repository.RolEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private RolEmpleadoRepository rolEmpleadoRepository;

    public Empleado guardarEmpleado(Empleado empleado) throws RecursoNoEncontradoException{
        //busca si existe el rol del empleado en la base de datos antes de guardar
        if(!rolEmpleadoRepository.existsById(empleado.getRol().getId())) {
            throw new RecursoNoEncontradoException("Rol no encontrado con id: " + empleado.getRol().getId());
        }

        return empleadoRepository.save(empleado);
    }

    public List<Empleado> listarEmpleados() {
        return (List<Empleado>) empleadoRepository.findAll();
    }

    public Empleado buscarEmpleadoPorId(Long id) throws RecursoNoEncontradoException {
        //guardamos en un optional el empleado si existe
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if(empleado.isEmpty()) { //si no existe (vacío el optional) lanzamos excepcion
            throw new RecursoNoEncontradoException("Empleado no encontrado con id: " + id);
        }
        return empleado.get(); //si existe, obtenemos al empleado
    }

    public Empleado actualizarEmpleado(Long id, Empleado empleadoActualizado) throws RecursoNoEncontradoException {
        Empleado empleado = buscarEmpleadoPorId(id); //buscamos si existe

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
            empleado.setDireccion(empleadoActualizado.getDireccion());
        }
        if(empleadoActualizado.getRol() != null) { //valida que exista el nuevo rol
            if(!rolEmpleadoRepository.existsById(empleadoActualizado.getRol().getId())) {
                throw new RecursoNoEncontradoException("Rol no encontrado con id: " + empleadoActualizado.getRol().getId());
            }
            empleado.setRol(empleadoActualizado.getRol());
        }

        return empleadoRepository.save(empleado);
    }

    public void eliminarEmpleado(Long id) throws RecursoNoEncontradoException {
        if(!empleadoRepository.existsById(id)) { //revisa que existe el empleado
            throw new RecursoNoEncontradoException("Empleado no encontrado con id: " + id);
        }
        empleadoRepository.deleteById(id); //lo elimina
    }
}
