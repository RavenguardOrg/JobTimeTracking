/*
 * Copyright (C) 2020 Anika.Schmidt
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

import de.ravenguard.jobtimetracking.GuiLoader;
import de.ravenguard.jobtimetracking.JobTimeTracking;
import de.ravenguard.jobtimetracking.boundary.TimeTrackingService;
import de.ravenguard.jobtimetracking.entity.TimeType;
import de.ravenguard.jobtimetracking.entity.Timetracking;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.persistence.PersistenceException;

/**
 *
 * @author Anika.Schmidt
 */
public class DataRecordDBController {

  @FXML
  private TableColumn<Timetracking, String> colBegin;
  @FXML
  private TableColumn<Timetracking, String> colEnd;
  @FXML
  private TableColumn<Timetracking, String> colType;
  @FXML
  private Label lblDrDbErrors;
  private Stage primaryStage;
  private TimeTrackingService service;
  @FXML
  private TableView<Timetracking> tblvDatarecords;

  public TableView<Timetracking> getTblvDatarecords() {
    return tblvDatarecords;
  }

  public void initialize() {
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    colBegin.setCellValueFactory(cell -> {
      if (cell == null) {
        return null;
      } else {
        Timetracking timetracking = cell.getValue();
        if (timetracking.getBegin() == null) {
          return null;
        } else {
          return new SimpleObjectProperty<>(formatter.format(timetracking.getBegin()));
        }
      }
    });

    colEnd.setCellValueFactory(cell -> {
      if (cell == null) {
        return null;
      } else {
        Timetracking timetracking = cell.getValue();
        if (timetracking.getEnd() == null) {
          return null;
        } else {
          return new SimpleObjectProperty<>(formatter.format(timetracking.getEnd()));
        }
      }
    });
    colType.setCellValueFactory(cell -> {
      if (cell == null) {
        return null;
      } else {
        Timetracking timetracking = cell.getValue();
        if (timetracking.getType() == null) {
          return null;
        } else {
          return new SimpleObjectProperty<>(timetracking.getType().getLabel());
        }
      }
    });
  }

