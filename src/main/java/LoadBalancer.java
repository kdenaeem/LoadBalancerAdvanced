import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoadBalancer {
    private List<ServerInstance> instancesList = new ArrayList<>();
    public Random random = new Random();

    public LoadBalancer() {

    }

    public void sendRequest(String method, InputStream stream) throws IOException {
        var instance = retrieveServerInstance();
        var connection = (HttpURLConnection) instance.getBackendURL().openConnection();
//        set do output and input stream to
        connection.setRequestMethod(method);

    }


    public synchronized ServerInstance retrieveServerInstance() {
//        if you can remove from the list, make a copy and take a size and work on a copy
//        using synchronised on add/remove instances along with retrieving
//        int random = this.random.nextInt(instancesList.size());
//        return instancesList.get(random);
        return null;
    }

    public boolean containsInstance(URL url) {
        for (ServerInstance instance : instancesList) {
            if (instance.getBackendURL().equals(url)) {
                return true;
            }
        }

        return false;
    }

    public synchronized void  addServerInstance(URL url) {
        if (containsInstance(url)) {
            throw new IllegalArgumentException("URL already exists");
        }
        instancesList.add(new ServerInstance(url));
    }
}
