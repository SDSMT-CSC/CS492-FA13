package edu.sdsmt.mcs.dereknjon.dereknjonmediaplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	
	public Context context;
	public MediaPlayer medPlayer;
	public SurfaceView sv;
	public SurfaceHolder sh;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//medPlayer = new MediaPlayer();
		//context = this.getApplicationContext();
		
		
		//startService(new Intent(this, MyService.class));
		
		
		sv = (SurfaceView) findViewById(R.id.surfaceView1);
		sv.setVisibility(View.GONE);
		sh = sv.getHolder();
		sh.setFixedSize(176, 144);
		sh.addCallback(this);
		sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		
		Button audioBttn = (Button) this.findViewById(R.id.PlayAudio);
		audioBttn.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				playAudio();
			}
			
		});
		
		Button videoButton = (Button) this.findViewById(R.id.PlayVideo);
		videoButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				playVideo();
			}
			
		});
		
		Button stopButton = (Button) this.findViewById(R.id.StopButtn);
		stopButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				if( medPlayer != null)
				{
					medPlayer.stop();
					medPlayer.release();
					medPlayer = null;
					sv.setVisibility(View.GONE);
				}
				
				
			}
			
		});
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void playAudio(){
		if( medPlayer != null)
		{
			medPlayer.release();
			medPlayer = null;
		}
		medPlayer = MediaPlayer.create(this, R.raw.brodyquest); // initialize
        medPlayer.start();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if(medPlayer != null)
		{
			medPlayer.release();
			medPlayer = null;
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(medPlayer != null)
		{
			medPlayer.release();
			medPlayer = null;
		}
	}



	public void playVideo(){
		
		

		if( medPlayer != null)
		{
			medPlayer.release();
			medPlayer = null;
		}
		try{
			getWindow().setFormat(PixelFormat.UNKNOWN);
			sv.setVisibility(View.VISIBLE);
			
			medPlayer = MediaPlayer.create(this, R.raw.brodyquestvid);
			medPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			

			medPlayer.prepare();
			
		}
		catch(Exception e)
		{
			//HANDLE IT SOMEHOW!!!
			e.printStackTrace();
		}
		
		
        medPlayer.start();
	}



	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
		// TODO Auto-generated method stub
		
	}



	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		medPlayer.setDisplay(sh);
	}



	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}
