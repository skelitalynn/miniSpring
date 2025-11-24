package org.springframework.core.io;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlResource implements Resource{
    private final java.net.URL url;

    public UrlResource(java.net.URL url) {
        this.url = url;
    }

    @Override
    public java.io.InputStream getInputStream() throws java.io.IOException {
        URLConnection con = this.url.openConnection();
        try {
            return con.getInputStream();
        }
        catch (IOException ex) {
            // Close the HTTP connection (if applicable).
            try {
                if (con instanceof java.net.HttpURLConnection) {
                    ((java.net.HttpURLConnection) con).disconnect();
                }
            }
            catch (Throwable ex2) {
                // Ignore
            }
            throw ex;
        }
    }
}
