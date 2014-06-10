package com.pkmmte.themecentral.model;

public class LoginResponse
{
	public static final int UNKNOWN = -1;
	public static final int INCORRECT = 0;
	
	public boolean success;
	public int reason;
	
	public LoginResponse()
	{
		this.success = false;
		this.reason = UNKNOWN;
	}
}