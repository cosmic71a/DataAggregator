package functions.function1;

import java.io.IOException;
import functions.function2.SearchedBlogList;
import paths.JsonFiles;


public class FuncTesting {

	public static void main(String[] args) {
		try {
			String jsonString = JsonReader.readJsonFile(JsonFiles.UTODAY);
			LoadedBlogList uto = new LoadedBlogList();
			uto.loadData(jsonString);
			
			String[] targets = {"koRea", "blOCKcHaiN"};
			SearchedBlogList sbl = new SearchedBlogList(uto.getBlogList());
			sbl.searchByKeywords(targets, -1);
			sbl.displayBlogList(0);
			
			//List<KeywordObject> keke = sUto.findTrendingKeywords("0000-00-00", "9999-99-99");
			
//			for (KeywordObject keyword : keke) {
//				System.out.println(keyword.getFoundKeyword() + " " + keyword.getTimeAppears());
//			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        

	}

}
