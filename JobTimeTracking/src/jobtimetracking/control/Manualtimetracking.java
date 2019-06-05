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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author Anika.Schmidt
 */
public class Manualtimetracking {

    @FXML
    private ComboBox cbbDropDown;
    @FXML
    private DatePicker dpDatePickerBegin;
    @FXML
    private DatePicker dpDatePickerEnde;
    @FXML
    private TextField txtTimePickerBegin;
    @FXML
    private TextField txtTimePickerEnde;
    
    @FXML
    public void onSave(ActionEvent event) throws IOException {
        //Save Times
    }

    @FXML
    public void onCancel(ActionEvent event) throws IOException {
        //Cancel Times
    }
}
