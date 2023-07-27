package com.github.tharindusathis.jomodoro.controller;

import java.util.Optional;
import javafx.stage.Stage;

public abstract class AbstractController {

  Stage stage;
  private ControllerManager controllerManager;

  public Optional<ControllerManager> getControllerManager() {
    return Optional.ofNullable(controllerManager);
  }

  public void setControllerManager(ControllerManager controllerManager) {
    this.controllerManager = controllerManager;
  }

  public Stage getStage() {
    return stage;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }
}
