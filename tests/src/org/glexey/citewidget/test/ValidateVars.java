package org.glexey.citewidget.test;

import org.glexey.citewidget.Vars;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;

public class ValidateVars extends AndroidTestCase {

	Context ctx;
	Vars vars;
	
	protected void setUp() throws Exception {
		
		ctx = getContext(); // context of the app under test
		
		// Clear all preferences
		SharedPreferences prefs = ctx.getSharedPreferences(Vars.pref_key, Context.MODE_PRIVATE);
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
