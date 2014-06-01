package com.coffeearmy.librarian.fragments;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.coffeearmy.librarian.R;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

/** class for login in Dropbox and get permission from the user
 *   This class is based in the Dropbox SDK official tutorial */
public class PromptDropboxLoginFragment extends Fragment {

	private static DropboxAPI<AndroidAuthSession> mDBApi;
	private static Fragment mPrompFrag;
	final static private String APP_KEY = "l8bdlkx9jy53ow0";
	final static private String APP_SECRET = "h3o4hidwfj0e36a";
	public static final String FRAGMENT_TAG = "prompt_dropbox_login";
	public static final String ACCOUNT_PREFS_NAME = "account_pref_name";
	final static private String ACCESS_KEY_NAME = "ACCESS_KEY";
	final static private String ACCESS_SECRET_NAME = "ACCESS_SECRET";

	private Button mBtnPromptLogin;

	public static Fragment getInstance() {
		if (mPrompFrag == null) {
			mPrompFrag = new PromptDropboxLoginFragment();
		}
		return mPrompFrag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View promptView = inflater.inflate(R.layout.prompt_dropbox_login, null);
		mBtnPromptLogin = (Button) promptView
				.findViewById(R.id.btnLoginDropbox);
		mBtnPromptLogin.setOnClickListener(new OnClickPromptLogin());
		return promptView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Prepare the session 
		AndroidAuthSession session = buildSession();
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);
	}

	@Override
	public void onResume() {
		super.onResume();
		loginLogic();
	}

	private void loginLogic() {
		AndroidAuthSession session = mDBApi.getSession();
		if (session.authenticationSuccessful()) {
			try {
				// Required to complete auth, sets the access token on the
				// session
				session.finishAuthentication();

				// Store it locally in our app for later use
				storeAuth(session);

			} catch (IllegalStateException e) {
				Log.i("DbAuthLog", "Error authenticating", e);
			}
		}
		successLogin();
	}

	private void storeAuth(AndroidAuthSession session) {
		// Store the OAuth 2 access token, if there is one.
		String oauth2AccessToken = session.getOAuth2AccessToken();
		if (oauth2AccessToken != null) {
			SharedPreferences prefs = getActivity().getSharedPreferences(
					ACCOUNT_PREFS_NAME, 0);
			Editor edit = prefs.edit();
			edit.putString(ACCESS_KEY_NAME, "oauth2:");
			edit.putString(ACCESS_SECRET_NAME, oauth2AccessToken);
			edit.commit();
		}
	}

	private AndroidAuthSession buildSession() {
		AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);

		AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
		loadAuth(session);
		return session;
	}

	/**
	 * Shows keeping the access keys returned from Trusted Authenticator in a
	 * local store, rather than storing user name & password, and
	 * re-authenticating each time (which is not to be done, ever).
	 */
	private void loadAuth(AndroidAuthSession session) {
		SharedPreferences prefs = getActivity().getSharedPreferences(
				ACCOUNT_PREFS_NAME, 0);
		String key = prefs.getString(ACCESS_KEY_NAME, null);
		String secret = prefs.getString(ACCESS_SECRET_NAME, null);
		if (key == null || secret == null || key.length() == 0
				|| secret.length() == 0)
			return;
		session.setOAuth2AccessToken(secret);

	}

	private void successLogin() {
		//Only if the App is linked to Dropbox
		if (mDBApi.getSession().isLinked())
			navigateToList();
	}

	private void navigateToList() {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		fm.beginTransaction()
				.replace(R.id.fragment_container,
						EbookGridFragment.getInstance(),
						EbookGridFragment.FRAGMENT_TAG).commit();
	}
	
	protected class OnClickPromptLogin implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			mDBApi.getSession().startOAuth2Authentication(getActivity());
		}

	}
	
	public static DropboxAPI<AndroidAuthSession> getAPIDropbox() {
		return mDBApi;
	}

	public static void logOutDropbox() {
		// Remove credentials from the session
		mDBApi.getSession().unlink();
	}

}
