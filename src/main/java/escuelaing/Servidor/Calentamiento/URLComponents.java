/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escuelaing.Servidor.Calentamiento;

/**
 *
 * @author diego
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

 

public class URLComponents {

 

    public static void main(String[] args){
       ScanURL("http://campusvirtual.escuelaing.edu.co/moodle/mod/assign/view.php?id=34731");
    }
    
    public static void ScanURL(String site){
        URL siteUrl;
        try {
            siteUrl = new URL(site);
            System.out.println("Authority: "+siteUrl.getAuthority());
            System.out.println("File:  "+siteUrl.getFile());
            System.out.println("Host: "+siteUrl.getHost());
            System.out.println("Path: "+siteUrl.getPath());
            System.out.println("Protocol: "+siteUrl.getProtocol());
            System.out.println("Query: "+siteUrl.getQuery());
            System.out.println("Port: "+siteUrl.getPort());
            System.out.println("Ref: "+siteUrl.getRef());
        } catch (MalformedURLException ex) {
            Logger.getLogger(URLComponents.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}