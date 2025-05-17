package org.axelgutierrez.piapoo2025.service;

import org.axelgutierrez.piapoo2025.exception.RecursoNoEncontradoException;
import org.axelgutierrez.piapoo2025.model.Cliente;
import org.axelgutierrez.piapoo2025.model.Direccion;
import org.axelgutierrez.piapoo2025.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Axel");
        cliente.setApellidos("Gutiérrez");
        cliente.setEmail("axel@mail.com");
        cliente.setTelefono("1234567890");
        cliente.setDireccion(new Direccion());
    }

    @Test
    void guardarCliente_deberiaRetornarClienteGuardado() {
        given(clienteRepository.save(cliente)).willReturn(cliente);

        Cliente resultado = clienteService.guardar(cliente);

        assertNotNull(resultado); //que no sea NULL
        assertEquals("Axel", resultado.getNombre()); //verifica el nombre
        verify(clienteRepository, times(1)).save(cliente); //verifica que el metodo save se haya llamado exactamente una vez con el cliente dado
    }

    @Test
    void listarClientes_deberiaRetornarListaDeClientes() {
        given(clienteRepository.findAll()).willReturn(List.of(cliente));

        List<Cliente> resultado = clienteService.listar();

        assertEquals(1, resultado.size()); //verifica que tenga un cliente
        verify(clienteRepository, times(1)).findAll(); //que el metodo se haya llamado una vez
    }

    @Test
    void buscarPorId_conIdExistente_deberiaRetornarCliente() throws RecursoNoEncontradoException {
        given(clienteRepository.findById(1L)).willReturn(Optional.of(cliente));

        Cliente resultado = clienteService.buscarPorId(1L);

        assertNotNull(resultado); //que no sea NULL
        assertEquals("Axel", resultado.getNombre()); //verifica el nombre
        verify(clienteRepository).findById(1L); //verifica que el metodo findById se haya llamado con el id dado
    }

    @Test
    void buscarPorId_conIdNoExistente_deberiaLanzarExcepcion() {
        given(clienteRepository.findById(99L)).willReturn(Optional.empty());

        //verifica que se lance la excepcion RecursoNoEncontradoException
        assertThrows(RecursoNoEncontradoException.class, () -> clienteService.buscarPorId(99L));
        verify(clienteRepository).findById(99L); //verifica que el metodo findById se haya llamado con el id dado
    }

    @Test
    void eliminar_conIdExistente_deberiaEliminarCliente() {
        given(clienteRepository.existsById(1L)).willReturn(true);

        assertDoesNotThrow(() -> clienteService.eliminar(1L)); //verifica que no lance ninguna excepcion
        verify(clienteRepository).deleteById(1L); //verifica que el metodo deleteById se haya llamado con el id dado
    }

    @Test
    void eliminar_conIdNoExistente_deberiaLanzarExcepcion() {
        given(clienteRepository.existsById(99L)).willReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> clienteService.eliminar(99L)); //verifica que lance la excepcion RecursoNoEncontradoException
        verify(clienteRepository, never()).deleteById(anyLong()); //verifica que el metodo deleteById no se haya llamado con el id dado
    }
}
