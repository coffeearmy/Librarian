package com.coffeearmy.librarian.rest;

import java.util.ArrayList;
import java.util.List;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class EpubLoader extends AsyncTaskLoader<List<Entry>> {

	private DropboxAPI<AndroidAuthSession> mDropboxAPI;
	private ArrayList<Entry> mEpubsList;

	public EpubLoader(Context context, DropboxAPI<AndroidAuthSession> dropboxAPI) {
		super(context);
		mDropboxAPI=dropboxAPI;

	}

	@Override
	public List<Entry> loadInBackground() {
		prepareDownload();
		 mEpubsList= new ArrayList<DropboxAPI.Entry>();
		if(mDropboxAPI!=null){
			try {
				//Aproach using metadata and navigate through folders
//				Entry epubsInDropbox=mDropboxAPI.metadata("/", 0, null, true, null);
//				recursiveSearch(epubsInDropbox);
				
				//Aproach using search 
				mEpubsList= (ArrayList<Entry>) mDropboxAPI.search("/",".epub", 0, true);
			} catch (DropboxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return mEpubsList;
	}
	
	private void prepareDownload() {
		if(mEpubsList==null){
			mEpubsList= new ArrayList<DropboxAPI.Entry>();
		}else{
			mEpubsList.removeAll(mEpubsList);
		}
		
	}

	private void recursiveSearch(Entry entryParent){
		
		if(entryParent!=null){
			for (Entry entry : entryParent.contents) {
				if(!entry.isDir){
					mEpubsList.add(entry);
				}else{
					if(entry.contents!=null&&entry.contents.size()>0)
						recursiveSearch(entry);
				}
			}			
		}		
	}

}
