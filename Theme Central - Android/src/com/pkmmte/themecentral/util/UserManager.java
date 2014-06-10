package com.pkmmte.themecentral.util;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.content.SharedPreferences;

import com.pkmmte.themecentral.data.Constants;
import com.pkmmte.themecentral.model.LoginResponse;
import com.pkmmte.themecentral.model.User;

public class UserManager
{
//	TODO
	//	- Everything
	
	// JSON Constants
	private final String USER_ID = "user_id";
	private final String USER_USERNAME = "username";
	
	// Keep track of single instance. Helps maintain same User Manager without requiring Activity instance.
	private static UserManager mInstance = null;
	
	// Stored user data. Null if not currently logged in or token expired.
	public User mUser;
	
	// For issue tracking purposes.
	private boolean debugEnabled;
	private static final String TAG = "UserManager";
	
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
	
	public LoginResponse login(String email, String password)
	{
		LoginResponse mResponse = new LoginResponse();
		
		
		
		return mResponse;
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