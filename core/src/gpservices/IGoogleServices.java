package gpservices;

public interface IGoogleServices {

	void signIn();
	void signOut();
	void rateGame();
	void submitScore(long score);
	void showScores();
	void updateAchievements(String achievement, int points);
  void showAchievements();
	boolean isSignedIn();
	void gameHelperOnStart();
	
	// IActionResolver Interstitial
	void showInterstitial(Runnable then);

	// Banner

	void showBannerAd();
	void hideBannerAd();

	// Rewarded
	void showRewarded();
	boolean isRewarded();

	// Network connection
	boolean isNetworkAvailable();

	// For toast
	void showToast(String text);

}
