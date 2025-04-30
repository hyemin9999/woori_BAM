package com.woori.BAM.networkTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClient {
	public static void main(String[] args) {
		String serverIP = "127.0.0.1"; // 서버주소 (localhost = 내컴퓨터)
		int port = 9999; // 대기중인 서버의 port 번호 --> 서버와 tcp연결 --> 성공 socket 객체를 통해서

		try (Socket socket = new Socket(serverIP, port)) { // 인자로 통신을 하기위해서는 ip+port 통신을 먼저함.
			// 서버에 메시지 전송 (요청1)
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println("안녕하세요");

			// 서버 응답 수신
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String serverResponse = in.readLine();
			System.out.println("서버 응답: " + serverResponse);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}