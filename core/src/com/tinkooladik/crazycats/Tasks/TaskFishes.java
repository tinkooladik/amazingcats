package com.tinkooladik.crazycats.Tasks;

import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Settings;

/**
 * Created by Oladik on 09.11.2016.
 *
 */

class TaskFishes extends Task {

	TaskFishes(int target) {
		this.target = target;
		description = ("Collect " + target + " fishes\n     in a single game");
	}

	public void update() {
		Settings.taskProgress = AcidCat.fishScore > target ? target : AcidCat.fishScore;
	}

	@Override
	public boolean isCompleted() {
		return (Settings.taskProgress >= target);
	}

}
