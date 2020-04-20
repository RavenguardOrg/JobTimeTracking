/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ravenguard.jobtimetracking.view;

import de.ravenguard.jobtimetracking.entity.TimeType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 *
 * @author Anika.Schmidt
 */
public class DatarecordsUpdateController {

  @FXML
  private ComboBox<TimeType> cbbDropDown;
  @FXML
  private DatePicker dpDatePickerBegin;
  @FXML
  private DatePicker dpDatePickerEnd;
  @FXML
  private Label lblDBRUErrors;
  @FXML
  private TextField txtTimePickerBegin;
  @FXML
  private TextField txtTimePickerEnd;

  public ComboBox<TimeType> getCbbDropDown() {
    return cbbDropDown;
  }

  public DatePicker getDpDatePickerBegin() {
    return dpDatePickerBegin;
  }

  public DatePicker getDpDatePickerEnd() {
    return dpDatePickerEnd;
  }

  public Label getLblDBRUErrors() {
    return lblDBRUErrors;
  }

  public TextField getTxtTimePickerBegin() {
    return txtTimePickerBegin;
  }

  public TextField getTxtTimePickerEnd() {
    return txtTimePickerEnd;
  }

  /**
   * initialize
   */
  @FXML
  public void initialize() {
    cbbDropDown.getItems().addAll(TimeType.values());
    cbbDropDown.setConverter(new StringConverter<TimeType>() {

      @Override
      public TimeType fromString(String string) {
        for (TimeType type : TimeType.values()) {
          if (type.getLabel().equalsIgnoreCase(string)) {
            return type;
          }
        }
        return null;
      }

      @Override
      public String toString(TimeType object) {
        return object.getLabel();
      }
    });
  }
}
