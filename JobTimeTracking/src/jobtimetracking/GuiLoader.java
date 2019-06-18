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
package jobtimetracking;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * this helper class load the FXML and come from another project
 * https://github.com/ColdanR/ausbildungsnachweis
 *
 * @author Bernd.Schmidt
 * @author Anika.Schmidt
 *
 * @param <C> controller class
 * @param <P> root class
 */
public class GuiLoader<C, P extends Node> {

    private final C controller;
    private final P root;

    /**
     * Loads the controller and Node for the content or dialog.
     *
     * @param template Name of FXML file
     * @throws IOException loading error
     */
    public GuiLoader(String template) throws IOException {
        template = "/jobtimetracking/view/" + template;
        final FXMLLoader loader = new FXMLLoader(getClass().getResource(template));
        root = loader.load();
        controller = loader.getController();
    }

    public C getController() {
        return controller;
    }

    public P getRoot() {
        return root;
    }
}
