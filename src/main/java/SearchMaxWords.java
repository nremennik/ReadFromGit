import java.util.*;

public class SearchMaxWords
{
    List<Map.Entry<String, Integer>> sortedWords = new ArrayList();


    public void getMaxCountWords(HashMap<String, Integer> words, int count)
    {

        words.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(sortedWords::add);

        for(int i = 0;i<count;i++)
        {
            System.out.println("Word "+sortedWords.get(sortedWords.size()-1-i));
        }

    }

}
