package escuelaing.Servidor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args){
        escuelaing.Servidor.Reto.HttpServer Server = new escuelaing.Servidor.Reto.HttpServer();
        Server.start();
    }
}
