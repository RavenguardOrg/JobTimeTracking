/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Anika.Schmidt
 */
public class Profile {

    @FXML
    private TextField txtSurename;
    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtSecondname;
    @FXML
    private TextField txtCompany;
    @FXML
    private TextField txtDepartment;
    @FXML
    private TextField txthoursperweek;
    @FXML
    private TextField txtdaysperweek;
    @FXML
    private TextField txtvacationdays;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblErrorMessage;

    public Label getLblErrorMessage() {
        return lblErrorMessage;
    }

    public void setLblErrorMessage(Label lblErrorMessage) {
        this.lblErrorMessage = lblErrorMessage;
    }

    public TextField getTxtSurename() {
        return txtSurename;
    }

    public void setTxtSurename(TextField txtSurename) {
        this.txtSurename = txtSurename;
    }

    public TextField getTxtFirstname() {
        return txtFirstname;
    }

    public void setTxtFirstname(TextField txtFirstname) {
        this.txtFirstname = txtFirstname;
    }

    public TextField getTxtSecondname() {
        return txtSecondname;
    }

    public void setTxtSecondname(TextField txtSecondname) {
        this.txtSecondname = txtSecondname;
    }

    public TextField getTxtCompany() {
        return txtCompany;
    }

    public void setTxtCompany(TextField txtCompany) {
        this.txtCompany = txtCompany;
    }

    public TextField getTxtDepartment() {
        return txtDepartment;
    }

    public void setTxtDepartment(TextField txtDepartment) {
        this.txtDepartment = txtDepartment;
    }

    public TextField getTxthoursperweek() {
        return txthoursperweek;
    }

    public void setTxthoursperweek(TextField txthoursperweek) {
        this.txthoursperweek = txthoursperweek;
    }

    public TextField getTxtdaysperweek() {
        return txtdaysperweek;
    }

    public void setTxtdaysperweek(TextField txtdaysperweek) {
        this.txtdaysperweek = txtdaysperweek;
    }

    public TextField getTxtvacationdays() {
        return txtvacationdays;
    }

    public void setTxtvacationdays(TextField txtvacationdays) {
        this.txtvacationdays = txtvacationdays;
    }

    public TextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(TextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public PasswordField getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(PasswordField txtPassword) {
        this.txtPassword = txtPassword;
    }

}
