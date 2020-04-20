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
public class EvaluationStandardWeekController {

  @FXML
  private Label lblBalance;
  @FXML
  private Label lblBreaks;
  @FXML
  private Label lblOvertime;
  @FXML
  private Label lblOwn;
  @FXML
  private Label lblQuota;

  public Label getLblBalance() {
    return lblBalance;
  }

  public Label getLblBreaks() {
    return lblBreaks;
  }

  public Label getLblOvertime() {
    return lblOvertime;
  }

  public Label getLblOwn() {
    return lblOwn;
  }

  public Label getLblQuota() {
    return lblQuota;
  }
}
