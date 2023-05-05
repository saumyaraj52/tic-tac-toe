package com.tictactoe.game;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Game 
{
	int n;
	Board board;
	Deque<EnumPlayer> players ;
	
	public Game(int n)
	{
		this.n = n;
		this.board= new Board(n);
		this.players = new ArrayDeque<EnumPlayer>();
	}
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Deque<EnumPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(Deque<EnumPlayer> players) {
		this.players = players;
	}

	public void play()
	{
		System.out.println("Players Set");
		System.out.println(this.players.getFirst());
		System.out.println(this.players.getLast());
		
		boolean tie = false;
		int idx = 0;
		
		while(tie == false&&idx != 9)
		{
			EnumPlayer next = this.players.pollFirst();
			this.players.addLast(next);
			if(next == EnumPlayer.HUMAN)
			{
				Queue<Integer> q = new LinkedList<Integer>();
				for(int i=0;i<this.n;i++)
				{
					for(int j=0;j<this.n;j++)
					{
						if(this.board.cells[i][j].p == EnumPieceValue.__) 
						{
							int val = i*this.n+j+1;
							q.add(val);
							//System.out.print(val+" ");
						}
						//System.out.print(this.board.cells[i][j].p+" ");
					}
					//System.out.println("");
				}
				System.out.print("Choose index to play from: ");
				if(q.size()==0)
					break;
				while(!q.isEmpty())
				{
					System.out.print(q.poll()+" ");
				}
				System.out.println("");
				Scanner sc = new Scanner(System.in);
				int newidx = sc.nextInt();
				
				int row=(newidx-1)/this.n;
				int col=(newidx-1)%this.n;
				this.board.cells[row][col].p = EnumPieceValue.X;
				if(checkWin(EnumPieceValue.X)==true)
				{
					System.out.println("Hooray!! you won");
					return;
				}
			}
			else
			{
				//Computer Turn
				GameMove gameMove=computerTurn();
				if(gameMove.val==0)
					break;
				this.board.cells[gameMove.row][gameMove.col].p = EnumPieceValue.O;
				if(checkWin(EnumPieceValue.O)==true)
				{
					System.out.println("Oops!!Computer Won");
					return;
				}
			}
			System.out.println("");
			for(int i=0;i<this.n;i++)
			{
				for(int j=0;j<this.n;j++)
				{
					System.out.print(this.board.cells[i][j].p+" | ");
				}
				System.out.println("");
			}
			//tie = true;
		}
		
		System.out.println("It's a tie");
		
	}
	public boolean isMovesLeft()
	{
		for(int i=0;i<this.n;i++)
		{
			for(int j=0;j<this.n;j++)
			{
				if(this.board.cells[i][j].p==EnumPieceValue.__) return true;
			}
		}
		return false;
	}
	
	public int minimax(int depth,boolean isMax)
	{
		int score = evaluate();
		if (score == 10)
	        return score;
	  
	    if (score == -10)
	        return score;

	    if (isMovesLeft() == false)
	        return 0;
		
		if(isMax)
		{
			int best = -1000;
			for(int i=0;i<this.n;i++)
			{
				for(int j=0;j<this.n;j++)
				{
					if(this.board.cells[i][j].p==EnumPieceValue.__)
					{
						this.board.cells[i][j].p = EnumPieceValue.X;
						best=minimax(depth+1,!isMax);
						this.board.cells[i][j].p = EnumPieceValue.__;
					}
				}
			}
			return best;
		}
		else
		{
			int best = 1000;
			for(int i=0;i<this.n;i++)
			{
				for(int j=0;j<this.n;j++)
				{
					if(this.board.cells[i][j].p==EnumPieceValue.__)
					{
						this.board.cells[i][j].p = EnumPieceValue.O;
						best=minimax(depth+1,!isMax);
						this.board.cells[i][j].p = EnumPieceValue.__;
					}
				}
			}
			return best;
		}
	}
	
	public GameMove computerTurn()
	{
		GameMove move = new GameMove();
		int row=-1;
		int col=-1;
		int best = -1000;
		for(int i=0;i<this.n;i++)
		{
			for(int j=0;j<this.n;j++)
			{
				if(this.board.cells[i][j].p==EnumPieceValue.__)
				{
					this.board.cells[i][j].p = EnumPieceValue.O;
					int curVal = minimax(0,false);
					this.board.cells[i][j].p = EnumPieceValue.__;
					if(curVal>best)
					{
						curVal=best;
						row = i;
						col=j;
					}
				}
			}
		}
		move.setCol(col);
		move.setRow(row);
		move.setVal(best);
		return move;
	}
	
	public int evaluate()
	{
		//rows
		int i=0,j=1;
		boolean found=false;
		for(;i<this.n;i++)
		{
			EnumPieceValue p = this.board.cells[i][0].p;
			found = false;
			for(;j<this.n;j++)
			{
				if(this.board.cells[i][j].p!=p)
				{
					found = true;
					break;
				}
			}
			if(found == false)
			{
				if(p==EnumPieceValue.O) return 10;
				else return -10;
			}
				
		}
		//cols
		for(i=0;i<this.n;i++)
		{
			EnumPieceValue p = this.board.cells[0][i].p;
			found = false;
			for(j=1;j<this.n;j++)
			{
				if(this.board.cells[j][i].p!=p)
				{
					found = true;
					break;
				}
			}
			if(found==false)
			{
				if(p==EnumPieceValue.O) return 10;
				else return -10;
			}
		}
		//diagonal 1
		found = false;
		EnumPieceValue p1 = this.board.cells[0][0].p;
		for(i=0;i<this.n;i++)
		{
			if(this.board.cells[i][i].p!=p1)
			{
				found = true;
				break;
			}
		}
		if(i==n)
		{
			if(p1==EnumPieceValue.O) return 10;
			else return -10;
		}

		//diagonal 2
		
		found = false;
		p1 = this.board.cells[0][this.n-1].p;
		for(i=0;i<this.n;i++)
		{
			if(this.board.cells[i][n-i-1].p!=p1)
			{
				found = true;
				break;
			}
		}
		if(found==false)
		{
			if(p1==EnumPieceValue.O) return 10;
			else return -10;
		}
		return 0;
	}
	
	public boolean checkWin(EnumPieceValue p)
	{
		//rows
		for(int i=0;i<this.n;i++)
		{
			int cntrow=0;
			for(int j=0;j<this.n;j++)
			{
				if(this.board.cells[i][j].p==p)
					cntrow++;
			}
			if(cntrow==n)
				return true;
		}
		
		//cols
		for(int i=0;i<this.n;i++)
		{
			int cntcol=0;
			for(int j=0;j<this.n;j++)
			{
				if(this.board.cells[j][i].p==p)
					cntcol++;
			}
			if(cntcol==n)
				return true;
		}
		//diagonal 1
		int cntdia=0;
		for(int i=0;i<this.n;i++)
		{
			if(this.board.cells[i][i].p==p)
				cntdia++;
		}
		if(cntdia==n)
			return true;	
		//diagonal 2
		
		cntdia=0;
		for(int i=0;i<this.n;i++)
		{
			if(this.board.cells[i][n-i-1].p==p)
				cntdia++;
		}
		if(cntdia==n)
			return true;	
		return false;
	}
}
