package com.pkmmte.themecentral.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pkmmte.themecentral.R;
import com.pkmmte.themecentral.util.UserManager;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final EditText editEmail = (EditText) findViewById(R.id.editEmail);
		final EditText editPass = (EditText) findViewById(R.id.editPassword);
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String Email = editEmail.getText().toString();
				String Password = editPass.getText().toString();
				
				UserManager.getInstance(MainActivity.this).loginUserAsync(Email, Password);
			}
		});
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
}
