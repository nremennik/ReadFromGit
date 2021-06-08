import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectToGit
{

    public InputStream getConnectionToGit(String strRepoUrl) throws IOException
    {
        URL repoUrl = new URL(strRepoUrl);
        HttpURLConnection conn = (HttpURLConnection) repoUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        InputStream isRepoList = conn.getInputStream();

        return isRepoList;
    }
}
