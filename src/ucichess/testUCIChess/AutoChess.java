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

    public AutoChess() {
    //run Engine1
    UCIChess engine1=new UCIChess("C:\\Users\\tondeur-h.CHV\\Downloads\\Protector_1_6_0\\bin\\Protector_Win32.exe");
    //run engine2
    UCIChess engine2=new UCIChess("C:/perso/javafx/TP/stockfish-6-win/Windows/stockfish-6-32.exe");
   //get name of first one
    engine1.get_uciOk(false);
   String nameEngine1=engine1.getEngineName();
   //get name of second's
   engine2.get_uciOk(false);
   String nameEngine2=engine2.getEngineName();
   
   //play max 500 turn
   int turn=1;
   while (turn<=500){
       
       //wait engine1
       engine1.get_readyOk(false);
       
            //play white for engine1
            engine1.send_cmd(UCIChess.GOTHINK); //think for best move
            String repw=engine1.get_bestMove(true);  //read response
            if (moves==null){moves=repw;} //just the first move
            else {moves=moves+" "+repw;} //incruise moves list
            System.out.println(nameEngine1+"=> White play (turn "+turn+") "+repw);
            //test if is the winner
            if (engine1.getPonder().compareTo("mate")==0){System.out.println("\nturn("+turn+")"+nameEngine1+" playing WHITE WIN\n"); break;}
            //apply moves to all engines
            engine1.move_FromSTART(moves,false); //make move
            engine2.move_FromSTART(moves,false); //make move
       
          
            //wait for engine2
            engine2.get_readyOk(false);
       
            //play black for engine2
            engine2.send_cmd(UCIChess.GOTHINK); //think for best move
            String repb=engine2.get_bestMove(true);  //read response
            moves=moves+" "+repb; //incruise moves list
            System.out.println(nameEngine2+"=> Black play (turn "+turn+") "+repb);
            //test if is the winner
            if (engine2.getPonder().compareTo("mate")==0) {System.out.println("\nturn("+turn+")"+nameEngine2+" playing BLACK WIN\n");break;}
            System.out.println("moves : "+moves);
            //apply moves to all engines
            engine1.move_FromSTART(moves,false); //make move
            engine2.move_FromSTART(moves,false); //make move
        
   turn++;
   }
        //draw ending game et stop all engines
        if (turn<500) {System.out.println("\nfull moves : "+moves+" mate\n");}
        else {System.out.println("\nfull moves : "+moves+" stall\n");}
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
