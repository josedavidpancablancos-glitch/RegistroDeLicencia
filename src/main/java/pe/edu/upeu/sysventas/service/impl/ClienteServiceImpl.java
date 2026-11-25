package pe.edu.upeu.sysventas.service.impl;

import pe.edu.upeu.sysventas.model.Cliente;
import pe.edu.upeu.sysventas.repository.ClienteRepository;
import pe.edu.upeu.sysventas.repository.ClienteRepositoryImpl;
import pe.edu.upeu.sysventas.service.ClienteService;
import java.util.List;

public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository repository = new ClienteRepositoryImpl();

    @Override
    public List<Cliente> listar() { return repository.findAll(); }

    @Override
    public Cliente buscarPorDni(String dni) { return repository.findByDni(dni); }

    @Override
    public Cliente registrar(Cliente cliente) { return repository.save(cliente); }

    @Override
    public Cliente modificar(String dniOriginal, Cliente cliente) {
        return repository.update(dniOriginal, cliente);
    }

    @Override
    public void eliminar(String dni) { repository.delete(dni); }
}
