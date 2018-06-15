package projeto.java;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatServidor {

    List<PrintWriter> escritores = new ArrayList<>();
    
    public ChatServidor() {
        this.escritores = new ArrayList();

        ServerSocket server;
        Scanner leitor;
        try {
            server = new ServerSocket(5432);
            while (true) {
                Socket socket = server.accept();
                new Thread(new EscutaCliente(socket)).start();
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                escritores.add(pw);
            }
        } catch (IOException e) {
        }
    }
    
    private void encaminhar(String texto){
        for (PrintWriter pw : escritores){
            try{
                pw.println(texto);
            pw.flush();
            }catch(Exception e){
            }
        }
    }
    
    private class EscutaCliente implements Runnable {

        Scanner leitor;

        public EscutaCliente(Socket socket) {
            try {
                leitor = new Scanner(socket.getInputStream());
            }catch (Exception e) {}
        }

        @Override
        public void run() {
            try {
                String texto;
                while ((texto = leitor.nextLine()) != null){
                    System.out.println(texto);
                    encaminhar(texto);
                }
            }catch (Exception x) {}
        }
    }

    public static void main(String[] args) {
        new ChatServidor();
    }
}