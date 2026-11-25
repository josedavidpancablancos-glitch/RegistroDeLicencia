package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepositoryImpl implements ClienteRepository {
    private final List<Cliente> datos = new ArrayList<>();

    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(datos);
    }

    @Override
    public Cliente findByDni(String dni) {
        return datos.stream()
                .filter(c -> c.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Cliente save(Cliente cliente) {
        datos.add(cliente);
        return cliente;
    }

    @Override
    public Cliente update(String dniOriginal, Cliente cliente) {
        Cliente actual = findByDni(dniOriginal);
        if (actual == null) return null;

        actual.setDni(cliente.getDni());
        actual.setNombre(cliente.getNombre());
        actual.setTelefono(cliente.getTelefono());
        return actual;
    }

    @Override
    public void delete(String dni) {
        datos.removeIf(c -> c.getDni().equals(dni));
    }
}
