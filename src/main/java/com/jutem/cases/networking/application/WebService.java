package com.jutem.cases.networking.application;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class WebService {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("server is ready");
        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                //http last line
                if("".equals(line))
                    break;
                System.out.println(line);
            }

            File file = new File(".");
            Arrays.stream(file.listFiles()).forEach(f -> System.out.println(f.getAbsolutePath()));

            BufferedReader fileReader = new BufferedReader(new FileReader("src/main/resources/HelloWorld.html"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while ((line = fileReader.readLine()) != null) {
                writer.write(line);
            }
            writer.flush();
            socket.close();
        }
    }
}
