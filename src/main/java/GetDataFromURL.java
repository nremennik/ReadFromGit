import java.io.IOException;
import java.io.InputStream;
import java.util.Set;


public class GetDataFromURL
{
    public static void main(String[] args)
    {
        final String readMeUrl ="https://raw.githubusercontent.com/";
        String readMe="README.md";
        String defaultUser = "spotify";
        String user = defaultUser;
        if (args.length > 0)
        {
            user = args[0];
        }
        String strRepoUrl = "https://api.github.com/users/" + user + "/repos";

        ConnectToGit connectToGit = new ConnectToGit();
        InputStream inputStream = null;
        try
        {
            inputStream = connectToGit.getConnectionToGit(strRepoUrl);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        ParserGit parserGit =new ParserGit();
        try
        {
            parserGit.getParserJson(inputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Set<String> urlForSearch = parserGit.getUrlGit();
       // urlForSearch.forEach((value)->System.out.println(value));

        for(String s: urlForSearch)
        {
            try
            {
                inputStream =connectToGit.getConnectionToGit(readMeUrl+user+s+readMe);
            }
            catch (IOException e)
            {
                // System.out.println("No file README.md");

                break;
            }
            try
            {
                parserGit.getParserText(inputStream);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }


        SearchMaxWords printWords = new SearchMaxWords();
        printWords.getMaxCountWords(parserGit.getWords(), 3);
    }
}


