package com.tictactoe.game;

public class Board {
	
	
	int n;
	Cell cells[][];
	
	public Board(int n)
	{
		this.n = n;
		cells = new Cell[n][n];
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				cells[i][j]=new Cell(EnumPieceValue.__);
			}
		}
	}

}
