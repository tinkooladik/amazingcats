package com.tinkooladik.crazycats.Tasks;

import com.tinkooladik.crazycats.Settings;

/**
 * Created by Oladik on 09.11.2016.
 *
 */

class TaskGames extends Task {

	TaskGames(int target) {
		this.target = target;
		description = ("Play " + target + " games");
	}

	public void update() {
		Settings.taskProgress++;
	}

	@Override
	public boolean isCompleted() {
		return (Settings.taskProgress >= target);
	}

}
