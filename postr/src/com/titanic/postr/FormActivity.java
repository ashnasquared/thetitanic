package com.titanic.postr;

import android.app.Activity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.regex.*;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;


public class FormActivity extends Activity implements View.OnClickListener {
	EditText titleField;
	EditText endField;
	EditText startField;
	EditText locField;	
	Button cbutton;
	Button sbutton;
	Button pbutton;


	public void onCreate(Bundle savedInstanceState) {
		Log.v("v","v");
		super.onCreate(savedInstanceState);
		Log.v("a","a");
		setContentView(R.layout.form_layout);

		endField = (EditText) findViewById(R.id.ShowEndDate);
		locField = (EditText) findViewById(R.id.ShowLocation);
		startField = (EditText) findViewById(R.id.ShowStartDate);
		titleField = (EditText) findViewById(R.id.EditEventName);

		cbutton = (Button) findViewById(R.id.Cancel);
		sbutton = (Button) findViewById(R.id.Submit);
		pbutton = (Button) findViewById(R.id.BackPic);

		Log.v("c","c");
		Intent intent = getIntent(); //should get the intent that was used to call this activity
		Log.v("d","d");
		String picText = intent.getStringExtra("TEXT"); //gets the text string that was sent to this activity (contents of image)
		Log.v("e","e");
		try {
			String [] details = parse(picText);
			startField.setText(details[0]);
			endField.setText(details[1]);
			locField.setText(details[2]);
			titleField.setText(details[3]);



			sbutton.setOnClickListener(this);
			cbutton.setOnClickListener(this);

		} catch (Exception e) {
			Log.v("in the catch", "in the catch");
			Log.v("f","f");
			Toast.makeText(this, "Text not recognized. Please try again", Toast.LENGTH_LONG).show();
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
		}


	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.Submit:
			Log.v("saf", "herdsae");
			Intent intent = new Intent(this, CalActivity.class);
			Log.v("gsdf", "ffe");
			String title = titleField.getText().toString();

			String location = locField.getText().toString();

			String startDate = startField.getText().toString();

			Log.v("here", "here");

			String endDate = endField.getText().toString();

			intent.putExtra("TITLE", title);
			intent.putExtra("LOC", location);
			Log.v("there", "there");
			intent.putExtra("TIME_START", startDate);
			intent.putExtra("TIME_END", endDate);
			Log.v("he", "he");
			startActivity(intent);   
			break;
		case R.id.Cancel:
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			break;

		}
	}


	//takes a string read from the poster
	//returns an array of strings
	//s[0] = start time, s[1] = end time, s[2] = location, s[3] = title
	String[] months = new String[]{"JANUARY", "FEBRUARY", "MARCH",
			"APRIL",
			"MAY", "JUNE", "JULY", "AUGUST",
			"SEPTEMBER", "OCTOBER", "NOVEMBER",
	"DECEMBER"};
	String[] abbrevs = new String[]{"JAN", "FEB", "MAR", "APR", "MAY",
			"JUN", "JUL", "AUG", "SEPT", "OCT",
			"NOV", "DEC"};
	HashMap<String, Integer> monthMap = new HashMap<String, Integer>();
	public String[] parse(String file){
		try {
			//map months to position in year
			for (int i = 0; i < 12; i++){
				monthMap.put(months[i], i+1);
				monthMap.put(abbrevs[i], i+1);
			}

			String[] details = new String[4];
			String[] lines = file.split("\n");

			//find event title (first line of poster)
			details[3] = lines[0];

			//find date by searching for month names
			int lastNumLine = -1;
			String month = "";
			String monthDigits = "";
			int i = 0;
			for (String line : lines){
				String digits = line.replaceAll("\\D+","");
				String digitsWSpace = line.replaceAll("\\D+"," ");
				if (digits.length() > 0){ //only check lines with numbers
					lastNumLine = i;
					for (String m : months){
						if (line.contains(m)){
							month = m;
							monthDigits = digitsWSpace;
						}
					}
					for (String m : abbrevs){
						if (line.contains(m)){
							month = m;
							monthDigits = digitsWSpace;
						}
					}
				}
				i++;
			}
			String[] dayYear = monthDigits.trim().split(" ");
			String day = dayYear[0];
			//System.out.println(monthDigits+" - "+day);
			if (day.length() == 1) day = "0"+day;
			//year defaults to 2014 if not given, fix this eventually
			//BUG: if the format is month day time, then the time is interpreted
			//as a year
			String year = dayYear.length > 1 ? dayYear[1] : "2014";
			String monthNum = Integer.toString(monthMap.get(month));
			if (monthNum.length() == 1) monthNum = "0"+monthNum;

			//set location based on line after last containing numbers
			String location = "No location found"; //changed if there's a location
			if (lastNumLine < lines.length-1 && lastNumLine >= 0){
				location = lines[lastNumLine+1];
			}
			details[2] = location;	

			//find time by searching for regex #:# or #AM or #PM
			//String timeRegex = "[0-9]{1,2}:[0-9]{2}' '*[AP]M";
			//i think this one works, but it should probably be tested
			String timeRegex = "\\d\\d?[(:\\d\\d)( *[AP]M)(:\\d\\d *[AP]M)]";
			Pattern pattern = Pattern.compile(timeRegex);
			Matcher matcher = pattern.matcher(file);
			String startTime;
			String[] startHourMin;
			/*if (matcher.find()){
	      startTime = matcher.group();
	      System.out.println("found time "+startTime);
	      //assumes all times are pm right now
	      startHourMin = startTime.replaceAll("\\D"," ").split(" ");
	      //startHourMin = new String[]{"12", "00"};
	      } else {
	      System.out.println("no time found");*/
			startTime = "12 00";
			startHourMin = new String[]{"12", "00"};
			//}
			String endTime;
			String[] endHourMin;
			/*if (matcher.find()){
	      endTime = matcher.group();
	      endHourMin = startTime.replaceAll("\\D"," ").split(" ");
	      //endHourMin = new String[]{"12", "00"};
	      } else {*/
			endHourMin =new String[]{(Integer.parseInt(startHourMin[0])+1)+"",
					startHourMin[1]};
			//}

			//combine date and time into right format
			Calendar start = Calendar.getInstance(TimeZone.getDefault());
			//System.out.println(year+" "+monthNum+" "+day+" "+startHourMin[0]+" "+startHourMin[1]);
			start.set(Integer.parseInt(year), Integer.parseInt(monthNum),
					Integer.parseInt(day), Integer.parseInt(startHourMin[0]),
					Integer.parseInt(startHourMin[1]));
			Calendar end = Calendar.getInstance(TimeZone.getDefault());
			end.set(Integer.parseInt(year), Integer.parseInt(monthNum),
					Integer.parseInt(day), Integer.parseInt(endHourMin[0]),
					Integer.parseInt(endHourMin[1]));
			String startTimeFinal = Long.toString(start.getTimeInMillis());
			String endTimeFinal = Long.toString(end.getTimeInMillis());
			details[0] = startTimeFinal;
			details[1] = endTimeFinal;

			return details;
		} catch (Exception e){
			Log.v("in catch", "in catch");
			//Intent i = new Intent(this, MainActivity.class);
			//startActivity(i);
		}
		return null;
	}
<<<<<<< HEAD
	
	public void dBSave(){
		final Context context = this;
		
		cbutton = (Button)findViewById(R.id.DBSave);
 
		cbutton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, DbSave.class);
                            startActivity(intent);   
 
			}
 
		});
		
	}
=======

>>>>>>> 37a7e9ced208d2e24f7be99194acb4b1fd589373
}


