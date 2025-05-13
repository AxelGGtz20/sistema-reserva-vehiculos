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

    /**
     * Endpoint para crear un nuevo Vehiculo.
     * <br>
     * Este método recibe un objeto "Vehiculo", lo pasa al servicio para guardarlo
     * en la base de datos y devuelve el vehiculo creado.
     *
     * @param vehiculo el objeto "Vehiculo" que se va a crear
     * @return el vehiculo creado y guardado en la base de datos.
     */
    @PostMapping
    public Vehiculo crearVehiculo(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.guardarVehiculo(vehiculo);
    }

    /**
     * Endpoint para obtener la lista de todos los vehiculos almacenados en la base de datos.
     * <br>
     * El metodo llama al servicio para obtener la lista de vehiculos y devuelve la lista.
     *
     * @return una lista con todos los vehiculos almacenados en la base de datos.
     */
    @GetMapping
    public List<Vehiculo> mostrarVehiculos() {
        return vehiculoService.listarVehiculos();
    }

    /**
     * Endpoint para obtener un vehiculo almacenado en la base de datos.
     * <br>
     * El metodo llama al servicio para obtener un vehiculo por medio de su id y devuelve la información del vehiculo.
     *
     * @param id el id del vehiculo que se quiere obtener de la base de datos.
     * @return el vehiculo con el id dado.
     * @throws RecursoNoEncontradoException si no existe el vehiculo con el id dado en la base de datos.
     */
    @GetMapping("/{id}")
    public Vehiculo mostrarVehiculo(@PathVariable Long id) throws RecursoNoEncontradoException {
        return vehiculoService.buscarVehiculoPorId(id);
    }

    /**
     * Endpoint para actualizar un vehiculo almacenado en la base de datos.
     * <br>
     * El metodo recibe el id del vehiculo que se desea actualizar y un objeto "Vehiculo" con la información que se desea cambiar y llama al servicio para actualizar el vehiculo.
     * <br>
     * Solo se actualizan los datos que no sean null.
     *
     * @param id el id del vehiculo que se desea actualizar.
     * @param vehiculoActualizado el objeto "Vehiculo" con la información que se desea cambiar.
     * @return el vehiculo con los cambios realizados.
     * @throws RecursoNoEncontradoException si no existe el vehiculo con el id dado en la base de datos.
     */
    @PutMapping("/{id}")
    public Vehiculo actualizarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculoActualizado) throws RecursoNoEncontradoException {
        return vehiculoService.actualizarVehiculo(id, vehiculoActualizado);
    }

    /**
     * Endpoint para eliminar un vehiculo almacenado en la base de datos.
     * <br>
     * El metodo recibe el id del vehiculo que se desea eliminar y llama al servicio para eliminar el vehiculo.
     *
     * @param id el id del vehiculo que se desea eliminar.
     * @throws RecursoNoEncontradoException si no existe el vehiculo con el id dado en la base de datos.
     */
    @DeleteMapping("/{id}")
    public void eliminarVehiculo(@PathVariable Long id) throws RecursoNoEncontradoException {
        vehiculoService.eliminarVehiculo(id);
    }

    /**
     * Endpoint para obtener todos los vehiculos almacenados en la base de datos que tengan un estado dado.
     * <br>
     * El metodo llama al servicio para obtener todos los vehiculos que tengan un estado dado y devuelve la lista.
     *
     * @param estado el estado del vehiculo que se quiere obtener.
     * @return una lista con todos los vehiculos almacenados en la base de datos que tengan un estado dado.
     */
    @GetMapping("/estado/{estado}")
    public List<Vehiculo> obtenerPorEstado(@PathVariable String estado) {
        return vehiculoService.obtenerPorEstado(estado);
    }

    /**
     * Endpoint para obtener todos los vehiculos almacenados en la base de datos que tengan un precio por día maximo dado.
     * <br>
     * El metodo llama al servicio para obtener todos los vehiculos que tengan un precio por día maximo dado y devuelve la lista.
     *
     * @param precioPorDia el precio por día maximo del vehiculo que se quiere obtener.
     * @return una lista con todos los vehiculos almacenados en la base de datos que tengan un precio por día maximo dado.
     */
    @GetMapping("/precio-maximo/{precioPorDia}")
    public List<Vehiculo> obtenerPorPrecioMaximo(@PathVariable Double precioPorDia) {
        return vehiculoService.obtenerPorPrecioMaximo(precioPorDia);
    }
}
