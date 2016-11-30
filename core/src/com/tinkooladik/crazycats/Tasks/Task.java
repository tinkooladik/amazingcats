package com.tinkooladik.crazycats.Tasks;

/**
 * Created by Oladik on 09.11.2016.
 */

public abstract class Task {
	protected String description = "unknown task";
	protected int target;

	public String getDescription() {
		return description;
	}

	public abstract boolean isCompleted();

	public abstract void update();

}
