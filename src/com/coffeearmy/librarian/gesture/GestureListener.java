package com.coffeearmy.librarian.gesture;

import android.view.GestureDetector;
import android.view.MotionEvent;

/** Simple Gesture Listener for cheking when the user double taps*/
public class GestureListener extends
GestureDetector.SimpleOnGestureListener {
	
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		return true;
	}

}