  public void onDelete(ActionEvent event) {
    Timetracking selected = tblvDatarecords.getSelectionModel().getSelectedItem();
    if (selected != null) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmation Dialog");
      alert.setContentText("Are you sure to delete the selected record?");
      alert.getButtonTypes().clear();
      alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

      alert.showAndWait().ifPresent(button -> {
        if (button == ButtonType.YES) {
          try {
            service.deleteTimeTracking(selected);
            tblvDatarecords.getItems().remove(selected);
            lblDrDbErrors.setText("Record deleted");
            lblDrDbErrors.setStyle("color: blue;");
          } catch (PersistenceException ex) {
            Logger.getLogger(JobTimeTracking.class.getName()).log(Level.SEVERE, null, ex);
            lblDrDbErrors.setText("Error deleting record");
            lblDrDbErrors.setStyle("color: red;");
          }
        }
      });
    } else {
      lblDrDbErrors.setText("Please select a record in the table");
      lblDrDbErrors.setStyle("color: red;");
    }
  }

  public void onUpdate(ActionEvent event) {
    Timetracking selected = tblvDatarecords.getSelectionModel().getSelectedItem();
    if (selected != null) {
      try {
        final GuiLoader<DatarecordsUpdateController, AnchorPane> helper
                = new GuiLoader<>("datarecordsUpdate.fxml");
        DatarecordsUpdateController controller = helper.getController();
        AnchorPane anchorPane = helper.getRoot();

        ButtonType btnSaveProfile = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelProfile = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Initialize Dialog for edit Timetracking
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Update Timetracking");
        dialog.getDialogPane().getButtonTypes().addAll(btnSaveProfile, btnCancelProfile);
        dialog.getDialogPane().setContent(anchorPane);
        Stage stageRegister = (Stage) dialog.getDialogPane().getScene().getWindow();
        stageRegister.getIcons().add(new Image(this.getClass().getResourceAsStream("/de/ravenguard/jobtimetracking/images/EditTimetrackingDialogTitle.png")));

        // Initialize Values
        controller.getDpDatePickerBegin().setValue(selected.getBegin().toLocalDate());
        controller.getDpDatePickerEnd().setValue(selected.getEnd().toLocalDate());
        controller.getTxtTimePickerBegin().setText(selected.getBegin().format(DateTimeFormatter.ISO_TIME));
        controller.getTxtTimePickerEnd().setText(selected.getEnd().format(DateTimeFormatter.ISO_TIME));
        controller.getCbbDropDown().setValue(selected.getType());

        // Setup button events
        Button saveButton = (Button) dialog.getDialogPane().lookupButton(btnSaveProfile);
        saveButton.addEventFilter(ActionEvent.ACTION,
                eventValidate -> {
                  if (!validateTrackingUpdate(controller, dialog, selected)) {
                    eventValidate.consume();
                  }
                });

        // Call dialog
        dialog.showAndWait();

        tblvDatarecords.getItems().clear();
        tblvDatarecords.getItems().addAll(service.getTrackings());
      } catch (IOException ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        lblDrDbErrors.setText("Error opening edit timetracking");
        lblDrDbErrors.setStyle("color: red;");
      }
    } else {
      lblDrDbErrors.setText("Please select a record in the table");
      lblDrDbErrors.setStyle("color: red;");
    }
  }

  public void setPrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public void setService(TimeTrackingService service) {
    this.service = service;
  }

  public void setTblvDatarecords(TableView<Timetracking> tblvDatarecords) {
    this.tblvDatarecords = tblvDatarecords;
  }

  private boolean validateTrackingUpdate(DatarecordsUpdateController controller, Dialog<ButtonType> dialog, Timetracking element) {
    List<String> errors = new ArrayList<>();
    Label errorMessage = controller.getLblDBRUErrors();
    String beginTime = controller.getTxtTimePickerBegin().getText();
    String endTime = controller.getTxtTimePickerEnd().getText();
    LocalDate beginDate = controller.getDpDatePickerBegin().getValue();
    LocalDate endDate = controller.getDpDatePickerEnd().getValue();
    TimeType timeType = controller.getCbbDropDown().getValue();
    LocalTime timeBegin = null;
    LocalTime timeEnd = null;

    errorMessage.setText("");
    // Check input Parameters
    if (beginDate == null || beginDate.isAfter(endDate)) {
      errors.add("Begin date may not be empty!");
    }
    if (endDate == null || endDate.isBefore(beginDate)) {
      errors.add("End date may not be empty!");
    }
    if (timeType == null) {
      errors.add("Please choose a time type!");
    }
    if (timeType == null || !timeType.isCompleteDay()) {
      if (beginTime == null || beginTime.trim().isEmpty()) {
        errors.add("Begin time may not be empty!");
      }
      if (endTime == null || endTime.trim().isEmpty()) {
        errors.add("End time may not be empty!");
      }
      if (errors.isEmpty()) {
        try {
          timeBegin = LocalTime.parse(beginTime);
        } catch (DateTimeParseException ex) {
          errors.add("Begin Time is not valid!");
        }
        try {
          timeEnd = LocalTime.parse(endTime);
        } catch (DateTimeParseException ex) {
          errors.add("End Time is not valid!");
        }
      }
    } else {
      timeBegin = LocalTime.of(0, 0, 0);
      timeEnd = LocalTime.of(23, 59, 59);
    }

    if (errors.isEmpty() && beginDate != null && endDate != null) {
      element.setType(timeType);
      element.setBegin(beginDate.atTime(timeBegin));
      element.setEnd(endDate.atTime(timeEnd));
      errors.addAll(service.updateTimeTracking(element));
    }
    if (!errors.isEmpty()) {
      // Join ErrorMessages to single String using stream API
      errorMessage.setText(errors.stream().collect(Collectors.joining(System.lineSeparator())));

      // FIT SIZE
      dialog.getDialogPane().getScene().getWindow().sizeToScene();
    }
    return errors.isEmpty();
  }
}
