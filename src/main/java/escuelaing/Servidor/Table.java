/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escuelaing.Servidor;

/**
 *
 * @author diego
 */
public class Table {
    public String date = "";
    public String temperature = "";
    
    public Table(String date, String temperature){
        this.date = date;
        this.temperature = temperature;
    }
    
    public void Print(){
        System.out.println("|"+this.date+"|"+this.temperature+"|");
    }
}
