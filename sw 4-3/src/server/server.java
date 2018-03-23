package server;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import header.Header;

public class server {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerManager sm = new ServerManager();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}		
	}      
}
     