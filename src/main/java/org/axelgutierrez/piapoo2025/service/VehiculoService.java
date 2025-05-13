package org.axelgutierrez.piapoo2025.service;

import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.model.Vehiculo;
import org.axelgutierrez.piapoo2025.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    /**
     * Guarda un vehiculo en la base de datos.
     * <br>
     * El método recibe un objeto "Vehiculo" y lo guarda en la base de datos usando el repositorio.
     *
     * @param vehiculo el vehiculo que se va a guardar
     * @return el vehiculo guardado con su id generado automaticamente
     */
    public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    /**
     * Obtiene una lista de todos los vehiculos en la base de datos.
     * <br>
     * El metodo consulta la base de datos a través del repositorio y devuelve una lista con todos los vehiculos.
     *
     * @return una lista con todos los vehiculos almacenados en la base de datos.
     */
    public List<Vehiculo> listarVehiculos() {
        return (List<Vehiculo>) vehiculoRepository.findAll();
    }

    /**
     * Obtiene un vehiculo de la base de datos.
     * <br>
     * El metodo consulta la base de datos a través del repositorio y devuelve el vehiculo con el id dado.
     *
     * @param id el id del vehiculo que se quiere obtener de la base de datos.
     * @return el vehiculo con el id dado.
     * @throws RecursoNoEncontradoException si no existe el vehiculo con el id dado en la base de datos.
    */
    public Vehiculo buscarVehiculoPorId(Long id) throws RecursoNoEncontradoException {
        //guardamos en un optional el vehiculo si existe
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(id);
        if(vehiculo.isEmpty()) { //si no existe (vacío el optional) lanzamos excepcion
            throw new RecursoNoEncontradoException("Vehiculo no encontrado con id: " + id);
        }
        return vehiculo.get(); //si existe, obtenemos el vehiculo
    }

    /**
     * Actualiza un vehiculo en la base de datos.
     * <br>
     * El metodo recibe el id del vehiculo que se desea actualizar y un objeto "Vehiculo" con la información que se desea cambiar y lo actualiza en la base de datos a través del repositorio.
     * <br>
     * Solo se actualizan los datos que no sean null.
     *
     * @param id el id del vehiculo que se desea actualizar.
     * @param vehiculoActualizado el objeto "Vehiculo" con la información que se desea cambiar.
     * @return el vehiculo con los cambios realizados.
     * @throws RecursoNoEncontradoException si no existe el vehiculo con el id dado en la base de datos.
    */
    public Vehiculo actualizarVehiculo(Long id, Vehiculo vehiculoActualizado) throws RecursoNoEncontradoException {
        Vehiculo vehiculo = buscarVehiculoPorId(id); //buscamos si existe

        //si es null no lo quiere actualizar, si NO es null, actualizamos el atributo
        if (vehiculoActualizado.getMarca() != null) {
            vehiculo.setMarca(vehiculoActualizado.getMarca());
        }
        if (vehiculoActualizado.getPlaca() != null) {
            vehiculo.setPlaca(vehiculoActualizado.getPlaca());
        }
        if (vehiculoActualizado.getModelo() != null) {
            vehiculo.setModelo(vehiculoActualizado.getModelo());
        }
        if(vehiculoActualizado.getColor() != null) {
            vehiculo.setColor(vehiculoActualizado.getColor());
        }
        if(vehiculoActualizado.getEstado() != null) {
            vehiculo.setEstado(vehiculoActualizado.getEstado());
        }
        if(vehiculoActualizado.getPrecioPorDia() != null) {
            vehiculo.setPrecioPorDia(vehiculoActualizado.getPrecioPorDia());
        }

        return vehiculoRepository.save(vehiculo); //sobreescribimos los datos pedidos
    }

    /**
     * Elimina un vehiculo de la base de datos.
     * <br>
     * El metodo recibe el id del vehiculo que se desea eliminar y lo elimina de la base de datos a través del repositorio.
     *
     * @param id el id del vehiculo que se desea eliminar.
     * @throws RecursoNoEncontradoException si no existe el vehiculo con el id dado en la base de datos.
    */
    public void eliminarVehiculo(Long id) throws RecursoNoEncontradoException {
        if(!vehiculoRepository.existsById(id)) { //si no existe lanza excepcion
            throw new RecursoNoEncontradoException("Vehiculo no encontrado con id: " + id);
        }
        vehiculoRepository.deleteById(id); //si existe lo elimina
    }

    /**
     * Obtiene todos los vehiculos con un estado dado.
     * <br>
     * El metodo consulta la base de datos a través del repositorio y devuelve una lista con todos los vehiculos con el estado dado.
     *
     * @param estado el estado del vehiculo que se quiere obtener de la base de datos.
     * @return una lista con todos los vehiculos con el estado dado almacenados en la base de datos.
     */
    public List<Vehiculo> obtenerPorEstado(String estado) {
        return vehiculoRepository.findByEstado(estado);
    }

    /**
     * Obtiene todos los vehiculos con un precio por dia menor o igual al dado.
     * <br>
     * El metodo consulta la base de datos a través del repositorio y devuelve una lista con todos los vehiculos con un precio por dia menor o igual al dado.
     *
     * @param precioPorDia el precio por dia de los vehiculos que se quieren obtener de la base de datos.
     * @return una lista con todos los vehiculos con un precio por dia menor o igual al dado, almacenados en la base de datos.
     */
    public List<Vehiculo> obtenerPorPrecioMaximo(Double precioPorDia) {
        return vehiculoRepository.findByPrecioPorDiaLessThanEqualOrderByPrecioPorDiaDesc(precioPorDia);
    }
}
