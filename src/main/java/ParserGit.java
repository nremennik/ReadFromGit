import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ParserGit
{

    private ObjectMapper mapper = new ObjectMapper();
    private Set<String> urlGit = new HashSet<>();
    private HashMap<String, Integer> words = new HashMap<>();

    public void getParserJson(InputStream isRepoList) throws IOException
    {
        JsonParser repoParser = mapper.createParser(isRepoList);
        JsonToken token = repoParser.nextToken();

        if (token != JsonToken.START_ARRAY) // Should be in the arrray
        {
            throw new RuntimeException("Root node must be array");
        }
        while (!repoParser.isClosed())
        {
            if (token == JsonToken.START_OBJECT)
            {
                //System.out.printf("----- Inside object, iterate it:\n");
                String strUrlRep ="";

                while (token != JsonToken.END_OBJECT && !repoParser.isClosed())
                {
                    if (token == JsonToken.VALUE_STRING && repoParser.getCurrentName().equalsIgnoreCase("html_url"))
                    {
                        String name = repoParser.getCurrentName();
                        String value = repoParser.getValueAsString();
                        String[] parts = value.split("/");
                        strUrlRep=parts[parts.length-1];
                        // System.out.printf("\t\tGot new token [%s] [%s]:[%s]\n", token, name, value);
                        // urlGit.add(value);
                    }
                    if(token == JsonToken.VALUE_STRING && repoParser.getCurrentName().equalsIgnoreCase("default_branch"))
                    {
                        String value = repoParser.getValueAsString();
                        urlGit.add("/"+strUrlRep+"/"+value+"/");
                    }
                    token = repoParser.nextToken();
                    if (token == JsonToken.START_OBJECT) // Skip inner objects
                    {
                        repoParser.skipChildren();
                    }
                }
                //System.out.printf("----- Object end\n");
            } else
            {
                token = repoParser.nextToken();
            }
        }
        isRepoList.close();
    }

    public void getParserText(InputStream inputStream) throws IOException
    {
        InputStreamReader isReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(isReader);

       // System.out.println("-----------------");
        String str;
        while ((str = reader.readLine()) != null)
        {
            String[] tmp = str.split("\\s+");
           for(int i=0;i<tmp.length;i++)
           {
               if (tmp[i].length() > 4)
               {
                   if (words.containsKey(tmp[i]))
                   {
                       int val = words.get(tmp[i]);
                       words.put(tmp[i], ++val);
                   } else
                   {
                       words.put(tmp[i], 1);
                   }
               }
           }

        }
        //System.out.println("-----------------");
    }

    public Set<String> getUrlGit()
    {
        return (urlGit);
    }

    public HashMap<String, Integer> getWords()
    {
        return (words);
    }

}
