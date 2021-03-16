package com.company;
import java.net.*;

/* A class representing <URL, depth> pairs for our Crawler */
public class URLDepthPair {



    /* Fields to represent the current URL and current depth */
    private final int currentDepth;
    private final String currentURL;
    /* A constructor that sets the input to the current URL and depth*/
    public URLDepthPair(String URL, int depth) {
        currentDepth = depth;
        currentURL = URL;
    }
    /* A method that returns the current URL */
    public String getURL() {
        return currentURL;
    }
    /* A method that returns the current depth */
    public int getDepth() {
        return currentDepth;
    }
    /* A method that the current URL and current depth in string format */
    public String toString() {
        String stringDepth = Integer.toString(currentDepth);
        return stringDepth + '\t' + currentURL;
    }
    /* A method that returns the docPath of the current URL */
    public String getDocPath() {
        try {
            URL url = new URL(currentURL);
            return url.getPath();
        }
        catch (MalformedURLException e) {
            System.err.println("MalformedURLException:\n\t" + e.getMessage());
            return null;
        }
    }
    /*  A method that returns the webHost of the current URL */
    public String getWebHost() {
        try {
            URL url = new URL(currentURL);
            return url.getHost();
        }
        catch (MalformedURLException exception) {
            System.err.println("MalformedURLException:\n\t" + exception.getMessage());
            return null;
        }
    }


}
