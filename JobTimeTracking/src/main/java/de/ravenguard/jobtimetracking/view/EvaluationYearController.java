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

/**
 *
 * @author Anika.Schmidt
 */
public class EvaluationYearController {

  @FXML
  private Label lblBalanceM;
  @FXML
  private Label lblBalanceY;
  @FXML
  private Label lblBreaksM;
  @FXML
  private Label lblBreaksY;
  @FXML
  private Label lblOvertimeM;
  @FXML
  private Label lblOvertimeY;
  @FXML
  private Label lblOwnM;
  @FXML
  private Label lblOwnY;
  @FXML
  private Label lblQuotaM;
  @FXML
  private Label lblQuotaY;

  public Label getLblBalanceM() {
    return lblBalanceM;
  }

  public Label getLblBalanceY() {
    return lblBalanceY;
  }

  public Label getLblBreaksM() {
    return lblBreaksM;
  }

  public Label getLblBreaksY() {
    return lblBreaksY;
  }

  public Label getLblOvertimeM() {
    return lblOvertimeM;
  }

  public Label getLblOvertimeY() {
    return lblOvertimeY;
  }

  public Label getLblOwnM() {
    return lblOwnM;
  }

  public Label getLblOwnY() {
    return lblOwnY;
  }

  public Label getLblQuotaM() {
    return lblQuotaM;
  }

  public Label getLblQuotaY() {
    return lblQuotaY;
  }

}
