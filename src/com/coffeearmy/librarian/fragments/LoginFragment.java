package com.coffeearmy.librarian.fragments;


import com.coffeearmy.librarian.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginFragment extends Fragment {

	public static final String FRAGMENT_TAG = "loging_fragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login_view, null);
		return view;
	}

}
