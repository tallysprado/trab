package projeto.java;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;

public class Servidor {

    public static void main (String [] args) throws Exception {
        ServerSocket server = new ServerSocket(54321);
        while (true) {
            Socket socket = server.accept();
            try (PrintWriter w = new PrintWriter (socket.getOutputStream())) {
                w.println("Sucesso");
            }
        }
    }
}