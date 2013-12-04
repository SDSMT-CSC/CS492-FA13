package edu.sdsmt.WornerTillma.GraphicsExample;

/*
 * This example is exactly the same as the BlingExample except that it
 * doesn't set the size of the View (EffectsWidget) so when the EffectsWidget
 * is added to the Activity's layout, only the first widget added shows
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class BlingNoSize extends Activity
{
	private static class EffectsWidget extends View
	{
		Paint paint;
		PaintEffect effect;
		String description;
		
		public interface PaintEffect
		{
			void setEffect(Paint p);
		}
		
		public EffectsWidget(Context context, String descrip, PaintEffect effect)
		{
			super(context);
			
			this.paint = new Paint();
			this.effect = effect;
			this.description = descrip;
		}
		
		@Override
		protected void onDraw(Canvas canvas)
		{
			this.paint.reset();
			this.paint.setAntiAlias(true);
			
			this.effect.setEffect(this.paint);
			this.paint.setColor(Color.DKGRAY);
			
			this.paint.setStrokeWidth(20);
			canvas.drawLine(25, 20, 275, 50, this.paint);
			
			this.paint.setTextSize(36);
			canvas.drawText("Android", 75, 90, this.paint);
			
			this.paint.reset();
			this.paint.setColor(Color.BLACK);
			canvas.drawText(String.valueOf(this.description), 2.0F, 120.0F, this.paint);
			
			this.paint.setStyle(Paint.Style.STROKE);
			this.paint.setStrokeWidth(2);
			canvas.drawRect(canvas.getClipBounds(), this.paint);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty_layout);
		
		LinearLayout l = (LinearLayout)findViewById(R.id.LinearLayout);
		l.addView(new EffectsWidget(
				this, 
				"Add Shadow Layer",
	 		    new EffectsWidget.PaintEffect()
	   		    {
			        @Override 
			        public void setEffect(Paint p) 
			        { 
			        	p.setShadowLayer(3, 6, 4, Color.BLUE);
			        }
			    }));
		l.addView(new EffectsWidget(
				this, 
				"Add Gradient",
	 		    new EffectsWidget.PaintEffect()
	   		    {
			        @Override 
			        public void setEffect(Paint p) 
			        { 
			        	p.setShader(
			        			new LinearGradient(
			        					0.0F,
			        					0.0F,
			        					100.0F,
			        					10.0F,
			        					new int[] { Color.BLACK, Color.RED, Color.YELLOW },
			        					new float[] { 0.0F, 0.5F, 0.95F },
			        					Shader.TileMode.REPEAT));
			        }
			    }));
		l.addView(new EffectsWidget(
				this, 
				"Add Blur",
	 		    new EffectsWidget.PaintEffect()
	   		    {
			        @Override 
			        public void setEffect(Paint p) 
			        { 
			        	p.setMaskFilter(new BlurMaskFilter(2, BlurMaskFilter.Blur.NORMAL));
			        }
			    }));
	}
}
