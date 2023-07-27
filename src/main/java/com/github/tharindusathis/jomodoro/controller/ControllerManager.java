package com.github.tharindusathis.jomodoro.controller;

import javafx.stage.Stage;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class ControllerManager {
    private Map<ViewConfig, Controller> controllersMap;

    private <T extends Controller> T findController(Class<T> controllerType) {
        if (controllersMap == null) return null;

        for (Map.Entry<ViewConfig, Controller> entry : controllersMap.entrySet()) {
            if (controllerType.isInstance(entry.getValue())) {
                return (T) entry.getValue();
            }
        }
        return null;
    }

    public <T extends Controller> Optional<T> getController(Class<T> controllerType) {
        return Optional.ofNullable(findController(controllerType));
    }

    public void registerController(ViewConfig view, Controller controller, Stage stage) {
        if (controllersMap == null) {
            controllersMap = new EnumMap<>(ViewConfig.class);
        }
        if (controllersMap.containsKey(view)) return;
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
