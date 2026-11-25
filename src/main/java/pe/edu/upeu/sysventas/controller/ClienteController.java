package pe.edu.upeu.sysventas.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pe.edu.upeu.sysventas.model.Cliente;
import pe.edu.upeu.sysventas.service.ClienteService;
import pe.edu.upeu.sysventas.service.impl.ClienteServiceImpl;

public class ClienteController {
    @FXML private TextField txtDni, txtNombre, txtTelefono, txtFiltro;
    @FXML private TableView<Cliente> tableView;
    @FXML private TableColumn<Cliente, String> colDni, colNombre, colTelefono;

    private final ClienteService service = new ClienteServiceImpl();
    private final ObservableList<Cliente> datos = FXCollections.observableArrayList();
    private String dniOriginal = null;

    @FXML
    public void initialize() {
        colDni.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getDni()));
        colNombre.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));
        colTelefono.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getTelefono()));

        tableView.setItems(datos);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, old, seleccionado) -> {
            if (seleccionado != null) cargarFormulario(seleccionado);
        });
        listar();
    }

    @FXML
    private void registrar() {
        if (!validar()) return;

        if (service.buscarPorDni(txtDni.getText().trim()) != null) {
            mensaje(Alert.AlertType.ERROR, "Error", "El DNI ya está registrado como cliente.");
            return;
        }

        service.registrar(leerFormulario());
        listar();
        limpiar();
        mensaje(Alert.AlertType.INFORMATION, "Cliente", "Cliente registrado correctamente.");
    }

    @FXML
    private void modificar() {
        if (dniOriginal == null) {
            mensaje(Alert.AlertType.WARNING, "Aviso", "Seleccione un cliente para modificar.");
            return;
        }

        if (!validar()) return;

        String nuevoDni = txtDni.getText().trim();
        if (!dniOriginal.equals(nuevoDni) && service.buscarPorDni(nuevoDni) != null) {
            mensaje(Alert.AlertType.ERROR, "Error", "El nuevo DNI ya está registrado.");
            return;
        }

        service.modificar(dniOriginal, leerFormulario());
        listar();
        limpiar();
        mensaje(Alert.AlertType.INFORMATION, "Cliente", "Cliente modificado correctamente.");
    }

    @FXML
    private void eliminar() {
        Cliente seleccionado = tableView.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mensaje(Alert.AlertType.WARNING, "Aviso", "Seleccione un cliente para eliminar.");
            return;
        }

        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION,
                "¿Desea eliminar al cliente con DNI " + seleccionado.getDni() + "?",
                ButtonType.YES, ButtonType.NO
        );
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null);

        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            service.eliminar(seleccionado.getDni());
            listar();
            limpiar();
        }
    }

    @FXML
    private void limpiar() {
        txtDni.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtFiltro.clear();
        dniOriginal = null;
        tableView.getSelectionModel().clearSelection();
    }

    @FXML
    private void buscar() {
        String filtro = txtFiltro.getText().trim().toLowerCase();

        if (filtro.isEmpty()) {
            listar();
            return;
        }

        datos.setAll(service.listar().stream()
                .filter(c -> c.getDni().toLowerCase().contains(filtro)
                        || c.getNombre().toLowerCase().contains(filtro))
                .toList());
    }

    private void listar() {
        datos.setAll(service.listar());
    }

    private Cliente leerFormulario() {
        return new Cliente(
                txtDni.getText().trim(),
                txtNombre.getText().trim(),
                txtTelefono.getText().trim()
        );
    }

    private void cargarFormulario(Cliente c) {
        dniOriginal = c.getDni();
        txtDni.setText(c.getDni());
        txtNombre.setText(c.getNombre());
        txtTelefono.setText(c.getTelefono());
    }

    private boolean validar() {
        if (!txtDni.getText().trim().matches("\\d{8}")) {
            mensaje(Alert.AlertType.ERROR, "Error", "El DNI debe tener 8 números.");
            return false;
        }

        if (txtNombre.getText().trim().isEmpty()) {
            mensaje(Alert.AlertType.ERROR, "Error", "Ingrese el nombre completo.");
            return false;
        }

        if (!txtTelefono.getText().trim().matches("\\d{9}")) {
            mensaje(Alert.AlertType.ERROR, "Error", "El teléfono debe tener 9 números.");
            return false;
        }

        return true;
    }

    private void mensaje(Alert.AlertType tipo, String titulo, String texto) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(texto);
        a.showAndWait();
    }
}
