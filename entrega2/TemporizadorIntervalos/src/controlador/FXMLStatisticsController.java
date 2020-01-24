/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import modelo.Sesion;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //no se como pasar las sesiones
        sesion = getSesion();
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
        tDescanso = (durDescEjercInt*(numEj - 1)) + (durDescCircInt*numCirc);
        chartData = FXCollections.observableArrayList(
                new PieChart.Data("Tiempo de ejercicio físico", tEjercicio),
                new PieChart.Data("Tiempo de descanso", tDescanso)
        );
        
        chart.setData(chartData);
   
    }  
    public Sesion getSesion(){
         return sesion;
     }
    
}
