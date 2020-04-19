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

import de.ravenguard.jobtimetracking.model.TimeType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * Manualtimetracking
 *
 * @author Anika.Schmidt
 */
public class Manualtimetracking {

  @FXML
  private ComboBox<TimeType> cbbDropDown;
  @FXML
  private DatePicker dpDatePickerBegin;
  @FXML
  private DatePicker dpDatePickerEnde;
  @FXML
  private TextField txtTimePickerBegin;
  @FXML
  private TextField txtTimePickerEnde;
  @FXML
  private Label lblMTTErrors;

  public Label getLblMTTErrors() {
    return lblMTTErrors;
  }

  public void setLblMTTErrors(Label lblMTTErrors) {
    this.lblMTTErrors = lblMTTErrors;
  }

  public ComboBox<TimeType> getCbbDropDown() {
    return cbbDropDown;
  }

  public DatePicker getDpDatePickerBegin() {
    return dpDatePickerBegin;
  }

  public DatePicker getDpDatePickerEnde() {
    return dpDatePickerEnde;
  }

  public TextField getTxtTimePickerBegin() {
    return txtTimePickerBegin;
  }

  public TextField getTxtTimePickerEnde() {
    return txtTimePickerEnde;
  }

  /**
   * initialize
   */
  @FXML
  public void initialize() {
    cbbDropDown.getItems().addAll(TimeType.values());
    cbbDropDown.setConverter(new StringConverter<TimeType>() {
      @Override
      public String toString(TimeType object) {
        return object.getLabel();
      }

      @Override
      public TimeType fromString(String string) {
        for (TimeType type : TimeType.values()) {
          if (type.getLabel().equalsIgnoreCase(string)) {
            return type;
          }
        }
        return null;
      }
    });
  }
}
