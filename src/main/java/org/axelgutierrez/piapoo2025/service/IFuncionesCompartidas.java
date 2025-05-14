package org.axelgutierrez.piapoo2025.service;

import java.util.List;

public interface IFuncionesCompartidas<T> {
    T guardar(T entidad) throws Exception;
    List<T> listar() throws Exception;
    T buscarPorId(Long id) throws Exception;
    T actualizar(Long id, T entidad) throws Exception;
    void eliminar(Long id) throws Exception;
}
