package com.titanic.postr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.os.DropBoxManager.Entry;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;


public class DbSave extends android.app.Activity {
	
	final static private String APP_KEY = "vhtv1td6jne8eqj";
	final static private String APP_SECRET = "7dvjrjyp0f3d77e";
	final static private AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
	
	private DropboxAPI<AndroidAuthSession> mDBApi;
	
	public DbSave(){
		
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);
		mDBApi.getSession().startOAuth2Authentication(this);
	}
	
	protected void onResume() {
	    super.onResume();

	    if (mDBApi.getSession().authenticationSuccessful()) {
	        try {
	            // Required to complete auth, sets the access token on the session
	            mDBApi.getSession().finishAuthentication();

	            String accessToken = mDBApi.getSession().getOAuth2AccessToken();
	        } catch (IllegalStateException e) {
	            Log.i("DbAuthLog", "Error authenticating", e);
	        }
	    }
	}
	
	public void saveFile(){
		File file = new File("working-draft.txt");
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			DropboxAPI.Entry response = mDBApi.putFile("/magnum-opus.txt", inputStream, file.length(), null, null); 
			Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
