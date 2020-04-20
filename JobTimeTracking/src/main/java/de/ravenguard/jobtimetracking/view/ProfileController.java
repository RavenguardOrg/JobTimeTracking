/*
 * Copyright (C) 2019 Anika Schmidt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.ravenguard.jobtimetracking.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Anika.Schmidt
 */
public class ProfileController {

  @FXML
  private TextField txtsSurename;
  @FXML
  private TextField txtFirstname;
  @FXML
  private TextField txtSecondname;
  @FXML
  private TextField txtCompany;
  @FXML
  private TextField txtDepartment;
  @FXML
  private TextField txtHoursPerWeek;
  @FXML
  private TextField txtDaysPerWeek;
  @FXML
  private TextField txtVacationDays;
  @FXML
  private TextField txtUsername;
  @FXML
  private PasswordField txtPassword;
  @FXML
  private Label lblErrorMessage;

  public Label getLblErrorMessage() {
    return lblErrorMessage;
  }

  public TextField getTxtsSurename() {
    return txtsSurename;
  }

  public void setTxtsSurename(TextField txtsSurename) {
    this.txtsSurename = txtsSurename;
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

  public TextField getTxtHoursPerWeek() {
    return txtHoursPerWeek;
  }

  public void setTxtHoursPerWeek(TextField txtHoursPerWeek) {
    this.txtHoursPerWeek = txtHoursPerWeek;
  }

  public TextField getTxtDaysPerWeek() {
    return txtDaysPerWeek;
  }

  public void setTxtDaysPerWeek(TextField txtDaysPerWeek) {
    this.txtDaysPerWeek = txtDaysPerWeek;
  }

  public TextField getTxtVacationDays() {
    return txtVacationDays;
  }

  public void setTxtVacationDays(TextField txtVacationDays) {
    this.txtVacationDays = txtVacationDays;
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
