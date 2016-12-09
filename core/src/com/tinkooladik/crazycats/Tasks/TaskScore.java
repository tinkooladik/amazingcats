package com.tinkooladik.crazycats.Tasks;

import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Settings;

/**
 * Created by Oladik on 09.11.2016.
 *
 */

class TaskScore extends Task {

	TaskScore(int target) {
		this.target = target;
		description = ("Score " + target + " points\n     in a single game");
	}

	public void update() {
		Settings.taskProgress = AcidCat.score >  target ? target : AcidCat.score;
	}

	@Override
	public boolean isCompleted() {
		return Settings.taskProgress >= target;
	}

}
