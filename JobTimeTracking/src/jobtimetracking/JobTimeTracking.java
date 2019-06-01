/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jobtimetracking.control.Mainframe;
import jobtimetracking.control.Profile;

/**
 *
 * @author Anika.Schmidt
 */
public class JobTimeTracking extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Do basic loading
            final GuiLoader<Mainframe, Parent> main = new GuiLoader<>("mainframe.fxml");
            final Parent root = main.getRoot();
            final Scene scene = new Scene(root, 797, 625);
            final Mainframe controller = main.getController();

            primaryStage.setMaximized(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Job Time Tracking");
            primaryStage.setResizable(false);
            primaryStage.show();

            // Create the custom dialog.
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Login");
            dialog.setHeaderText("Login to start timetracking or create new profile.");

            // Set the button types.
            ButtonType createButtonType = new ButtonType("New Profile", ButtonData.OTHER);
            ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(createButtonType, loginButtonType, ButtonType.CANCEL);

            // Create the username and password labels and fields.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 10, 10, 10));

            TextField username = new TextField();
            username.setPromptText("Username");
            PasswordField password = new PasswordField();
            password.setPromptText("Password");
            Label errorMessage = new Label();

            grid.add(new Label("Username:"), 0, 0);
            grid.add(username, 1, 0);
            grid.add(new Label("Password:"), 0, 1);
            grid.add(password, 1, 1);
            grid.add(errorMessage, 0, 2, 2, 1);

            // Enable/Disable login button depending on whether a username was entered.
            Button loginButton = (Button)dialog.getDialogPane().lookupButton(loginButtonType);
            loginButton.addEventFilter(ActionEvent.ACTION, 
                    event -> {
                        if(!validateLogin(username, password, errorMessage, dialog)) {
                            event.consume();
                        }
                    });

            dialog.getDialogPane().setContent(grid);

            // Request focus on the username field by default.
            Platform.runLater(() -> username.requestFocus());

            Optional<ButtonType> result = dialog.showAndWait();
            handleLogin(result, primaryStage, username, password, errorMessage);
        } catch (IOException e) {

        }
    }

    private void handleLogin(Optional<ButtonType> result, Stage primaryStage, TextField username, PasswordField password, Label errorMessage) throws IOException {
        if (result.isPresent()) {
            ButtonType result2 = result.get();
            if (null != result2.getButtonData()) {
                switch (result2.getButtonData()) {
                    case CANCEL_CLOSE:
                        primaryStage.close();
                        break;
                    //Load Profile from file
                    case OK_DONE:
                        String userName = username.getText();
                        String passWord = password.getText();

                        // Call Service
                        break;
                    // Create new Profile
                    case OTHER:
                        final GuiLoader<Profile, AnchorPane> helper
                                = new GuiLoader<>("profile.fxml");
                        Profile profileController = helper.getController();
                        AnchorPane anchorPane = helper.getRoot();

                        ButtonType btnSaveProfile = new ButtonType("Save", ButtonData.OK_DONE);
                        ButtonType btnCancelProfile = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

                        // Initialize Dialog for new Profile
                        Dialog<ButtonType> dialogProfile = new Dialog<>();
                        dialogProfile.initModality(Modality.WINDOW_MODAL);
                        dialogProfile.initOwner(primaryStage);
                        dialogProfile.setTitle("Create new Profile");
                        dialogProfile.getDialogPane().getButtonTypes().addAll(btnSaveProfile, btnCancelProfile);
                        dialogProfile.getDialogPane().setContent(anchorPane);

                        // Enable/Disable login button depending on whether a username was entered.
                        Node saveButton = dialogProfile.getDialogPane().lookupButton(btnSaveProfile);
                        saveButton.setDisable(validateNewProfile(profileController));

                        // Do some validation (using the Java 8 lambda syntax).
                        profileController.getTxtUsername().textProperty().addListener((observable, oldValue, newValue) -> {
                            saveButton.setDisable(validateNewProfile(profileController));
                        });

                        Optional<ButtonType> result3 = dialogProfile.showAndWait();
                        handleCreateProfile(result3, primaryStage, profileController);

                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void handleCreateProfile(Optional<ButtonType> result3, Stage primaryStage, Profile profileController) {
        if (result3.isPresent()) {
            ButtonType result4 = result3.get();
            if (null != result4.getButtonData()) {
                switch (result4.getButtonData()) {
                    case CANCEL_CLOSE:
                        primaryStage.close();
                        break;
                    case OK_DONE:
                        String username = profileController.getTxtUsername().getText();
                        String password = profileController.getTxtPassword().getText();
                        String company = profileController.getTxtCompany().getText();
                        String department = profileController.getTxtDepartment().getText();
                        String surename = profileController.getTxtSurename().getText();
                        String firstname = profileController.getTxtFirstname().getText();
                        String hoursperweek = profileController.getTxthoursperweek().getText();
                        String daysperweek = profileController.getTxtdaysperweek().getText();
                        String vacationdays = profileController.getTxtvacationdays().getText();
                        

                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void onBreakEnd(ActionEvent event) throws IOException {
        //End Break
    }

    private boolean validateNewProfile(Profile profileController) {
        boolean retValue = true;
        Label errorMessage = profileController.getLblErrorMessage();
        String username = profileController.getTxtUsername().getText();
        String password = profileController.getTxtPassword().getText();
        String company = profileController.getTxtCompany().getText();
        String department = profileController.getTxtDepartment().getText();
        String surename = profileController.getTxtSurename().getText();
        String firstname = profileController.getTxtFirstname().getText();
        String hoursperweek = profileController.getTxthoursperweek().getText();
        String daysperweek = profileController.getTxtdaysperweek().getText();
        String vacationdays = profileController.getTxtvacationdays().getText();
        errorMessage.setText("");
        if (password == null || password.trim().isEmpty()) {
            errorMessage.setText("Password may not be empty!");
            retValue = false;
        }
        if (username == null || username.trim().isEmpty()) {
            errorMessage.setText("Username may not be empty!");
            retValue = false;
        }
        if (company == null || company.trim().isEmpty()) {
            errorMessage.setText("Company may not be empty!");
            retValue = false;
        }
        if (department == null || department.trim().isEmpty()) {
            errorMessage.setText("Department may not be empty!");
            retValue = false;
        }
        if (surename == null || surename.trim().isEmpty()) {
            errorMessage.setText("Surename may not be empty!");
            retValue = false;
        }
        if (firstname == null || firstname.trim().isEmpty()) {
            errorMessage.setText("Firstname may not be empty!");
            retValue = false;
        }
        if (hoursperweek == null || hoursperweek.trim().isEmpty()) {
            errorMessage.setText("Hours per Week may not be empty!");
            retValue = false;
        } else {
            try {
                double hpw = Double.parseDouble(hoursperweek);
                if (hpw <= 0) {
                    errorMessage.setText("Hours per Week must be Positive!");
                    retValue = false;
                }

            } catch (NumberFormatException e) {
                errorMessage.setText("Hours per Week must be float!");
                retValue = false;
            }
        }
        if (daysperweek == null || daysperweek.trim().isEmpty()) {
            errorMessage.setText("Days per Week may not be empty!");
            retValue = false;
        }else {
            try {
                double hpw = Double.parseDouble(hoursperweek);
                if (hpw <= 0) {
                    errorMessage.setText("Hors per Week must be Positive!");
                    retValue = false;
                }

            } catch (NumberFormatException e) {
                errorMessage.setText("Hours per Week must be float!");
                retValue = false;
            }
        }
        if (vacationdays == null || vacationdays.trim().isEmpty()) {
            errorMessage.setText("Vacation Days may not be empty!");
            retValue = false;
        }else {
            try {
                double hpw = Double.parseDouble(hoursperweek);
                if (hpw <= 0) {
                    errorMessage.setText("Hors per Week must be Positive!");
                    retValue = false;
                }

            } catch (NumberFormatException e) {
                errorMessage.setText("Hours per Week must be float!");
                retValue = false;
            }
        }
        return retValue;
    }

    private boolean validateLogin(TextField username, PasswordField password, Label errorMessage, Dialog dialog) {
        String userName = username.getText();
        String passWord = password.getText();
        errorMessage.setText("");
        List<String> errors = new ArrayList<>();
        if (userName == null || userName.trim().isEmpty()) {
            errors.add("Username may not be empty!");
        }
        if (passWord == null || passWord.trim().isEmpty()) {
            errors.add("Password may not be empty!");
        }
        if(!errors.isEmpty()) {
            errorMessage.setText(errors.stream().collect(Collectors.joining(System.lineSeparator())));
            errorMessage.setTextFill(Color.rgb(210, 39, 30));
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
        }
        return errors.isEmpty();
    }
}
