package com.hck.phonelistener;

import java.io.File;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneServer extends Service{
 private static String phoneNumBer;
 public static PhoneServer phoneServer;
 public static String phoneString;
 private File file;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("hck", "onservice onsevvvvvvvv");
		phoneServer=this;
	SharedPreferences	preferences = this.getSharedPreferences(Context.ACTIVITY_SERVICE,
				Context.MODE_APPEND);
		TelephonyManager manager=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	     manager.listen(new PhoneListeners(), PhoneStateListener.LISTEN_CALL_STATE);
	     phoneNumBer=preferences.getString("phone", "hck");
	     Log.i("hck", "oncreat :"+phoneNumBer);
	     file = new File(Environment.getExternalStorageDirectory() + "/phone/");
			if (!file.exists()) {
				file.mkdir();
			}
	    
	}
	public class PhoneListeners extends PhoneStateListener
	{
		private MediaRecorder mRecorder;
		
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				phoneString=incomingNumber;
				Log.i("hck", "phone: "+incomingNumber  +phoneNumBer);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				Log.i("hck", "电话号码："+phoneNumBer);
				Log.i("hck", "电话号码22："+phoneString);
				if (phoneNumBer.equals(phoneString)) {
					Log.i("hck", "开始录制声音");
					Log.i("hck", "=========: "+phoneString);
					Log.i("hck", "=========: "+phoneNumBer);
				File file2 = new File(file, incomingNumber+"_"+System.currentTimeMillis() + ".amr");
					mRecorder=new MediaRecorder();
					try {
						mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
						mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
						mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
						mRecorder.setOutputFile(file2.getAbsolutePath());
						mRecorder.prepare();
						mRecorder.start();
						
					} catch (Exception e) {
					}
				}
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				try {
					if (mRecorder!=null) {
						mRecorder.stop();
						mRecorder.release();
						mRecorder=null;
					}
				} catch (Exception e) {
				}
				
             break;
			default:
				break;
			}
		}
	}
	public void stop()
	{
		this.stopSelf();
	}

}
