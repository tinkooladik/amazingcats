package com.tinkooladik.crazycats.Tasks;

/**
 * Created by Oladik on 09.11.2016.
 */

public abstract class Task {
  public int target;
  protected String description = "unknown task";

  public String getDescription() {
    return description;
  }

  public abstract boolean isCompleted();

  public abstract void update();
}
