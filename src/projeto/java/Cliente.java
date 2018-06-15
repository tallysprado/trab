package projeto.java;

import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main (String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 54321);
        System.out.println("Conexão estabelecida entre cliente e servidor!");
        Scanner s = new Scanner(socket.getInputStream());
        System.out.println("Mensagem: " + s.nextLine());
        s.close();

    }
}