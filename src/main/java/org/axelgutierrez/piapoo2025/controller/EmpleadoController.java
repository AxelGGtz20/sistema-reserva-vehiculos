package org.axelgutierrez.piapoo2025.controller;

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

    @PostMapping //guardar empleado en la base de datos
    public Empleado crearEmpleado(@RequestBody Empleado empleado) throws RecursoNoEncontradoException {
        return empleadoService.guardarEmpleado(empleado);
    }

    @GetMapping //mostrar la lista de empleados
    public List<Empleado> mostrarEmpleados() {
        return empleadoService.listarEmpleados();
    }

    @GetMapping("/{id}") //muestra un empleado buscado por su id
    public Empleado mostrarEmpleado(@PathVariable Long id) throws RecursoNoEncontradoException {
        return empleadoService.buscarEmpleadoPorId(id);
    }

    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoActualizado) throws RecursoNoEncontradoException {
        return empleadoService.actualizarEmpleado(id, empleadoActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id) throws RecursoNoEncontradoException {
        empleadoService.eliminarEmpleado(id);
    }
}
