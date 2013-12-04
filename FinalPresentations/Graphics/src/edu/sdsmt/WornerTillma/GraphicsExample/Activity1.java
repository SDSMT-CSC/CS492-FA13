package edu.sdsmt.WornerTillma.GraphicsExample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activity1 extends Activity implements OnClickListener 
{	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity1);
		
		((Button)findViewById(R.id.bling)).setOnClickListener(this);
		((Button)findViewById(R.id.blingNoSize)).setOnClickListener(this);
		((Button)findViewById(R.id.canvasDrawing)).setOnClickListener(this);
		((Button)findViewById(R.id.drawables)).setOnClickListener(this);
		((Button)findViewById(R.id.drawingText)).setOnClickListener(this);
		((Button)findViewById(R.id.matrices)).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.activity1, menu);
		return true;
	}

	@Override
	public void onClick(View v) 
	{
		int id = v.getId();
		Intent intent = null;
		
		switch(id)
		{
			case R.id.bling:
				intent = new Intent(this, BlingExample.class);
				break;
			case R.id.blingNoSize:
				intent = new Intent(this, BlingNoSize.class);
				break;
			case R.id.canvasDrawing:
				intent = new Intent(this, PaintExample.class);
				break;
			case R.id.drawables:
				intent = new Intent(this, DrawablesExample.class);
				break;
			case R.id.drawingText:
				intent = new Intent(this, TextExample.class);
				break;
			case R.id.matrices:
				intent = new Intent(this, MatrixExample.class);
				break;
		}
		
		startActivity(intent);
	}
}
