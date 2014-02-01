package com.titanic.postr;

import java.io.File;
import java.io.FileInputStream;
<<<<<<< HEAD
=======
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
>>>>>>> 9fce9aa08555198d1d6810ed7f427992fd84506f
import java.io.IOException;

<<<<<<< HEAD
=======

import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.Pixa;
>>>>>>> 9fce9aa08555198d1d6810ed7f427992fd84506f
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

	//BINARIZE FOR REAL!
	
	
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

<<<<<<< HEAD
				String pictureText = ""; //update this to be the result of the tesseract stuff
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 4;
=======
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

				Bitmap bitmap2 = BitmapFactory.decodeFile(filePath, options);
				
       // BINARIZE HERE!!!
				
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


				// _image.setImageBitmap( bitmap );

				Log.v(TAG, "Before baseApi");
>>>>>>> 9fce9aa08555198d1d6810ed7f427992fd84506f

				Bitmap bitmap = BitmapFactory.decodeFile(filePath, options );
				TessBaseAPI baseApi = new TessBaseAPI();
<<<<<<< HEAD
				//DATA_PATH = Path to the storage
				// lang = for which the language data exists, usually "eng"
=======
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
>>>>>>> 9fce9aa08555198d1d6810ed7f427992fd84506f
				
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

<<<<<<< HEAD
        		Log.v(recognizedText, "output");


				 
				Log.v(pictureText, "pictureText");
=======
				recognizedText = recognizedText.trim();

				if ( recognizedText.length() != 0 ) {
					Log.v(TAG, recognizedText);
					Toast.makeText(this, recognizedText, Toast.LENGTH_LONG).show();
				}

				
>>>>>>> 9fce9aa08555198d1d6810ed7f427992fd84506f
				Intent intent = new Intent(context, FormActivity.class); //create the new form activity
				Log.v("emma", "momo");
				intent.putExtra("TEXT", pictureText); //store the results of the ocr to send to the form
				Log.v("emma", "bobo");
				startActivity(intent); 
				Log.v("emma", "mshdsdkhs");
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}
	}
	
	//binarizing stuff

	    /** Desired tile X dimension; actual size may vary */
	    public final static int OTSU_SIZE_X = 32;

	    /** Desired tile Y dimension; actual size may vary */
	    public final static int OTSU_SIZE_Y = 32;

	    /** Desired X smoothing value */
	    public final static int OTSU_SMOOTH_X = 2;

	    /** Desired Y smoothing value */
	    public final static int OTSU_SMOOTH_Y = 2;

	    /** Fraction of the max Otsu score, typically 0.1 */
	    public final static float OTSU_SCORE_FRACTION = 0.1f;

	    /**
	     * Performs locally-adaptive Otsu threshold binarization with default
	     * parameters.
	     *
	     * @param pixs An 8 bpp PIX source image.
	     * @return A 1 bpp thresholded PIX image.
	     */
	    public static Pix otsuAdaptiveThreshold(Pix pixs) {
	        return otsuAdaptiveThreshold(
	                pixs, OTSU_SIZE_X, OTSU_SIZE_Y, OTSU_SMOOTH_X, OTSU_SMOOTH_Y, OTSU_SCORE_FRACTION);
	    }

	    /**
	     * Performs locally-adaptive Otsu threshold binarization.
	     * <p>
	     * Notes:
	     * <ol>
	     * <li>The Otsu method finds a single global threshold for an image. This
	     * function allows a locally adapted threshold to be found for each tile
	     * into which the image is broken up.
	     * <li>The array of threshold values, one for each tile, constitutes a
	     * highly downscaled image. This array is optionally smoothed using a
	     * convolution. The full width and height of the convolution kernel are (2 *
	     * smoothX + 1) and (2 * smoothY + 1).
	     * <li>The minimum tile dimension allowed is 16. If such small tiles are
	     * used, it is recommended to use smoothing, because without smoothing, each
	     * small tile determines the splitting threshold independently. A tile that
	     * is entirely in the image bg will then hallucinate fg, resulting in a very
	     * noisy binarization. The smoothing should be large enough that no tile is
	     * only influenced by one type (fg or bg) of pixels, because it will force a
	     * split of its pixels.
	     * <li>To get a single global threshold for the entire image, use input
	     * values of sizeX and sizeY that are larger than the image. For this
	     * situation, the smoothing parameters are ignored.
	     * <li>The threshold values partition the image pixels into two classes: one
	     * whose values are less than the threshold and another whose values are
	     * greater than or equal to the threshold. This is the same use of
	     * 'threshold' as in pixThresholdToBinary().
	     * <li>The scorefract is the fraction of the maximum Otsu score, which is
	     * used to determine the range over which the histogram minimum is searched.
	     * See numaSplitDistribution() for details on the underlying method of
	     * choosing a threshold.
	     * <li>This uses enables a modified version of the Otsu criterion for
	     * splitting the distribution of pixels in each tile into a fg and bg part.
	     * The modification consists of searching for a minimum in the histogram
	     * over a range of pixel values where the Otsu score is within a defined
	     * fraction, scoreFraction, of the max score. To get the original Otsu
	     * algorithm, set scoreFraction == 0.
	     * </ol>
	     *
	     * @param pixs An 8 bpp PIX source image.
	     * @param sizeX Desired tile X dimension; actual size may vary.
	     * @param sizeY Desired tile Y dimension; actual size may vary.
	     * @param smoothX Half-width of convolution kernel applied to threshold
	     *            array: use 0 for no smoothing.
	     * @param smoothY Half-height of convolution kernel applied to threshold
	     *            array: use 0 for no smoothing.
	     * @param scoreFraction Fraction of the max Otsu score; typ. 0.1 (use 0.0
	     *            for standard Otsu).
	     * @return A 1 bpp thresholded PIX image.
	     */
	    public static Pix otsuAdaptiveThreshold(
	            Pix pixs, int sizeX, int sizeY, int smoothX, int smoothY, float scoreFraction) {
	        if (pixs == null)
	            throw new IllegalArgumentException("Source pix must be non-null");
	        if (pixs.getDepth() != 8)
	            throw new IllegalArgumentException("Source pix depth must be 8bpp");

	        int nativePix = nativeOtsuAdaptiveThreshold(
	                pixs.getNativePix(), sizeX, sizeY, smoothX, smoothY, scoreFraction);

	        if (nativePix == 0)
	            throw new RuntimeException("Failed to perform Otsu adaptive threshold on image");

	        return new Pix(nativePix);
	    }

	    // ***************
	    // * NATIVE CODE *
	    // ***************

	    private static native int nativeOtsuAdaptiveThreshold(
	            int nativePix, int sizeX, int sizeY, int smoothX, int smoothY, float scoreFract);
	    
	    
	//end binarizing stuff

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
