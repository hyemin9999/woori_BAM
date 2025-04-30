package com.woori.BAM.networkTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
	public static void main(String[] args) {
		int port = 9999; // 서버 포트 초기값 설정

		// ServerSocket 타입의 변수 선언 및 객체 생성
		// public ServerSocket(int port) throws IOException 발생시
		// IOException 에서 처리하도록함. try-catch
		// serverSocket 만들어지면 (1번) 대기상태가됨. ==> 서버먼저시작
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("서버가 포트 " + port + "에서 대기 중입니다..."); // 1번

			Socket clientSocket = serverSocket.accept(); // 클라이언트 연결 수락 ==> 연결됨
			System.out.println("클라이언트 연결됨: " + clientSocket.getInetAddress()); // 클라이언트 IP출력

			// 데이터 수신
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String clientMessage = in.readLine();
			System.out.println("클라이언트로부터 수신: " + clientMessage);

			// 데이터 송신
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			out.println("접속 감사합니다.");

			clientSocket.close(); // 다른 클라이언트를 위해서 닫는다.
			System.out.println("서버 종료");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
