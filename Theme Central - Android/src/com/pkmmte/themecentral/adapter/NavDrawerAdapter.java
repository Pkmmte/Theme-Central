package com.pkmmte.themecentral.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pkmmte.themecentral.R;

public class NavDrawerAdapter extends BaseAdapter
{
	// Essential Resources
	private Context mContext;
	private List<String> mDrawerItems;
	
	// Current index and custom fonts
	private int currentPage;
	private Typeface fontNormal;
	private Typeface fontSelected;
	
	public NavDrawerAdapter(Context context)
	{
		this.mContext = context;
		this.mDrawerItems = new ArrayList<String>();
		
		this.fontNormal = Typeface.create("sans-serif-light", Typeface.NORMAL);
		this.fontSelected = Typeface.create("sans-serif", Typeface.BOLD);
	}
	
	public void addItem(String drawerItem)
	{
		mDrawerItems.add(drawerItem);
	}
	
	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount()
	{
		return mDrawerItems.size();
	}
	
	@Override
	public String getItem(int position)
	{
		return mDrawerItems.get(position);
	}
	
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup)
	{
		ViewHolder holder = null;
		
		if (convertView == null)
		{
			holder = new ViewHolder();
			LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.drawer_item, null);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		// Set text
		holder.txtTitle.setText(mDrawerItems.get(position));
		
		// Set bold if selected
		holder.txtTitle.setTypeface((currentPage == position) ? fontSelected : fontNormal);
		
		return convertView;
	}
	
	private class ViewHolder
	{
		public TextView txtTitle;
	}
}
