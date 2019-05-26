/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.control;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 *
 * @author Anika.Schmidt
 */
public class Profileediting {
    
    @FXML
    private TextField txtSurename;
    @FXML
    private TextField txtCompany;
    @FXML
    private TextField txtDepartment;
    @FXML
    private TextField txtvacationdays;
    @FXML
    private TextField txthoursperweek;
    @FXML
    private TextField txtdaysperweek;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    
    @FXML
    public void onUpdate(ActionEvent event) throws IOException {
        //Update Profile
    }

    @FXML
    public void onCancel(ActionEvent event) throws IOException {
        //Cancel Profile
    }
}
