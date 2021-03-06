package com.coffeearmy.librarian.fragments;

import com.coffeearmy.librarian.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/** Custom dialog for showing the ePub dialog, if is not available yet shows a message*/
public class ImageDialog extends DialogFragment {

	public static final String FRAGMENT_TAG = "image_dialog";
	private static ImageDialog mDialog;
	static Bitmap mImageToShow;

	public static ImageDialog getInstance(Bitmap b) {
		mImageToShow = b;
		if (mDialog == null) {
			mDialog = new ImageDialog();
		}
		return mDialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View dialogView = null;
		
		if (mImageToShow != null) {
			ImageView imgView = new ImageView(getActivity());
			imgView.setImageBitmap(mImageToShow);
			imgView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
			
			dialogView = imgView;
		} else {
			TextView txtView = new TextView(getActivity());
			txtView.setText(R.string.downloading);
			dialogView = txtView;
		}
		//The dialog does not have title or buttons
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		return dialogView;
	}

}
