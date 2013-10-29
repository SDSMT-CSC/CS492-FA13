package edu.sdsmt.cs492.example15.service.bind;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ListActivity
{

	private static final String ACTION_START_SERVICE = "edu.sdsmt.cs492.example15.action.START_SERVICE";
	private BindService _service;

	private ArrayAdapter<String> _adapter;
	private ArrayList<String> _list;

	private Button _buttonGetNewList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Build up list adapter.
		_list = new ArrayList<String>();
		_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, _list);
		setListAdapter(_adapter);

		// Assign click listener to button.
		_buttonGetNewList = (Button) findViewById(R.id.buttonGetList);
		_buttonGetNewList.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				// If the Service is bound, tell the use how many elements exist
				// in the service member list and swap how list adapter data.
				if (_service != null)
				{
					Toast.makeText(view.getContext(), "Number of elements: " + _service.getNameList().size(), Toast.LENGTH_SHORT).show();
					
					_list.clear();
					_list.addAll(_service.getNameList());
					_adapter.notifyDataSetChanged();
				}
			}
		});
		
		// Create Broadcast Receiver which in turns starts Service.
		sendBroadcast(new Intent(ACTION_START_SERVICE));
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		// Bind to the specific service.
		bindService(new Intent(this, BindService.class), _serviceConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onPause()
	{
		// Remove binding.
		unbindService(_serviceConnection);
		super.onPause();
	}
	
	@Override
	protected void onDestroy()
	{
		// Stop the service if we are being destroyed.
		stopService(new Intent(this, BindService.class));
		super.onDestroy();
	}
	
	private ServiceConnection _serviceConnection = new ServiceConnection()
	{
		// Required for Bind service to work.
		
		@Override
		public void onServiceConnected(ComponentName className, IBinder binder)
		{
			// Get a reference to the service to fulfill binding effort.  This is
			// necessary to call the getNameList() method.
			_service = ((BindService.BindServiceBinder) binder).getService();
			Toast.makeText(MainActivity.this, "Service Connected", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onServiceDisconnected(ComponentName className)
		{
			// Only called in "extreme" situations, systems kills, etc.
			// Unlikely to occur in own app.
			
			_service = null;
			Toast.makeText(MainActivity.this, "Service Disconnected", Toast.LENGTH_LONG).show();
		}
	};

}
