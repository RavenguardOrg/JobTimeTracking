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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Anika.Schmidt
 */
public class Mainframe {

    @FXML
    private ScrollPane spView;
    @FXML
    private Label lblStatusTimeTracking;
    @FXML
    private AnchorPane apDetail;
    @FXML
    private Label lblLiveWorkTime;

    private Stage primaryStage;

    @FXML
    public void onEditProfile(ActionEvent event) throws IOException {
        //Open the Profil in Detail
    }

    @FXML
    public void onManualTimeTracking(ActionEvent event) throws IOException {
        //Open the ManualTimeTracking in Main
    }

    @FXML
    public void onEvaluation(ActionEvent event) throws IOException {
        //Open the Evaluation in Main
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

        dialogProfile.showAndWait();

        timeline.play();
    }

    public void start() {
        timeline.play();
    }

    @FXML
    public void initialize() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            change(lblLiveWorkTime);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
    }

    private Timeline timeline;
    private int secs = 0;

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
}
