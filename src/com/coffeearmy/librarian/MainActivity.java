package com.coffeearmy.librarian;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;

import com.coffeearmy.librarian.fragments.EbookListFragment;
import com.coffeearmy.librarian.fragments.LoginFragment;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;

public class MainActivity extends ActionBarActivity {

	private FragmentManager mFragManager;
	private DropboxAPI<AndroidAuthSession> mDBApi;
	final static private String APP_KEY = "l8bdlkx9jy53ow0";
	final static private String APP_SECRET = "h3o4hidwfj0e36a";
	final static private AccessType ACCESS_TYPE = AccessType.DROPBOX;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		onCreateFragments();
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);
		// MyActivity below should be your activity class name
		mDBApi.getSession().startOAuth2Authentication(MainActivity.this);
	}

	
	private void onCreateFragments() {
		mFragManager = getSupportFragmentManager();
		mFragManager
				.beginTransaction()
				.replace(R.id.fragment_container, new EbookListFragment(),
						LoginFragment.FRAGMENT_TAG).commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void onResume() {
	    super.onResume();

	    if (mDBApi.getSession().authenticationSuccessful()) {
	        try {
	            // Required to complete auth, sets the access token on the session
	            mDBApi.getSession().finishAuthentication();

	            String accessToken = mDBApi.getSession().getOAuth2AccessToken();
	        } catch (IllegalStateException e) {
	            Log.i("DbAuthLog", "Error authenticating", e);
	        }
	    }
	}
	
	


}
