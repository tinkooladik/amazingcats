package com.tinkooladik.crazycats.Tasks;

import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Settings;

/**
 * Created by Oladik on 09.11.2016.
 */

class TaskGamesWithFishesInARow extends Task {

  TaskGamesWithFishesInARow(int target) {
    this.target = target;
    description = ("Play 3 games in a row\n     with more then " + target + " fishes");
  }

  public void update() {
    Settings.taskProgress = AcidCat.fishScore > target ? Settings.taskProgress + 1 : 0;
  }

  @Override public boolean isCompleted() {
    return (Settings.taskProgress >= 3);
  }
}
