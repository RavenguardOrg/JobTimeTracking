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
package de.ravenguard.jobtimetracking;

import de.ravenguard.jobtimetracking.GuiLoader;
import de.ravenguard.jobtimetracking.JobTimeTracking;
import de.ravenguard.jobtimetracking.view.MainframeController;
import de.ravenguard.jobtimetracking.view.ProfileController;
import de.ravenguard.jobtimetracking.boundary.TimeTrackingService;
import de.ravenguard.jobtimetracking.control.LoginException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.persistence.NoResultException;

/**
 *
 * @author Anika.Schmidt
 */
public class JobTimeTracking extends Application {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    service.close();
  }

  private final TimeTrackingService service = new TimeTrackingService();

  /**
   *
   * @param primaryStage
   */
  @Override
  public void start(Stage primaryStage) {
    try {
      // Do basic loading
      final GuiLoader<MainframeController, Parent> main = new GuiLoader<>("mainframe.fxml");
      final Parent root = main.getRoot();
      final Scene scene = new Scene(root);
      final MainframeController controller = main.getController();
      controller.setPrimaryStage(primaryStage);
      controller.setService(service);

      primaryStage.setMaximized(false);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Job Time Tracking");
      primaryStage.setResizable(true);
      primaryStage.getIcons().add(new Image(JobTimeTracking.class.getResourceAsStream("/de/ravenguard/jobtimetracking/images/TimerMainTitleIcon.png")));
      primaryStage.show();

      // Create the custom dialog.
      Dialog<ButtonType> dialog = new Dialog<>();
      dialog.setTitle("Login");
      dialog.setHeaderText("Login to start timetracking or create new profile.");

      // Set the button types.
      ButtonType createButtonType = new ButtonType("New Profile", ButtonData.OTHER);
      ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
      dialog.getDialogPane().getButtonTypes().addAll(createButtonType, loginButtonType, ButtonType.CANCEL);
      Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
      dialogStage.getIcons().add(new Image(JobTimeTracking.class.getResourceAsStream("/de/ravenguard/jobtimetracking/images/LoginTitleIcon.png")));

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
      errorMessage.setFont(Font.font("System", FontWeight.BOLD, 14));
      errorMessage.setTextFill(Color.rgb(210, 39, 30));

      grid.add(new Label("Username:"), 0, 0);
      grid.add(username, 1, 0);
      grid.add(new Label("Password:"), 0, 1);
      grid.add(password, 1, 1);
      grid.add(errorMessage, 0, 2, 2, 1);

      // Enable/Disable login button depending on whether a username was entered.
      Button loginButton = (Button) dialog.getDialogPane().lookupButton(loginButtonType);
      loginButton.addEventFilter(ActionEvent.ACTION,
              event -> {
                if (!validateLogin(username, password, errorMessage, dialog, service)) {
                  event.consume();
                }
              });

      dialog.getDialogPane().setContent(grid);

      // Request focus on the username field by default.
      Platform.runLater(() -> username.requestFocus());

      Optional<ButtonType> result = dialog.showAndWait();
      if (handleLogin(result, primaryStage)) {
        controller.start();
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
          service.endAutomaticTimeTracking();
        });
      }
    } catch (IOException ex) {
      Logger.getLogger(JobTimeTracking.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Handle create new Profile
   *
   * @param result New Profile Dialog Result
   * @param primaryStage Main Stage
   * @return true if successful creation, otherwise false
   */
  private boolean handleCreateProfile(Optional<ButtonType> result, Stage primaryStage) {
    if (result.isPresent()) {
      ButtonType resultButtonType = result.get();
      if (null != resultButtonType.getButtonData()) {
        switch (resultButtonType.getButtonData()) {
          case CANCEL_CLOSE:
            primaryStage.close();
            return false;
          case OK_DONE:
            return true;
          default:
            break;
        }
      }
    }
    return false;
  }

  /**
   * Handle Loging Dialog
   *
   * @param result Login Dialog Result
   * @param primaryStage Main Stage
   * @return true if successful login or registration, otherwise false
   * @throws IOException faild to load fxml-Template
   */
  private boolean handleLogin(Optional<ButtonType> result, Stage primaryStage) throws IOException {
    if (result.isPresent()) {
      ButtonType result2 = result.get();
      if (null != result2.getButtonData()) {
        switch (result2.getButtonData()) {
          case CANCEL_CLOSE:
            primaryStage.close();
            return false;
          //Load Profile from file
          case OK_DONE:
            return true;
          // Create new Profile
          case OTHER:
            final GuiLoader<ProfileController, AnchorPane> helper
                    = new GuiLoader<>("profile.fxml");
            ProfileController profileController = helper.getController();
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
            Stage stageRegister = (Stage) dialogProfile.getDialogPane().getScene().getWindow();
            stageRegister.getIcons().add(new Image(this.getClass().getResourceAsStream("/de/ravenguard/jobtimetracking/images/ProfileTitleIcon.png")));

            // Enable/Disable login button depending on whether a username was entered.
            Button saveButton = (Button) dialogProfile.getDialogPane().lookupButton(btnSaveProfile);
            saveButton.addEventFilter(ActionEvent.ACTION,
                    event -> {
                      if (!validateNewProfile(profileController, dialogProfile)) {
                        event.consume();
                      }
                    });

            Optional<ButtonType> result3 = dialogProfile.showAndWait();
            return handleCreateProfile(result3, primaryStage);
          default:
            break;
        }
      }
    }
    return false;
  }

  /**
   * ValidateLogin
   *
   * @param username Textfield for Username
   * @param password Textfield for Password
   * @param errorMessage Label to display ErrorMessages
   * @param dialog to resize
   * @return true if everything is ok, otherwise false
   */
  private boolean validateLogin(TextField username, PasswordField password, Label errorMessage, Dialog<ButtonType> dialog, TimeTrackingService service) {
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
    try {
      service.login(userName, passWord);
    } catch (NoResultException | LoginException ex) {
      Logger.getLogger(JobTimeTracking.class.getName()).log(Level.SEVERE, null, ex);
      errors.add("Login not successful");
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
   * Validate input from new Profile
   *
   * @param profileController Controller to access input
   * @param dialog to resize
   * @return true if everything is ok, otherwise false
   */
  private boolean validateNewProfile(ProfileController profileController, Dialog<ButtonType> dialog) {
    List<String> errors = new ArrayList<>();
    Label errorMessage = profileController.getLblErrorMessage();
    String username = profileController.getTxtUsername().getText();
    String password = profileController.getTxtPassword().getText();
    String company = profileController.getTxtCompany().getText();
    String department = profileController.getTxtDepartment().getText();
    String surename = profileController.getTxtsSurename().getText();
    String firstname = profileController.getTxtFirstname().getText();
    String secondname = profileController.getTxtSecondname().getText();
    String hoursperweek = profileController.getTxtHoursPerWeek().getText();
    String daysperweek = profileController.getTxtDaysPerWeek().getText();
    String vacationdays = profileController.getTxtVacationDays().getText();
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
    if (firstname == null || firstname.trim().isEmpty()) {
      errors.add("Firstname may not be empty!");
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
      service.register(username, password, company, department, surename, firstname, secondname, hpw, dpw, vd);
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
