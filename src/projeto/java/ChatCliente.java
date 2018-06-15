package projeto.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatCliente extends JFrame {

    JTextField textoParaEnviar;
    Socket socket;
    PrintWriter escritor;
    String nome;
    JTextArea textoRecebido;
    Scanner leitor;

    private class EscutaServidor implements Runnable {

        @Override
        public void run() {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            try {
                String texto = leitor.nextLine();
                while (texto != null) {
                    textoRecebido.append(texto + "\n");
                }
            } catch (Exception e) {
            }
        }

    }

    public ChatCliente(String nome) {
        super("Chat:" + nome);
        this.nome = nome;

        Font fonte = new Font("Serif", Font.PLAIN, 26);
        textoParaEnviar = new JTextField();
        textoParaEnviar.setFont(fonte);
        JButton botao = new JButton("Enviar");
        botao.setFont(fonte);
        botao.addActionListener(new EnviarListener());

        Container envio = new JPanel();
        envio.setLayout(new BorderLayout());
        envio.add(BorderLayout.CENTER, textoParaEnviar);
        envio.add(BorderLayout.EAST, botao);
        getContentPane().add(BorderLayout.SOUTH, envio);

        textoRecebido = new JTextArea();
        textoRecebido.setFont(fonte);
        JScrollPane scroll = new JScrollPane(textoRecebido);

        getContentPane().add(BorderLayout.CENTER, scroll);
        getContentPane().add(BorderLayout.SOUTH, envio);

        configurarRede();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }

    private class EnviarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            escritor.println(nome + ": " + textoParaEnviar.getText());
            escritor.flush();
            textoParaEnviar.setText("");
            textoParaEnviar.requestFocus();
        }
    }

    private void configurarRede() {
        try {
            socket = new Socket("127.0.0.1", 5432);
            escritor = new PrintWriter(socket.getOutputStream());
            leitor = new Scanner(socket.getInputStream());
            new Thread(new EscutaServidor()).start();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws Exception {
        new ChatCliente("Daniel");
        new ChatCliente("Lucy");
    }
}
