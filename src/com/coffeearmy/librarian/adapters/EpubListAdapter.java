package com.coffeearmy.librarian.adapters;

import java.util.ArrayList;

import com.coffeearmy.librarian.R;
import com.dropbox.client2.DropboxAPI.Entry;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EpubListAdapter extends ArrayAdapter<Entry> {

	// ViewHolder pattern
	static class ViewHolder {
		public TextView titleEPub;
		public TextView dateEpub;
		public ImageView imgEPub;
	}

	private Context mContext;
	private ArrayList<Entry> mItemList;
	private int mItemLayout;

	// private ImageLoader mImageLoader;

	public EpubListAdapter(Context context, int resource,
			ArrayList<Entry> mItemList) {
		super(context, resource, mItemList);

		this.mContext = context;
		this.mItemList = mItemList;
		this.mItemLayout = resource;
		// this.mImageLoader=ImageLoader.getInstance();
		// mImageLoader=ImageLoader.getInstance();
		// options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.ic_launcher)
		// .showImageForEmptyUri(R.drawable.ic_launcher)
		// .cacheOnDisc()
		// .cacheInMemory()
		// .build();
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
			viewHolder.titleEPub = (TextView) rowView.findViewById(R.id.txtBookName);
			viewHolder.imgEPub = (ImageView) rowView
					.findViewById(R.id.imgEbook);
			viewHolder.dateEpub = (TextView) rowView.findViewById(R.id.txtVDateFile);

			// Store the viewHolder in the tag of the view
			rowView.setTag(viewHolder);
		}
		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		Entry item = mItemList.get(position);

		holder.titleEPub.setText(item.fileName());
		holder.dateEpub.setText(item.clientMtime + "");

		// Load image, decode it to Bitmap and display Bitmap in ImageView (or
		// any other view which implements ImageAware interface)
		// Using universal-imager-loader lib
		// if(item.getFoto()!=null&&! item.getFoto().equals("")){
		//
		// mImageLoader.displayImage(item.getFoto(), holder.icon,options);
		// }

		return rowView;
	}

	public void changeDataSet(ArrayList<Entry> arrayList) {
		mItemList.clear();
		mItemList.addAll(arrayList);
		notifyDataSetChanged();
	}

}
