package com.coffeearmy.librarian.rest;

import java.util.List;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class EpubLoader extends AsyncTaskLoader<List<Entry>> {

	private DropboxAPI<AndroidAuthSession> mDropboxAPI;

	public EpubLoader(Context context, DropboxAPI<AndroidAuthSession> dropboxAPI) {
		super(context);
		mDropboxAPI=dropboxAPI;

	}

	@Override
	public List<Entry> loadInBackground() {
		if(mDropboxAPI!=null){
			//Call REST API
		}
		return null;
	}

}
