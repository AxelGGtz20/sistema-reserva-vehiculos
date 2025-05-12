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

    public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public List<Vehiculo> listarVehiculos() {
        return (List<Vehiculo>) vehiculoRepository.findAll();
    }

    public Vehiculo buscarVehiculoPorId(Long id) throws RecursoNoEncontradoException {
        //guardamos en un optional el vehiculo si existe
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(id);
        if(vehiculo.isEmpty()) { //si no existe (vacío el optional) lanzamos excepcion
            throw new RecursoNoEncontradoException("Vehiculo no encontrado con id: " + id);
        }
        return vehiculo.get(); //si existe, obtenemos el vehiculo
    }

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

    public void eliminarVehiculo(Long id) throws RecursoNoEncontradoException {
        if(!vehiculoRepository.existsById(id)) { //si no existe lanza excepcion
            throw new RecursoNoEncontradoException("Vehiculo no encontrado con id: " + id);
        }
        vehiculoRepository.deleteById(id); //si existe lo elimina
    }

    //busca por estado (DISPONIBLE O EN_MANTENIMIENTO)
    public List<Vehiculo> obtenerPorEstado(String estado) {
        return vehiculoRepository.findByEstado(estado);
    }

    //busca todos los vehiculos con un precioPorDia menor o igualal querido
    public List<Vehiculo> obtenerPorPrecioMaximo(Double precioPorDia) {
        return vehiculoRepository.findByPrecioPorDiaLessThanEqualOrderByPrecioPorDiaDesc(precioPorDia);
    }
}
