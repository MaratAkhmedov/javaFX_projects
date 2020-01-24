/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
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

    BooleanProperty parado = new SimpleBooleanProperty(false);
    BooleanProperty iniciado = new SimpleBooleanProperty(false);

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
    @FXML
    private Button sigBut;
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
    
    public int tiempoInternoParado = 0;
    public int tiempoEjercicioSaltado = 0;
    public int tiempoDescansado = 0;
    public String ejText = "Ejercicio";
    public String descText = "Descanso";
    public int newText = 0;
    private int ultimaParada = 0;
    private int numeroEjercicios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // disable de los buttons
        iniBut.disableProperty().bind(iniciado);
        parBut.disableProperty().bind(Bindings.or(Bindings.not(iniciado), parado));
        reaBut.disableProperty().bind(Bindings.or(Bindings.not(iniciado), Bindings.not(parado)));
        sigBut.disableProperty().bind(Bindings.or(Bindings.not(iniciado), Bindings.not(parado)));
        resetBut.disableProperty().bind(Bindings.not(parado));
        progressIndicator.setProgress(0);
        mainHbox.setVisible(false);
        circuitHbox.setVisible(false);
        stats.setVisible(false);
        tiempoTotalTranscurrido = 0;
        

        textoTiempo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (servicio.getTiempo() != null && !servicio.getTiempo().equals("00:00")) {
                tiempoTotalTranscurrido++;

            }
            // System.out.println("textfield changed from " + oldValue + " to " + newValue);
            if (tiempoTotal != 0 && tiempoTotalTranscurrido != 0) {
                progressIndicator.setProgress((double) tiempoTotalTranscurrido / (double) tiempoTotal);
            }
            System.out.println(tiempoTotalTranscurrido + "    " + tiempoTotal);
            if (servicio.getTiempo() != null && servicio.getTiempo().equals("00:00") && !(textoEjercicio.getText().equals("")) && !(textoCircuito.getText().equals(""))) {
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

    @FXML
    private void siguiente(ActionEvent event) throws IOException {
        servicio.setCambiarEstado(true);
        ultimaParada = (int) System.currentTimeMillis();
        int durEjTeórica = (int) sesion.getT_ejercicio().getSeconds();
        
        System.out.println(numeroEjercicios);
        
        
        //access the controller and call a method, creo que no sirve de nada
        /*FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/vista/FXMLSettingsView.fxml"));
        FXMLSettingsViewController controller = loader.getController();
        
        
        int nEj = controller.getNEjs();*/
        
        //int nEj = (Integer) numEjercicios.getValue();
        int numEjercicio =  Integer.parseInt(textoEjercicio.getText());
        int numCircuito = Integer.parseInt(textoCircuito.getText());
        if (ejercicio_descanso.getText().equals(ejText)){
            //transformamos el tiempo (texto en un array donde el primer elem son minutos y el segundo son segundos)
            String[] tiempo = textoTiempo.getText().split(":");
        
             //tiempo del ejercicio que marca el texto en segundos
            int tiempoEj = Integer.parseInt(tiempo[0]) * 60 + Integer.parseInt(tiempo[1]);
        
        
            ejercicio_descanso.setText(descText);
            int tiempoSaltado = durEjTeórica - tiempoEj;
            tiempoEjercicioSaltado = tiempoEjercicioSaltado + tiempoSaltado;
        }
        else{ejercicio_descanso.setText(ejText);
        //int numEjSig = sesion.getN_ejercicios();
        //int numCircSig = sesion.getN_circuitos();
        //tras pasar al circuito 2 se buguea y se queda en uno
            if (newText == numeroEjercicios){
                newText = 0;
                numCircuito = numCircuito + 1;
                textoEjercicio.setText("1");
                textoCircuito.setText(String.valueOf(numCircuito));
            }
            else{
            newText = numEjercicio + 1;
            textoEjercicio.setText(String.valueOf(newText));}
        
        }
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
        SpinnerValueFactory.IntegerSpinnerValueFactory configDescansoEjercicios = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 300, 5);
        configDescansoEjercicios.setAmountToStepBy(5);
        SpinnerValueFactory<Integer> descansoEjerciciosValue = configDescansoEjercicios;

        descansoEntreEjercicios.setValueFactory(descansoEjerciciosValue);
    }

    @FXML
    private void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vista/FXMLSettingsView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void initSesion(Sesion sesion) {
        this.sesion = sesion;
        servicio = new IntervalTimerS();
        
        numeroEjercicios = sesion.getN_ejercicios();
        
        servicio.setSesionTipo(sesion);
        AudioClip plonkSound3 = new AudioClip(getClass().getResource("/sounds/Success.mp3").toString());

        textoTiempo.textProperty().bind(servicio.tiempoProperty());

        servicio.setOnSucceeded(c -> {
            if (servicio.getValue()) {
                FXMLLoader loader = new FXMLLoader();
                IntervalTimerS controller = loader.getController();
                //no se por  q controller vale null
                int tStopped = controller.getStoppedDurationSesion();
                tiempoInternoParado = tStopped;
                if(parBut.isPressed()){
                    int tPausado = (int) System.currentTimeMillis();
                    tiempoInternoParado = tStopped + (tPausado - ultimaParada);
                }
                if(progressIndicator.getProgress() != 1){progressIndicator.setProgress(1);}
                plonkSound3.play();
                stats.setVisible(true);
                Text text = (Text) progressIndicator.lookup(".percentage");
                text.setText("ENTRENAMIENTO COMPLETADO");
                progressIndicator.setPrefWidth(text.getLayoutBounds().getWidth());
//                Alert alertaFinal = new Alert(Alert.AlertType.CONFIRMATION);
//                alertaFinal.setContentText("ENTRENAMIENTO COMPLETADO");
//                alertaFinal.showAndWait();

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
        long unCircuito = s.getN_ejercicios() * s.getT_ejercicio().getSeconds() + (s.getN_ejercicios() - 1) * s.getDescanso_ejercicio().getSeconds();
        res = unCircuito * s.getN_circuitos() + (s.getN_circuitos() - 1) * s.getDescanso_circuito().getSeconds();
        tiempoTotal = res;
    }

    private void setTextCircuitoEjercicio(long tiempoTotalTranscurrido, long tiempoTotal) {
        int numEj = sesion.getN_ejercicios(); //3
        long durEj = sesion.getT_ejercicio().getSeconds(); //5
        long durDescEjerc = sesion.getDescanso_ejercicio().getSeconds();
        long durDescCirc = sesion.getDescanso_circuito().getSeconds();
        AudioClip plonkSound1 = new AudioClip(getClass().getResource("/sounds/ChangeCircuitSound.mp3").toString());
        AudioClip plonkSound2 = new AudioClip(getClass().getResource("/sounds/FirePageNew.mp3").toString());

        int ejercicio = Integer.parseInt(textoEjercicio.getText());
        int circuito = Integer.parseInt(textoCircuito.getText());
        long tiempoCircuito = numEj * sesion.getT_ejercicio().getSeconds() + (numEj - 1) * sesion.getDescanso_ejercicio().getSeconds();

        if (tiempoTotalTranscurrido >= (tiempoCircuito + durDescCirc) * circuito) {
            int textCircuito = circuito + 1;
            textoCircuito.setText(String.valueOf(textCircuito));
            textoEjercicio.setText("1");
            tiempoDescansado = tiempoDescansado + (int) durDescCirc;
            plonkSound1.play();
            plonkSound2.play();

        } else if (tiempoTotalTranscurrido >= (durEj + durDescEjerc) * ejercicio + ((tiempoCircuito + durDescCirc) * (circuito - 1))) {
            newText = ejercicio + 1;
            tiempoDescansado = tiempoDescansado + (int) durDescEjerc;
            textoEjercicio.setText(String.valueOf(newText));
            ejercicio_descanso.setText("Ejercicio");
            plonkSound2.play();

        } else {
            ejercicio_descanso.setText("Descanso");
            plonkSound2.play();
         }

        System.out.println("tiempo transcurrido: " + tiempoTotalTranscurrido);
    }

    @FXML
    private void showStatistics(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/vista/FXMLStatistics.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        //access the controller and call a method
        FXMLStatisticsController controller = loader.getController();
        controller.initSesion(sesion);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public int getTiempoInterno() {
        return tiempoInternoParado;
    }
    public int getTiempoEjercicioSaltado() {
        return tiempoEjercicioSaltado;
    }
    public int getTiempoDescansado() {
        return tiempoDescansado;
    }
    

}
