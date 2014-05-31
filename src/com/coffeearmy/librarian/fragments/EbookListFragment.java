package com.coffeearmy.librarian.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.coffeearmy.librarian.R;
import com.coffeearmy.librarian.adapters.EpubListAdapter;
import com.coffeearmy.librarian.data.EPubData;
import com.coffeearmy.librarian.events.OnSuccessAuthorization;
import com.coffeearmy.librarian.events.OttoBusHelper;
import com.coffeearmy.librarian.rest.EpubLoader;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.squareup.otto.Subscribe;

public class EbookListFragment extends Fragment implements
		LoaderCallbacks<List<EPubData>> {

	public static final String FRAGMENT_TAG = "ebook_fragment";
	private static final int LOADER_EPUB_ID = 0;
	private DropboxAPI<AndroidAuthSession> mDropboxAPI;
	private EpubListAdapter mAdapterList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		OttoBusHelper.getCurrentBus().register(this);
		super.onCreate(savedInstanceState);
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ListView listEpubs = new ListView(getActivity());
		mAdapterList= new EpubListAdapter(getActivity(), R.layout.item_epub_list, new ArrayList<EPubData>());
		listEpubs.setAdapter(mAdapterList);
		return listEpubs;
	}
	
	/** Is subscribed to the event bus waiting for the onFinishAutentification event to occur*/
	@Subscribe
	public void onFinishAuthentification(OnSuccessAuthorization event){
		if(event.getType()==OnSuccessAuthorization.Type.SUCCESS){
			mDropboxAPI=event.getDropboxAPI();
			getLoaderManager().initLoader(LOADER_EPUB_ID, null, this).forceLoad();
		}
	}
	
	//*** LOADER METHODS ****//
	@Override
	public Loader<List<EPubData>> onCreateLoader(int arg0, Bundle arg1) {
		return new EpubLoader(getActivity(),mDropboxAPI);
	}

	@Override
	public void onLoadFinished(Loader<List<EPubData>> arg0, List<EPubData> arg1) {
		mAdapterList.changeDataSet((ArrayList<EPubData>) arg1);

	}

	@Override
	public void onLoaderReset(Loader<List<EPubData>> arg0) {
		mAdapterList.changeDataSet(new ArrayList<EPubData>());

	}

}
