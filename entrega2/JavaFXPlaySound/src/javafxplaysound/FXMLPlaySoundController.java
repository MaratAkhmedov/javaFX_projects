/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxplaysound;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;

/**
 *
 * @author svalero
 */
public class FXMLPlaySoundController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button bPlaySound;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void bPlaySoundOnAction(ActionEvent event) {
       
        AudioClip plonkSound = new AudioClip(getClass().getResource("sounds/ZenTemplateBell.wav").toString()    );
        plonkSound.play();
    }
    
}
