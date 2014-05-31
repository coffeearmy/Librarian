package com.coffeearmy.librarian.rest;

import java.util.ArrayList;
import java.util.List;

import com.coffeearmy.librarian.data.EPubData;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class EpubLoader extends AsyncTaskLoader<List<EPubData>> {

	private DropboxAPI<AndroidAuthSession> mDropboxAPI;

	public EpubLoader(Context context, DropboxAPI<AndroidAuthSession> dropboxAPI) {
		super(context);
		mDropboxAPI = dropboxAPI;

	}

	@Override
	public List<EPubData> loadInBackground() {

		ArrayList<EPubData> mEpubsList = new ArrayList<EPubData>();
		if (mDropboxAPI != null) {
			try {
				// Aproach using metadata and navigate through folders
				// Entry epubsInDropbox=mDropboxAPI.metadata("/", 0, null, true,
				// null);
				// recursiveSearch(epubsInDropbox);

				// Aproach using search
				List<Entry> ePubInDropbox = mDropboxAPI.search("/",
						".epub", 0, true);
				for (Entry entry : ePubInDropbox) {
					if(!entry.mimeType.contains("ePub"));
						mEpubsList.add(new EPubData(entry));
				}
			} catch (DropboxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return mEpubsList;
	}

	/**
	 * Method for navigate inside folders and retrieve files Unused
	 * @param mEpubsList 
	 * */
	private void recursiveSearch(Entry entryParent, List<Entry> mEpubsList) {

		if (entryParent != null) {
			for (Entry entry : entryParent.contents) {
				if (!entry.isDir) {
					mEpubsList.add(entry);
				} else {
					if (entry.contents != null && entry.contents.size() > 0)
						recursiveSearch(entry, mEpubsList);
				}
			}
		}
	}

}
