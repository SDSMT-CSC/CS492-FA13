package com.parse.starter;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.*;

public class ParseStarterProjectActivity extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//code to setup a user and sign him up
		ParseUser user = new ParseUser();
		user.setUsername("Brian Butterfield");
		user.setPassword("iloveponies1");
		user.setEmail("BrianB@innovsys.com");
		
		user.signUpInBackground(new SignUpCallback() {
			  public void done(ParseException e) {
			    if (e == null) {
			      runParse();
			    } else {
			    	//if it can't make him, most likely there is already a Brian here. Log it in
			    	ParseUser.logInInBackground("Brian Butterfield", "iloveponies1", new LogInCallback() {
			    		  public void done(ParseUser user, ParseException e) {
			    		    if (user != null) {
			    		      runParse(); //run the rest of the code
			    		    } else {
			    		      // Signup failed. Look at the ParseException to see what happened.
			    		    }
			    		  }
			    		});
			    }
			  }
			});
	}
		
	public void runParse() {
		//make an object to save
		ParseObject gameScore = new ParseObject("GameScore");
		gameScore.put("score", -97);
		gameScore.put("playerName", "Alex Wulff");
		gameScore.put("cheatMode", true);
		gameScore.saveInBackground();
		
		//find the current user (Brian)
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null)
		{
		  Toast.makeText( this.getApplication(), currentUser.getUsername(), Toast.LENGTH_LONG).show();
		}
		
		//this is here as the toast embedded below could not find a context
		final Context context = this.getApplication();
		
		//call a cloud function
		ParseCloud.callFunctionInBackground("hello", new HashMap<String, Object>(), new FunctionCallback<String>() {
			  public void done(String result, ParseException e) {
			    if (e == null) {
			    	Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
			    }
			  }
			});
		
		//query parse for an object already stored
		ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
		query.getInBackground("1ERR6iSask", new GetCallback<ParseObject>() {
		  public void done(ParseObject retrievedScore, ParseException e) {
		    if (e == null) {
		      // object will be your game score
		    	
		      Integer score = retrievedScore.getInt("score");
		      String name = retrievedScore.getString("playerName");
		      Boolean cheat = retrievedScore.getBoolean("cheatMode");
		      
		      TextView text = (TextView) findViewById(R.id.textView1);
		      text.setText(score.toString());
		      text = (TextView) findViewById(R.id.textView2);
		      text.setText(name);
		      text = (TextView) findViewById(R.id.textView3);
		      text.setText(cheat.toString());
		      
		    } else {
		      // something went wrong
		    }
		  }
		});
		
		
		ParseAnalytics.trackAppOpened(getIntent());
	}
}
