package gpservices;

public interface IGoogleServices {

	public void signIn();
	public void signOut();
	public void rateGame();
	public void submitScore(long score);
	public void showScores();
	public boolean isSignedIn();
	public void gameHelperOnStart();
	
	// IActionResolver Interstitial
	public void showInterstitial(Runnable then);

	// Banner

	public void showBannerAd();
	public void hideBannerAd();

	// Rewarded
	public void showRewarded();

	// Network connection
	public boolean isNetworkAvailable();

	// For toast
	public void showToast(String text);

}
