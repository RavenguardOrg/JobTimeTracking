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
package de.ravenguard.jobtimetracking.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Anika.Schmidt
 */
public class Profileediting {

  @FXML
  private Label lblErrorMessage;

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

  public Label getLblErrorMessage() {
    return lblErrorMessage;
  }

  public TextField getTxtSurename() {
    return txtSurename;
  }

  public TextField getTxtCompany() {
    return txtCompany;
  }

  public TextField getTxtDepartment() {
    return txtDepartment;
  }

  public TextField getTxtvacationdays() {
    return txtvacationdays;
  }

  public TextField getTxthoursperweek() {
    return txthoursperweek;
  }

  public TextField getTxtdaysperweek() {
    return txtdaysperweek;
  }

  public TextField getTxtUsername() {
    return txtUsername;
  }

  public TextField getTxtPassword() {
    return txtPassword;
  }
}
