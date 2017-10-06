/*
*Eric Kannampuzha
*Project 1
*Class EchoClient
*CS 380
*Nima
*/

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.net.Socket;

public final class EchoClient {

    public static void main(String[] args) throws Exception {
        try {
            Socket socket = new Socket("18.221.102.182", 38001);
            Runnable inputHandler = () -> {
                try {
                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    while(true) {
                        String line;
                        line = br.readLine();
                        if(line == null) {
                            System.exit(0);
                        }
                        System.out.println("Server> " + line);
                    }
                }
                 catch(Exception e) {
                    e.printStackTrace();
                 }
            };
            
            Runnable outputHandler = () -> {
                try {
                    OutputStream os = socket.getOutputStream();
                    PrintStream out = new PrintStream(os, true, "UTF-8");
                    while(true) {
                        System.out.print("Client> ");
                        InputStreamReader input = new InputStreamReader(System.in, "UTF-8");
                        BufferedReader binput = new BufferedReader(input);
                        String message = binput.readLine();
                        if(message.equals("exit")) {
                            socket.close();
                            System.exit(0);
                        }
                        out.printf(message + "\n");
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            };
            Thread inputHandlerThread = new Thread(inputHandler);
            Thread outputHandlerThread = new Thread(outputHandler);
            outputHandlerThread.start();
            inputHandlerThread.start();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}















