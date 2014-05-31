package com.coffeearmy.librarian.fragments;

import com.coffeearmy.librarian.MainActivity;
import com.coffeearmy.librarian.R;
import com.coffeearmy.librarian.events.OnSuccessAuthorization;
import com.coffeearmy.librarian.events.OttoBusHelper;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PromptDropboxLoginFragment extends Fragment{
	private static DropboxAPI<AndroidAuthSession> mDBApi;
	final static private String APP_KEY = "l8bdlkx9jy53ow0";
	final static private String APP_SECRET = "h3o4hidwfj0e36a";
	final static private AccessType ACCESS_TYPE = AccessType.DROPBOX;
	public static final String FRAGMENT_TAG = "prompt_dropbox_login";
	
	private Button mBtnPromptLogin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View promptView= inflater.inflate(R.layout.prompt_dropbox_login, null);
		mBtnPromptLogin= (Button) promptView.findViewById(R.id.btnLoginDropbox);
		mBtnPromptLogin.setOnClickListener(new OnClickPromptLogin());
		return promptView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);
//		// MyActivity below should be your activity class name
//		mDBApi.getSession().startOAuth2Authentication(getActivity());
	}

	@Override
	public void onResume() {
	    super.onResume();
	    loginLogic();    
	}
	
	public static  DropboxAPI<AndroidAuthSession> getAPIDropbox(){
		return mDBApi;
	}
	
	protected class OnClickPromptLogin implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			mDBApi.getSession().startOAuth2Authentication(getActivity());				
		}
		
	}
	
	
	private void loginLogic(){
		if (mDBApi.getSession().authenticationSuccessful()) {
	        try {
	            // Required to complete auth, sets the access token on the session
	            mDBApi.getSession().finishAuthentication();

	            String accessToken = mDBApi.getSession().getOAuth2AccessToken();
	            successLogin();
	        } catch (IllegalStateException e) {
	            Log.i("DbAuthLog", "Error authenticating", e);
	        }
	    }
	}

	private void successLogin() {
		navigateToList();
		//OttoBusHelper.getCurrentBus().post(new OnSuccessAuthorization(OnSuccessAuthorization.Type.SUCCESS, mDBApi));
	}

	private void navigateToList() {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		fm.beginTransaction()
				.replace(R.id.fragment_container, new EbookGridFragment(),
						EbookGridFragment.FRAGMENT_TAG).commit();
	}
}
