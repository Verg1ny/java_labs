package com.company;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 /* The class implements all the method necessary gfor correct functioning ow WEB crawler
 /* Scans through all the links and finds the links of the correct form.
 /* Then add them to list, scans through new until the maximum depth of search is reached
 */
public class Crawler {
    /**
     * Class constructor
     * */
    public static void main(String[] args) {

        /* Current depth variable */
        int depth = 0;
        /* Checks if the input is correct */
        if (args.length != 2) {
            System.out.println("Введите данные в формате:  <URL> <глубина поиска>");
            System.exit(1);
        }
        else {
            try {
                depth = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException nfe) {
                System.out.println("Введите данные в формате:  <URL> <глубина поиска>");
                System.exit(1);
            }
        }
        LinkedList<URLDepthPair> pendingURLs = new LinkedList<>();
        LinkedList<URLDepthPair> processedURLs = new LinkedList<>();
        /* URLDepthPair to start with. Zero depth and input link*/
        URLDepthPair currentDepthPair = new URLDepthPair(args[0], 0);
        pendingURLs.add(currentDepthPair);
        ArrayList<String> seenURLs = new ArrayList<>();
        seenURLs.add(currentDepthPair.getURL());
        /* Iterate through pending URLs, visit each WEB site and get all links from the page */
        while (pendingURLs.size() != 0) {
            /* Get the pending URL, add it to the list of processing URLs and store its depth value*/
            URLDepthPair depthPair = pendingURLs.pop();
            processedURLs.add(depthPair);
            int pendingURLDepth = depthPair.getDepth();
            /* Get all links from the page and store them as a linkedList*/
            LinkedList<String> listOfLinks;
            listOfLinks = Crawler.getAllLinks(depthPair);
            /* If not maximum depth get all links that were not seen before to lists */
            if (pendingURLDepth < depth) {
                for (String newURL : listOfLinks) {
                    if (!seenURLs.contains(newURL)) {
                        URLDepthPair newDepthPair = new URLDepthPair(newURL, pendingURLDepth + 1);
                        pendingURLs.add(newDepthPair);
                        seenURLs.add(newURL);
                    }
                }
            }
        }
        processedURLs.stream().distinct().forEach(System.out::println);
    }
    /* A method that takes a URLDepthPair and returns a <String>LinkedList
     * Takes a link from URLDepthPair, connects to the website, finds all links
     * on the site, and adds them to a new returned LinkedList
     */
    private static LinkedList<String> getAllLinks(URLDepthPair myDepthPair) {
        LinkedList<String> URLs = new LinkedList<>();
        Socket socket;
        try {
            socket = new Socket(myDepthPair.getWebHost(), 80);
        }
        catch (UnknownHostException e1) {
            System.err.println("UnknownHostException:\n\t" + e1.getMessage());
            return URLs;
        }
        catch (IOException e2) {
            System.err.println("IOException:\n\t" + e2.getMessage());
            return URLs;
        }
        /* Sets socket timeout to 3s*/
        try {
            socket.setSoTimeout(3000);
        }
        catch (SocketException e3) {
            System.err.println("SocketException:\n\t" + e3.getMessage());
            return URLs;
        }
        /* Strings represents the docPath URL came from and the webHost */
        String docPath = myDepthPair.getDocPath();
        String webHost = myDepthPair.getWebHost();
        OutputStream outputStream;
        try {
            outputStream = socket.getOutputStream();
        }
        catch (IOException e4) {
            System.err.println("IOException:\n\t" + e4.getMessage());
            return URLs;
        }
        /* PrintWriter will clear out after every output*/
        PrintWriter printWriter = new PrintWriter(outputStream, true);
        printWriter.println("GET " + docPath + " HTTP/1.1");
        printWriter.println("Host: " + webHost);
        printWriter.println("Connection: close");
        printWriter.println();
        InputStream inputStream;
        try {
            inputStream = socket.getInputStream();
        }
        catch (IOException e5){
            System.err.println("IOException:\n\t" + e5.getMessage());
            return URLs;
        }
        /* InputStreamReader and BufferedReader to read lines from the input stream*/
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        while (true) {
            String line;
            try {
                line = bufferedReader.readLine();
            }
            catch (IOException e6) {
                System.err.println("IOException:\n\t" + e6.getMessage());
                return URLs;
            }
            if (line == null)
                break;
            int beginIndex;
            int endIndex;
            int index = 0;
            while (true) {
                /* A constant for the string indicating a link */
                String URL_INDICATOR = "<a href=\"";
                /* A constant for the string indicating the end of the webhost and beginning of docpath */
                String END_URL = "\"";
                index = line.indexOf(URL_INDICATOR, index);
                if (index == -1)
                    break;
                index += URL_INDICATOR.length();
                beginIndex = index;
                /* Search for end symbol in the current line and set to endIndex */
                endIndex = line.indexOf(END_URL, index);
                index = endIndex;
                if (index == -1){
                    break;
                }
                String newLink = line.substring(beginIndex, endIndex);
                if (newLink.startsWith("http")){
                    URLs.add(newLink);
                }
            }

        }
        return URLs;
    }

}
