/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escuelaing.Servidor.LittleSpark;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this class represent the HTTP request line
 * @author dnielben
 */
public class Request {
    
    private String method;
    private String requestURI;
    private String HTTPVersion;
    private URI theuri;
    private Map<String,String> query;
    
    public Request(String requestLine){
        parseRequestLine(requestLine);
    }
    
    public void parseRequestLine(String requestLine){
        try {
            String[] components= requestLine.split("\\s");
            method = components[0];
            requestURI = components[1];
            HTTPVersion = components[2];
            setTheuri(new URI(requestURI));
            query = parseQuery(theuri.getQuery());
        } catch (URISyntaxException ex) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @return the requestURI
     */
    public String getRequestURI() {
        return requestURI;
    }

    /**
     * @return the HTTPVersion
     */
    public String getHTTPVersion() {
        return HTTPVersion;
    }
    
    public String toString(){
        return method + " " + requestURI + " " + HTTPVersion + "\n\r" +
                getTheuri() + "\n\r" +
                "Query: " + query;
    }

    /**
     * @return the theuri
     */
    public URI getTheuri() {
        return theuri;
    }

    /**
     * @param theuri the theuri to set
     */
    public void setTheuri(URI theuri) {
        this.theuri = theuri;
    }

    private Map<String, String> parseQuery(String query) {
        if( query == null) return null;
        Map<String, String> theQuery = new HashMap();
        String[] nameValuePairs = query.split("&");
        for(String nameValuePair: nameValuePairs){
            int index = nameValuePair.indexOf("=");
            if(index!=-1){
                theQuery.put(nameValuePair.substring(0, index), nameValuePair.substring(index+1));
            }
        }        
        return theQuery;
    }
    
    public String getValFromQuery(String varname){
        return query.get(varname);
    }
    
}