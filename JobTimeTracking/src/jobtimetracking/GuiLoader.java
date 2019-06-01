/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 *
 * @author Anika.Schmidt
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