package edu.sdsmt.cs492.example3.asynctask;

public interface IWebRequestListener
{
	public void onWebRequestStart();
	public void onWebRequestProgressUpdate(String progress);
	public void onWebRequestComplete(String result);
}