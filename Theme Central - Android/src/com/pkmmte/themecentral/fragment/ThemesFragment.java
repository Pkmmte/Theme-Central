package com.pkmmte.themecentral.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pkmmte.themecentral.R;

public class ThemesFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_themes, container, false);
		return view;
	}

	@Override
	public void onStart()
	{
		super.onStart();
	}
}