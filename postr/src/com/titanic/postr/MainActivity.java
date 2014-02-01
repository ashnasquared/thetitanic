package com.titanic.postr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Files.FileColumns;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		Context context = this;
		
		Uri fileUri;

		//check if external storage is currently writable

		// Get the directory for the app's private pictures directory. 
		File imageStorageDir = new File(context.getExternalFilesDir(
				Environment.DIRECTORY_PICTURES), "posters");
		
		if (!imageStorageDir.exists()){
	        if (! imageStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	        }
	    }

		File imageFile = new File(imageStorageDir.getPath() + File.separator +
		        "img_001.jpg");
				
		fileUri = Uri.fromFile(imageFile);

		Log.v(imageFile.getAbsolutePath(), "saved");

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		final Context context = this;
		Log.v("here", "oAR");
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			Log.v("ds", "sfdfd");
			if (resultCode == RESULT_OK) {
				Log.v("in", "if");
				// Image captured and saved to fileUri specified in the Intent
				//Toast.makeText(this, "Image saved to:\n" +
				//		data.getData(), Toast.LENGTH_LONG).show();

				String filePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/" + "posters" + "/" + "img_001.jpg";

				Log.v(filePath, "please");

				String pictureText = ""; //update this to be the result of the tesseract stuff
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 4;

				Bitmap bitmap = BitmapFactory.decodeFile(filePath, options );
				TessBaseAPI baseApi = new TessBaseAPI();
				//DATA_PATH = Path to the storage
				// lang = for which the language data exists, usually "eng"
				
				AssetManager assetManager = getAssets();
        		String[] files;
        		String DATA_PATH = "";
        		try {
        			Log.v("goood","goooood");
        			files = assetManager.list("tessdata");
        			DATA_PATH = files[8];
        			Log.v(files[0], "files");

        			Log.v(files[8], "files8");
        		} catch (IOException e) {
        			Log.v("bad", "baaaad");
        		}
        		baseApi.init(DATA_PATH, "eng");
        		// Eg. baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata", "eng");
        		baseApi.setImage(bitmap);
        		String recognizedText = baseApi.getUTF8Text();
        		baseApi.end();
        		//System.out.println(recognizedText);

        		Log.v(recognizedText, "output");


				 
				Log.v(pictureText, "pictureText");
				Intent intent = new Intent(context, FormActivity.class); //create the new form activity
				Log.v("emma", "momo");
				intent.putExtra("TEXT", pictureText); //store the results of the ocr to send to the form
				Log.v("emma", "bobo");
				startActivity(intent); 
				Log.v("emma", "mshdsdkhs");
			} else if (resultCode == RESULT_CANCELED) {
				Log.v("sfdfs", "fsdaa");
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
				Log.v("dfad", "asfddf");
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
