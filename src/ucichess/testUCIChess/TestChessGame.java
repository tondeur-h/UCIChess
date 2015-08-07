/*
 * Copyright (C) 2015 tondeur-h
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ucichess.testUCIChess;

import java.util.Scanner;
import ucichess.ChessBoard;
import ucichess.UCIChess;

/**
 *
 * @author tondeur-h
 */
public class TestChessGame {
boolean gameisOn;
String currFEN;
String listMoves;
String whiteMove;
String blackMove;
ucichess.UCIChess uci;
    
    public TestChessGame() {
        currFEN=ChessBoard.STARTPOSITION;
        gameisOn=true;
        runEngine();
        gameloop();
    } //end constructor
    
    
    public void gameloop(){
    while (gameisOn){
        //White to play => human player...
        whiteMove=askMove();
        //apply white move
        listMoves=listMoves+" "+whiteMove;
        currFEN=ChessBoard.moveFromFEN(currFEN, whiteMove);
        ChessBoard.show_chessboard();
        
        //black move
        uci.move_FromFEN(currFEN, blackMove,false);
        uci.go_Think_MoveTime(1000);
        blackMove=uci.get_BestMove(false);
        //keep list moves in memory
        listMoves=listMoves+" "+blackMove;
        //draw move and calculate new FEN
        currFEN=ChessBoard.moveFromFEN(currFEN, blackMove);
        ChessBoard.show_chessboard();
        if (uci.is_opponent_Mated(false)){
           System.exit(0);
        }
    } //end while 
        
    } //end gameLoop
    
    
    public void runEngine(){
        uci=new UCIChess("C:\\Arena\\Engines\\stockfish-6-win\\Windows\\stockfish-6-64.exe");
        if (uci.get_UciOk(false)){
            System.out.println("Engine "+uci.getEngineName()+" is ready...");   
        }
    }
    
    
    public String askMove(){
        System.out.print("White move ie=> e2e4 : ");
        Scanner sc=new Scanner(System.in);
        return sc.nextLine();
    }
    
    
    public static void main(String[] args) {
        new TestAutoChess();
    }
    
}
