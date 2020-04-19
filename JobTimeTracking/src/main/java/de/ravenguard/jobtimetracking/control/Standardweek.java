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

/**
 *
 * @author Anika.Schmidt
 */
public class Standardweek {

  @FXML
  private Label lblSollAusgabe;
  @FXML
  private Label lblHabenAusgabe;
  @FXML
  private Label lblDifferenzAusgabe;
  @FXML
  private Label lblMehrstundenAusgabe;
  @FXML
  private Label lblBreaksAW;

  public Label getLblSollAusgabe() {
    return lblSollAusgabe;
  }

  public Label getLblHabenAusgabe() {
    return lblHabenAusgabe;
  }

  public Label getLblDifferezAusgabe() {
    return lblDifferenzAusgabe;
  }

  public Label getLblMehrstundenAusgabe() {
    return lblMehrstundenAusgabe;
  }

  public Label getLblBreakAW() {
    return lblBreaksAW;
  }
}
