package com.pkmmte.themecentral.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.pkmmte.themecentral.data.Constants;
import com.pkmmte.themecentral.model.LoginResponse;
import com.pkmmte.themecentral.model.User;

public class UserManager
{
//	TODO
	//	- Everything
	
	// Public Static Constants
	public static final String TAG = "tag";
	public static final String TAG_LOGIN = "login";
	public static final String TAG_REGISTER = "register";
	
	// JSON Constants
	private final String USER_ID = "user_id";
	private final String USER_USERNAME = "username";
	
	// Keep track of single instance. Helps maintain same User Manager without requiring Activity instance.
	private static UserManager mInstance = null;
	
	// Stored user data. Null if not currently logged in or token expired.
	public User mUser;
	
	// For issue tracking purposes.
	private boolean debugEnabled;
	private static final String LOG_TAG = "UserManager";
	
	// Preferences to locally save data
	private SharedPreferences mPrefs;
	
	// A context is always important for some reason...
	private Context mContext;
	
	// Listeners for various loading events
	///private List<OnUserStatusChangedListener> mOnUserStatusChangedListeners;
	///private List<OnBasicInfoLoadListener> mOnBasicInfoLoadListeners;
	
	// Our handy client for getting API JSON data
	private HttpClient httpClient;
	
	public static void createInstance(Context context)
	{
		if (mInstance == null)
			mInstance = new UserManager(context.getApplicationContext());
	}
	
	public static UserManager getInstance()
	{
		return mInstance;
	}
	
	public static UserManager getInstance(Context context)
	{
		if (mInstance == null)
			mInstance = new UserManager(context.getApplicationContext());
		
		return mInstance;
	}
	
	public UserManager(Context context)
	{
		this.debugEnabled = false;
		this.mContext = context;
		this.mUser = null;
		this.mPrefs = context.getSharedPreferences(Constants.PREFS_TAG, 0);
		///this.mOnUserStatusChangedListeners = new ArrayList<OnUserStatusChangedListener>();
		///this.mOnBasicInfoLoadListeners = new ArrayList<OnBasicInfoLoadListener>();
		//this.loadClientData();
		this.initHttpClient();
	}
	
	public LoginResponse loginUser(String email, String password) throws ClientProtocolException, IOException, JSONException
	{
		LoginResponse mResponse = new LoginResponse();
		
		// Build parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(TAG, TAG_LOGIN));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		
		JSONObject json = Utils.getJSONFromUrl(Constants.API_LOGIN_URL, params, httpClient);
		Log.i(LOG_TAG, json.toString());
		
		return mResponse;
	}
	
	public void loginUserAsync(final String email, final String password)
	{
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					loginUser(email, password);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}
		}.execute();
	}
	
	public void setDebugging(boolean debug)
	{
		this.debugEnabled = debug;
	}
	
	private void initHttpClient()
	{
		// Basic HTTP parameters & manager set up
		HttpParams parameters = new BasicHttpParams();
		HttpProtocolParams.setVersion(parameters, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(parameters, HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(parameters, false);
		HttpConnectionParams.setTcpNoDelay(parameters, true);
		HttpConnectionParams.setSocketBufferSize(parameters, 8192);
		
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		ClientConnectionManager tsccm = new ThreadSafeClientConnManager(parameters, schReg);
		
		// Finally, initialize our client with these parameters and manager
		this.httpClient = new DefaultHttpClient(tsccm, parameters);
	}
}