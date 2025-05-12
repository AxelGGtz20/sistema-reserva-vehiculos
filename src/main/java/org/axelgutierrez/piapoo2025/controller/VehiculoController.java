package org.axelgutierrez.piapoo2025.controller;

import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.model.Vehiculo;
import org.axelgutierrez.piapoo2025.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    //Guardar vehiculos
    @PostMapping
    public Vehiculo crearVehiculo(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.guardarVehiculo(vehiculo);
    }

    //Mostrar la lista de los Vehiculos
    @GetMapping
    public List<Vehiculo> mostrarVehiculos() {
        return vehiculoService.listarVehiculos();
    }

    //mostrar un vehiculo buscado por ID
    @GetMapping("/{id}")
    public Vehiculo mostrarVehiculo(@PathVariable Long id) throws RecursoNoEncontradoException {
        return vehiculoService.buscarVehiculoPorId(id);
    }

    //Actualizar la información del Vehiculo
    @PutMapping("/{id}")
    public Vehiculo actualizarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculoActualizado) throws RecursoNoEncontradoException {
        return vehiculoService.actualizarVehiculo(id, vehiculoActualizado);
    }

    //Eliminar Vehiculo
    @DeleteMapping("/{id}")
    public void eliminarVehiculo(@PathVariable Long id) throws RecursoNoEncontradoException {
        vehiculoService.eliminarVehiculo(id);
    }

    //Mostrar Vehiculos dependiendo de su Estado DISPONIBLE o EN_MANTENIMIENTO
    @GetMapping("/estado/{estado}")
    public List<Vehiculo> obtenerPorEstado(@PathVariable String estado) {
        return vehiculoService.obtenerPorEstado(estado);
    }

    //Mostrar Vehiculos con un Precio por día mayor o igual al ingresado
    @GetMapping("/precio-maximo/{precioPorDia}")
    public List<Vehiculo> obtenerPorPrecioMaximo(@PathVariable Double precioPorDia) {
        return vehiculoService.obtenerPorPrecioMaximo(precioPorDia);
    }
}
