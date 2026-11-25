package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.model.Cliente;
import java.util.List;

public interface ClienteRepository {
    List<Cliente> findAll();
    Cliente findByDni(String dni);
    Cliente save(Cliente cliente);
    Cliente update(String dniOriginal, Cliente cliente);
    void delete(String dni);
}
