package functions.function1;

import java.util.Comparator;

import data.blogdata.BlogObject;

public class BlogComparatorByRank implements Comparator<BlogObject> {
	private final int compareByDate;

	public BlogComparatorByRank(int compareByDate) {
		this.compareByDate = compareByDate;
	}
	
	@Override
	public int compare(BlogObject blog1, BlogObject blog2) {
		Integer rank1 = Integer.valueOf(blog1.getRank());
		Integer rank2 = Integer.valueOf(blog2.getRank());
		
		int rankCompare = rank2.compareTo(rank1);
		int dateCompare = compareByDate * blog1.getTime().compareTo(blog2.getTime());
		
		return (rankCompare != 0) ? rankCompare : dateCompare;
	}
}
