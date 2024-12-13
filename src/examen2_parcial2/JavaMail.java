/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2_parcial2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author hp
 */
public class JavaMail {

    public RandomAccessFile ListUsers;
    public CurrentUser useract;
    public EmailNodo nodo;

    public JavaMail() {
        this.useract = null;

        try {
            File arch = new File("Mails");
            arch.mkdir();
            ListUsers = new RandomAccessFile("Mails/usuarios.eml", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//------Login & SignIn Manejo--------------------------------------------------------------------------------------------------------------------------------
    public boolean Login(String username, String pass) throws IOException {
        if (verificarLog(username, pass)) {
            useract = new CurrentUser(username);
            useract.loadFromFile();
            return true;
        }
        return false;
    }

    public CurrentUser getUserByUsername(String username) {
        File userFile = new File("Mails/" + username + "_emails.eml");

        if (userFile.exists()) {
            return new CurrentUser(username);  
        } else {
            return null; 
        }
    }

    public boolean CrearCuenta(String username, String pass) throws IOException {
        if (!ExisteUsername(username)) {
            ListUsers.seek(ListUsers.length());
            ListUsers.writeUTF(username);
            ListUsers.writeUTF(pass);
            CurrentUser UA = new CurrentUser(username);
            useract = UA;

            return true;
        }
        return false;
    }

    private boolean verificarLog(String Username, String Password) throws IOException {
        ListUsers.seek(0);
        while (ListUsers.getFilePointer() < ListUsers.length()) {
            String name = ListUsers.readUTF();
            String pass = ListUsers.readUTF();

            if (name.equals(Username) && pass.equals(Password)) {
                return true;
            }
        }
        return false;
    }

    private boolean ExisteUsername(String Username) throws IOException {
        ListUsers.seek(0);
        while (ListUsers.getFilePointer() < ListUsers.length()) {
            String name = ListUsers.readUTF();
            ListUsers.readUTF();

            if (name.equals(Username)) {
                return true;
            }
        }
        return false;
    }

//-----------------------------------------------------------------------------------------------------------------------------------------------
    public void inbox(JList<String> List, JLabel Label) throws IOException {
        String CurrentUser = useract.username + "@javamail.com";
        Label.setText(CurrentUser);
        useract.inbox(List);
    }

    public void createEmail(String remitente, String asunto, String contenido, CurrentUser recipient) throws IOException {
        if (recipient != null) {
            recipient.gotEmail(remitente, asunto, contenido, recipient);
        } else {
            throw new IOException("Recipient not found.");
        }
    }

    public void read(int pos) throws IOException {
        useract.readEmail(pos);
    }
}
