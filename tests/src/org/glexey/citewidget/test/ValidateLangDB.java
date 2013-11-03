package org.glexey.citewidget.test;

import org.glexey.citewidget.Cite;
import org.glexey.citewidget.LangDB;
import org.glexey.citewidget.LangDB.LangDBException;

import android.content.Context;
import android.content.res.Resources;
import android.test.InstrumentationTestCase;

public class ValidateLangDB extends InstrumentationTestCase {

	private LangDB db_ru;
	private Context tst_ctx;
	
	protected void setUp() throws Exception {
		
		// context of the AndroidTestCase
		tst_ctx = getInstrumentation().getContext();

		db_ru = new LangDB(tst_ctx, "ru_test.db");
		
		super.setUp();
	}

	protected void tearDown() throws Exception {
		db_ru.deleteDB();
		super.tearDown();
	}
	
	public void testDeleteCreateExists() throws LangDBException {
		db_ru.deleteDB();
		assertFalse(db_ru.dbExists());
		db_ru.createDB();
		assertTrue(db_ru.dbExists());
		assertEquals(0, db_ru.length());
	}
	
	public void testBasicAppend() throws LangDBException {
		db_ru.deleteDB();
		db_ru.createDB();
		Cite c0 = new Cite("Quote 0|Author 0|Comment 0");
		db_ru.append(c0);
		assertEquals(1, db_ru.length());
	}
	
	public void testBasicGet() throws LangDBException {
		db_ru.deleteDB();
		db_ru.createDB();
		Cite c0 = new Cite("Quote 0|Author 0|Comment 0");
		db_ru.append(c0);
		Cite c1 = db_ru.get(0);
		assertTrue(c0.equals(c1));
	}
	
	public void testUpdateFromResource() throws LangDBException {
		db_ru.deleteDB();
		db_ru.createDB();
        Resources res = tst_ctx.getResources();
		db_ru.updateFromResource(R.array.CiteArrRU);
		// Check that the correct number of elements were read from the resource
        String[] quotes = res.getStringArray(R.array.CiteArrRU);
        assertEquals(quotes.length, db_ru.length());
	}

	public void testAppendShiftPop() throws LangDBException {
		db_ru.deleteDB();
		db_ru.createDB();
		Cite c0 = new Cite("Quote 0|Author 0|Comment 0");
		Cite c1 = new Cite("Quote 1|Author 1");
		Cite c2 = new Cite("Quote 2");
		Cite c3 = new Cite("Quote 3");
		long l = 0;
		db_ru.append(c0);
		assertEquals(++l, db_ru.length());
		db_ru.append(c1);
		assertEquals(++l, db_ru.length());
		db_ru.append(c2);
		assertEquals(++l, db_ru.length());
		db_ru.append(c3);
		assertEquals(++l, db_ru.length());
		
		assertTrue(c0.equals(db_ru.get(0)));
		assertTrue(c1.equals(db_ru.get(1)));
		assertTrue(c2.equals(db_ru.get(2)));
		assertTrue(c3.equals(db_ru.get(3)));
		
		Cite c0p = db_ru.shift();
		assertEquals(--l, db_ru.length());
		assertTrue(c0.equals(c0p));

		Cite c1p = db_ru.shift();
		assertEquals(--l, db_ru.length());
		assertTrue(c1.equals(c1p));

		Cite c3p = db_ru.pop();
		assertEquals(--l, db_ru.length());
		assertTrue(c3.equals(c3p));
		
	}
	
}
