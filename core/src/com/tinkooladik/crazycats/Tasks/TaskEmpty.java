package com.tinkooladik.crazycats.Tasks;

/**
 * Created by Oladik on 13.12.2016.
 */

public class TaskEmpty extends Task {

	TaskEmpty() {
		description = ("You've completed all the tasks");
	}

	public void update() {

	}

	@Override
	public boolean isCompleted() {
		return false;
	}

}
