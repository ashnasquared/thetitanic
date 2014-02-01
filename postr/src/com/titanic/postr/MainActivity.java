package com.titanic.postr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import com.googlecode.leptonica.android.Pix;
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

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		final Context context = this;
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				//get image
				String filePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/" + "posters" + "/" + "img_001.jpg";

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
				
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 4;
				String TAG = "OCR";

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
				     int threshold = 75;
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

				Log.v(TAG, "OCRED TEXT: " + recognizedText);


				recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9:/]+", " ");
				

				recognizedText = recognizedText.trim();

				if ( recognizedText.length() != 0 ) {
					Log.v(TAG, recognizedText);
					Toast.makeText(this, recognizedText, Toast.LENGTH_LONG).show();
				}

				
				Intent intent = new Intent(context, FormActivity.class); //create the new form activity
				intent.putExtra("TEXT", recognizedText); //store the results of the ocr to send to the form
				//startActivity(intent); 
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
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
