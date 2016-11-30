package com.tinkooladik.crazycats.Tasks;

import com.tinkooladik.crazycats.AcidCat;
import com.tinkooladik.crazycats.Settings;

/**
 * Created by Oladik on 09.11.2016.
 */

public class TaskGamesWithFishes extends Task {

	public TaskGamesWithFishes(int target) {
		this.target = target;
		description = "Play 3 games with more\n     then " + target + "fishes";
	}

	public void update() {
		if (AcidCat.fishScore > target)
			Settings.taskProgress++;
	}

	@Override
	public boolean isCompleted() {
		return (Settings.taskProgress >= 3);
	}

}
