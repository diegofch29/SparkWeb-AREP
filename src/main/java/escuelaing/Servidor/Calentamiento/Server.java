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

 public class Server {
   public static void main(String[] args) throws IOException {
   ServerSocket serverSocket = null;
   try {
    serverSocket = new ServerSocket(35000);
   } catch (IOException e) {
    System.err.println("Could not listen on port: 35000.");
    System.exit(1);
   }
   Socket clientSocket = null;
   try {
    clientSocket = serverSocket.accept();
   } catch (IOException e) {
    System.err.println("Accept failed.");
    System.exit(1);
   }
   PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
   BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
   String inputLine, outputLine;
   while ((inputLine = in .readLine()) != null) {
       if (inputLine.equals("Quit")) break;
       int input = (int) Math.pow(Integer.parseInt(inputLine),2);
       System.out.println("Mensaje: "+input);
       outputLine = "Respuesta: " + input;
    out.println(outputLine);
   }
   out.close(); in .close();
   clientSocket.close();
   serverSocket.close();
  }
 }