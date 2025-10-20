package functions.function1;

import java.util.Comparator;

import data.blogdata.BlogObject;

public class BlogComparatorByDate implements Comparator<BlogObject> {
	@Override
	public int compare(BlogObject blog1, BlogObject blog2) {
		return blog1.getTime().compareTo(blog2.getTime());
	}
}
