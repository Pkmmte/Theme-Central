package com.pkmmte.themecentral.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.pkmmte.themecentral.R;
import com.pkmmte.themecentral.adapter.NavDrawerAdapter;
import com.pkmmte.themecentral.fragment.HelpFragment;
import com.pkmmte.themecentral.fragment.HomeFragment;
import com.pkmmte.themecentral.fragment.IconsFragment;
import com.pkmmte.themecentral.fragment.MiscFragment;
import com.pkmmte.themecentral.fragment.SettingsFragment;
import com.pkmmte.themecentral.fragment.ThemesFragment;
import com.pkmmte.themecentral.fragment.WidgetsFragment;
import com.pkmmte.themecentral.util.BlurTransform;
import com.pkmmte.themecentral.util.RoundTransform;
import com.pkmmte.themecentral.view.CircularImageView;
import com.pkmmte.themecentral.view.PkDrawerLayout;
import com.squareup.picasso.Picasso;

public class MainActivity extends FragmentActivity
{
	@SuppressWarnings("unused")
	private static final String TAG = "MainActivity";
	
	// Section Constants
	public static final int ACCOUNT = -1;
	public static final int HOME = 0;
	public static final int THEMES = 1;
	public static final int ICONS = 2;
	public static final int WIDGETS = 3;
	public static final int MISC = 4;
	public static final int SETTINGS = 5;
	public static final int HELP = 6;
	
	// ActionBar & Title
	private ActionBar mActionBar;
	private CharSequence mTitle;
	
	// Navigation Drawer
	private ActionBarDrawerToggle mDrawerToggle;
	private NavDrawerAdapter mDrawerAdapter;
	private PkDrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	
	// Sections & Manager
	private int currentSection;
	private List<Fragment> mSections;
	private FragmentManager mFragmentManager;
	
	// Views
	private CircularImageView imgAvatar;
	private ImageView imgBanner;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initActionBar();
		initViews();
		initManagers();
		initSections();
		initNavDrawer();
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
		selectPage(currentSection);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			default:
				return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
		}
	}
	
	private void initActionBar()
	{
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
		///mActionBar.setIcon(R.drawable.ic_launcher_white);
	}
	
	private void initViews()
	{
		mDrawerLayout = (PkDrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
	}
	
	private void initManagers()
	{
		mFragmentManager = getSupportFragmentManager();
	}
	
	private void initSections()
	{
		mSections = new ArrayList<Fragment>();
		mSections.add(new HomeFragment());
		mSections.add(new ThemesFragment());
		mSections.add(new IconsFragment());
		mSections.add(new WidgetsFragment());
		mSections.add(new MiscFragment());
		mSections.add(new SettingsFragment());
		mSections.add(new HelpFragment());
		currentSection = HOME;
	}
	
	private void initNavDrawer()
	{
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer_indicator, R.string.drawer_open, R.string.drawer_close) {
			@Override
			public void onDrawerClosed(View view) {
				mActionBar.setSubtitle(mTitle);
				invalidateOptionsMenu();
			}
			
			@Override
			public void onDrawerOpened(View drawerView) {
				mActionBar.setSubtitle(null);
				invalidateOptionsMenu();
			}
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mDrawerAdapter = new NavDrawerAdapter(MainActivity.this);
		mDrawerAdapter.addItem(getResources().getString(R.string.home));
		mDrawerAdapter.addItem(getResources().getString(R.string.themes));
		mDrawerAdapter.addItem(getResources().getString(R.string.icon_packs));
		mDrawerAdapter.addItem(getResources().getString(R.string.widgets));
		mDrawerAdapter.addItem(getResources().getString(R.string.misc));

		// Add header
		View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drawer_header, null, false);
		mDrawerList.addHeaderView(headerView);
		
		// Add footer
		View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drawer_footer, null, false);
		mDrawerList.addFooterView(footerView);
		
		// Initialize header views
		imgAvatar = (CircularImageView) headerView.findViewById(R.id.imgAvatar);
		imgBanner = (ImageView) headerView.findViewById(R.id.imgBanner);
		
		// Set list adapter
		mDrawerList.setAdapter(mDrawerAdapter);
		
		// Apply onClick listeners
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long index) {
				selectPage(position - mDrawerList.getHeaderViewsCount());
			}
		});
		((Button) footerView.findViewById(R.id.btnSettings)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectPage(SETTINGS);
			}
		});
		((Button) footerView.findViewById(R.id.btnHelp)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectPage(HELP);
			}
		});
		
		// TODO Test Remove
		Picasso.with(this).load(R.drawable.default_avatar).transform(new RoundTransform(0,0)).into(imgAvatar);
		Picasso.with(this).load(R.drawable.drawer_default_banner).transform(new BlurTransform(this)).into(imgBanner);
	}
	
	public void selectPage(int selection)
	{
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		final FragmentTransaction transaction = mFragmentManager.beginTransaction();
		transaction.setCustomAnimations(R.anim.quickfade_in, R.anim.quickfade_out);
		
		switch(selection) {
			case HOME:
				transaction.replace(R.id.contentFragment, mSections.get(HOME));
				mTitle = mDrawerAdapter.getItem(HOME);
				break;
			case THEMES:
				transaction.replace(R.id.contentFragment, mSections.get(THEMES));
				mTitle = mDrawerAdapter.getItem(THEMES);
				break;
			case ICONS:
				transaction.replace(R.id.contentFragment, mSections.get(ICONS));
				mTitle = mDrawerAdapter.getItem(ICONS);
				break;
			case WIDGETS:
				transaction.replace(R.id.contentFragment, mSections.get(WIDGETS));
				mTitle = mDrawerAdapter.getItem(WIDGETS);
				break;
			case MISC:
				transaction.replace(R.id.contentFragment, mSections.get(MISC));
				mTitle = mDrawerAdapter.getItem(MISC);
				break;
			case SETTINGS:
				transaction.replace(R.id.contentFragment, mSections.get(SETTINGS));
				mTitle = getResources().getString(R.string.settings);
				break;
			case HELP:
				transaction.replace(R.id.contentFragment, mSections.get(HELP));
				mTitle = getResources().getString(R.string.help);
				break;
		}
		
		transaction.commit();
		
		currentSection = selection;
		mDrawerAdapter.setCurrentPage(selection);
		mDrawerLayout.closeDrawers();
	}
}