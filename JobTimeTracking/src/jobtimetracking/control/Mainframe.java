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
package jobtimetracking.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import jobtimetracking.GuiLoader;
import jobtimetracking.logic.TimeTrackingService;

/**
 *
 * @author Anika.Schmidt
 */
public class Mainframe {

    @FXML
    private ScrollPane spView;
    @FXML
    private Label lblLiveWorkTime;

    private Stage primaryStage;
    private TimeTrackingService service;
    private Timeline timeline;
    private int secs = 0;

    public void setService(TimeTrackingService service) {
        this.service = service;
    }

    @FXML
    public void onEditProfile(ActionEvent event) throws IOException {
        //Open the Profil in Dialog
        final GuiLoader<Profileediting, AnchorPane> helper
                = new GuiLoader<>("profileediting.fxml");
        Profileediting profileController = helper.getController();
        AnchorPane anchorPane = helper.getRoot();

        ButtonType btnSaveProfile = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelProfile = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Initialize Dialog for edit Profile
        Dialog<ButtonType> dialogProfile = new Dialog<>();
        dialogProfile.initModality(Modality.WINDOW_MODAL);
        dialogProfile.initOwner(primaryStage);
        dialogProfile.setTitle("Update Profile");
        dialogProfile.getDialogPane().getButtonTypes().addAll(btnSaveProfile, btnCancelProfile);
        dialogProfile.getDialogPane().setContent(anchorPane);
        Stage stageRegister = (Stage) dialogProfile.getDialogPane().getScene().getWindow();
        stageRegister.getIcons().add(new Image(this.getClass().getResourceAsStream("/jobtimetracking/view/ProfileTitleIcon.png")));

        // Enable/Disable login button depending on whether a username was entered.
        Button saveButton = (Button) dialogProfile.getDialogPane().lookupButton(btnSaveProfile);
        saveButton.addEventFilter(ActionEvent.ACTION,
                eventValidate -> {
                    if (!validateNewProfile(profileController, dialogProfile)) {
                        eventValidate.consume();
                    }
                });
    }

    @FXML
    public void onManualTimeTracking(ActionEvent event) throws IOException {
        //Open the ManualTimeTracking in Main
        final GuiLoader<Manualtimetracking, AnchorPane> helper
                = new GuiLoader<>("manualtimetracking.fxml");
        Manualtimetracking manualtimetracking = helper.getController();
        AnchorPane anchorPane = helper.getRoot();

        ButtonType btnSaveProfile = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelProfile = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        Dialog<ButtonType> dialogProfile = new Dialog<>();
        dialogProfile.initModality(Modality.WINDOW_MODAL);
        dialogProfile.initOwner(primaryStage);
        dialogProfile.setTitle("Manual time tracking");
        dialogProfile.getDialogPane().getButtonTypes().addAll(btnSaveProfile, btnCancelProfile);
        dialogProfile.getDialogPane().setContent(anchorPane);
        Stage stageRegister = (Stage) dialogProfile.getDialogPane().getScene().getWindow();
        stageRegister.getIcons().add(new Image(this.getClass().getResourceAsStream("/jobtimetracking/view/ManaualTimeTrackingDialogIcon.png")));

        dialogProfile.showAndWait();
    }

    @FXML
    public void onEvaluation(ActionEvent event) throws IOException {
        //Open the Evaluation in Main
        spView.setContent(spView);
    }

