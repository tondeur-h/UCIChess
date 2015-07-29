/*
 * Copyright (C) 2015 tondeur herve
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

import ucichess.UCIChess;

/**
 * UCIChess auto-chess example : A game between two engines
 * @author tondeur herve 2015 GPL V3.0
 */
public class AutoChess {
    String moves=null; //keep moves in a String

    final boolean traceMode=true;
    final long timeThinking=150; //time thinking in miliseconds
    
    public AutoChess() {
    //run Engine1
    UCIChess engine1=new UCIChess("C:\\Arena\\Engines\\Protector_1_6_0\\bin\\Protector_Win64.exe");
    //run engine2
    UCIChess engine2=new UCIChess("C:\\Arena\\Engines\\stockfish-6-win\\Windows\\stockfish-6-64.exe");
   //get name of first one
    engine1.get_UciOk(false);
   String nameEngine1=engine1.getEngineName();
   //get name of second's
   engine2.get_UciOk(false);
   String nameEngine2=engine2.getEngineName();
   
        System.out.println(nameEngine1+" is white player.");
        System.out.println(nameEngine2+" is black player.\n");
   
   //play max 500 turns per tournament
   int turn=1;
   while (turn<=500){
       
       //wait engine1
       engine1.get_ReadyOk(traceMode);
       
            //play white for engine1
            //engine1.go_Think(); //think for best move x seconds
             engine1.go_Think_MoveTime(timeThinking);//think for best move x seconds
            String repw=engine1.get_BestMove(traceMode);  //read response
            if (moves==null){moves=repw;} //just the first move
            else {moves=moves+" "+repw;} //incruise moves list
            System.out.println("\n"+nameEngine1+"=> White play (turn "+turn+") "+repw+"\n");
            //if white bestmove is (none) then black win
            if (repw.compareTo("(none)")==0){System.out.println("\nturn("+turn+")"+nameEngine2+" playing BLACK WIN\n");moves=moves+" white mate";break;}
            //apply moves to all engines
            engine1.move_FromSTART(moves,traceMode); //make move
            engine2.move_FromSTART(moves,traceMode); //make move
       
          
            //wait for engine2
            engine2.get_ReadyOk(traceMode);
       
            //play black for engine2
            //engine2.go_Think(); //think for best move
            engine2.go_Think_MoveTime(timeThinking);//think for best move
            String repb=engine2.get_BestMove(traceMode);  //read response
            moves=moves+" "+repb; //incruise moves list
            System.out.println("\n"+nameEngine2+"=> Black play (turn "+turn+") "+repb+"\n");
            //if black bestmove is (none) then white win
            if (repb.compareTo("(none)")==0) {System.out.println("\nturn("+turn+")"+nameEngine1+" playing WHITE WIN\n");moves=moves+" black mate";break;}
            System.out.println("moves : "+moves+"\n");
            //apply moves to all engines
            engine1.move_FromSTART(moves,traceMode); //make move
            engine2.move_FromSTART(moves,traceMode); //make move
        
   turn++;
   }
        //draw end of tournament and stop all engines
        if (turn<500) {System.out.println("\nfull moves : "+moves+"\n");}
        else {System.out.println("\nfull moves : "+moves+" stall\n");}
        //bye
        System.out.println("End match!");
        engine1.stop_Engine();
        engine2.stop_Engine();
   
    } //end game!
    
    
    /***************************
     * All is in the constructor
     * @param args not uses
     ***************************/
    public static void main(String[] args) {
        new AutoChess();
    }
}
