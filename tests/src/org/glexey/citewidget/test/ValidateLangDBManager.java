/**
 * 
 */
package org.glexey.citewidget.test;

import java.util.ArrayList;

import org.glexey.citewidget.Cite;
import org.glexey.citewidget.LangDBManager;

import android.content.Context;
import android.test.InstrumentationTestCase;
//import android.test.RenamingDelegatingContext;

/**
 * @author aagaidiu
 *
 */
public class ValidateLangDBManager extends InstrumentationTestCase {

	private Context tst_ctx;
	//private RenamingDelegatingContext target_ctx;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		
		// context of the AndroidTestCase
		tst_ctx = getInstrumentation().getContext();

		// For ******** testing, have to use the context of target application,
		// otherwise file creation will fail due to lack of access.
		// Use renaming context not to accidentally corrupt the application files.
		//target_ctx = new RenamingDelegatingContext(getInstrumentation().getTargetContext(), "test_");

		super.setUp();
	}

	/* (non-Javadoc)
	 * @see android.test.InstrumentationTestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	// The main functionality of LangDBManager class is to give us quotes
	// let's test that it always gives us some quote, even if a stub
	public void testGetNextQuoteBasic() {
		LangDBManager reader = new LangDBManager(tst_ctx, R.array.languages);
		Cite cite = reader.getNextQuote();
		assertNotNull(cite);
	}
	
	// LangDBManager should get the list of languages from resources upon
	// constructing, from string array 'languages'
	public void testLanguagesArrayConstruction() {
		LangDBManager reader = new LangDBManager(tst_ctx, R.array.languages);
		String[] langs = reader.getlanguageList();
		assertNotNull(langs);
		assertTrue(langs.length == 3);
		assertTrue(langs[0].equals("ru"));
		assertTrue(langs[1].equals("en"));
		assertTrue(langs[2].equals("es"));
	}
	
	public void testReaderInitialization() {
		LangDBManager reader = new LangDBManager(tst_ctx, R.array.languages);
		// Create a set of 2 dictionaries per language, e.g.: "ru.fix", "ru.web"
		reader.initFromScratch();
		Cite cite = reader.getNextQuote();
		assertTrue(cite.equals(new Cite("Цитата номер 1|Алексей")));
	}
	
	// Add a quote to the specified dictionary
	public void testAddQuote() {
		LangDBManager reader = new LangDBManager(tst_ctx, R.array.languages);
		// Create a set of 2 dictionaries per language, e.g.: "ru.fix", "ru.web"
		reader.initFromScratch();
		Cite cite1 = new Cite("Test quote");
		reader.addQuote("ru.web", cite1);
		Cite cite2 = reader.getNextQuote("ru.web");
		assertTrue(cite1.equals(cite2));
	}
	
	public void testHistory() {
		LangDBManager reader = new LangDBManager(tst_ctx, R.array.languages);
		// Create a set of 2 dictionaries per language, e.g.: "ru.fix", "ru.web"
		reader.initFromScratch();
		Cite[] cite = new Cite[10];
		for (int i=0; i<10; i++) {
			cite[i] = reader.getNextQuote();
		}
		// test set contains 9 quotes, so 10th should return a stub
		assertTrue(cite[9].equals(LangDBManager.stubCite));
		ArrayList<Cite> hist = reader.getQuoteHistory();
		assertNotNull(hist);
		assertEquals(9, hist.size());
		for (int i=8; i>=0; i--) {
			Cite histCite = hist.get(8-i);
			assertEquals(cite[i], histCite);
		}
		// Blow up past max history size and then check history stays at max size
		for(int i=0; i<LangDBManager.historySize+10; i++)
			reader.addQuote("ru.web", new Cite("Cite" + i));
		ArrayList<Cite> maxhist = reader.getQuoteHistory();
		assertEquals(LangDBManager.historySize, maxhist.size());
	}
	
	public void testLanguageDisabling() {
		fail("testLanguageDisabling() not yet written");
	}
}
