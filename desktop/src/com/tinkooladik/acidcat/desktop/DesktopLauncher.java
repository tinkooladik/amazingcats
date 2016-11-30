package com.tinkooladik.acidcat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tinkooladik.crazycats.AcidCat;

import gpservices.DesktopGoogleServices;

public class DesktopLauncher {
	private static DesktopLauncher application;
	public static void main (String[] arg) {
		if (application == null) {
	            application = new DesktopLauncher();
	    }
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new AcidCat(new DesktopGoogleServices()), config);
	}

}
