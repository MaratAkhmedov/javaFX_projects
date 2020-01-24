/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author igugl
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private GridPane gridTable;
    @FXML
    private Circle circulo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        int numRows = gridTable.getRowConstraints().size();
        int numColumns = gridTable.getColumnConstraints().size();
        System.out.println(numRows + " " + numColumns);
        /*circulo.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
             public void handle(final KeyEvent event) {
                                  System.out.println("OK");
                 if(event.getCode().equals(KeyCode.UP)){
                 System.out.println("OK");
                 }
                
            }
       });*/
        gridTable.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
             public void handle(final KeyEvent event) {
                 int positionY = GridPane.getRowIndex(circulo);
                 int positionX = GridPane.getColumnIndex(circulo);
                switch(event.getCode().toString()){
                    case "UP":
                        if(positionY == 0){
                         GridPane.setRowIndex(circulo, numRows-1);
                        }
                        else{
                        GridPane.setRowIndex(circulo, positionY - 1);
                        }
                        break;
                    case "DOWN":
                        if(positionY == numRows-1){
                         GridPane.setRowIndex(circulo, 0);
                        }
                        else{
                        GridPane.setRowIndex(circulo, positionY + 1);
                        }
                        break;
                    case "LEFT":
                        if(positionX == 0){
                         GridPane.setColumnIndex(circulo, numColumns-1);
                        }
                        else{
                        GridPane.setColumnIndex(circulo, positionX - 1);
                        }
                        break;
                    case "RIGHT": 
                        if(positionX == numColumns-1){
                         GridPane.setColumnIndex(circulo, 0);
                        }
                        else{
                        GridPane.setColumnIndex(circulo, positionX + 1);
                        }
                        break;
                }
            }
       });
    }    
    
}
