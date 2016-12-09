package com.tinkooladik.crazycats.Tasks;

import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Settings;

/**
 * Created by Oladik on 09.11.2016.
 *
 */

class TaskScoreTotal extends Task {

	TaskScoreTotal(int target) {
		this.target = target;
		description = ("Score a total of " + target + " points");
	}

	public void update() {
		Settings.taskProgress += AcidCat.score;
	}

	@Override
	public boolean isCompleted() {
		return Settings.taskProgress >= target;
	}

}
