package pe.edu.upeu.sysventas.service;

import pe.edu.upeu.sysventas.model.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> listar();
    Cliente buscarPorDni(String dni);
    Cliente registrar(Cliente cliente);
    Cliente modificar(String dniOriginal, Cliente cliente);
    void eliminar(String dni);
}
