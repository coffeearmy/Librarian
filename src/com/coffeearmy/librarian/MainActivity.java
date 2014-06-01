package com.coffeearmy.librarian;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.coffeearmy.librarian.fragments.PromptDropboxLoginFragment;

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
				.replace(R.id.fragment_container, PromptDropboxLoginFragment.getInstance(),
						PromptDropboxLoginFragment.FRAGMENT_TAG).commit();

	}	

}
