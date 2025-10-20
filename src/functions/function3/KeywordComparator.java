package functions.function3;

import java.util.Comparator;

public class KeywordComparator implements Comparator<KeywordObject> {
	@Override
	public int compare(KeywordObject key1, KeywordObject key2) {
		Integer rank1 = Integer.valueOf(key1.getTimeAppears());
		Integer rank2 = Integer.valueOf(key2.getTimeAppears());
		
		return rank2.compareTo(rank1);
	}
}
