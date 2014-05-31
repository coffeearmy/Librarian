package com.coffeearmy.librarian.gesture;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureListener extends
GestureDetector.SimpleOnGestureListener {
	
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		return true;
	}

}