    @FXML
    public void onBreak(ActionEvent event) throws IOException {
        //Open a popup with break information
        timeline.pause();

        String template = "/jobtimetracking/view/popupBreak.fxml";
        final FXMLLoader loader = new FXMLLoader(getClass().getResource(template));
        AnchorPane anchorPane = loader.load();

        ButtonType btnEnd = new ButtonType("End Break", ButtonBar.ButtonData.OK_DONE);

        Dialog<ButtonType> dialogProfile = new Dialog<>();
        dialogProfile.initModality(Modality.WINDOW_MODAL);
        dialogProfile.initOwner(primaryStage);
        dialogProfile.setTitle("Create new Profile");
        dialogProfile.getDialogPane().getButtonTypes().addAll(btnEnd);
        dialogProfile.getDialogPane().setContent(anchorPane);
        Stage stageRegister = (Stage) dialogProfile.getDialogPane().getScene().getWindow();
        stageRegister.getIcons().add(new Image(this.getClass().getResourceAsStream("/jobtimetracking/view/BreakTitleIcon.png")));
        service.createTimeTrackingRecord();

        dialogProfile.showAndWait();

        timeline.play();
        service.createTimeTrackingRecord();
    }

    public void start() {
        timeline.play();
        service.startAutomaticTimeTracking();
    }

    @FXML
    public void initialize() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            change(lblLiveWorkTime);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
    }

    void change(Label text) {

        int min = (secs / 60) % 60;
        int hour = (secs / 3600);
        int sec = (secs % 60);

        String formatted = String.format("%02d:%02d:%02d", hour, min, sec);

        text.setText(formatted);
        secs++;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private boolean validateNewProfile(Profileediting profileController, Dialog dialog) {
        List<String> errors = new ArrayList<>();
        Label errorMessage = profileController.getLblErrorMessage();
        String username = profileController.getTxtUsername().getText();
        String password = profileController.getTxtPassword().getText();
        String company = profileController.getTxtCompany().getText();
        String department = profileController.getTxtDepartment().getText();
        String surename = profileController.getTxtSurename().getText();
        String hoursperweek = profileController.getTxthoursperweek().getText();
        String daysperweek = profileController.getTxtdaysperweek().getText();
        String vacationdays = profileController.getTxtvacationdays().getText();
        double hpw = 0;
        double dpw = 0;
        double vd = 0;
        errorMessage.setText("");
        // Check input Parameters
        if (password == null || password.trim().isEmpty()) {
            errors.add("Password may not be empty!");
        }
        if (username == null || username.trim().isEmpty()) {
            errors.add("Username may not be empty!");
        }
        if (company == null || company.trim().isEmpty()) {
            errors.add("Company may not be empty!");
        }
        if (department == null || department.trim().isEmpty()) {
            errors.add("Department may not be empty!");
        }
        if (surename == null || surename.trim().isEmpty()) {
            errors.add("Surename may not be empty!");
        }
        if (hoursperweek == null || hoursperweek.trim().isEmpty()) {
            errors.add("Hours per Week may not be empty!");
        } else {
            try {
                hpw = Double.parseDouble(hoursperweek);
                if (hpw <= 0) {
                    errors.add("Hours per Week must be Positive!");
                }

            } catch (NumberFormatException e) {
                errors.add("Hours per Week must be float!");
            }
        }
        if (daysperweek == null || daysperweek.trim().isEmpty()) {
            errors.add("Days per Week may not be empty!");
        } else {
            try {
                dpw = Double.parseDouble(daysperweek);
                if (dpw <= 0) {
                    errors.add("Days per Week  must be Positive!");
                }

            } catch (NumberFormatException e) {
                errors.add("Days per Week must be a Number!");
            }
        }
        if (vacationdays == null || vacationdays.trim().isEmpty()) {
            errors.add("Vacation Days may not be empty!");
        } else {
            try {
                vd = Double.parseDouble(vacationdays);
                if (vd <= 0) {
                    errors.add("Vacation days must be Positive!");
                }

            } catch (NumberFormatException e) {
                errors.add("Vacation days must be a number!");
            }
        }
        errors.addAll(service.updateProfile(username, password, company, department, surename, hpw, dpw, vd));
        if (!errors.isEmpty()) {
            // Join ErrorMessages to single String using stream API
            errorMessage.setText(errors.stream().collect(Collectors.joining(System.lineSeparator())));

            // FIT SIZE
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
        }
        return errors.isEmpty();
    }
}
