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

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author Anika.Schmidt
 */
public class Evaluation {

    @FXML
    private Label lblSollMonat;
    @FXML
    private Label lblHabenMonat;
    @FXML
    private Label lblDifferenzMonat;
    @FXML
    private Label lblMehrstundenMonat;
    @FXML
    private Label lblJarResturlaub;
    @FXML
    private Label lblJahrUrlaub;
    @FXML
    private Label lblSollJahr;
    @FXML
    private Label lblHabenJahr;
    @FXML
    private Label lblDifferenzJahr;
    @FXML
    private Label lblMehrstundenJahr;
    @FXML
    private Label lblResturlaubAusgabe;
    @FXML
    private Label lblUrlaubAusgabe;
    @FXML
    private Label lblBreaksMonthE;
    @FXML
    private Label lblBreaksYearE;

    public Label getLblSollMonat() {
        return lblSollMonat;
    }

    public Label getLblHabenMonat() {
        return lblHabenMonat;
    }

    public Label getLblDifferenzMonat() {
        return lblDifferenzMonat;
    }

    public Label getLblMehrstundenMonat() {
        return lblMehrstundenMonat;
    }

    public Label getLblJarResturlaub() {
        return lblJarResturlaub;
    }

    public Label getLblJahrUrlaub() {
        return lblJahrUrlaub;
    }

    public Label getLblSollJahr() {
        return lblSollJahr;
    }

    public Label getLblHabenJahr() {
        return lblHabenJahr;
    }

    public Label getLblDifferenzJahr() {
        return lblDifferenzJahr;
    }

    public Label getLblMehrstundenJahr() {
        return lblMehrstundenJahr;
    }

    public Label getLblResturlaubAusgabe() {
        return lblResturlaubAusgabe;
    }

    public Label getLblUrlaubAusgabe() {
        return lblUrlaubAusgabe;
    }

    public Label getLblBreaksMonthE() {
        return lblBreaksMonthE;
    }

    public Label getLblBreaksYearE() {
        return lblBreaksYearE;
    }

}
