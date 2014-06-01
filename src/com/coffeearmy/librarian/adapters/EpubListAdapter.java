package com.coffeearmy.librarian.adapters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coffeearmy.librarian.MainActivity;
import com.coffeearmy.librarian.R;
import com.coffeearmy.librarian.data.EPubData;
import com.coffeearmy.librarian.fragments.ImageDialog;
import com.coffeearmy.librarian.fragments.PromptDropboxLoginFragment;
import com.coffeearmy.librarian.gesture.GestureListener;
import com.dropbox.client2.exception.DropboxException;

/** A ArrayAdapter which loads EPubData elements */
public class EpubListAdapter extends ArrayAdapter<EPubData> {

	// ViewHolder pattern
	// The ePubData is stored for the asynctask can fill it's fields
	static class ViewHolder {
		TextView titleEPub;
		TextView dateEpub;
		ProgressBar loadingBar;
		ImageView imgEPub;
		EPubData ePub;

		public void setePub(EPubData ePub) {
			this.ePub = ePub;
		}

		public EPubData getePub() {
			return ePub;
		}

	}

	private Context mContext;
	private ArrayList<EPubData> mItemList;
	private int mItemLayout;
	private GestureDetector mGestureDetector;

	public EpubListAdapter(Context context, int resource,
			ArrayList<EPubData> mItemList) {
		super(context, resource, mItemList);

		this.mContext = context;
		this.mItemList = mItemList;
		this.mItemLayout = resource;
		// Gesture Detector for listen the imagen double click
		mGestureDetector = new GestureDetector(context, new GestureListener());

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// If the convertview is null needs to be inflated, if not null it can
		// be reuse it
		if (rowView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			rowView = inflater.inflate(mItemLayout, null);

			// configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.titleEPub = (TextView) rowView
					.findViewById(R.id.txtBookName);
			viewHolder.imgEPub = (ImageView) rowView
					.findViewById(R.id.imgEbook);
			viewHolder.dateEpub = (TextView) rowView
					.findViewById(R.id.txtVDateFile);
			viewHolder.loadingBar = (ProgressBar) rowView
					.findViewById(R.id.progBePubLoad);
			viewHolder.imgEPub.setOnTouchListener(new OnDoubleTap());

			// Store the viewHolder in the tag of the view
			rowView.setTag(viewHolder);
		}
		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		EPubData ePub = mItemList.get(position);

		holder.setePub(ePub);

		// Fill elements with Dropbox metadada
		holder.titleEPub.setText(ePub.getFileName());
		holder.dateEpub.setText(ePub.getDate().toLocaleString());
		holder.imgEPub.setTag(position);

		// if ePub metadata is loaded
		if (ePub.isEPubMetadataLoaded()) {
			holder.titleEPub.setText(ePub.getTitle());
			holder.imgEPub.setImageBitmap(ePub.getThumbnail());
			holder.loadingBar.setVisibility(View.GONE);

		} else {
			// if the item don't have the epub Metadata begin to download the
			// file and extract the cover an title
			new DownloadEpubAndFillList().execute(holder);
		}

		return rowView;
	}

	/** Change the list items with a news ones */
	public void changeDataSet(ArrayList<EPubData> arrayList) {
		mItemList.clear();
		mItemList.addAll(arrayList);
		notifyDataSetChanged();
	}

	/**
	 * Class for download the ePubs files and use ePubLib to extract the cover
	 * and title
	 */
	private class DownloadEpubAndFillList extends
			AsyncTask<ViewHolder, Void, ViewHolder> {

		@Override
		protected ViewHolder doInBackground(ViewHolder... params) {

			ViewHolder viewHolder = params[0];
			// 1. download ePub
			EPubData epub = viewHolder.getePub();
			String path = getContext().getFilesDir().getPath().toString()
					+ epub.getFileName();

			File file = new File(path);

			FileOutputStream outputStream;

			try {
				outputStream = new FileOutputStream(file);
				PromptDropboxLoginFragment.getAPIDropbox().getFile(
						epub.getPath(), null, outputStream, null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DropboxException e) {
				e.printStackTrace();
			}

			// 2.Read ePub metadata
			Book epubBook = null;
			try {
				// Large ePub files throw OutofMemoryException, with
				// readEpubLazy is solved
				epubBook = (new EpubReader()).readEpubLazy(path, "UTF-8");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Retrieve ePub Cover, may not exist
			try {
				epub.setCover(BitmapFactory.decodeStream(epubBook
						.getCoverImage().getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} finally {
				// if cover is null use the default icon
				if (epub.getCover() == null)
					epub.setCover(BitmapFactory.decodeResource(getContext()
							.getResources(), R.drawable.ic_launcher));
			}

			// Retrieve ePub title
			try {
				epub.setTitle(epubBook.getTitle());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (epub.getTitle().equals(EPubData.EMPTY_STRING))
					epub.setTitle(epub.getFileName());
			}

			// Delete items
			epub = null;
			try {
				file.deleteOnExit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				file.delete();
			}

			return viewHolder;
		}

		@Override
		protected void onPostExecute(ViewHolder result) {
			EPubData ePub = result.getePub();
			result.titleEPub.setText(ePub.getTitle());
			result.imgEPub.setImageBitmap(ePub.getThumbnail());
			// Hide Progress bar
			result.loadingBar.setVisibility(View.GONE);
		}

	}

	/** Listener double tap with a gesture detector */
	protected class OnDoubleTap implements View.OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (mGestureDetector.onTouchEvent(event)) {
				showCover((Integer) v.getTag());
			}
			return false;
		}
	}

	/** Shows the cover in a custom dialog */
	private void showCover(Integer tag) {
		FragmentManager fm = ((MainActivity) mContext)
				.getSupportFragmentManager();

		ImageDialog.getInstance(mItemList.get(tag).getCover()).show(fm,
				ImageDialog.FRAGMENT_TAG);
	}

}
