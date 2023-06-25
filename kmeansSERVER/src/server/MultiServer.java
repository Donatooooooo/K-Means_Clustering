package server;

import java.net.*;
import java.io.IOException;


public class MultiServer {
	 private static int PORT;
	 
	 public MultiServer(int port) {
		 MultiServer.PORT = port;
		 try {
			 run();
		 } catch (IOException e) {
			 System.out.println(e.getMessage());
		 }
	}
	 
	 public static void main(String[] args) {
		new MultiServer(8082);
	 }
	 
	 private void run() throws IOException {
		 ServerSocket s = new ServerSocket(PORT);
		 System.out.println("+++ Server ON +++");
		 try {
				while(true) {
						// Si blocca finchè non si verifica una connessione:
						Socket socket = s.accept();
						try {
								new ServerOneClient(socket);
						} catch(IOException e) {
							socket.close();
						}
				}
			} finally {
				s.close();
				System.out.println("+++ Server OFF +++");
			}
	 }
}
