package com.titanic.postr;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

public class CalActivity extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_layout);
		Intent intent = getIntent(); //should get the intent that was used to call this activity
		String eventTitle = intent.getStringExtra("TITLE"); //get the data about the activity to be added to the calendar
		String eventLocation = intent.getStringExtra("LOC");
		String startTime = intent.getStringExtra("TIME_START");
		String endTime = intent.getStringExtra("TIME_END");
		
		Intent calIntent = new Intent(Intent.ACTION_INSERT); //create an intent to store the event in the calendar

		//I don't think we need this line, but if this isn't working it's a possible cause
		//intent.setData(CalendarContract.Events.CONTENT_URI);
		calIntent.setType("vnd.android.cursor.item/event");
		calIntent.putExtra(Events.TITLE, eventTitle);
		calIntent.putExtra(Events.EVENT_LOCATION, eventLocation);

		//need to decide what format the date will be sent in, and how to parse it
		GregorianCalendar calDate = new GregorianCalendar(2012, 10, 02);
		intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
		  calDate.getTimeInMillis());
		intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
		  calDate.getTimeInMillis());
		startActivity(intent);
		
		//I'm not sure if this will open the calendar or just add the event.
	}

}
