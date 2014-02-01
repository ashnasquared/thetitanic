package com.titanic.postr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;

public class FormActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_layout);
		Intent intent = getIntent(); //should get the intent that was used to call this activity
		String picText = intent.getStringExtra("TEXT"); //gets the text string that was sent to this activity (contents of image)
	}
	
	public String getDate(String picText){
		EditText dateText = (EditText)findViewById(R.id.ShowStartDate);
		//WHAT ABOUT END DATE?????
		dateText.setText("date");
		return "date";
	}
	
	public String getWhere(String picText){
		EditText locationText = (EditText)findViewById(R.id.ShowLocation);
		locationText.setText("location");
		return "location";
	}
	
	public String getTitle(String picText){
		EditText titleText = (EditText)findViewById(R.id.EditEventName);
		titleText.setText("title");
		return "title";
	}
	
	Button cbutton;
	Button sbutton;
	Button pbutton;
	
	public void submitButton(){
		final Context context = this;
		
		cbutton = (Button)findViewById(R.id.Submit);
 
		cbutton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, CalActivity.class);
			    
			    final EditText titleField = (EditText) findViewById(R.id.EditEventName);
			    String title = titleField.getText().toString();
			    
			    final EditText locField = (EditText) findViewById(R.id.ShowLocation);
			    String location = locField.getText().toString();
			    
			    final EditText startField = (EditText) findViewById(R.id.ShowStartDate);
			    String startDate = startField.getText().toString();
			    
			    final EditText endField = (EditText) findViewById(R.id.ShowEndDate);
			    String endDate = endField.getText().toString();
			    
			    intent.putExtra("TITLE", title);
				intent.putExtra("LOC", location);
				intent.putExtra("TIME_START", startDate);
				intent.putExtra("TIME_END", endDate);
                startActivity(intent);   
 
			}
 
		});
	}
	
	public void cancelButton(){
		final Context context = this;
		
		cbutton = (Button)findViewById(R.id.Cancel);
 
		cbutton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, Cancel.class);
                            startActivity(intent);   
 
			}
 
		});
		
	}
	
	public void backToPicButton(){
		final Context context = this;
		
		cbutton = (Button)findViewById(R.id.Cancel);
 
		cbutton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, BackPic.class);
                            startActivity(intent);   
 
			}
 
		});
		
	}
}


