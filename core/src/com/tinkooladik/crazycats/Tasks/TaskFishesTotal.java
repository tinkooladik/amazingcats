package com.tinkooladik.crazycats.Tasks;

import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Settings;

/**
 * Created by Oladik on 09.11.2016.
 */

public class TaskFishesTotal extends Task {

	public TaskFishesTotal(int target) {
		this.target = target;
		description = ("Collect a total of \n     " + target + " fishes");
	}

	public void update() {
		int result = Settings.taskProgress + AcidCat.fishScore;
		Settings.taskProgress = result > target ? target : result;
	}

	@Override
	public boolean isCompleted() {
		return Settings.taskProgress >= target;
	}

}
