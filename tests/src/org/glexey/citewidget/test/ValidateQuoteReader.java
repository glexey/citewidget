/**
 * 
 */
package org.glexey.citewidget.test;

import org.glexey.citewidget.Cite;
import org.glexey.citewidget.QuoteReader;

import android.content.Context;
import android.test.InstrumentationTestCase;
//import android.test.RenamingDelegatingContext;

/**
 * @author aagaidiu
 *
 */
public class ValidateQuoteReader extends InstrumentationTestCase {

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

	// The main functionality of QuoteReader class is to give us quotes
	// let's test that it always gives us some quote
	public void testGetNextQuoteBasic() {
		QuoteReader reader = new QuoteReader(tst_ctx);
		Cite cite = reader.GetNextQuote();
		assertNotNull(cite);
	}
}