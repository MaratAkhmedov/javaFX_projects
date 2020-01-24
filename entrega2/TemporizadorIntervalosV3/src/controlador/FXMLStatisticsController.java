/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import modelo.Sesion;
import temporizadorintervalos.IntervalTimerS;

/**
 * FXML Controller class
 *
 * @author cosmi
 */
public class FXMLStatisticsController implements Initializable {

    @FXML
    private PieChart chart;
    ObservableList<PieChart.Data> chartData;
    int tDescanso;
    int tEjercicio;
    public Sesion sesion;
    @FXML
    private BarChart<String,Number> bar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*      
        chart.setData(chartData);*/
   
    }  
    
    
    public void initSesion(Sesion sesion){
        this.sesion = sesion;
        //Cálculo tiempo teórico
        //Tiempo ejercicio
        long durEj = sesion.getT_ejercicio().getSeconds();
        int durEjInt = (int) durEj;
        int numEj = sesion.getN_ejercicios();
        int numCirc = sesion.getN_circuitos();
        tEjercicio = durEjInt*numEj*numCirc;
        //Tiempo descanso
        long durDescEjerc = sesion.getDescanso_ejercicio().getSeconds();
        int durDescEjercInt = (int) durDescEjerc;
        long durDescCirc = sesion.getDescanso_circuito().getSeconds();
        int durDescCircInt = (int) durDescCirc;
        tDescanso = durDescEjercInt*((numEj - 1)*numCirc) + (durDescCircInt*(numCirc - 1));
        chartData = FXCollections.observableArrayList(
                new PieChart.Data("Tiempo de ejercicio físico", tEjercicio),
                new PieChart.Data("Tiempo de descanso", tDescanso)
        );
       chart.setData(chartData);
       int tTotal = tEjercicio + tDescanso;
       
       //Cálculo tiempo real sesion
       FXMLLoader loader = new FXMLLoader();
       FXMLTemporizadorController controller = loader.getController();
       int tStopped = controller.getTiempoInterno();
       int tiempoSaltado = controller.getTiempoEjercicioSaltado();
       int tDescansado = controller.getTiempoDescansado();
       int tiempoRealDescanso = tStopped + tDescansado;
       int tEjercicioReal = durEjInt - tiempoSaltado;
       int tReal = tEjercicioReal + tiempoRealDescanso;
       
       //Gráfico barras
       NumberAxis yAxis = new NumberAxis();
       bar.setTitle("Tiempo teórico vs tiempo real (segundos)"); 
       yAxis.setLabel("Segundos");
       XYChart.Series series1 = new XYChart.Series();
       series1.setName("Tiempo programado de la sesión"); 
       series1.getData().add(new XYChart.Data("", tTotal));
       XYChart.Series series2 = new XYChart.Series(); 
       series2.setName("Tiempo real utilizado");
       series2.getData().add(new XYChart.Data("", tReal));
       
       bar.getData().addAll(series1, series2);
        
        
        
        
    }

    @FXML
    private void changeScreenButtonPushed(ActionEvent event) throws IOException {
           Parent tableViewParent = FXMLLoader.load(getClass().getResource("/vista/FXMLTemporizador.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
}
