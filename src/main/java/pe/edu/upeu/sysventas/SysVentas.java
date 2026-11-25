package pe.edu.upeu.sysventas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SysVentas extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_licencia.fxml"));
        Scene scene = new Scene(loader.load(), 1150, 700);
        var css = getClass().getResource("/css/style.css");
        if (css != null) scene.getStylesheets().add(css.toExternalForm());
        stage.setTitle("Sistema de Registro de Licencias de Conducir");
        stage.setScene(scene);
        stage.setMinWidth(950);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
