/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import model.Model;

/**
 * FXML Controller class
 *
 * @author igugl
 */
public class FXMLNotasController implements Initializable {
    @FXML
    private PieChart miPie;
    @FXML
    private BarChart<String, Number> miBar;
    private Model miModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void sumar(ActionEvent event) {
        String buttonTitle = ((Button)event.getSource()).getText();
        miModel.sumarTipoNota(buttonTitle);
    }

    public void setModel(Model model) {
        miModel = model;
        miPie.setData(model.getPieData());
        XYChart.Series miserie = new XYChart.Series();
        miserie.setData(miModel.getBarData());
        miBar.getData().add(miserie);
        
    }
    
}
