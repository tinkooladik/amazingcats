package com.tinkooladik.crazycats.Tasks;

import com.tinkooladik.crazycats.Settings;

/**
 * Created by Oladik on 10.11.2016.
 */

public class TaskFactory {

  public Task getNewTask() {
    Task task = null;
    int taskNum = Settings.taskNum;
    switch (taskNum) {
      case 0:
        task = new TaskGames(3);
        break;
      case 1:
        task = new TaskFishesTotal(15);
        break;
      case 2:
        task = new TaskScore(100);
        break;
      case 3:
        task = new TaskFishes(5);
        break;
      case 4:
        task = new TaskBeatBestScore(1);
        break;
      case 5:
        task = new TaskGamesWithScoreInARow(75);
        break;
      case 6:
        task = new TaskGamesWithFishes(5);
        break;
      case 7:
        task = new TaskScoreTotal(200);
        break;
      case 8:
        task = new TaskGamesWithFishesInARow(2);
        break;
      case 9:
        task = new TaskGamesWithScore(100);
        break;
      case 10:
        task = new TaskFishes(10);
        break;
      case 11:
        task = new TaskBeatBestScore(1);
        break;
      case 12:
        task = new TaskGames(10);
        break;
      case 13:
        task = new TaskScoreTotal(350);
        break;
      case 14:
        task = new TaskGamesWithScoreInARow(150);
        break;
      case 15:
        task = new TaskFishesTotal(50);
        break;
      case 16:
        task = new TaskGamesWithFishesInARow(15);
        break;
      case 17:
        task = new TaskGamesWithScore(300);
        break;
      case 18:
        task = new TaskBeatBestScore(3);
        break;
      case 19:
        task = new TaskGames(20);
        break;
      default:
        task = new TaskEmpty();
        break;
    }
    return task;
  }
}
