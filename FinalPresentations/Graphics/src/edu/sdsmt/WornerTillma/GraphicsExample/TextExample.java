package edu.sdsmt.WornerTillma.GraphicsExample;

/*
 * Example from page 235-236 of Programming Android
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class TextExample extends Activity
{
	private static class DrawText extends View
	{
		private Paint paint;
		private Path path;
		
		public DrawText(Context context)
		{
			super(context);
			this.paint = new Paint();
			this.path = new Path();
		}
		
		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.drawColor(Color.WHITE);;
			this.paint.reset();
			
			//normal text
			this.paint.setColor(Color.RED);
			this.paint.setTextSize(100);
			canvas.drawText("Android", 100, 100, this.paint);
			
			//arc text
			this.path.reset();
			this.path.addArc(new RectF(0, 200, 600, 1000), 240, 90);
			this.paint.setColor(Color.BLUE);
			canvas.drawTextOnPath("Android", path, 0, 0, this.paint);
			
			float[] pos = new float[]
					{
						20, 400,
						120, 450,
						220, 400,
						320, 450,
						420, 400,
						520, 450,
						620, 400
					};
			
			//"random" text
			this.paint.setColor(Color.GREEN);
			canvas.drawPosText("Android", pos, this.paint);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty_layout);
		
		LinearLayout l = (LinearLayout)findViewById(R.id.LinearLayout);
		l.addView(new DrawText(this));
	}
}
