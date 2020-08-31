/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escuelaing.Servidor.Calentamiento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author diego
 */
public class CatchURL {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("input your URL:\t");
        Scanner scan = new Scanner(System.in);
        String URL = scan.nextLine();
        saveHTML(URL);
    }
    public static void saveHTML(String URL_String){
        FileWriter writer = null;

        String temp;
        try {
            URL URL_ = new URL(URL_String);
            try {
                File myObj = new File("task_2/resultado.html");
                writer = new FileWriter("task_2/resultado.html");
                BufferedReader reader = new BufferedReader(new InputStreamReader(URL_.openStream()));
                while ((temp = reader.readLine()) != null) {
                    writer.write(temp);
                }
                writer.close();
            } catch (IOException x) {
                System.err.println(x);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(CatchURL.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("File resultado.html saved at task_2 check the project directory");
    }
}

