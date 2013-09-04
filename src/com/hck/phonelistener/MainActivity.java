package com.hck.phonelistener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends Activity {
   private EditText editor;
   private SharedPreferences preferences;
   private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		preferences = this.getSharedPreferences(Context.ACTIVITY_SERVICE,
				Context.MODE_APPEND);
		editor=(EditText) findViewById(R.id.edi);
		intent=new Intent();
		intent.setClass(this, PhoneServer.class);
		this.stopService(intent);
		if (PhoneServer.phoneServer!=null) {
			PhoneServer.phoneServer.stopSelf();
		}
	}

	public void save(View view)
	{
		Editor e=preferences.edit();
		e.putString("phone", editor.getText().toString());
		e.commit();
		startService(intent);
		this.finish();
	}

}
