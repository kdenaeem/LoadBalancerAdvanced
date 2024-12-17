import java.net.URL;

public class ServerInstance {
    private URL backendURL;

    public ServerInstance(URL backendURL) {
        this.backendURL = backendURL;
    }

    public URL getBackendURL() {
        return backendURL;
    }
}
