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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.pkmmte.themecentral.view.PkDrawerLayout;

public class MainActivity extends FragmentActivity
{
	@SuppressWarnings("unused")
	private static final String TAG = "MainActivity";
	
	// Section Constants
	public static final int ACCOUNT = 0;
	public static final int HOME = 1;
	public static final int THEMES = 2;
	public static final int ICONS = 3;
	public static final int WIDGETS = 4;
	public static final int MISC = 5;
	public static final int SETTINGS = 6;
	public static final int HELP = 7;
	
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
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		return true;
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
		Log.d("WTF", "" + (mDrawerLayout == null));
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
		mDrawerAdapter.addItem(getString(R.string.home));
		mDrawerAdapter.addItem(getString(R.string.themes));
		mDrawerAdapter.addItem(getString(R.string.icon_packs));
		mDrawerAdapter.addItem(getString(R.string.widgets));
		mDrawerAdapter.addItem(getString(R.string.misc));

		//
		View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drawer_header, null, false);
		mDrawerList.addHeaderView(headerView);
		
		//
		View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drawer_footer, null, false);
		mDrawerList.addFooterView(footerView);
		
		//
		mDrawerList.setAdapter(mDrawerAdapter);
		
		//
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long index) {
				selectPage(position);
			}
		});
		/*((Button) footerView.findViewById(R.id.btnSettings)).setOnClickListener(new OnClickListener() {
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
		});*/
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
				mTitle = mDrawerAdapter.getItem(SETTINGS);
				break;
			case HELP:
				transaction.replace(R.id.contentFragment, mSections.get(HELP));
				mTitle = mDrawerAdapter.getItem(HELP);
				break;
		}
		
		transaction.commit();
		
		currentSection = selection;
		mDrawerAdapter.setCurrentPage(selection);
		mDrawerLayout.closeDrawers();
	}
}