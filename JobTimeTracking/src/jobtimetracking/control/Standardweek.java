/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.control;

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
    private Label lblDifferezAusgabe;
    @FXML
    private Label lblMehrstundenAusgabe;
    @FXML
    private Label lblResturlaubAusgabe;
    @FXML
    private Label lblJahrResturlaub;
    @FXML
    private Label lblBreakAW;

    public Label getLblSollAusgabe() {
        return lblSollAusgabe;
    }

    public Label getLblHabenAusgabe() {
        return lblHabenAusgabe;
    }

    public Label getLblDifferezAusgabe() {
        return lblDifferezAusgabe;
    }

    public Label getLblMehrstundenAusgabe() {
        return lblMehrstundenAusgabe;
    }

    public Label getLblResturlaubAusgabe() {
        return lblResturlaubAusgabe;
    }

    public Label getLblJahrResturlaub() {
        return lblJahrResturlaub;
    }

    public Label getLblBreakAW() {
        return lblBreakAW;
    }
}
