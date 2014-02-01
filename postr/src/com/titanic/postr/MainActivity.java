package com.titanic.postr;

import java.io.File;
<<<<<<< HEAD
import java.io.FileInputStream;
import java.io.IOException;

=======
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import com.googlecode.leptonica.android.Pix;
>>>>>>> 37a7e9ced208d2e24f7be99194acb4b1fd589373
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
		
		//make a new posters folder if it doesn't exist
		if (!imageStorageDir.exists()){
	        if (! imageStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	        }
	    }

		//make file to store image from camera
		File imageFile = new File(imageStorageDir.getPath() + File.separator +
		        "img_001.jpg");
				
		fileUri = Uri.fromFile(imageFile);

		Log.v(imageFile.getAbsolutePath(), "saved");

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
<<<<<<< HEAD

=======
	
	//copy tess data (in Assests) to external storage
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
	
>>>>>>> 37a7e9ced208d2e24f7be99194acb4b1fd589373
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		final Context context = this;
		Log.v("here", "oAR");
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			Log.v("ds", "sfdfd");
			if (resultCode == RESULT_OK) {
<<<<<<< HEAD
				Log.v("in", "if");
				// Image captured and saved to fileUri specified in the Intent
				//Toast.makeText(this, "Image saved to:\n" +
				//		data.getData(), Toast.LENGTH_LONG).show();
=======
>>>>>>> 37a7e9ced208d2e24f7be99194acb4b1fd589373

				//get image
				String filePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/" + "posters" + "/" + "img_001.jpg";

<<<<<<< HEAD
				Log.v(filePath, "please");

				String pictureText = ""; //update this to be the result of the tesseract stuff
=======
				//make folder to store tess dict. 
				File dictDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "tessdata");
				
				if (!dictDir.exists()){
			        if (! dictDir.mkdirs()){
			            Log.d("dictDir", "failed to create directory");
			        }
			    }
				
				if (dictDir.list().length == 0){
			        copyAssets();
			    }
				
>>>>>>> 37a7e9ced208d2e24f7be99194acb4b1fd589373
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 4;

<<<<<<< HEAD
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
=======
				Bitmap bitmap2 = BitmapFactory.decodeFile(filePath, options);
				
				//diretctory to save binarized image
				String DATA_PATH = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
				
       // BINARIZE Image
				
				int height = bitmap2.getHeight();
				int width = bitmap2.getWidth();
				int[] colors = new int[width*height];
				for(int y=0; y<height; y++){
				  for(int x=0; x<width; x++){
				     int color = bitmap2.getPixel(x,y);
				     int red = color & 0xff;
				     int green = (color >> 8)&0xff;
				     int blue = (color >> 16)&0xff;
				     int avg = (red+green+blue)/3;
				     int threshold = 90;
				     if (avg > threshold){
				    	 colors[y*width+x] = 0xffffffff;
				     } else {
				    	 colors[y*width+x] = 0xff000000;
				     }
				  }
				}
				Bitmap bitmap = Bitmap.createBitmap(colors, width, height, Bitmap.Config.ARGB_8888);
				

				Matrix mtx = new Matrix();
				mtx.preRotate(90);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
											bitmap.getHeight(), mtx, false);
				
				FileOutputStream out;
				try {
					out = new FileOutputStream(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/" + "posters" + "/" + "img_002.jpg");		
					bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);/*new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "pictures/" + filename);*/
				} catch (FileNotFoundException e1) {
					Log.v("in catch", "in catch");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        
		//END BINARIZE!!
				
				try {
					ExifInterface exif = new ExifInterface(DATA_PATH);
					int exifOrientation = exif.getAttributeInt(
							ExifInterface.TAG_ORIENTATION,
							ExifInterface.ORIENTATION_NORMAL);

					Log.v(TAG, "Orient: " + exifOrientation);

					// Convert to ARGB_8888, required by tess
					bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

				} catch (IOException e) {
					Log.e(TAG, "Couldn't correct orientation: " + e.toString());
				}

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


				recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9:/\n]+", " ");
				

				recognizedText = recognizedText.trim();

				/*if ( recognizedText.length() != 0 ) {
					Log.v(TAG, "OCRED TEXT: " + recognizedText);
					Toast.makeText(this, recognizedText, Toast.LENGTH_LONG).show();
				}*/

				
				Intent intent = new Intent(this, FormActivity.class); //create the new form activity
				Log.v("jhj", "khkjh");
				intent.putExtra("TEXT", recognizedText); //store the results of the ocr to send to the form
				Log.v("hh", "hh");
				startActivity(intent); 
>>>>>>> 37a7e9ced208d2e24f7be99194acb4b1fd589373
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
