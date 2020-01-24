/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SumaResta;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 *
 * @author maak
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private Text resultado;
    @FXML
    private Button button1;
    @FXML
    private Button button5;
    @FXML
    private Button button10;
    @FXML
    private CheckBox restar;
    @FXML
    private Text avisoRestando;
    @FXML
    private Button buttonSuma;
    
    private int valueAdd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       valueAdd = 0;
       
       EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>(){

             @Override
             public void handle(final ActionEvent event) {

                if (event.getSource() instanceof Button)
                //System.out.println(((Button)event.getSource()).getText());
                valueAdd = Integer.parseInt(((Button)event.getSource()).getText());
      
            }
        
       };
       button1.setOnAction(myHandler);
       button5.setOnAction(myHandler);
       button10.setOnAction(myHandler);
       
       buttonSuma.setOnAction((final ActionEvent event) -> {
           int prevRes = Integer.parseInt(resultado.getText());
           if(restar.selectedProperty().getValue()){
               resultado.setText(String.valueOf(prevRes - valueAdd));
           }else{
               resultado.setText(String.valueOf(prevRes + valueAdd));
           }
       });
       
       restar.selectedProperty().addListener((objecto,antiguo,nuevo)->{
          //if checkbox is checked show message
          if(nuevo){
              avisoRestando.setText("Estas Restando!!!");
          }
          if(!nuevo){
              avisoRestando.setText("");
          }
       });
       
       //como hace con bind lo de arriba (vustom string if checkbox is changed)
       
       
    }    
    
    
    
}
