package com.coffeearmy.librarian.events;

import com.squareup.otto.Bus;

public class OttoBusHelper {

	private static Bus _currentBus;

	public OttoBusHelper() {
	}

	public static Bus getCurrentBus() {
		if (_currentBus == null) {
			_currentBus = new Bus();
		}
		return _currentBus;
	}
}
