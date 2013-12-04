package edu.sdsmt.WornerTillma.GraphicsExample;

/*
 * Example from page 237-239 of Programming Android
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MatrixExample extends Activity
{
	private interface Transformation
	{
		void transform(Canvas canvas);
		String describe();
	}
	
	private static class TransformWidget extends View
	{
		private Paint paint;
		private Transformation transformation;
		
		public TransformWidget(Context context, Transformation xform)
		{
			super(context);
			
			this.transformation = xform;
			this.paint = new Paint();
			
			setMinimumWidth(300);
			setMinimumHeight(150);
		}
		
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
		{
			setMeasuredDimension(
					getSuggestedMinimumWidth(),
					getSuggestedMinimumHeight());
		}
		
		@Override
		protected void onDraw(Canvas canvas)
		{
			canvas.drawColor(Color.WHITE);
			this.paint.reset();
			
			// save the state of the matrix associated with the canvas
			canvas.save();
			
			// set the canvas's matrix to do the transform on anything painted to the canvas
			this.transformation.transform(canvas);
			
			this.paint.setTextSize(30);
			this.paint.setColor(Color.BLUE);
			canvas.drawText("Hello", 40, 75, this.paint);
			
			this.paint.setTextSize(36);
			this.paint.setColor(Color.RED);
			canvas.drawText("Android", 35, 105, this.paint);
		
			// restore the canvas's matrix so the next stuff isn't transformed
			canvas.restore();
			
			this.paint.setColor(Color.BLACK);
			this.paint.setStyle(Paint.Style.STROKE);
			canvas.drawRect(canvas.getClipBounds(), this.paint);
			
			this.paint.setTextSize(20);
			canvas.drawText(transformation.describe(), 5, 130, this.paint);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty_layout);
		
		LinearLayout l = (LinearLayout)findViewById(R.id.LinearLayout);
		
		// identity matrix
		l.addView(new TransformWidget(
				this, 
	 		    new Transformation()
	   		    {
			        @Override 
			        public String describe() 
			        { 
			    	    return "identity"; 
		    	    }
			      
			        @Override 
			        public void transform(Canvas canvas) { }
			    }));
		
		// rotate -30 degrees
		l.addView(new TransformWidget(
				this, 
	 		    new Transformation()
	   		    {
			        @Override 
			        public String describe() 
			        { 
			    	    return "rotate(-30)"; 
		    	    }
			      
			        @Override 
			        public void transform(Canvas canvas) 
			        { 
			        	canvas.translate(0, 30);
			    	    canvas.rotate(-30.0F);
			        }
			    }));
		
		// scale 0.5 in the x direction, 0.8 in the y direction
		l.addView(new TransformWidget(
				this, 
	 		    new Transformation()
	   		    {
			        @Override 
			        public String describe() 
			        { 
			    	    return "scale(.5, .8)"; 
		    	    }
			      
			        @Override 
			        public void transform(Canvas canvas) 
			        { 
			    	    canvas.scale(0.5F, 0.8F);
			        }
			    }));
		
		// skew 0.1 in the x direction, 0.3 in the y direction
		l.addView(new TransformWidget(
				this, 
	 		    new Transformation()
	   		    {
			        @Override 
			        public String describe() 
			        { 
			    	    return "skew(.1, .3)"; 
		    	    }
			      
			        @Override 
			        public void transform(Canvas canvas) 
			        { 
			        	canvas.translate(0, -20);
			    	    canvas.skew(0.1F, 0.3F);
			        }
			    }));
		
		// translate 40 in the x direction, -20 in the y direction
		l.addView(new TransformWidget(
				this, 
	 		    new Transformation()
	   		    {
			        @Override 
			        public String describe() 
			        { 
			    	    return "translate(30, -10)"; 
		    	    }
			      
			        @Override 
			        public void transform(Canvas canvas) 
			        { 
			    	    canvas.translate(40.0F, -20.0F);
			        }
			    }));
	}
}
