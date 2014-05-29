package com.coffeearmy.librarian.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coffeearmy.librarian.R;

/**
 * Fragment for login in the user Dropbox account.
 */
public class LoginFragment extends Fragment {

	public static final String FRAGMENT_TAG = "loging_fragment";
	private EditText mEdtUsername;
	private EditText mEdtPass;
	private Button btnLogin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login_view, null);
		mEdtUsername = (EditText) view.findViewById(R.id.edtUserName);
		mEdtPass = (EditText) view.findViewById(R.id.edtPassword);
		btnLogin = (Button) view.findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new onLoginClick());
		return view;
	}

	/** Behaviour for the login button. */
	protected class onLoginClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (checkFields(mEdtPass, mEdtUsername)) {
				beginLogin(mEdtUsername.getText().toString(), mEdtPass
						.getText().toString());
			} else {
				Toast.makeText(getActivity(), "Fill all fields",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	/**
	 * Method for check if the login fields are valid For now only is checked if
	 * the field is empty.
	 */
	private boolean checkFields(EditText... editTexts) {
		boolean allOK = true;
		for (EditText editText : editTexts) {
			if (TextUtils.isEmpty(editText.getText())) {
				editText.setBackgroundColor(0x22FF0000);
				allOK = false;
			} else {
				editText.setBackgroundColor(Color.WHITE);
			}
		}
		return allOK;
	}

	/** Method for begin the login if everything is OK */
	private void beginLogin(String string, String string2) {
		// For now
		Boolean loginOK = true;
		if (loginOK) {
			navigateToList();
		} else {
			Toast.makeText(getActivity(), "Invalid user/password",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void navigateToList() {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		Fragment frag = new EbookListFragment();
		fm.beginTransaction()
				.replace(R.id.fragment_container, frag,
						EbookListFragment.FRAGMENT_TAG).commit();

	}

}
