package com.coffeearmy.librarian.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

public class ImageDialog extends DialogFragment {
	
	public static final String FRAGMENT_TAG = "image_dialog";
	private static ImageDialog mDialog;
	static Bitmap mImageToShow;

	public static ImageDialog getInstance(Bitmap b){	
		mImageToShow=b;
		if(mDialog==null){
			mDialog= new ImageDialog();
		}
		return mDialog;		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ImageView imgView= new ImageView(getActivity());
		imgView.setImageBitmap(mImageToShow);
		imgView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();				
			}
		});
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		return imgView;
	}

}
