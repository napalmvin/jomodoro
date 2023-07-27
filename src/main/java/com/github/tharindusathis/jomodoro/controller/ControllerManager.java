package com.github.tharindusathis.jomodoro.controller;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import javafx.stage.Stage;

public class ControllerManager {

  private Map<ViewConfig, AbstractController> controllersMap;

  private <T extends AbstractController> T findController(Class<T> controllerType) {
    if (controllersMap == null) {
      return null;
    }

    for (Map.Entry<ViewConfig, AbstractController> entry : controllersMap.entrySet()) {
      if (controllerType.isInstance(entry.getValue())) {
        return (T) entry.getValue();
      }
    }
    return null;
  }

  public <T extends AbstractController> Optional<T> getController(Class<T> controllerType) {
    return Optional.ofNullable(findController(controllerType));
  }

  public void registerController(ViewConfig view, AbstractController controller, Stage stage) {
    if (controllersMap == null) {
      controllersMap = new EnumMap<>(ViewConfig.class);
    }
    if (controllersMap.containsKey(view)) {
      return;
    }
    controller.setStage(stage);
    controller.setControllerManager(this);
    controllersMap.put(view, controller);
  }

  public void showView(ViewConfig view) {
    controllersMap.forEach((v, controller) ->
    {
      if (v == view) {
        controller.getStage().show();
      } else {
        controller.getStage().hide();
      }
    });
  }

}
