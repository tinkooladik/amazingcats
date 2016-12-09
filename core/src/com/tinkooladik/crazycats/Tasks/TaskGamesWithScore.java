package com.tinkooladik.crazycats.Tasks;

import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Settings;

/**
 * Created by Oladik on 09.11.2016.
 *
 */

public class TaskGamesWithScore extends Task {

	public TaskGamesWithScore(int target) {
		this.target = target;
		description = ("Play a total of 3 games with more then " + target + " score points");
	}

	public void update() {
		if (AcidCat.score > target) Settings.taskProgress++;
	}

	@Override
	public boolean isCompleted() {
		return Settings.taskProgress >= target;
	}

}