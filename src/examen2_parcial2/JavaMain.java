/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package examen2_parcial2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Laura Sabillon
 */
public class JavaMain extends JFrame {

    private JavaMail mail;

    public JavaMain() {
        mail = new JavaMail();
        setTitle("CORREO ELECTRONICO!");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("CORREO ELECTRONICO");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 20, 0);

        JButton crear = createButton("CREAR CUENTA");
        JButton login = createButton("LOGIN");
        JButton salir = createButton("SALIR");

        panel.add(crear, gbc);
        gbc.gridy++;
        panel.add(login, gbc);
        gbc.gridy++;
        panel.add(salir, gbc);

        panel.setBackground(Color.BLACK);

        add(panel, BorderLayout.CENTER);

        setVisible(true);

        // CREAR CUENTA 
        crear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame crearC = new JFrame("CREAR CUENTA");
                crearC.setSize(400, 300);
                crearC.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                crearC.setLocationRelativeTo(null);

                JPanel crearPanel = new JPanel();
                crearPanel.setLayout(new GridBagLayout());
                crearPanel.setBackground(Color.BLACK);

                GridBagConstraints gbcCrear = new GridBagConstraints();
                gbcCrear.gridx = 0;
                gbcCrear.gridy = GridBagConstraints.RELATIVE;
                gbcCrear.insets = new Insets(10, 10, 10, 10);
                gbcCrear.anchor = GridBagConstraints.WEST;

                JLabel usuario = new JLabel("Usuario:");
                usuario.setForeground(Color.WHITE);
                crearPanel.add(usuario, gbcCrear);

                JTextField usuarioField = new JTextField(20);
                gbcCrear.gridx = 1;
                crearPanel.add(usuarioField, gbcCrear);

                JLabel contra = new JLabel("Contraseña:");
                contra.setForeground(Color.WHITE);
                gbcCrear.gridx = 0;
                crearPanel.add(contra, gbcCrear);

                JPasswordField password = new JPasswordField(20);
                gbcCrear.gridx = 1;
                crearPanel.add(password, gbcCrear);

                JLabel confirm = new JLabel("Confirmar Contraseña:");
                confirm.setForeground(Color.WHITE);
                gbcCrear.gridx = 0;
                crearPanel.add(confirm, gbcCrear);

                JPasswordField passwordC = new JPasswordField(20);
                gbcCrear.gridx = 1;
                crearPanel.add(passwordC, gbcCrear);

                gbcCrear.gridx = 0;
                gbcCrear.gridy = GridBagConstraints.RELATIVE;
                gbcCrear.gridwidth = 2;

