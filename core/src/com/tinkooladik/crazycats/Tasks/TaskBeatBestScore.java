package com.tinkooladik.crazycats.Tasks;

import com.tinkooladik.crazycats.Settings;

/**
 * Created by Oladik on 09.11.2016.
 */

public class TaskBeatBestScore extends Task {

	public TaskBeatBestScore(int target) {
		this.target = target;
		description = target == 1 ? "Beat your best score! " : "Beat your best score\n     for " + target + " times";
	}

	public void update() {
		Settings.taskProgress = Settings.newMaxScore ? Settings.taskProgress +1 : 0;
	}

	@Override
	public boolean isCompleted() {
		return (Settings.taskProgress >= target);
	}

}
