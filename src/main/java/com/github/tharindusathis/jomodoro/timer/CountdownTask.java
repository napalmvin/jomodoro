package com.github.tharindusathis.jomodoro.timer;

import java.util.TimerTask;
import java.util.function.Consumer;
import javafx.application.Platform;

/**
 * @author tharindusathis
 */
public class CountdownTask extends TimerTask {

  Consumer<Integer> remainingSecondsSetter;
  int remainingSeconds;

  public CountdownTask() {
  }

  public CountdownTask(int remainingSeconds, Consumer<Integer> remainingSecondsSetter) {
    this.remainingSeconds = remainingSeconds;
    this.remainingSecondsSetter = remainingSecondsSetter;
  }

  @Override
  public void run() {
    Platform.runLater(() -> {
      remainingSecondsSetter.accept(remainingSeconds);
      if (remainingSeconds > 0) {
        remainingSeconds--;
      } else {
        this.cancel();
      }
    });
  }
}
