package com.pkmmte.themecentral.util;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils
{
	public static final String LOG_TAG = "Utils";
	
	public static JSONObject getJSONFromUrl(String url, List<NameValuePair> params, HttpClient client) throws ClientProtocolException, IOException, JSONException
	{
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(params));
        String response = client.execute(post, new BasicResponseHandler());
        
        // Shut down connection manager, be efficient.
        //httpClient.getConnectionManager().shutdown();
        
        // Save user data inside user object
        JSONObject jsonResponse = new JSONObject(response);

		// return JSON String
		return jsonResponse;
	}
}