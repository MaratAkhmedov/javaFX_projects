/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author maak
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Rectangle rectangulo;
    @FXML
    private Slider hSlider;
    @FXML
    private Slider vSlider;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       hSlider.valueProperty().addListener((objecto,antiguo,nuevo)->{
           System.out.println(objecto.getValue().toString() + "   " + nuevo + "antiguo: " + antiguo);
           rectangulo.setWidth((double) nuevo);
       });
       
      /* vSlider.valueProperty().addListener((objecto,antiguo,nuevo)->{
           rectangulo.setHeight((double) nuevo);
       });*/
      //rectangulo.widthProperty().bind(hSlider.valueProperty());
      rectangulo.heightProperty().bind(vSlider.valueProperty());

      
    }    
    
}
