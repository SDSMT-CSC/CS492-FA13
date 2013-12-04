package edu.sdsmt.WornerTillma.GraphicsExample;

/*
 * Example from page 233 of Programming Android
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class PaintExample extends Activity
{
	private static class PaintCanvas extends View
	{
		private Paint paint;
		
		public PaintCanvas(Context context)
		{
			super(context);
			this.paint = new Paint();
		}
		
		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.drawColor(Color.WHITE);
			
			this.paint.reset();
			
			//basic vertical line
			canvas.drawLine(100, 10, 100, 500, this.paint);
			
			//vertical line
			this.paint.setColor(Color.RED);
			this.paint.setStrokeWidth(50);
			canvas.drawLine(200, 10, 200, 500, this.paint);
			
			//horizontal line
			this.paint.setColor(Color.GREEN);
			this.paint.setStrokeWidth(25);
			
			//loop for drawing
			for(int y = 75, alpha = 255; alpha > 2; alpha >>= 1, y += 50)
			{
				this.paint.setAlpha(alpha);
				canvas.drawLine(50, y, 500, y, this.paint);
			}
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty_layout);
		
		LinearLayout l = (LinearLayout)findViewById(R.id.LinearLayout);
		l.addView(new PaintCanvas(this));
	}
}
