/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.control;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author Anika.Schmidt
 */
public class Manualtimetracking {

    @FXML
    private ComboBox cbbDropDown;
    @FXML
    private DatePicker dpDatePickerBegin;
    @FXML
    private DatePicker dpDatePickerEnde;
    @FXML
    private TextField txtTimePickerBegin;
    @FXML
    private TextField txtTimePickerEnde;
    
    @FXML
    public void onSave(ActionEvent event) throws IOException {
        //Save Times
    }

    @FXML
    public void onCancel(ActionEvent event) throws IOException {
        //Cancel Times
    }
}