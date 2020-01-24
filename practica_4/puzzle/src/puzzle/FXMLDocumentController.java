/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import static utiles8puzzle.Utils.generarVectorAleatorio;

/**
 *
 * @author maak
 */
public class FXMLDocumentController implements Initializable {
    
    /**
     * añadimos la biblioteca: en "archivos" creamos carpeta lib y luego
     * pulsamos con mouse2 en proyecto puzzle (root) propiedades ->bibliotecas -> añadir bibliotecas -> añadir jar -> 
     * ->buscamos utils y lo añadimos pero con path RELATIVO
     */
    
    private int hueco_X = 2;
    private int hueco_Y = 2;
    
    @FXML
    private GridPane grid;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //creamos el vector aleatorio de tamaño 8
        int[] vectorInicial= generarVectorAleatorio(8);
        ObservableList<Node> children = grid.getChildren();
        for(int i = 0; i < children.size(); i++){
            ((Button)children.get(i)).setText(Integer.toString(vectorInicial[i]));
        }
    }    

    @FXML
    private void moverHueco(MouseEvent event) {
        //obtenemos el boton pulsado
        Button button = (Button)event.getSource();
        int button_x = grid.getColumnIndex(button);
        int button_y = grid.getRowIndex(button);
        if(esAdyacente(button_x, button_y)){
            grid.setConstraints(button, hueco_X, hueco_Y);
            hueco_X = button_x;
            hueco_Y = button_y;
        }
    }

    @FXML
    private void reiniciar(ActionEvent event) {
        reiniciar();
    }
    
    private void reiniciar() {
        //hay que poner todos los botones a su sitio
        int[] vectorInicial= generarVectorAleatorio(8);
        ObservableList<Node> children = grid.getChildren();
        for(int i = 0; i < children.size(); i++){
            ((Button)children.get(i)).setText(Integer.toString(vectorInicial[i]));
        }
    }

    @FXML
    private void terminar(ActionEvent event) {
        ((Stage)grid.getScene().getWindow()).close();
    }
    
    private boolean esAdyacente(int boton_X, int boton_Y){
        if(boton_X == hueco_X){
            if(Math.abs(hueco_Y - boton_Y) == 1) return true;
        }else if(boton_Y == hueco_Y){
            if(Math.abs(hueco_X - boton_X) == 1) return true;
        }
        return false;
    }
    
}
