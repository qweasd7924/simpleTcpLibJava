package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Connection open");

        } catch (IOException e) {
            e.printStackTrace();
//
//            try {
//                clientSocket.close();
//                startConnection(ip,port);
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
        }

    }

    public String sendMessage(String msg) throws IOException {
        if (!out.checkError()){
            out.println(msg);
            out.flush();
            System.out.println(msg + " sended");

        }else{
            System.out.println("Has error in stream");
        }

//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String resp = "";
        System.out.println(in.ready());

        if (in.ready()) {
            resp = in.readLine();
        }

        System.out.println(resp + " resp");
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client c = new Client();

        c.startConnection("192.168.0.110", 2000);
        System.out.println(c.sendMessage("12321"));
//        c.sendMessage("OK3");
        c.stopConnection();
    }
}