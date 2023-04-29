package org.in5bm.marvinlarios.juanavila.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.in5bm.marvinlarios.juanavila.db.Conexion;
import org.in5bm.marvinlarios.juanavila.models.Alumnos;
import org.in5bm.marvinlarios.juanavila.system.Principal;

/**
 *
  * @Author     : Marvin Larios Cutzal
    Carne 2021097
    @Autor      : Juan Avila Flores
    Carne 2021047
 *
 */
public class AlumnosController implements Initializable {
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    @FXML
    private TextField txtCarnet;

    @FXML
    private TextField txtNombre1;

    @FXML
    private TextField txtNombre2;

    @FXML
    private TextField txtNombre3;

    @FXML
    private TextField txtApellido1;

    @FXML
    private TextField txtApellido2;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;
    
    @FXML
    private ImageView imgNuevo;

    @FXML
    private ImageView imgModificar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReporte;

    @FXML
    private TableView tblAlumnos;

    @FXML
    private TableColumn colCarnet;

    @FXML
    private TableColumn colNombre1;

    @FXML
    private TableColumn colNombre2;

    @FXML
    private TableColumn colNombre3;

    @FXML
    private TableColumn colApellido1;

    @FXML
    private TableColumn colApellido2;

    @FXML
    private ObservableList<Alumnos> listaAlumnos;

    private final String PAQUETE_IMAGES = "org/in5bm/marvinlarios/juanavila/resources/images/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos() {
        tblAlumnos.setItems(getAlumnos());
        colCarnet.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("carne"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido2"));
    }
    
    public ObservableList getAlumnos() {

        ArrayList<Alumnos> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("call sp_alumnos_read()");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Alumnos alumno = new Alumnos();

                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));

                lista.add(alumno);

                System.out.println(alumno.toString());
            }
            
            listaAlumnos = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\n Se produjo un error al intentar consultar la lista de Alumnos");
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return listaAlumnos;
    }

    private void habilitarCampos() {
        txtCarnet.setEditable(true);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);

        txtCarnet.setDisable(false);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
    }

    private void limpiarCampos() {
        txtCarnet.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtNombre3.setText("");
        txtApellido1.clear();
        txtApellido2.clear();

    }

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "guardar.png"));

                btnModificar.setText("Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png"));

                btnEliminar.setDisable(true);
                imgEliminar.setDisable(true);

                btnEliminar.setVisible(false);
                imgEliminar.setVisible(false);

                btnReporte.setDisable(true);
                imgReporte.setDisable(true);

                btnReporte.setVisible(false);
                imgReporte.setVisible(false);

                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                
        }
    }
private boolean agregarAlumnos(){
        Alumnos alumno = new Alumnos();
        alumno.setCarne(txtCarnet.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.
}
    @FXML
    private void clicModificar() {
        
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                
                btnNuevo.setDisable(true);
                imgNuevo.setDisable(true);
                
                btnNuevo.setVisible(false);
                imgNuevo.setVisible(false);
                
                btnModificar.setText("Guardar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "guardar.png"));
                
                btnEliminar.setText("Cancelar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png"));
                
                btnReporte.setDisable(true);
                imgReporte.setDisable(true);
                
                btnReporte.setVisible(false);
                imgReporte.setVisible(false);
                
                operacion = Operacion.ACTUALIZAR;
                
                break;
                
            case GUARDAR:
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "agregar.png"));
                
                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));
                
                btnEliminar.setDisable(false);
                imgEliminar.setDisable(false);
                
                btnEliminar.setVisible(true);
                imgEliminar.setVisible(true);
                
                btnReporte.setDisable(false);
                imgReporte.setDisable(false);
                
                btnReporte.setVisible(true);
                imgReporte.setVisible(true);
                
                operacion = Operacion.NINGUNO;
                break;

        }
    }
    
    @FXML
    private void clicEliminar() {
        switch(operacion) {
            case ACTUALIZAR: //CANCELAR LA ACTUALIZACIÓN
                btnNuevo.setDisable(false);
                imgNuevo.setDisable(false);
                
                btnNuevo.setVisible(true);
                imgNuevo.setVisible(true);
                
                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));
                
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));
                
                btnReporte.setDisable(false);
                imgReporte.setDisable(false);
                
                btnReporte.setVisible(true);
                imgReporte.setVisible(true);
                
                operacion = Operacion.NINGUNO;
                
                break;
            
        }
    }

    @FXML
    private void clicReporte() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("¡AVISO!");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta opcion solo esta disponible en la versión PRO");
        Stage dialog = new Stage();
        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(PAQUETE_IMAGES + "editar.png"));
        alerta.show();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
