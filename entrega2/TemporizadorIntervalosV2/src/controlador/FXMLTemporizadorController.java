/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.sun.javafx.scene.control.skin.ProgressIndicatorSkin;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.StyleableBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import modelo.Sesion;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import temporizadorintervalos.IntervalTimerS;

/**
 *
 * @author jsoler
 */
public class FXMLTemporizadorController implements Initializable {
    BooleanProperty parado= new SimpleBooleanProperty(false);
    BooleanProperty iniciado= new SimpleBooleanProperty(false);
        
    private IntervalTimerS servicio;
    public Sesion sesion;
    
    @FXML
    private Text textoTiempo;   
    @FXML
    private Button iniBut;
    @FXML
    private Button reaBut;
    @FXML
    private Button parBut;
   // @FXML
   // private Button sigBut;
    private Spinner<Integer> descansoEntreCircuitos;
    private Spinner<Integer> numEjercicios;
    private Spinner<Integer> durEjercicio;
    private Spinner<Integer> descansoEntreEjercicios;
    private Spinner<Integer> numCircuitos;
    //@FXML
    //private Button detenerButton;
    @FXML
    private Button resetBut;
    public long tiempoTotalTranscurrido;
    private int tiempoInterno;
    @FXML
    private ProgressIndicator progressIndicator;
    long tiempoTotal;
    @FXML
    private Label textoEjercicio;
    @FXML
    private Label textoCircuito;
    @FXML
    private HBox circuitHbox;
    @FXML
    private HBox mainHbox;
    @FXML
    private Label ejercicio_descanso;
    @FXML
    private HBox title;
    @FXML
    private HBox stats;
    @FXML
    private Button statButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // disable de los buttons
        iniBut.disableProperty().bind(iniciado);
        parBut.disableProperty().bind(Bindings.or(Bindings.not(iniciado), parado));
        reaBut.disableProperty().bind(Bindings.or(Bindings.not(iniciado), Bindings.not(parado)));
       // sigBut.disableProperty().bind(Bindings.or(Bindings.not(iniciado), Bindings.not(parado)));
        resetBut.disableProperty().bind(Bindings.not(parado));
        progressIndicator.setProgress(0);
        mainHbox.setVisible(false);
        circuitHbox.setVisible(false);
        stats.setVisible(false);
        tiempoTotalTranscurrido = 0;
        tiempoInterno = 0;
        
        
    textoTiempo.textProperty().addListener((observable, oldValue, newValue) -> {
        if(servicio.getTiempo() != null && !servicio.getTiempo().equals("00:00")){
            tiempoTotalTranscurrido++;
            //tiempoInterno++;
        }
           // System.out.println("textfield changed from " + oldValue + " to " + newValue);
        if(tiempoTotal !=0 && tiempoTotalTranscurrido!= 0){
            progressIndicator.setProgress((double)tiempoTotalTranscurrido/(double)tiempoTotal);
        }
        System.out.println(tiempoTotalTranscurrido +  "    " + tiempoTotal);
        if(servicio.getTiempo() != null && servicio.getTiempo().equals("00:00") && !(textoEjercicio.getText().equals("")) && !(textoCircuito.getText().equals(""))){
            setTextCircuitoEjercicio(tiempoTotalTranscurrido, tiempoTotal);
        }
    });
    

    }    

    private void iniciar(ActionEvent event) {
        servicio.start();
        iniciado.set(true);
    }

    @FXML
    private void reanudar(ActionEvent event) {
        servicio.restart();
        parado.set(false);
    }

    @FXML
    private void parar(ActionEvent event) {
        servicio.cancel();
        parado.set(true);
    }

    private void siguiente(ActionEvent event) {
        servicio.setCambiarEstado(true);
        servicio.restart();
    }

    @FXML
    private void reset(ActionEvent event) {
        mainHbox.setVisible(false);
        circuitHbox.setVisible(false);
        servicio.restaurarInicio(); 
        iniciado.set(false);
        textoEjercicio.setText("");
        textoCircuito.setText("");
        statButton.setVisible(false);
    }

    private void configurarSpinners() {
        // NUMEROS CIRCUITOS 
        // Value factory.
        SpinnerValueFactory<Integer> numCircuitosValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        numCircuitos.setValueFactory(numCircuitosValue);
        
        //CONFIGURACION SPINNERS DESCANSO ENTRE CIRCUITOS
        SpinnerValueFactory.IntegerSpinnerValueFactory descansoCircuitosConfig = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 10);
        descansoCircuitosConfig.setAmountToStepBy(5);
        SpinnerValueFactory<Integer> descansoCircuitos = descansoCircuitosConfig;
        descansoEntreCircuitos.setValueFactory(descansoCircuitos);
        
        //CONFIGURACION SPINNERS numEjercicios
        SpinnerValueFactory<Integer> numeroEjerciciosValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        numEjercicios.setValueFactory(numeroEjerciciosValue);
        
         //CONFIGURACION SPINNERS duracion de ejercicios
        SpinnerValueFactory.IntegerSpinnerValueFactory duracionEjerciciosConfig = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, 30);
        duracionEjerciciosConfig.setAmountToStepBy(5);
        SpinnerValueFactory<Integer> duracionEjercicios = duracionEjerciciosConfig;
        durEjercicio.setValueFactory(duracionEjercicios);
        
         
         //CONFIGURACION SPINNERS duracion de ejercicios
        SpinnerValueFactory.IntegerSpinnerValueFactory  configDescansoEjercicios= new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 300, 5);
        configDescansoEjercicios.setAmountToStepBy(5);
        SpinnerValueFactory<Integer> descansoEjerciciosValue = configDescansoEjercicios;
        
        descansoEntreEjercicios.setValueFactory(descansoEjerciciosValue);
    }

    @FXML
    private void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vista/FXMLSettingsView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
    public void initSesion(Sesion sesion){
         this.sesion = sesion;
         servicio = new IntervalTimerS();
        servicio.setSesionTipo(sesion);
        AudioClip plonkSound3 = new AudioClip(getClass().getResource("/sounds/Success.mp3").toString()    );

        
                
        textoTiempo.textProperty().bind(servicio.tiempoProperty());
        
        servicio.setOnSucceeded(c -> {
            if (servicio.getValue()) {
                plonkSound3.play();
                stats.setVisible(true);
                //Alert alertaFinal = new Alert(Alert.AlertType.INFORMATION);
                //alertaFinal.setContentText("ENTRENAMIENTO TERMINADO");
                //alertaFinal.showAndWait();
                Text text = (Text) progressIndicator.lookup(".percentage");
                text.setText("Entrenamiento terminado");
                 progressIndicator.setPrefWidth(text.getLayoutBounds().getWidth());
                
            }
        });
        
        servicio.start();
        iniciado.set(true);
        
        elTiempoTotal(sesion);
                    title.setVisible(false);
                    mainHbox.setVisible(true);
                    circuitHbox.setVisible(true);
                    textoEjercicio.setText("1");
                    textoCircuito.setText("1");

    }

    private void elTiempoTotal(Sesion s) {
        long res = 0;
        long unCircuito = s.getN_ejercicios()*s.getT_ejercicio().getSeconds() + (s.getN_ejercicios() - 1) * s.getDescanso_ejercicio().getSeconds();
        res = unCircuito * s.getN_circuitos() + (s.getN_circuitos() - 1) * s.getDescanso_circuito().getSeconds();
        tiempoTotal = res;
    }

    private void setTextCircuitoEjercicio(long tiempoTotalTranscurrido, long tiempoTotal) {
        int numEj = sesion.getN_ejercicios(); //3
        long durEj = sesion.getT_ejercicio().getSeconds(); //5
        long durDescEjerc = sesion.getDescanso_ejercicio().getSeconds();
        long durDescCirc = sesion.getDescanso_circuito().getSeconds();
        AudioClip plonkSound1 = new AudioClip(getClass().getResource("/sounds/WrongBuzzer.mp3").toString()    );
        AudioClip plonkSound2 = new AudioClip(getClass().getResource("/sounds/FirePager.mp3").toString()    );
                
        int ejercicio = Integer.parseInt(textoEjercicio.getText());
        int circuito = Integer.parseInt(textoCircuito.getText());
        long tiempoCircuito = numEj*sesion.getT_ejercicio().getSeconds() + (numEj - 1) * sesion.getDescanso_ejercicio().getSeconds();
        
//        //Castings para las condiciones de al musica
//        int tiempoCircuitoInt = (int) tiempoCircuito;
//        int durEjInt = (int) durEj;
//        int durDescEjercInt = (int) durDescEjerc;
//        int durDescCircInt = (int) durDescCirc;
        
//        if(durEjInt - tiempoInterno == 2 || durDescEjercInt - tiempoInterno == 2){
//            plonkSound2.play();
//        }
//        if(durDescCircInt - tiempoInterno == 2){
//            plonkSound1.play();
//            plonkSound2.play();
//            }
        if(tiempoTotalTranscurrido >= (tiempoCircuito + durDescCirc)* circuito){
            int textCircuito = circuito + 1;
            textoCircuito.setText(String.valueOf(textCircuito));
            textoEjercicio.setText("1");
            plonkSound1.play();
            plonkSound2.play();
            //tiempoInterno = 0;
         }
                
        else if(tiempoTotalTranscurrido >= (durEj + durDescEjerc) * ejercicio + ((tiempoCircuito + durDescCirc) * (circuito - 1))){
            int newText = ejercicio + 1;
            textoEjercicio.setText(String.valueOf(newText));  
            ejercicio_descanso.setText("Ejercicio");
            plonkSound2.play();
            //tiempoInterno = 0;
        }
        else{
            ejercicio_descanso.setText("Descanso");
            plonkSound2.play();
            //tiempoInterno = 0;
        }
        
        System.out.println("tiempo transcurrido: " + tiempoTotalTranscurrido);
    }

    @FXML
    private void showStatistics(ActionEvent event) throws IOException {
       /* Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vista/FXMLStatistics.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();*/
        
         FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/vista/FXMLStatistics.fxml"));
        Parent tableViewParent = loader.load();
        
        Scene tableViewScene = new Scene(tableViewParent);
        
        //access the controller and call a method
        FXMLStatisticsController controller = loader.getController();
        controller.initSesion(sesion);
                       
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }

    }
