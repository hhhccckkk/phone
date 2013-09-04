package com.hck.phonelistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Brodcast extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
            Intent intent2=new Intent(context,PhoneServer.class);
            context.startService(intent2);
            Log.i("hck", "onReceive onReceive");
	}
	

}
