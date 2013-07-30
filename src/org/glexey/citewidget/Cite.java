package org.glexey.citewidget;

public class Cite {
	public boolean used = false;
	public String text = "";
	public String author = "";
	public String comment = "";

	public Cite(String s) {
		super();
		// Split the string to citing text and author
		String[] tokens = s.split("\\|");
		text = tokens[0];
		if (tokens.length > 1)	author = tokens[1];
		if (tokens.length > 2) comment = tokens[2];
	}
}
