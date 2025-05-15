package org.axelgutierrez.piapoo2025.controller;

import jakarta.validation.Valid;
import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.model.Empleado;
import org.axelgutierrez.piapoo2025.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    /**
     * Endpoint para crear un nuevo empleado.
     * <br>
     * Este método recibe un objeto "Empleado", lo pasa al servicio para guardarlo
     * en la base de datos y devuelve el empleado creado.
     *
     * @param empleado el objeto "Empleado" que se va a crear
     * @return el empleado creado y guardado en la base de datos.
     * @throws RecursoNoEncontradoException si no existe el rol del empleado en la base de datos.
     */
    @PostMapping
    public Empleado crearEmpleado(@RequestBody @Valid Empleado empleado) throws RecursoNoEncontradoException {
        return empleadoService.guardar(empleado);
    }

    /**
     * Endpoint para obtener la lista de todos los empleados almacenados en la base de datos.
     * <br>
     * El metodo llama al servicio para obtener la lista de empleados y devuelve la lista.
     *
     * @return una lista con todos los empleados almacenados en la base de datos.
     */
    @GetMapping
    public List<Empleado> mostrarEmpleados() {
        return empleadoService.listar();
    }

    /**
     * Endpoint para obtener un empleado almacenado en la base de datos.
     * <br>
     * El metodo llama al servicio para obtener un empleado por medio de su id y devuelve la información del empleado.
     *
     * @param id el id del empleado que se quiere obtener de la base de datos.
     * @return el empleado con el id dado.
     * @throws RecursoNoEncontradoException si no existe el empleado con el id dado en la base de datos.
    */
    @GetMapping("/{id}")
    public Empleado mostrarEmpleado(@PathVariable Long id) throws RecursoNoEncontradoException {
        return empleadoService.buscarPorId(id);
    }

    /**
     * Endpoint para actualizar un empleado almacenado en la base de datos.
     * <br>
     * El metodo recibe el id del empleado que se desea actualizar y un objeto "Empleado" con la información que se desea cambiar y llama al servicio para actualizar el empleado.
     * <br>
     * Solo se actualizan los datos que no sean null.
     *
     * @param id el id del empleado que se desea actualizar.
     * @param empleadoActualizado el objeto "Empleado" con la información que se desea cambiar.
     * @return el empleado con los cambios realizados.
     * @throws RecursoNoEncontradoException si no existe el empleado con el id dado en la base de datos.
    */
    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoActualizado) throws RecursoNoEncontradoException {
        return empleadoService.actualizar(id, empleadoActualizado);
    }

    /**
     * Endpoint para eliminar un empleado almacenado en la base de datos.
     * <br>
     * El metodo recibe el id del empleado que se desea eliminar y llama al servicio para eliminar el empleado.
     *
     * @param id el id del empleado que se desea eliminar.
     * @throws RecursoNoEncontradoException si no existe el empleado con el id dado en la base de datos.
    */
    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id) throws RecursoNoEncontradoException {
        empleadoService.eliminar(id);
    }
}
