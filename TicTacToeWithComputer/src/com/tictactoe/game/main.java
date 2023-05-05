package com.tictactoe.game;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class main {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		System.out.println("Hey my Project");
		
		Game game = new Game(3);
		System.out.print("Do you want to play first?");
		System.out.println("yes/no");
		Scanner sc = new Scanner(System.in);
		Deque<EnumPlayer> players = new ArrayDeque<EnumPlayer>();
		if(sc.hasNextLine())
		{
			String data = sc.nextLine();
			System.out.println(data);
			if( data.equals("yes") == true)
			{
				players.addFirst(EnumPlayer.HUMAN);
				players.addLast(EnumPlayer.COMPUTER);
			}
			else
			{
				players.addFirst(EnumPlayer.COMPUTER);
				players.addLast(EnumPlayer.HUMAN);
			}
		}
		game.setPlayers(players);
		game.play();
	}

}
