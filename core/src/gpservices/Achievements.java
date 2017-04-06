package gpservices;

import com.tinkooladik.crazycats.AcidCat;

/**
 * Created by User on 02.02.2017.
 */

public class Achievements {

  public static void updateAchievements(int fishes, int score) {
    updateFishesAchievements(fishes);
    updateScoreAchievements(score);
    updateGamesAchievents();
  }

  public static void updateFishesAchievements(int points) {
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQBw", points); // 100
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQDQ", points); // 1000
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQDg", points); // 2500
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQDw", points); // 5000
  }

  public static void updateScoreAchievements(int points) {
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQFg", points); // 1000
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQFw", points); // 5000
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQGA", points); // 10000
  }

  public static void updateTasksAchievements() {
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQGQ", 1); // 5
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQGg", 1); // 10
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQGw", 1); // 15
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQHA", 1); // 20
  }

  public static void updateGamesAchievents() {
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQEA", 1); // 5
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQEQ", 1); // 10
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQEg", 1); // 100
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQEw", 1); // 250
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQFA", 1); // 500
    AcidCat.googleServices.updateAchievements("CgkI_-Dr0fwXEAIQFQ", 1); // 1000
  }
}
