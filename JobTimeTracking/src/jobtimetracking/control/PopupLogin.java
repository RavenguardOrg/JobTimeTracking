/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.control;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Anika.Schmidt
 */
public class PopupLogin {
    
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    public void onNewProfle(ActionEvent event) throws IOException {
        //Open Profile to create new profile
    }

    @FXML
    public void onLogin(ActionEvent event) throws IOException {
        //Login with current username and password for automatic status tracker
    }

    @FXML
    public void onCancel(ActionEvent event) throws IOException {
        //Cancel the login insert
    }
}
