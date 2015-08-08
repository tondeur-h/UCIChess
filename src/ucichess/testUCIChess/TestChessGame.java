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

import java.util.ArrayList;
import java.util.Scanner;
import ucichess.ChessBoard;
import ucichess.ChessBoard.Position;
import ucichess.UCIChess;

/**
 *
 * @author tondeur-h
 */
public class TestChessGame {
boolean gameisOn;
String currFEN;
String listMoves="";
String whiteMove;
String blackMove;
ucichess.UCIChess uci;
String engName;
boolean testMove;
int count;


/*
 * prepare new game
 */
    public TestChessGame() {
        currFEN=ChessBoard.STARTPOSITION;
        gameisOn=true;
        testMove=false;
        count=1;
        
        runEngine();
        gameloop();
    } //end constructor
    
    
    /*
     * game loop
     */
    public void gameloop(){
        System.out.println("== NEW GAME ==");
        ChessBoard.assign_chessboard(currFEN);
        ChessBoard.show_chessboard();
        whiteMove="e2e4";
        
    while (gameisOn){
        
        //White to play => human player...
        System.out.println("----------------------------------------------------");
        System.out.println("== ("+count+") WHITE PLAY (Human) ==");
        while (!testMove){
        whiteMove=askMove();
        testMove=test_Move(whiteMove);
        if (!testMove) System.out.println(whiteMove+" this move is not correct, must be from a1 to h8 and formated like this e2e4!");
        }
        testMove=false; //for the next move...
        listMoves=listMoves+" "+whiteMove;
        //draw and apply white move on screen
        currFEN=ChessBoard.moveFromFEN(currFEN, whiteMove);
        ChessBoard.show_chessboard();
                System.out.println("FEN="+currFEN);
                
        //black move
        System.out.println("----------------------------------------------------");
        System.out.println("== ("+count+") BLACK PLAY ("+engName+") ==");
        uci.move_FromFEN(currFEN, blackMove,false);
        uci.go_Think_MoveTime(1000);
        blackMove=uci.get_BestMove(false);
        System.out.println("Is move = "+blackMove);
        //keep list moves in memory
        listMoves=listMoves+" "+blackMove;
        //draw move and calculate new FEN
        currFEN=ChessBoard.moveFromFEN(currFEN, blackMove);
        ChessBoard.show_chessboard();
        System.out.println("FEN="+currFEN);
        count++;
        
        //who is mated???
        if (uci.is_engine_Mated(false)){
            System.out.println("BLACK is mated");
            System.out.println(listMoves);
            System.exit(0);
        }
        
        if (uci.is_opponent_Mated(false)){
            System.out.println("WHITE is mated");
            System.out.println(listMoves);
            System.exit(0);
        }
        
    } //end while 
        
    } //end gameLoop
    
  
    /*
    run the engine 
    */
    public void runEngine(){
        uci=new UCIChess("C:\\Arena\\Engines\\stockfish-6-win\\Windows\\stockfish-6-64.exe");
        if (uci.get_UciOk(false)){
            engName=uci.getEngineName();
            System.out.println("Engine "+engName+" is ready...");   
        }
    }
    
    
    /*
    ask a move to the user and test it...
    */
    public String askMove(){
        System.out.print("White move ie=> "+whiteMove+" : ");
        Scanner sc=new Scanner(System.in);
        return sc.nextLine();
    }
    
    
    /*
    test a move from white player
    */
    public boolean test_Move(String move){

        
        //test format
        if (move.length()<4 || move.length()>5 ){return false;}
        //test letter and number
        if (move.charAt(0)<97 || move.charAt(0)> 104){return false;}
        if (move.charAt(1)<49 || move.charAt(1)> 56){return false;}
        if (move.charAt(2)<97 || move.charAt(2)> 104){return false;}
        if (move.charAt(3)<49 || move.charAt(3)> 56){return false;}
                
        
        if (move.length()==5){
        //showhelp
            if(move.charAt(4)==104){
                    //show help for all moves        
                ChessBoard.moveToCoord(move);
                int fc=ChessBoard.getColFrom();
                int fr=ChessBoard.getRowFrom();
                int tc=ChessBoard.getColTo();
                int tr=ChessBoard.getRowTo();

                ArrayList<Position> lom=ChessBoard.get_list_of_valid_moves(currFEN, fc, fr);
                for (int i=0;i<lom.size();i++){
                   int pc=lom.get(i).getCol();
                   int pr=lom.get(i).getRow();

                    System.out.println(ChessBoard.coordToMove(fc, fr, pc, pr,""));
                }
                return false;
        }
            
            //row destination must be 8 if promote
            if(move.charAt(3)!=56){return false;}
            //promote must be QRNB
            if(move.charAt(4)!=81 || move.charAt(4)!=82 || move.charAt(4)!=78 || move.charAt(4)!=66){return false;}
        }
        
        
        //test if is possible ?
        //translate move into coordinate
        ChessBoard.moveToCoord(move);
        int fc=ChessBoard.getColFrom();
        int fr=ChessBoard.getRowFrom();
        int tc=ChessBoard.getColTo();
        int tr=ChessBoard.getRowTo();
        
        if (ChessBoard.pieceIsBlack(fc, fr)){return false;}
        if (ChessBoard.square_is_Empty(fc, fr)){return false;}

        
        //test if destination coordinate is in valid moves.
        ArrayList<Position> lom=ChessBoard.get_list_of_valid_moves(currFEN, fc, fr);
        for (int i=0;i<lom.size();i++){
           int pc=lom.get(i).getCol();
           int pr=lom.get(i).getRow();
           if (tc==pc && tr==pr){return true;}
        }
            
        //default nothing match
        return false;
    }
    
    
    public static void main(String[] args) {
        new TestChessGame();
    }
    
}