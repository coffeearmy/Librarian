package com.coffeearmy.librarian.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.coffeearmy.librarian.events.OnSuccessAuthorization;
import com.coffeearmy.librarian.events.OttoBusHelper;
import com.coffeearmy.librarian.rest.EpubLoader;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.squareup.otto.Subscribe;

public class EbookListFragment extends Fragment implements
		LoaderCallbacks<List<Entry>> {

	public static final String FRAGMENT_TAG = "ebook_fragment";
	private static final int LOADER_EPUB_ID = 0;
	private DropboxAPI<AndroidAuthSession> mDropboxAPI;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		OttoBusHelper.getCurrentBus().register(this);
		super.onResume();
	}

	@Override
	public void onPause() {
		OttoBusHelper.getCurrentBus().unregister(this);
		super.onPause();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ListView listEpubs = new ListView(getActivity());

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Subscribe
	public void onFinishAuthentification(OnSuccessAuthorization event){
		if(event.getType()==OnSuccessAuthorization.Type.SUCCESS){
			mDropboxAPI=event.getDropboxAPI();
			getLoaderManager().initLoader(LOADER_EPUB_ID, null, this).forceLoad();
		}
	}
	
	//*** LOADER METHODS ****//
	@Override
	public Loader<List<Entry>> onCreateLoader(int arg0, Bundle arg1) {
		return new EpubLoader(getActivity(),mDropboxAPI);
	}

	@Override
	public void onLoadFinished(Loader<List<Entry>> arg0, List<Entry> arg1) {
		//mAdapterList.changeDataSet(arg1);

	}

	@Override
	public void onLoaderReset(Loader<List<Entry>> arg0) {
	//	mAdapterList.changeDataSet(new ArrayList<Entry>());

	}

}
