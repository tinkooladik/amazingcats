package com.tinkooladik.acidcat.android;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.tinkooladik.crazycats.AcidCat;

import gpservices.IGoogleServices;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices {

	private static final String INTERSTITIAL_UNIT_ID = "ca-app-pub-6529396180091306/5655275279"; //**//
	private InterstitialAd interstitialAd; //**//
	private GameHelper _gameHelper;

	private RewardedVideoAd mAd;
	private static final String REWARDED_UNIT_ID = "ca-app-pub-6529396180091306/6793284470";

	private AdView bannerAd;
	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-6529396180091306/6714389279";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MultiDex.install(this);

		// Create the GameHelper.
		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		_gameHelper.enableDebugLog(false);

		GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
			@Override
			public void onSignInSucceeded() {
			}

			@Override
			public void onSignInFailed() {
			}
		};

		_gameHelper.setup(gameHelperListener);

		// INTERSTITIAL ADS INTEGRATION
	    interstitialAd = new InterstitialAd(this);
	    interstitialAd.setAdUnitId(INTERSTITIAL_UNIT_ID);
	    interstitialAd.setAdListener(new AdListener() {
	      @Override
	      public void onAdLoaded() {
	     //   Toast.makeText(getApplicationContext(), "Finished Loading Interstitial", Toast.LENGTH_SHORT).show();
	      }
	      @Override
	      public void onAdClosed() {
			  requestNewInterstitial();
	      }
	    });

		requestNewInterstitial();
		setupAds();

		// The rest of your onCreate() code here...
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new AcidCat(this), config);

		View gameView = initializeForView(new AcidCat(this), config);


		// BANNER
		MobileAds.initialize(getApplicationContext(), BANNER_AD_UNIT_ID);

		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layout.addView(bannerAd, params);

		setContentView(layout);

		// REWARDED
		mAd = MobileAds.getRewardedVideoAdInstance(this);
		//mAd.setRewardedVideoAdListener(this);
		loadRewardedVideoAd();
	}

	@Override
	protected void onStart() {
		super.onStart();
		//_gameHelper.onStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		_gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		_gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void signIn() {
		// TODO Auto-generated method stub
		try {
			runOnUiThread(new Runnable() {
				//@Override
				public void run() {
					_gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e) {
			//Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		// TODO Auto-generated method stub
		try {
			runOnUiThread(new Runnable() {
				//@Override
				public void run() {
					_gameHelper.signOut();
				}
			});
		}
		catch (Exception e) {
			//Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {
		// TODO Auto-generated method stub
		// Replace the end of the URL with the package of your game
		String str ="https://play.google.com/store/apps/details?id=com.tinkooladik.acidcat.android";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));

	}

	@Override
	public void submitScore(long score) {
		// TODO Auto-generated method stub
		if (isSignedIn() == true) {
			Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
			// Show the leaderboard
			//startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), 0);
		}
		else
		{
		// Maybe sign in here then redirect to submitting score?
		}
	}

	@Override
	public void showScores() {
		// TODO Auto-generated method stub
		if (isSignedIn() == true)
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), 1);
		else
		{
		// Maybe sign in here then redirect to showing scores?
			signIn();
		}
	}

	@Override
	public boolean isSignedIn() {
		// TODO Auto-generated method stub
		return _gameHelper.isSignedIn();
	}

	// INTERTITIAL ADS STUFF

	@Override
	public void showInterstitial(final Runnable then) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (interstitialAd.isLoaded()) {
					interstitialAd.show();
				} else {
				//	Toast.makeText(getApplicationContext(), "Interstitial is not loaded", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void requestNewInterstitial() {
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("A086C24BCC40B2F05F919279DED26002")
				.addTestDevice("68F91B8F0F6A121B1D0C30B6B44FC0F1")
				.build();

		interstitialAd.loadAd(adRequest);
	}


	public void setupAds() {
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.INVISIBLE);
		bannerAd.setBackgroundColor(0xff000000); // black
		bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
		bannerAd.setAdSize(AdSize.SMART_BANNER);
	}

	@Override
	public void showBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.VISIBLE);
				AdRequest adRequest = new AdRequest.Builder()
						.addTestDevice("A086C24BCC40B2F05F919279DED26002")
						.addTestDevice("68F91B8F0F6A121B1D0C30B6B44FC0F1")
						.build();
				bannerAd.loadAd(adRequest);
			}
		});
	}

	@Override
	public void hideBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.INVISIBLE);
			}
		});
	}

	private void loadRewardedVideoAd() {
	mAd.loadAd(REWARDED_UNIT_ID, new AdRequest.Builder()
			.addTestDevice("A086C24BCC40B2F05F919279DED26002")
			.addTestDevice("68F91B8F0F6A121B1D0C30B6B44FC0F1")
			.build());
	}

	@Override
	public void showRewarded() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (mAd.isLoaded()) {
					mAd.show();
				} else {
				//	Toast.makeText(getApplicationContext(), "Rewarded is not loaded", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean isNetworkAvailable() {
		final ConnectivityManager conMan = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
		return conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected();
	}

	@Override
	public void showToast(String text) {
		final String txt = text;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void gameHelperOnStart() {
		_gameHelper.onStart(this);
	}

}


