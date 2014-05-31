package com.coffeearmy.librarian.adapters;

import java.io.File;
import java.io.FileInputStream;
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
import android.widget.TextView;

import com.coffeearmy.librarian.MainActivity;
import com.coffeearmy.librarian.R;
import com.coffeearmy.librarian.data.EPubData;
import com.coffeearmy.librarian.fragments.ImageDialog;
import com.coffeearmy.librarian.fragments.PromptDropboxLoginFragment;
import com.coffeearmy.librarian.gesture.GestureListener;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.exception.DropboxException;

public class EpubListAdapter extends ArrayAdapter<EPubData> {

	// ViewHolder pattern
	static class ViewHolder {
		TextView titleEPub;
		TextView dateEpub;
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

	// private ImageLoader mImageLoader;

	public EpubListAdapter(Context context, int resource,
			ArrayList<EPubData> mItemList) {
		super(context, resource, mItemList);

		this.mContext = context;
		this.mItemList = mItemList;
		this.mItemLayout = resource;
		mGestureDetector = new GestureDetector(context,new GestureListener());

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
			viewHolder.imgEPub.setOnTouchListener(new OnDoubleTap());
			// Store the viewHolder in the tag of the view
			rowView.setTag(viewHolder);
		}
		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		EPubData ePub = mItemList.get(position);
		holder.setePub(ePub);
		holder.titleEPub.setText(ePub.getFileName());
		holder.imgEPub.setTag(position);
		if (ePub.isEPubMetadataLoaded()) {
			holder.titleEPub.setText(ePub.getTitle());
			holder.imgEPub.setImageBitmap(ePub.getThumbnail());
			
		} else {
			new DownloadEpubAndFillList().execute(holder);
		}
		// holder.dateEpub.setText(item.getDate() + "");


		return rowView;
	}

	public void changeDataSet(ArrayList<EPubData> arrayList) {
		mItemList.clear();
		mItemList.addAll(arrayList);
		notifyDataSetChanged();
	}

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
				DropboxFileInfo info = PromptDropboxLoginFragment
						.getAPIDropbox().getFile(epub.getPath(), null,
								outputStream, null);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DropboxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 2.Read ePub metada
			try {
				Book epubBook = (new EpubReader())
						.readEpub(new FileInputStream(file));
				epub.setCover(BitmapFactory.decodeStream(epubBook
						.getCoverImage().getInputStream()));
				epub.setTitle(epubBook.getTitle());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return viewHolder;
		}

		@Override
		protected void onPostExecute(ViewHolder result) {
			EPubData ePub = result.getePub();
			result.titleEPub.setText(ePub.getTitle());
			result.imgEPub.setImageBitmap(ePub.getThumbnail());
			
		}

	}
	
	protected class OnDoubleTap implements View.OnTouchListener{
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(mGestureDetector.onTouchEvent(event)){
				showCover((Integer)v.getTag());
			}
			return false;
		}		
	}

	public void showCover(Integer tag) {
		FragmentManager fm = ((MainActivity)mContext).getSupportFragmentManager();
		
		ImageDialog.getInstance(mItemList.get(tag).getCover()).show(fm, ImageDialog.FRAGMENT_TAG);
	}

}
