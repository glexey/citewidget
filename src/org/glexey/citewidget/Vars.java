package org.glexey.citewidget;

import android.content.Context;
import android.content.SharedPreferences;

public class Vars {

	public static final String pref_key = "org.glexey.citewidget.PREFERENCE_FILE_KEY";

	private Context ctx;
	
	public Vars(Context ctx) {
		this.ctx = ctx;
	}
	
	public void put(String var_name, int new_value) {
		SharedPreferences sharedPref = ctx.getSharedPreferences(pref_key, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(var_name, new_value);
		editor.commit();
	}

	public int get(String var_name) {
		return get(var_name, 0);
	}

	public int get(String var_name, int default_value) {
		SharedPreferences sharedPref = ctx.getSharedPreferences(pref_key, Context.MODE_PRIVATE);
		return sharedPref.getInt(var_name, default_value);
	}


}
