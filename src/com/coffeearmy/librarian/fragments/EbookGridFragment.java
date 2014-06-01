package com.coffeearmy.librarian.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.coffeearmy.librarian.R;
import com.coffeearmy.librarian.adapters.EpubListAdapter;
import com.coffeearmy.librarian.data.EPubData;
import com.coffeearmy.librarian.loader.EPubLoader;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.etsy.android.grid.StaggeredGridView;

public class EbookGridFragment extends Fragment implements
		LoaderCallbacks<List<EPubData>> {

	public static final String FRAGMENT_TAG = "ebook_grid_fragment";
	private static final int LOADER_EPUB_ID = 0;
	private static EbookGridFragment mEbookGrid;
	private DropboxAPI<AndroidAuthSession> mDropboxAPI;
	private EpubListAdapter mAdapterList;
	private ArrayList<EPubData> mEPubList;

	public static EbookGridFragment getInstance() {
		if (mEbookGrid == null) {
			mEbookGrid = new EbookGridFragment();
		}
		return mEbookGrid;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
	//	OttoBusHelper.getCurrentBus().register(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View gridEpubView = (View) inflater.inflate(R.layout.grid_view_epubs,
				null);
		StaggeredGridView gridEpub = (StaggeredGridView) gridEpubView.findViewById(R.id.grid_view);
		onCreateEmptyView(gridEpub, inflater);
		mEPubList = new ArrayList<EPubData>();
		mAdapterList = new EpubListAdapter(getActivity(),
				R.layout.item_epub_grid, mEPubList);
		gridEpub.setAdapter(mAdapterList);

		mDropboxAPI = PromptDropboxLoginFragment.getAPIDropbox();
		getLoaderManager().initLoader(LOADER_EPUB_ID, null, this).forceLoad();
		return gridEpubView;
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	// *** LOADER METHODS ****//
	@Override
	public Loader<List<EPubData>> onCreateLoader(int arg0, Bundle arg1) {
		return new EPubLoader(getActivity(), mDropboxAPI);
	}

	@Override
	public void onLoadFinished(Loader<List<EPubData>> arg0, List<EPubData> arg1) {
		mEPubList = (ArrayList<EPubData>) arg1;
		mAdapterList.changeDataSet(mEPubList);

	}

	@Override
	public void onLoaderReset(Loader<List<EPubData>> arg0) {
		mAdapterList.changeDataSet(new ArrayList<EPubData>());

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuOrderAZ:
			if (mEPubList != null) {
				Collections.sort(mEPubList, EPubData.EPubDataNameComparator);
				mAdapterList.changeDataSet(mEPubList);
			}
			break;
		case R.id.menuOrderDate:
			if (mEPubList != null) {
				Collections.sort(mEPubList, EPubData.EPubDataDateComparator);
				mAdapterList.changeDataSet(mEPubList);
			}
			break;
		case R.id.action_settings:
			clearKeys();
			PromptDropboxLoginFragment.logOutDropbox();
			getActivity()
					.getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.fragment_container,
							PromptDropboxLoginFragment.getInstance(),
							PromptDropboxLoginFragment.FRAGMENT_TAG).commit();
		}
		return super.onOptionsItemSelected(item);
	}

	private void clearKeys() {
		SharedPreferences prefs = getActivity().getSharedPreferences(
				PromptDropboxLoginFragment.ACCOUNT_PREFS_NAME, 0);
		Editor edit = prefs.edit();
		edit.clear();
		edit.commit();
	}
	
	private void onCreateEmptyView(StaggeredGridView grid, LayoutInflater inflater) {
		View emptyView = inflater.inflate(R.layout.empty_view, null);
		((ViewGroup) grid.getParent()).addView(emptyView, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		grid.setEmptyView(emptyView);
	}
	

	
}
