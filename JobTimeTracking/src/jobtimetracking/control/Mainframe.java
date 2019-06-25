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
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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
import jobtimetracking.logic.EvaluationData;
import jobtimetracking.logic.TimeTrackingService;
import jobtimetracking.model.TimeType;
import jobtimetracking.model.Timetracking;

/**
 *
 * @author Anika.Schmidt
 */
public class Mainframe {

    @FXML
    private ScrollPane spView;
    @FXML
    private Label lblLiveWorkTime;
    @FXML
    private Button btnEvaluation;

    private Stage primaryStage;
    private TimeTrackingService service;
    private Timeline timeline;
    private int secs = 0;

    private boolean showStandardWeek = true;

    public void setService(TimeTrackingService service) {
        this.service = service;
    }

    /**
     *
     * @param event
     * @throws IOException
     */
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

        // Set Values before
        jobtimetracking.model.Profile profile = service.getProfile();
        profileController.getTxtCompany().setText(profile.getCompany());
        profileController.getTxtDepartment().setText(profile.getDepartment());
        profileController.getTxtPassword().setText(profile.getPassword());
        profileController.getTxtSurename().setText(profile.getSurename());
        profileController.getTxtUsername().setText(profile.getUsername());
        profileController.getTxtdaysperweek().setText(String.valueOf(profile.getDaysPerWeek()));
        profileController.getTxthoursperweek().setText(String.valueOf(profile.getHoursPerWeek()));
        profileController.getTxtvacationdays().setText(String.valueOf(profile.getVacationDays()));

        // Enable/Disable login button depending on whether a username was entered.
        Button saveButton = (Button) dialogProfile.getDialogPane().lookupButton(btnSaveProfile);
        saveButton.addEventFilter(ActionEvent.ACTION,
                eventValidate -> {
                    if (!validateNewProfile(profileController, dialogProfile)) {
                        eventValidate.consume();
                    }
                });

        dialogProfile.showAndWait();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
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
        dialogProfile.setTitle("Manual Time Tracking");
        dialogProfile.getDialogPane().getButtonTypes().addAll(btnSaveProfile, btnCancelProfile);
        dialogProfile.getDialogPane().setContent(anchorPane);
        Stage stageRegister = (Stage) dialogProfile.getDialogPane().getScene().getWindow();
        stageRegister.getIcons().add(new Image(this.getClass().getResourceAsStream("/jobtimetracking/view/ManaualTimeTrackingDialogIcon.png")));

        // Enable/Disable login button depending on whether a username was entered.
        Button saveButton = (Button) dialogProfile.getDialogPane().lookupButton(btnSaveProfile);
        saveButton.addEventFilter(ActionEvent.ACTION,
                eventValidate -> {
                    if (!validateManualTracking(manualtimetracking, dialogProfile)) {
                        eventValidate.consume();
                    }
                });

        dialogProfile.showAndWait();
    }

    @FXML
    public void onEvaluation(ActionEvent event) throws IOException {
        //Open the Evaluation in Main
        if (showStandardWeek) {
            setEvaluation();
        } else {
            setStandardWeek();
        }
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
        dialogProfile.setTitle("Break");
        dialogProfile.getDialogPane().getButtonTypes().addAll(btnEnd);
        dialogProfile.getDialogPane().setContent(anchorPane);
        Stage stageRegister = (Stage) dialogProfile.getDialogPane().getScene().getWindow();
        stageRegister.getIcons().add(new Image(this.getClass().getResourceAsStream("/jobtimetracking/view/BreakIcon.png")));
        service.createTimeTrackingRecord();

        dialogProfile.showAndWait();

        timeline.play();
        service.createTimeTrackingRecord();
    }

    public void start() throws IOException {
        timeline.play();
        service.startAutomaticTimeTracking();
        setStandardWeek();
    }

    /**
     *
     * @throws IOException
     */
    private void setStandardWeek() throws IOException {
        GuiLoader<Standardweek, AnchorPane> loader = new GuiLoader<>("standardweek.fxml");
        Standardweek controller = loader.getController();
        AnchorPane root = loader.getRoot();
        EvaluationData week = service.getWeekData();
        DecimalFormat format = new DecimalFormat("00.00");
        controller.getLblBreakAW().setText(format.format(week.getBreaks()));
        controller.getLblDifferezAusgabe().setText(format.format(week.getBalance()));
        controller.getLblHabenAusgabe().setText(format.format(week.getOwn()));
        controller.getLblMehrstundenAusgabe().setText(format.format(week.getOvertime()));
        controller.getLblSollAusgabe().setText(format.format(week.getQuota()));
        spView.setContent(root);
        btnEvaluation.setText("Evaluation");
        showStandardWeek = true;
    }

    /**
     *
     * @throws IOException
     */
    private void setEvaluation() throws IOException {
        GuiLoader<Evaluation, AnchorPane> loader = new GuiLoader<>("evaluation.fxml");
        Evaluation controller = loader.getController();
        AnchorPane root = loader.getRoot();
        EvaluationData month = service.getEvaluationMonth();
        EvaluationData year = service.getEvaluationYear();
        DecimalFormat format = new DecimalFormat("00.00");
        controller.getLblBreaksMonthE().setText(format.format(month.getBreaks()));
        controller.getLblBreaksYearE().setText(format.format(year.getBreaks()));
        controller.getLblDifferenzMonat().setText(format.format(month.getBalance()));
        controller.getLblDifferenzJahr().setText(format.format(year.getBalance()));
        controller.getLblHabenMonat().setText(format.format(month.getOwn()));
        controller.getLblHabenJahr().setText(format.format(year.getOwn()));
        controller.getLblMehrstundenMonat().setText(format.format(month.getOvertime()));
        controller.getLblMehrstundenJahr().setText(format.format(year.getOvertime()));
        controller.getLblSollMonat().setText(format.format(month.getQuota()));
        controller.getLblSollJahr().setText(format.format(year.getQuota()));
        spView.setContent(root);
        btnEvaluation.setText("StandardWeek");
        showStandardWeek = false;
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

    /**
     *
     * @param profileController
     * @param dialog
     * @return
     */
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
        if (errors.isEmpty()) {
            errors.addAll(service.updateProfile(username, password, company, department, surename, hpw, dpw, vd));
        }
        if (!errors.isEmpty()) {
            // Join ErrorMessages to single String using stream API
            errorMessage.setText(errors.stream().collect(Collectors.joining(System.lineSeparator())));

            // FIT SIZE
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
        }
        return errors.isEmpty();
    }

    /**
     *
     * @param manualTrackingController
     * @param dialog
     * @return
     */
    private boolean validateManualTracking(Manualtimetracking manualTrackingController, Dialog dialog) {
        List<String> errors = new ArrayList<>();
        Label errorMessage = manualTrackingController.getLblMTTErrors();
        String beginTime = manualTrackingController.getTxtTimePickerBegin().getText();
        String endTime = manualTrackingController.getTxtTimePickerEnde().getText();
        LocalDate beginDate = manualTrackingController.getDpDatePickerBegin().getValue();
        LocalDate endDate = manualTrackingController.getDpDatePickerEnde().getValue();
        TimeType timeType = manualTrackingController.getCbbDropDown().getValue();
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
            Timetracking element = new Timetracking();
            element.setType(timeType);
            element.setBegin(beginDate.atTime(timeBegin));
            element.setEnde(endDate.atTime(timeEnd));
            errors.addAll(service.addTimeTracking(element));
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
