package org.in5bm.marvinlarios.juanavila.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.in5bm.marvinlarios.juanavila.db.Conexion;
import org.in5bm.marvinlarios.juanavila.models.Salones;
import org.in5bm.marvinlarios.juanavila.system.Principal;

/**
 *
  * @Author     : Marvin Larios Cutzal
    Carne 2021097
    @Autor      : Juan Avila Flores
    Carne 2021047
 *
 */
public class SalonesController implements Initializable {
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;
    
    private Principal escenarioPrincipal;
    
    @FXML
    private TextField txtCodigoSalon;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtCapacidadMaxima;

    @FXML
    private TextField txtEdificio;

    @FXML
    private TextField txtNivel;

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
    private TableView tblSalones;

    @FXML
    private TableColumn colCodigoSalon;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colCapacidadMaxima;

    @FXML
    private TableColumn colEdificio;

    @FXML
    private TableColumn colNivel;
    
    @FXML
    private ObservableList<Salones> listaSalones;

    private final String PAQUETE_IMAGES = "org/in5bm/marvinlarios/juanavila/resources/images/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos() {
        tblSalones.setItems(getSalones());
        colCodigoSalon.setCellValueFactory(new PropertyValueFactory<Salones, String>("codigoSalon"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones, String>("descripcion"));
        colCapacidadMaxima.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones, String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("nivel"));
    }
    
    public ObservableList getSalones() {

        ArrayList<Salones> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("call sp_salones_read()");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Salones salones = new Salones();

                salones.setCodigoSalon(rs.getString(1));
                salones.setDescripcion(rs.getString(2));
                salones.setCapacidadMaxima(rs.getInt(3));
                salones.setEdificio(rs.getString(4));
                salones.setNivel(rs.getInt(5));

                lista.add(salones);

                System.out.println(salones.toString());
            }
            
            listaSalones = FXCollections.observableArrayList(lista);

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
        
        return listaSalones;
    }

    private void habilitarCampos() {
        txtCodigoSalon.setEditable(true);
        txtDescripcion.setEditable(true);
        txtCapacidadMaxima.setEditable(true);
        txtEdificio.setEditable(true);
        txtNivel.setEditable(true);

        txtCodigoSalon.setDisable(false);
        txtDescripcion.setDisable(false);
        txtCapacidadMaxima.setDisable(false);
        txtEdificio.setDisable(false);
        txtNivel.setDisable(false);
    }

    private void limpiarCampos() {
        txtCodigoSalon.setText("");
        txtDescripcion.setText("");
        txtCapacidadMaxima.setText("");
        txtEdificio.setText("");
        txtNivel.clear();

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

                operacion = SalonesController.Operacion.GUARDAR;
                break;
        }
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
                
                operacion = SalonesController.Operacion.ACTUALIZAR;
                
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
                
                operacion = SalonesController.Operacion.NINGUNO;
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
                
                operacion = SalonesController.Operacion.NINGUNO;
                
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
    // Ver si funciona el enlace entre ventanas
}
