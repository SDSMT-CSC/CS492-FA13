package edu.sdsmt.cs492.assignment3.weatherapp;

import edu.sdsmt.cs492.assignment3.weatherapp.model.Forecast;
import edu.sdsmt.cs492.assignment3.weatherapp.model.ForecastLocation;

public interface IListeners
{
	public void onLocationLoaded(ForecastLocation forecastLocation);
	public void onForecastLoaded(Forecast forecast);
}