                JCheckBox showPassword = new JCheckBox("Mostrar Contraseña");
                showPassword.setForeground(Color.WHITE);
                showPassword.setBackground(Color.BLACK);
                showPassword.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        char echoChar = showPassword.isSelected() ? (char) 0 : '*';
                        password.setEchoChar(echoChar);
                        passwordC.setEchoChar(echoChar);
                    }
                });
                crearPanel.add(showPassword, gbcCrear);

                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout());
                buttonPanel.setBackground(Color.BLACK);

                JButton c_confirm = new JButton("CONFIRMAR");
                c_confirm.setBackground(Color.BLACK);
                c_confirm.setForeground(Color.WHITE);

                JButton b_salir = new JButton("SALIR");
                b_salir.setBackground(Color.BLACK);
                b_salir.setForeground(Color.WHITE);
                b_salir.addActionListener(event -> crearC.dispose());

                buttonPanel.add(c_confirm);
                buttonPanel.add(b_salir);

                gbcCrear.gridx = 0;
                gbcCrear.gridy = GridBagConstraints.RELATIVE;
                gbcCrear.gridwidth = 2;
                crearPanel.add(buttonPanel, gbcCrear);

                crearC.add(crearPanel);
                crearC.setVisible(true);

                c_confirm.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String username = usuarioField.getText();
                        String pass = password.getText();
                        String passC = passwordC.getText();

                        if (username.equals("") || pass.equals("") || passC.equals("")) {
                            JOptionPane.showMessageDialog(null, "Tiene que llenar todas las casillas!");
                            return;
                        }
                        if (!pass.equals(passC)) {
                            JOptionPane.showMessageDialog(null, "Contraseñas no coinciden! Intente de nuevo.");
                            password.setText("");
                            passwordC.setText("");
                            return;
                        }

                        try {
                            if (mail.CrearCuenta(username, pass)) {
                                JOptionPane.showMessageDialog(null, "Se pudo crear la cuenta!");
                                dispose();
                                crearC.dispose();
                                frameMenu(username);
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo crear la cuenta!");
                            }
                        } catch (IOException es) {
                            JOptionPane.showMessageDialog(null, "No se pudo crear la cuenta!");
                        }
                    }
                }
                );
            }

        });

        // LOGIN
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame login = new JFrame("LOGIN");
                login.setSize(400, 300);
                login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                login.setLocationRelativeTo(null);

                JPanel crearPanel = new JPanel();
                crearPanel.setLayout(new GridBagLayout());
                crearPanel.setBackground(Color.BLACK);

                GridBagConstraints grids = new GridBagConstraints();
                grids.gridx = 0;
                grids.gridy = GridBagConstraints.RELATIVE;
                grids.insets = new Insets(10, 10, 10, 10);
                grids.anchor = GridBagConstraints.WEST;

                JLabel usuario = new JLabel("Usuario:");
                usuario.setForeground(Color.WHITE);
                crearPanel.add(usuario, grids);

                JTextField usuarioField = new JTextField(20);
                grids.gridx = 1;
                crearPanel.add(usuarioField, grids);

                JLabel contra = new JLabel("Contraseña:");
                contra.setForeground(Color.WHITE);
                grids.gridx = 0;
                crearPanel.add(contra, grids);

                JPasswordField password = new JPasswordField(20);
                grids.gridx = 1;
                crearPanel.add(password, grids);

                grids.gridx = 0;
                grids.gridy = GridBagConstraints.RELATIVE;
                grids.gridwidth = 2;

                JCheckBox showPassword = new JCheckBox("Mostrar Contraseña");
                showPassword.setForeground(Color.WHITE);
                showPassword.setBackground(Color.BLACK);
                showPassword.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        password.setEchoChar(showPassword.isSelected() ? (char) 0 : '*');
                    }
                });
                crearPanel.add(showPassword, grids);

                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout());
                buttonPanel.setBackground(Color.BLACK);

                JButton b_confirm = new JButton("CONFIRMAR");
                b_confirm.setBackground(Color.BLACK);
                b_confirm.setForeground(Color.WHITE);

                JButton b_salir = new JButton("SALIR");
                b_salir.setBackground(Color.BLACK);
                b_salir.setForeground(Color.WHITE);
                b_salir.addActionListener(event -> login.dispose());

                buttonPanel.add(b_confirm);
                buttonPanel.add(b_salir);

                grids.gridx = 0;
                grids.gridy = GridBagConstraints.RELATIVE;
                grids.gridwidth = 2;
                crearPanel.add(buttonPanel, grids);

                login.add(crearPanel);
                login.setVisible(true);

                b_confirm.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String username = usuarioField.getText();
                        String contraseña = password.getText();

                        if (username.equals("") || contraseña.equals("")) {
                            JOptionPane.showMessageDialog(null, "Tiene que llenar todas las casillas!");
                            return;
                        }
                        try {
                            if (mail.Login(username, contraseña)) {
                                JOptionPane.showMessageDialog(null, "Bienvenido " + username + " !");
                                login.dispose();
                                dispose();
                                frameMenu(username);
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo realizar login, usuario o contraseña incorrecta.");
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Usuario no existe");
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });

        // SALIR
        salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private JFrame frameMenu(String username) {
        JFrame frame = new JFrame("OPCIONES");
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JLabel title = new JLabel("BIENVENIDO A SU CORREO!!");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel otroPanel = new JPanel();
        otroPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        otroPanel.add(title, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 20, 0);

        JButton inbox = createButton("INBOX");
        JButton enviar = createButton("ENVIAR MENSAJE");
        JButton salir = createButton("SALIR");

        otroPanel.add(inbox, gbc);
        gbc.gridy++;
        otroPanel.add(enviar, gbc);
        gbc.gridy++;
        otroPanel.add(salir, gbc);

        otroPanel.setBackground(Color.BLACK);

        frame.add(otroPanel, BorderLayout.CENTER);

        //INBOX
        inbox.addActionListener(ez -> {
            JFrame frameInbox = new JFrame("BIENVENIDO A SU INBOX!!!");
            frameInbox.setSize(600, 700);
            frameInbox.setLocationRelativeTo(null);

            JLabel titulo = new JLabel("INBOX : ");
            titulo.setForeground(Color.WHITE);
            titulo.setFont(new Font("Arial", Font.BOLD, 16));

            JList<String> listaEmail = new JList<>();
            JLabel userLabel = new JLabel();

            try {
                mail.inbox(listaEmail, userLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }

            listaEmail.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = listaEmail.getSelectedIndex();
                    if (selectedIndex != -1 && selectedIndex < listaEmail.getModel().getSize()) {
                        try {
                            mail.read(selectedIndex + 1);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Selección inválida.");
                    }
                }
            });

            JScrollPane pan = new JScrollPane(listaEmail) {
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(500, 400);
                }
            };

            JPanel inboxPanel = new JPanel();
            inboxPanel.setBackground(Color.BLACK);
            inboxPanel.setLayout(new GridBagLayout());

            JButton regresar = new JButton("REGRESAR A MENU");
            regresar.setBackground(Color.BLACK);
            regresar.setForeground(Color.WHITE);
            regresar.setPreferredSize(new Dimension(200, 40));

            regresar.addActionListener(ed -> {
                frameInbox.dispose();
                frame.setVisible(true);
            });

            GridBagConstraints abc = new GridBagConstraints();
            abc.gridx = 0;
            abc.fill = GridBagConstraints.HORIZONTAL;
            abc.anchor = GridBagConstraints.CENTER;
            abc.insets = new Insets(10, 0, 10, 0);

            abc.gridy = 0;
            abc.insets = new Insets(10, 0, 10, 0);
            inboxPanel.add(titulo, abc);

            abc.gridy++;
            inboxPanel.add(pan, abc);

            abc.gridy++;
            inboxPanel.add(regresar, abc);
            frameInbox.add(inboxPanel, BorderLayout.CENTER);
            frameInbox.setVisible(true);

        });

        //MENSAJEAR
        enviar.addActionListener(es -> {

            JFrame mensajear = new JFrame("MANDAR MENSAJES");
            mensajear.setSize(500, 600);
            mensajear.setLocationRelativeTo(null);
            mensajear.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JLabel label = new JLabel("MANDAR MENSAJES");
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.BOLD, 18));
            label.setHorizontalAlignment(SwingConstants.CENTER);

            JTextField usuario = new JTextField();
            usuario.setPreferredSize(new Dimension(300, 50));
            usuario.setBorder(BorderFactory.createTitledBorder("Usuario"));

            JTextField asunto = new JTextField();
            asunto.setPreferredSize(new Dimension(300, 50));
            asunto.setBorder(BorderFactory.createTitledBorder("Asunto"));

            JTextArea mensaje = new JTextArea();
            mensaje.setLineWrap(true);
            mensaje.setWrapStyleWord(true);
            JScrollPane mensajeScroll = new JScrollPane(mensaje);
            mensajeScroll.setPreferredSize(new Dimension(300, 200));
            mensajeScroll.setBorder(BorderFactory.createTitledBorder("Mensaje"));

            JButton regresor = new JButton("REGRESAR");
            regresor.setBackground(Color.BLACK);
            regresor.setForeground(Color.WHITE);
            regresor.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            regresor.setPreferredSize(new Dimension(140, 40));
            regresor.addActionListener(ed -> mensajear.dispose());

            JButton mandar = new JButton("MANDAR");
            mandar.setBackground(Color.BLACK);
            mandar.setForeground(Color.WHITE);
            mandar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            mandar.setPreferredSize(new Dimension(140, 40));
            mandar.addActionListener(ex -> {
                String user = usuario.getText().trim();
                String subject = asunto.getText().trim();
                String message = mensaje.getText().trim();

                if (user.isEmpty() || subject.isEmpty() || message.isEmpty()) {
                    JOptionPane.showMessageDialog(mensajear, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(mensajear, "Mensaje enviado con éxito a " + user + "!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    usuario.setText("");
                    asunto.setText("");
                    mensaje.setText("");
                }
                try {
                    CurrentUser recip = mail.getUserByUsername(user);
                    String use = mail.useract.username;
                    mail.createEmail(use, subject, message, recip);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "No se pudo mandar el email!!");
                }
            });

            JPanel mensajePanel = new JPanel(new GridBagLayout());
            mensajePanel.setBackground(Color.BLACK);
            GridBagConstraints grid = new GridBagConstraints();
            grid.gridx = 0;
            grid.gridy = 0;
            grid.insets = new Insets(10, 0, 10, 0);
            grid.fill = GridBagConstraints.HORIZONTAL;

            mensajePanel.add(label, grid);

            grid.gridy++;
            mensajePanel.add(usuario, grid);

            grid.gridy++;
            mensajePanel.add(asunto, grid);

            grid.gridy++;
            mensajePanel.add(mensajeScroll, grid);

            grid.gridy++;
            grid.gridwidth = 1;
            grid.anchor = GridBagConstraints.CENTER;

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.BLACK);
            buttonPanel.add(mandar);
            buttonPanel.add(regresor);

            grid.gridy++;
            mensajePanel.add(buttonPanel, grid);

            mensajear.add(mensajePanel);
            mensajear.setVisible(true);
        });

        //SALIR DE INBOX
        salir.addActionListener(ex -> {
            mail.useract = null;
            frame.dispose();
            setVisible(true);
        });
        return frame;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
        return button;
    }

    public static void main(String args[]) {
        JavaMain main = new JavaMain();
        main.setVisible(true);
    }
}
