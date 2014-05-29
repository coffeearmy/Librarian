package com.coffeearmy.librarian;

import com.coffeearmy.librarian.fragments.LoginFragment;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

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
				.replace(R.id.fragment_container, new LoginFragment(),
						LoginFragment.FRAGMENT_TAG).commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
