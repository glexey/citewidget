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
	
	public boolean equals(Object obj) {
		// if the two objects are equal in reference, they are equal
		if (this == obj) {
			return true;
		} else if (obj instanceof Cite) {
			// If the text of two quotes match, they are equal
			// Author/Comment doesn't matter in this case
			return ((Cite) obj).text.equals(text);
		}
		return false;
	}
}
