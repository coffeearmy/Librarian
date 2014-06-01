package com.coffeearmy.librarian.loader;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.coffeearmy.librarian.data.EPubData;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;

/** AsyncTaskLoader for download Dropbox metadata items */
public class EPubLoader extends AsyncTaskLoader<List<EPubData>> {

	String mQuery = ".epub";
	private DropboxAPI<AndroidAuthSession> mDropboxAPI;

	public EPubLoader(Context context, DropboxAPI<AndroidAuthSession> dropboxAPI) {
		super(context);
		mDropboxAPI = dropboxAPI;
	}

	@Override
	public List<EPubData> loadInBackground() {

		ArrayList<EPubData> mEpubsList = new ArrayList<EPubData>();
		if (mDropboxAPI != null) {
			try {
				// Use Dropbox API search
				List<Entry> ePubInDropbox = mDropboxAPI.search("/", mQuery, 0,
						false);

				for (Entry entry : ePubInDropbox) {
					// Double check if the item is an ePub with the mimeType
					if (!entry.mimeType.contains("ePub"))
						mEpubsList.add(new EPubData(entry));
				}
			} catch (DropboxException e) {

				e.printStackTrace();
			}

		}
		return mEpubsList;
	}
}