package com.pkmmte.themecentral.activity;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.pkmmte.themecentral.R;
import com.pkmmte.themecentral.view.PkDrawerLayout;

public class MainActivity extends Activity
{
	@SuppressWarnings("unused")
	private static final String TAG = "MainActivity";
	
	// Section Constants
	public static final int HOME = 0;
	public static final int INFO = 1;
	public static final int ICONS = 2;
	public static final int REQUEST = 3;
	public static final int SETTINGS = 4;
	public static final int ABOUT = 5;
	public static final int COMMUNITY = 6;
	
	// ActionBar & Title
	private ActionBar mActionBar;
	private CharSequence mTitle;
	
	// Navigation Drawer
	private ActionBarDrawerToggle mDrawerToggle;
	///private NavDrawerAdapter mDrawerAdapter;
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
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}
	
	private void initViews()
	{
		mDrawerLayout = (PkDrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
	}
}