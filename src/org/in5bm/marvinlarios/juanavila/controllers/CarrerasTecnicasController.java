package org.in5bm.marvinlarios.juanavila.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import org.in5bm.marvinlarios.juanavila.db.Conexion;
import org.in5bm.marvinlarios.juanavila.models.CarrerasTecnicas;
import org.in5bm.marvinlarios.juanavila.system.Principal;

/**
 *
 * @Author     : Marvin Larios Cutzal
    Carne 2021097
    @Autor      : Juan Avila Flores
    Carne 2021047
 * 
 */
public class CarrerasTecnicasController implements Initializable{
    
    // Tipo de dato   nombre de la variable
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;
    
    private Principal escenarioPrincipal;
    
    @FXML
    private TextField txtCodigoTecnico;
    
    @FXML
    private TextField txtCarrera;
    
    @FXML
    private TextField txtGrado;
    
    @FXML
    private TextField txtSeccion;
    
    @FXML
    private TextField txtJornada;
    
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
    private TableView tblCarrerasTecnicas;
    
    @FXML
    private TableColumn colCodigoTecnico;
    
    @FXML
    private TableColumn colCarrera;
    
    @FXML
    private TableColumn colGrado;
    
    @FXML
    private TableColumn colSeccion;
    
    @FXML
    private TableColumn colJornada;
    
    @FXML
    private ObservableList<CarrerasTecnicas> listaCarrerasTecnicas;
    
    private final String PAQUETE_IMAGES = "org/in5bm/marvinlarios/juanavila/resources/images/";
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        cargarDatos();
    }
    
    public void cargarDatos() {
        tblCarrerasTecnicas.setItems(getCarrerasTecnicas());
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("codigoTecnico"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("carrera"));
        colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, Character>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("jornada"));
    }
    
    public ObservableList getCarrerasTecnicas() {

        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("call sp_carreras_tecnicas_read()");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CarrerasTecnicas carrerasTecnicas = new CarrerasTecnicas();

                carrerasTecnicas.setCodigoTecnico(rs.getString(1));
                carrerasTecnicas.setCarrera(rs.getString(2));
                carrerasTecnicas.setGrado(rs.getString(3));
                carrerasTecnicas.setSeccion(rs.getString(4).charAt(0));
                carrerasTecnicas.setJornada(rs.getString(5));

                lista.add(carrerasTecnicas);

                System.out.println(carrerasTecnicas.toString());
            }
            
            listaCarrerasTecnicas = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\n Se produjo un error al intentar consultar la lista de Carreras Tecnicas");
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
        
        return listaCarrerasTecnicas;
    }
    
    private void habilitarCampos() {
        txtCodigoTecnico.setEditable(true);
        txtCarrera.setEditable(true);
        txtGrado.setEditable(true);
        txtSeccion.setEditable(true);
        txtJornada.setEditable(true);
        
        txtCodigoTecnico.setDisable(false);
        txtCarrera.setDisable(false);
        txtGrado.setDisable(false);
        txtSeccion.setDisable(false);
        txtJornada.setDisable(false);
    }
    
    private void limpiarCampos() {
        txtCodigoTecnico.setText("");
        txtCarrera.setText("");
        txtGrado.setText("");
        txtSeccion.setText("");
        txtJornada.clear();
        
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

                operacion = CarrerasTecnicasController.Operacion.GUARDAR;
                break;
        }
    }
    
    @FXML
    private void clicModificar(){
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
                
                operacion = CarrerasTecnicasController.Operacion.ACTUALIZAR;
                
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
                
                operacion = CarrerasTecnicasController.Operacion.NINGUNO;
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
                
                operacion = CarrerasTecnicasController.Operacion.NINGUNO;
                
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
