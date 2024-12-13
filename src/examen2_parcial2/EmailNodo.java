/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2_parcial2;

/**
 *
 * @author dell
 */
public class EmailNodo {

    public long posicion;
    public String asunto;
    public String emisor;
    public boolean leido;
    public EmailNodo siguiente;

    public EmailNodo(long posicion, String remitente, String asunto, boolean leido) {
        this.posicion = posicion;
        this.asunto = asunto;
        this.emisor = remitente;
        this.leido = leido;
        this.siguiente = null;
    }

    @Override
    public String toString() {
        return "Nodo["
                + " posicion:" + posicion
                + " | emisor:'" + emisor + '\''
                + " | asunto:'" + asunto + '\''
                + " | leido =" + leido
                + ']';
    }
}
