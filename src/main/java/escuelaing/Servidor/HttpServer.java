package escuelaing.Servidor;

import escuelaing.Servidor.Reto.*;
import escuelaing.Servidor.LittleSpark.*;
import escuelaing.Servidor.LittleSpark.Request;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author dnielben
 */

public class HttpServer {

    private int port = 36000;
    private boolean running = false;

    public HttpServer() {
    }

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            new Integer(System.getenv("PORT"));
            ServerSocket serverSocket = null;

            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                System.err.println("Could not listen on port: " + port);
                System.exit(1);
            }

            running = true;
            while (running) {
                try {
                    Socket clientSocket = null;
                    try {
                        System.out.println("Listo para recibir en puerto 36000 ...");
                        clientSocket = serverSocket.accept();
                    } catch (IOException e) {
                        System.err.println("Accept failed.");
                        System.exit(1);
                    }

                    processRequest(clientSocket);

                    clientSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        Map<String, String> request = new HashMap<>();
        boolean requestLineReady = false;
        while ((inputLine = in.readLine()) != null) {
            if (!requestLineReady) {
                request.put("requestLine", inputLine);
                requestLineReady = true;
            } else {
                String[] entry = createEntry(inputLine);
                if (entry.length > 1) {
                    request.put(entry[0], entry[1]);
                }
            }
            if (!in.ready()) {
                break;
            }
        }
        Request req = new Request(request.get("requestLine"));

        System.out.println("RequestLine: " + req);

        createResponse(req, new PrintWriter(
                clientSocket.getOutputStream(), true), clientSocket);
        in.close();
    }

    private String[] createEntry(String rawEntry) {
        return rawEntry.split(":");
    }

    private void createResponse(Request req, PrintWriter out,Socket socket) {
        String outputLine = testResponse();
        URI theuri = req.getTheuri();
        if (theuri.getPath().startsWith("/Apps")) {
        }
        else if(theuri.getPath().contains(".png")){ 
            try {
                getImage(theuri.getPath(), socket, out);
            } catch (IOException ex) {
                Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            getStaticResource(theuri.getPath(), out);
        }
        out.close();
    }
    
    
    public void getImage(String path,Socket socket, PrintWriter out) throws IOException{
        BufferedImage image = ImageIO.read(new  File ("task_2/"+path));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        byte[] imageBytes = bos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String imageString = encoder.encode(imageBytes);
        bos.close();
        System.out.print(imageString);
        //ImageIO.write(image, "PNG", socket.getOutputStream());
        String header = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n";
        out.println(header);
        String outputLine = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>\""+path+"\"</title>"
                + "</head>"
                + "<body>"
                + "<img src=\"data:image/png;base64,"+imageString+"\"width=512 height=512>"
                + "</body>"
                + "</html>";
        out.println(outputLine);
        
        System.out.println("Image send");
    }
    
    
    private String testResponse() {
        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Title of the document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>Mi propio mensaje</h1>\n"
                + "</body>\n"
                + "</html>\n";
        return outputLine;
    }

    private void getStaticResource(String path, PrintWriter out) {
        Path file = Paths.get("task_2" + path);
        try (InputStream in = Files.newInputStream(file);
                BufferedReader reader
                = new BufferedReader(new InputStreamReader(in))) {
            String header = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n";
            out.println(header);
            String line = null; 
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}