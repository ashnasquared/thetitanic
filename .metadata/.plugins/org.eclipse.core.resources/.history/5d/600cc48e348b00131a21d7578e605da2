package com.titanic.postr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import com.googlecode.leptonica.android.Pixa;
import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
	private void copyAssets() {
	    AssetManager assetManager = getAssets();
	    String[] files = null;
	    try {
	        files = assetManager.list("tessdata");
	    } catch (IOException e) {
	        Log.e("tag", "Failed to get asset file list.", e);
	    }
	    for(String filename : files) {
	        InputStream in = null;
	        OutputStream out = null;
	        try {
	          in = assetManager.open("tessdata/"+filename);
	          File outFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "tessdata/" + filename);
	          out = new FileOutputStream(outFile);
	          copyFile(in, out);
	          in.close();
	          in = null;
	          out.flush();
	          out.close();
	          out = null;
	        } catch(IOException e) {
	            Log.e("tag", "Failed to copy asset file: " + filename, e);
	        }       
	    }
	}
	private void copyFile(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int read;
	    while((read = in.read(buffer)) != -1){
	      out.write(buffer, 0, read);
	    }
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		final Context context = this;
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				//Toast.makeText(this, "Image saved to:\n" +
				//		data.getData(), Toast.LENGTH_LONG).show();

				String filePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/" + "posters" + "/" + "img_001.jpg";

				String pictureText = ""; //update this to be the result of the tesseract stuff
				//BitmapFactory.Options options = new BitmapFactory.Options();
			//	options.inSampleSize = 4;

				//Bitmap bitmap = BitmapFactory.decodeFile(filePath, options );
				//TessBaseAPI baseApi = new TessBaseAPI();
				//DATA_PATH = Path to the storage
				// lang = for which the language data exists, usually "eng"	
				
				
				File dictDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "tessdata");
				
				String DATA_PATH = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
				
				if (!dictDir.exists()){
			        if (! dictDir.mkdirs()){
			            Log.d("dictDir", "failed to create directory");
			        }
			    }
				
				if (dictDir.list().length == 0){
			        copyAssets();
			    }
				
				
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 4;
				String TAG = "OCR";

				Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

				try {
					ExifInterface exif = new ExifInterface(DATA_PATH);
					int exifOrientation = exif.getAttributeInt(
							ExifInterface.TAG_ORIENTATION,
							ExifInterface.ORIENTATION_NORMAL);

					Log.v(TAG, "Orient: " + exifOrientation);

					int rotate = 0;

					switch (exifOrientation) {
					case ExifInterface.ORIENTATION_ROTATE_90:
						rotate = 90;
						break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						rotate = 180;
						break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						rotate = 270;
						break;
					}

					Log.v(TAG, "Rotation: " + rotate);

					if (rotate != 0) {

						// Getting width & height of the given image.
						int w = bitmap.getWidth();
						int h = bitmap.getHeight();

						// Setting pre rotate
						Matrix mtx = new Matrix();
						mtx.preRotate(rotate);

						// Rotating Bitmap
						bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
					}

					// Convert to ARGB_8888, required by tess
					bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

				} catch (IOException e) {
					Log.e(TAG, "Couldn't correct orientation: " + e.toString());
				}

				// _image.setImageBitmap( bitmap );

				Log.v(TAG, "Before baseApi");

				TessBaseAPI baseApi = new TessBaseAPI();
				baseApi.setDebug(true);
				baseApi.init(DATA_PATH, "eng");
				baseApi.setImage(bitmap);

				String recognizedText = baseApi.getUTF8Text();

				baseApi.end();

				// You now have the text in recognizedText var, you can do anything with it.
				// We will display a stripped out trimmed alpha-numeric version of it (if lang is eng)
				// so that garbage doesn't make it to the display.

				Log.v(TAG, "OCRED TEXT: " + recognizedText);


				recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
				

				recognizedText = recognizedText.trim();

				if ( recognizedText.length() != 0 ) {
					Log.v(TAG, recognizedText);
					Toast.makeText(this, recognizedText, Toast.LENGTH_LONG).show();
					
					//_field.setText(_field.getText().toString().length() == 0 ? recognizedText : _field.getText() + " " + recognizedText);
					//_field.setSelection(_field.getText().toString().length());
				}

        		
        /*		baseApi.init(DATA_PATH, "eng");
     
        		Log.v("run?", "run");
        		
        		baseApi.setImage(BitmapFactory.decodeResource(this.getResources(),R.drawable.ear));
        		
        		//baseApi.setImage(bitmap);
        		
        	
        		         		
        		pictureText = baseApi.getUTF8Text();
        		
        		Toast.makeText(this, pictureText, Toast.LENGTH_LONG).show();
        		Log.v(pictureText, pictureText);
        		
        		byte[] d;
				try {
					d = pictureText.getBytes("ASCII");

	        		String ascii = new String(d);
	        		Log.v(ascii, ascii);
				} catch (UnsupportedEncodingException e) {
					Log.v("probs", "probs");
				}
				
				Log.v(pictureText, pictureText);
        		
        		Pixa w = baseApi.getWords();
        		
        		Pixa t = baseApi.getTextlines();
        		
        		
        		baseApi.end();

        		Log.v("ran", "rand");
				Log.v(w.toString(), w.toString());
				Log.v(t.toString(), t.toString());
			*/	
				
				Intent intent = new Intent(context, FormActivity.class); //create the new form activity
				intent.putExtra("TEXT", pictureText); //store the results of the ocr to send to the form
				//startActivity(intent); 
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
