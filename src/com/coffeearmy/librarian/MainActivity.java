package com.coffeearmy.librarian;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;

import com.coffeearmy.librarian.events.OnSuccessAuthorization;
import com.coffeearmy.librarian.events.OttoBusHelper;
import com.coffeearmy.librarian.fragments.EbookListFragment;
import com.coffeearmy.librarian.fragments.LoginFragment;
import com.coffeearmy.librarian.fragments.PromptDropboxLoginFragment;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;

public class MainActivity extends ActionBarActivity {

	private FragmentManager mFragManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		onCreateFragments();
	}

	
	private void onCreateFragments() {
		mFragManager = getSupportFragmentManager();
		mFragManager
				.beginTransaction()
				.replace(R.id.fragment_container, new PromptDropboxLoginFragment(),
						PromptDropboxLoginFragment.FRAGMENT_TAG).commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
