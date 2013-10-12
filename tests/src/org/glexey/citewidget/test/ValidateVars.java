package org.glexey.citewidget.test;

import org.glexey.citewidget.Vars;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;

public class ValidateVars extends AndroidTestCase {

	private static final String pref_key = "org.glexey.citewidget.PREFERENCE_FILE_KEY";

	Context ctx;
	Vars vars;
	
	protected void setUp() throws Exception {
		
		ctx = getContext(); // context of test case, not the app under test
		                    // it's just easier to use
		
		// Clear all preferences
		SharedPreferences prefs = ctx.getSharedPreferences(pref_key, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();

		Vars temp_vars = new Vars(ctx);
		temp_vars.put("var35", 35);
		temp_vars = null;
		
		// Object under test
		vars = new Vars(ctx);
				
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testUninitializedVarGetsDefaultOrZero() {
		// W/o providing default value, get() method should return 0
		assertEquals(0, vars.get("uninitialized_1"));
		
		// When provided default value, get() sould return it
		assertEquals(47, vars.get("uninitialized_2", 47));
	}
	
	public void testExistingVar() {
		assertEquals(35, vars.get("var35"));
		assertEquals(35, vars.get("var35", 47));
	}
}
