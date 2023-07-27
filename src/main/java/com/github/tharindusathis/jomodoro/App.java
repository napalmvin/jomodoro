package com.github.tharindusathis.jomodoro;

import com.github.tharindusathis.jomodoro.controller.ControllerManager;
import com.github.tharindusathis.jomodoro.controller.ViewConfig;
import com.github.tharindusathis.jomodoro.timer.Configs;
import com.github.tharindusathis.jomodoro.util.Constants;
import com.github.tharindusathis.jomodoro.util.CustomFonts;
import com.github.tharindusathis.jomodoro.util.Loggers;
import com.github.tharindusathis.jomodoro.util.Resources;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author tharindusathis
 */
public class App extends Application {

  public static void main(String[] args) {
    launch();
  }

  private void createFullscreenView(ControllerManager manager) throws IOException {
    URL stageDescriptionUrl = getClass()
        .getResource("/com/github/tharindusathis/jomodoro/view/fullscreen-stage.fxml");
    FXMLLoader loader = new FXMLLoader(stageDescriptionUrl);

    Region contentRootRegion = loader.load();

    contentRootRegion.setPrefWidth(Constants.FULL_SCREEN_WIDT);
    contentRootRegion.setPrefHeight(Constants.FULL_SCREEN_HEIGHT);

    Group group = new Group(contentRootRegion);
    StackPane rootPane = new StackPane();

    rootPane.getChildren().add(group);
    rootPane.setStyle("-fx-background-color: rgba(0,0,0,0.8)");

    Scene scene = new Scene(rootPane, Constants.FULL_SCREEN_WIDT, Constants.FULL_SCREEN_HEIGHT);

    DoubleBinding width = rootPane.maxWidthProperty().divide(Constants.FULL_SCREEN_WIDT);
    group.scaleXProperty().bind(width);
    group.scaleYProperty().bind(width);
    rootPane.setMaxWidth(Constants.FULL_SCREEN_WIDT);

    Stage stage = createDefaultStage(scene);

    rootPane.setMaxWidth(Constants.FULL_SCREEN_WIDT * Configs.INIT_UI_SCALE_FACTOR * 2);
    manager.registerController(ViewConfig.FULLSCREEN, loader.getController(), stage);
  }

  private Stage createDefaultStage(Scene scene) {
    Stage stage = new Stage();
    stage.initOwner(getUtilityStage());
    stage.initStyle(StageStyle.TRANSPARENT);
    scene.setFill(Color.TRANSPARENT);
    stage.setScene(scene);
    stage.setAlwaysOnTop(true);
    return stage;
  }

  private Stage createMainView(ControllerManager manager) throws IOException {
    URL mainStageUrl = getClass().getResource(
        "/com/github/tharindusathis/jomodoro/view/main-stage.fxml");
    FXMLLoader loader = new FXMLLoader(mainStageUrl);
    Region contentRootRegion = loader.load();

    double origW = 720.0;
    double origH = 360.0;

    contentRootRegion.setPrefWidth(origW);
    contentRootRegion.setPrefHeight(origH);

    Group group = new Group(contentRootRegion); // non-resizable container (Group)
    StackPane rootPane = new StackPane();
    rootPane.getChildren().add(group);
    rootPane.setStyle("-fx-background-color: #00000000");
    Scene scene = new Scene(rootPane, origW, origH);

    // bind the scene's width and height to the scaling parameters on the group
    DoubleBinding width = scene.widthProperty().divide(origW);
    group.scaleXProperty().bind(width);
    group.scaleYProperty().bind(width);

    Stage stage = createDefaultStage(scene);

    // initial scaling
    stage.setWidth(origW * Configs.INIT_UI_SCALE_FACTOR);

    // restore when manual minimize
    stage.iconifiedProperty().addListener(
        (ov, t, t1) -> stage.setIconified(false));

    manager.registerController(ViewConfig.MAIN, loader.getController(), stage);
    return stage;
  }

  private void createNotifyFlashScreenView(ControllerManager manager) throws IOException {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource(
            "/com/github/tharindusathis/jomodoro/view/notify-flash-screen-stage.fxml"));
    Region contentRootRegion = loader.load();

    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    contentRootRegion.setPrefWidth(screenBounds.getWidth());
    contentRootRegion.setPrefHeight(screenBounds.getHeight());
    contentRootRegion.setMouseTransparent(true);
    contentRootRegion.setPickOnBounds(false);

    Stage stage = new Stage();
    stage.initOwner(getUtilityStage());
    stage.initStyle(StageStyle.TRANSPARENT);

    Scene scene = new Scene(contentRootRegion, screenBounds.getWidth(), screenBounds.getHeight());
    contentRootRegion.setStyle("-fx-background-color: rgba(0,0,0,0.0)");
    scene.setFill(Color.TRANSPARENT);
    stage.setScene(scene);
    stage.setAlwaysOnTop(true);
    manager.registerController(ViewConfig.NOTIFY_FLASH, loader.getController(), stage);
  }

  /**
   * Get a utility styled stage.
   *
   * @return Utility styled stage
   */
  private Stage getUtilityStage() {
    Stage utilityStage = new Stage();
    utilityStage.initStyle(StageStyle.UTILITY);
    utilityStage.setOpacity(0);
    utilityStage.setHeight(0);
    utilityStage.setWidth(0);
    utilityStage.show();
    return utilityStage;
  }

  @Override
  public void start(Stage stage) {
    initFonts();
    final ControllerManager manager = new ControllerManager();
    try {
      createFullscreenView(manager);
      createNotifyFlashScreenView(manager);
      createMainView(manager).show();
    } catch (IOException e) {
      Loggers.COMMON_LOGGER.log(Level.SEVERE, e::getMessage);
    }

  }

  private void initFonts() {
    try {
      String fontPath = getClass()
          .getResource("/com/github/tharindusathis/jomodoro/fonts/Roboto-Medium.ttf")
          .toExternalForm();
      Resources.addFont(CustomFonts.ROBOTO_250, Font.loadFont(fontPath, 250));
      Resources.addFont(CustomFonts.ROBOTO_87, Font.loadFont(fontPath, 87));
    } catch (Exception e) {
      Loggers.COMMON_LOGGER.log(Level.SEVERE, e::getMessage);
    }
  }


}