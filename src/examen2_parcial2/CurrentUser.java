/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2_parcial2;

/**
 *
 * @author Laura Sabillon
 */
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class CurrentUser {

    public String username;
    public RandomAccessFile archivo;
    public EmailNodo inicio;

    public CurrentUser(String username) {
        this.username = username;

        try {
            File directory = new File("Mails");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            archivo = new RandomAccessFile("Mails/" + username + "_emails.eml", "rw");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        inicio = null;
    }

    public boolean isEmpty() {
        return inicio == null;
    }

    public void addNodo(EmailNodo email) {
        if (email == null) {
            return;
        }

        if (isEmpty()) {
            inicio = email;
        } else {
            EmailNodo temp = inicio;

            while (temp.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = email;
        }
    }

    public void inbox(JList<String> list) throws IOException {
        if (isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay correos.");
            return;
        }

        DefaultListModel<String> model = new DefaultListModel<>();
        EmailNodo temp = inicio;
        int contador = 1;

        while (temp != null) {
            String item = contador + " - " + temp.emisor + " - " + temp.asunto + " - " + (temp.leido ? "Leído" : "No leído");
            model.addElement(item);
            temp = temp.siguiente;
            contador++;
        }

        list.setModel(model);

        JOptionPane.showMessageDialog(null, "Total de correos: " + (contador - 1));
    }

    public long gotEmail(String remitente, String asunto, String contenido, CurrentUser recipient) throws IOException {
        if (recipient == null) {
            throw new IllegalArgumentException("Remitente no puede ser nulo!");
        }

        RandomAccessFile recipientFile = new RandomAccessFile("Mails/" + recipient.username + "_emails.eml", "rw");
        recipientFile.seek(recipientFile.length());

        long posicion = recipientFile.getFilePointer();
        System.out.println("Writing email at position: " + posicion);

        recipientFile.writeLong(posicion);
        recipientFile.writeUTF(remitente);
        recipientFile.writeUTF(asunto);
        recipientFile.writeBoolean(false);
        recipientFile.writeUTF(contenido);
        recipientFile.writeUTF(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        EmailNodo nuevo = new EmailNodo(posicion, remitente, asunto, false);
        recipient.addNodo(nuevo);

        recipientFile.close();

        return posicion;
    }

    public void loadFromFile() throws IOException {
        archivo.seek(0);
        inicio = null;

        File file = new File("Mails/" + username + "_emails.eml");
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        System.out.println("File length: " + archivo.length());

        if (archivo.length() == 0) {
            System.out.println("The file is empty.");
            return;
        }

        while (archivo.getFilePointer() < archivo.length()) {
            try {
                long posicion = archivo.readLong();
                String remitente = archivo.readUTF();
                String asunto = archivo.readUTF();
                boolean leido = archivo.readBoolean();
                String contenido = archivo.readUTF();
                String fecha = archivo.readUTF();

                System.out.println("Loaded email: " + remitente + " | " + asunto);

                EmailNodo nuevo = new EmailNodo(posicion, remitente, asunto, leido);
                addNodo(nuevo);
            } catch (EOFException e) {
                System.out.println("Reached end of file while loading emails.");
                break;
            } catch (IOException e) {
                System.out.println("IOException while loading emails: " + e.getMessage());
                break;
            }
        }
    }

    public void readEmail(int posicion) throws IOException {
        if (posicion < 1) {
            throw new NoSuchElementException("Posición inválida.");
        }

        EmailNodo temp = inicio;
        int contador = 1;
        while (temp != null && contador < posicion) {
            temp = temp.siguiente;
            contador++;
        }

        if (temp == null) {
            throw new NoSuchElementException("No existe un email en la posición " + posicion);
        }

        archivo.seek(temp.posicion);
        archivo.readLong();
        String remitente = archivo.readUTF();
        String asunto = archivo.readUTF();
        archivo.writeBoolean(true);

        String contenido = archivo.readUTF();
        String fecha = archivo.readUTF();

        System.out.println("Reading email: " + remitente + " | " + asunto);

        String mensaje = "De: " + remitente
                + "\nAsunto: " + asunto
                + "\nContenido: " + contenido
                + "\nFecha: " + fecha;

        JOptionPane.showMessageDialog(null, mensaje, "Detalles del Email", JOptionPane.INFORMATION_MESSAGE);
        temp.leido = true;
    }

}
