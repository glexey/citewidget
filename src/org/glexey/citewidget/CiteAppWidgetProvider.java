package org.glexey.citewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class CiteAppWidgetProvider extends AppWidgetProvider {
	
	public static String ACTION_APPWIDGET_CLICK = "org.glexey.citewidget.CiteAppWidgetProvider.ACTION_APPWIDGET_CLICK";
	public static final String TAG = "CiteAppWidgetProvider";
	
	private static Language[] languages;
	
	public void loadLanguages(Context context, Vars vars) {
        Log.d(TAG, "loadLanguages("+context+")");
        Resources res = context.getResources();
		TypedArray ar = res.obtainTypedArray(R.array.languages);
		int len = ar.length();
		languages = new Language[len];
		for (int i = 0; i < len; i++) {
			int res_id = ar.getResourceId(i, 0);
			languages[i] = new Language(vars, res, res_id);
		}
		ar.recycle();
	}
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate("+context+")");
        Vars vars = new Vars(context); 
        
        if (languages == null) loadLanguages(context, vars);
		final int N = appWidgetIds.length;

        // loop through all app widgets the user has enabled
        for (int i=0; i<N; i++) {
        	int widgetId = appWidgetIds[i];

        	// Initialize widget variables, if needed
    		Bundle dict = appWidgetManager.getAppWidgetOptions(widgetId);
    		if (!dict.containsKey("lang_id")) {
    			dict.putInt("lang_id", 0);
    			appWidgetManager.updateAppWidgetOptions(widgetId, dict);
    		}
        	
        	// get our view so we can edit the time
        	RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget4x1);
 
        	updateCiteView(views, appWidgetManager, widgetId);

        	// Set up click processing (do I have to do it on every update? does it matter?)
            Intent intent = new Intent(context, CiteAppWidgetProvider.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            intent.setAction(ACTION_APPWIDGET_CLICK);
            
            //Bundle test = intent.getExtras();
            //Log.d(TAG, "test getExtras: " + test.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID));
            
            // 2nd argument to PendingIntent.getBroadcast, according to the documentation is:
            //   requestCode	Private request code for the sender (currently not used)
            // However, it works to differentiate two different intents. If we do not pass
            // distinct values, we land at the same instance of the intent
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
            		context, widgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.textCite, pendingIntent);

        	// update the widget
            Log.d(TAG, "Updating c widget "+widgetId);
        	appWidgetManager.updateAppWidget(widgetId, views);
        }
	}
	
	private void updateCiteView(RemoteViews views, AppWidgetManager appWidgetManager, int widgetId) {
		// update the cite text view based on the current language ("lang_id" field in widget options)
		Bundle dict = appWidgetManager.getAppWidgetOptions(widgetId);
		int lang_id = dict.getInt("lang_id");
    	if (languages.length > 0) {
			Cite cite = languages[lang_id].getNextCite();
			views.setTextViewText(R.id.textCite, cite.text);
			views.setTextViewText(R.id.textAuthor, cite.author);
    	} else {
        	views.setTextViewText(R.id.textCite, "languages.length == " + languages.length);
			views.setTextViewText(R.id.textAuthor, "Error");
    	}
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String str = intent.getAction();
		Vars vars = new Vars(context); 
        //Log.d(TAG, "onReceive(..) :: intent=" + intent + " action=" + str);
		if (str.equals(ACTION_APPWIDGET_CLICK)) {
			int n_onReceive_calls = vars.get("n_onReceive_calls", 0) + 1;
			vars.put("n_onReceive_calls", n_onReceive_calls);
	        Log.d(TAG, "onReceive(..) :: #" + n_onReceive_calls + " :: CLICK!!");
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget4x1);
            //views.setTextViewText(R.id.textCite, "TESTING");

            // Get widget id from extra info we've put on the Intent
            Bundle extras = intent.getExtras();
            if (extras == null) Log.wtf(TAG, "paramIntent.getExtras() == null");
            int widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
            
            // update widget instance language on click
            if (languages == null) loadLanguages(context, vars);
            Bundle dict = appWidgetManager.getAppWidgetOptions(widgetId);
            if (dict == null) Log.wtf(TAG, "appWidgetManager.getAppWidgetOptions("+widgetId+") == null");
            //Log.d(TAG, "dict="+dict+" languages="+languages);
            Log.d(TAG, "dict[lang_id]="+dict.getInt("lang_id"));
            int new_lang_id = (dict.getInt("lang_id") + 1) % languages.length;
  			dict.putInt("lang_id", new_lang_id);
   			appWidgetManager.updateAppWidgetOptions(widgetId, dict);

   			// update views
            updateCiteView(views, appWidgetManager, widgetId);
            
            // update the requesting widget with new vies 
            appWidgetManager.updateAppWidget(widgetId, views);
            //Log.d(TAG, "click: updateappwidget widgetId="+widgetId);
		} else {
	        Log.d(TAG, "onReceive() :: calling super.onReceive(..)");
			super.onReceive(context, intent);
		}
	}
}
