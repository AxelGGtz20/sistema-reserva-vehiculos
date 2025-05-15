package org.axelgutierrez.piapoo2025.service;

import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.exception.ReservaInvalidaException;
import org.axelgutierrez.piapoo2025.model.Reserva;
import org.axelgutierrez.piapoo2025.model.Vehiculo;
import org.axelgutierrez.piapoo2025.repository.ClienteRepository;
import org.axelgutierrez.piapoo2025.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService implements IFuncionesCompartidas<Reserva> {
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private VehiculoService vehiculoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Guarda una reserva en la base de datos.
     * <br>
     * El método recibe un objeto "Reserva" y lo guarda en la base de datos usando el repositorio.
     * <br>
     * Primero se valida que el vehiculo exista y que no tenga una reserva para esas fechas.
     * <br>
     * Si no hay ningún problema, se calcula el precio total de la reserva y se guarda en el objeto "Reserva".
     *
     * @param reserva el objeto "Reserva" que se va a guardar en la base de datos.
     * @return el objeto "Reserva" guardado con su id generado automaticamente.
     * @throws ReservaInvalidaException si el vehiculo ya tiene una reserva para esas fechas.
     * @throws RecursoNoEncontradoException si el vehiculo o el cliente no existen en la base de datos.
     */
    @Override
    public Reserva guardar(Reserva reserva) throws ReservaInvalidaException, RecursoNoEncontradoException {

        //validamos que el vehiculo exista en la base de datos
        Vehiculo vehiculo = vehiculoService.buscarPorId(reserva.getVehiculo().getId());
        //validamos que el cliente exista en la base de datos
        clienteService.buscarPorId(reserva.getCliente().getId());
        //validamos que el vehiculo no se encuentre en mantenimiento
        if(vehiculo.getEstado().equals("EN_MANTENIMIENTO")) {
            throw new ReservaInvalidaException("El vehiculo se encuentra en mantenimiento");
        }


        //Buscamos que el vehiculo no este reservado para esa fecha
        List<Reserva> reservasEnConflicto = reservaRepository.buscarReservasEnConflicto(vehiculo.getId(), reserva.getFechaInicio(), reserva.getFechaFin());

        //si la lista tiene elementos ya hay una reserva para esa fecha
        if(!reservasEnConflicto.isEmpty()) {
            throw new ReservaInvalidaException("El vehiculo ya esta reservado para esa fecha");
        }

        //Calcula los dias de la reserva (se le suma 1 por que no cuenta el ultimo)
        long dias = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin()) + 1;
        Double precioTotal = vehiculo.getPrecioPorDia() * dias;
        reserva.setPrecioTotal(precioTotal);

        return reservaRepository.save(reserva);
    }

    /**
     * Obtiene una lista de todas las reservas en la base de datos.
     * <br>
     * El metodo consulta la base de datos a través del repositorio y devuelve una lista con todas las reservas.
     *
     * @return una lista con todas las reservas almacenadas en la base de datos.
     */
    @Override
    public List<Reserva> listar() {
        return (List<Reserva>) reservaRepository.findAll();
    }

    /**
     * Obtiene una reserva de la base de datos.
     * <br>
     * El metodo consulta la base de datos a través del repositorio y devuelve la reserva con el id dado.
     *
     * @param Id el id de la reserva que se quiere obtener de la base de datos.
     * @return la reserva con el id dado.
     * @throws RecursoNoEncontradoException si no existe la reserva con el id dado en la base de datos.
    */
    @Override
    public Reserva buscarPorId(Long Id) throws RecursoNoEncontradoException {
        Optional<Reserva> reserva = reservaRepository.findById(Id);
        if(reserva.isEmpty()) {
            throw new RecursoNoEncontradoException("Reserva no encontrada con id: " + Id);
        }
        return reserva.get();
    }

    /**
     * Actualiza una reserva en la base de datos.
     * <br>
     * El metodo recibe el id de la reserva que se desea actualizar y un objeto "Reserva" con la información que se desea cambiar y lo actualiza en la base de datos a través del repositorio.
     *
     * @param Id el id de la reserva que se desea actualizar.
     * @param reservaActualizada el objeto "Reserva" con la información que se desea cambiar.
     * @return la reserva con los cambios realizados.
     * @throws RecursoNoEncontradoException si no existe la reserva  o el vehiculo con el id dado en la base de datos.
     * @throws ReservaInvalidaException si el vehiculo ya tiene una reserva para esas fechas.
     */
    @Override
    public Reserva actualizar(Long Id, Reserva reservaActualizada) throws RecursoNoEncontradoException, ReservaInvalidaException {
        Reserva reserva = buscarPorId(Id); //buscamos si existe, lanza excepcion si no

        //Guardamos los valores anteriores para facilitar la validacion
        LocalDate nuevaFechaInicio = reserva.getFechaInicio();
        LocalDate nuevaFechaFin = reserva.getFechaFin();
        Vehiculo nuevoVehiculo = reserva.getVehiculo();
        boolean validar = false;

        //si no es null, actualiza ese campo
        if (reservaActualizada.getFechaInicio() != null) {
            nuevaFechaInicio = reservaActualizada.getFechaInicio();
            validar = true;
        }
        if (reservaActualizada.getFechaFin() != null) {
            nuevaFechaFin = reservaActualizada.getFechaFin();
            validar = true;
        }
        if (reservaActualizada.getVehiculo() != null) {
            nuevoVehiculo = reservaActualizada.getVehiculo();
            vehiculoService.buscarPorId(nuevoVehiculo.getId());
            validar = true;
        }

        //si actualizan algun campo debemos re validar los datos y posibles solapamientos
        List<Reserva> reservasEnConflictos = new ArrayList<>();
        if (validar) {
            reservasEnConflictos = reservaRepository.buscarReservasEnConflictoAlActualizar(
                    nuevoVehiculo.getId(), nuevaFechaInicio, nuevaFechaFin, reserva.getId());
        }
        if (!reservasEnConflictos.isEmpty()) {
            throw new ReservaInvalidaException("No se puede actualizar, ya hay una reserva para el mismo vehiculo en esas fechas");
        }

        //después de la validación, actualizamos los campos que mando el usuario
        // Solo actualizamos los campos que llegaron
        if (reservaActualizada.getFechaInicio() != null) {
            reserva.setFechaInicio(nuevaFechaInicio);
        }
        if (reservaActualizada.getFechaFin() != null) {
            reserva.setFechaFin(nuevaFechaFin);
        }
        if (reservaActualizada.getVehiculo() != null) {
            reserva.setVehiculo(nuevoVehiculo);
        }

        //Calcula los dias de la reserva (se le suma 1 por que no cuenta el ultimo)
        long dias = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin()) + 1;
        Double precioTotal = reserva.getVehiculo().getPrecioPorDia() * dias;
        reserva.setPrecioTotal(precioTotal);

        return reservaRepository.save(reserva);
    }

    /**
     * Elimina una reserva de la base de datos.
     * <br>
     * El metodo recibe el id de la reserva que se desea eliminar y lo elimina de la base de datos a través del repositorio.
     *
     * @param Id el id de la reserva que se desea eliminar.
     * @throws RecursoNoEncontradoException si no existe la reserva con el id dado en la base de datos.
     */
    @Override
    public void eliminar(Long Id) throws RecursoNoEncontradoException {
        if(!reservaRepository.existsById(Id)) { //si no existe lanza excepcion
            throw new RecursoNoEncontradoException("Reserva no encontrada con id: " + Id);
        }
        reservaRepository.deleteById(Id); //si existe la eliminamos
    }

    /**
     * Obtiene el historial de reservas de un cliente.
     * <br>
     * El metodo recibe el id del cliente del cual desea conocer su historial de reservas y lo obtiene de la base de datos a través del repositorio.
     * @param clienteId el id del cliente el cual se desee conocer su historial.
     * @return la lista de reservas del cliente.
     * @throws RecursoNoEncontradoException si el cliente no existe en la base de datos.
     */
    public List<Reserva> obtenerReservasPorCliente(Long clienteId) throws RecursoNoEncontradoException {
        if(!clienteRepository.existsById(clienteId)) {
            throw new RecursoNoEncontradoException("Cliente no encontrado con id: " + clienteId);
        }
        return reservaRepository.findByClienteId(clienteId);
    }
}
