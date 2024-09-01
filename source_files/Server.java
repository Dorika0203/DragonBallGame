import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Server
{
	//Server Main
	public static void main(String[] args) throws IOException, InterruptedException
	{
		ServerSocket sso = new ServerSocket(5123);
		System.out.println("Server started");
		
		while(true)
		{
			Socket client = sso.accept();
			new Thread(new getClientThread(client)).start();
		}
	}
}
