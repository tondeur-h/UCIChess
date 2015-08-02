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
 *//*
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

import ucichess.ChessBoard;
import ucichess.UCIChess;

/**
 * Example : Test some of the UCIChess Methods...
 * @author tondeur herve 2015 GPL V3.0
 */
public class TestChess {

    
    public TestChess()  {
        
        //test with stockfish 6 chess engine
        //UCIChess uci=new UCIChess("C:\\Arena\\Engines\\stockfish-6-win\\Windows\\stockfish-6-64.exe/stockfish-6-32.exe");
        //test with protector chess engine
        UCIChess uci=new UCIChess("C:\\Arena\\Engines\\Protector_1_6_0\\bin\\Protector_Win64.exe");
    
            //ask uci infos
            System.out.println("======================TEST UCI COMMAND======================");
           //is uci ok ?
            System.out.println("uciok = "+uci.get_UciOk(false));
            //engine name and author(s)
            System.out.println("Engine Name = "+uci.getEngineName());
            System.out.println("Engine Author(s) = "+uci.get_EngineAuthor());
            System.out.println("==================TEST UCI OPTIONS RETRIEVE=================");
            //number of options in uci engine
            System.out.println("Numbers of options = "+uci.get_Number_Options());
            //list all uci options (names, type, values)
            System.out.format("%-30s %-10s %-20s\n","Name(id)","type","values");
            System.out.println("------------------------------------------------------------");
            for (int i=0;i<uci.get_Number_Options();i++)
            {
                System.out.format("%-30s %-10s %-20s\n",uci.get_Option(i).getId(),uci.get_Option(i).getType(),uci.get_Option(i).getValues() );
            }
            System.out.println("=====================PLAY A SMALL GAME=====================");
            //is engine ready?
            System.out.println("isready = "+uci.get_ReadyOk(false));
            
            //white play e2e4
            System.out.println("White play = e2e4");
            uci.move_FromSTART("e2e4 ",false); 
            System.out.println("-------------------------------------------------------");
            //is engine ready for next move?
            System.out.println("isready = "+uci.get_ReadyOk(false));
            
            //black move (engine play)
            uci.go_Think(); //think for best move
            String rep=uci.get_BestMove(false);  //read response
            System.out.println("---------------info on best move-----------------------");
            System.out.println("Number of infos lines = "+uci.get_Number_SimpleInfo());
            System.out.format("%-50s\n","Info lines");
            System.out.println("-------------------------------------------------------");
            for (int i=0;i<uci.get_Number_SimpleInfo();i++)
            {
                System.out.format("%-50s\n",uci.get_SimpleInfo(i).getInfo());
            }
            System.out.println("-------------------------------------------------------");
            System.out.println("Black play = "+rep); //draw best move
            System.out.println("Black ponder = "+uci.get_Ponder()); //best white next move
            uci.move_FromSTART("e2e4 "+rep,false); //make move
            System.out.println("-------------------------------------------------------");
            
            //is engine ready for next move?
            System.out.println("isready = "+uci.get_ReadyOk(false));
            
            //white play g1f3
            System.out.println("White play = g1f3");
            uci.move_FromSTART("e2e4 "+rep+" g1f3 ",false);
            System.out.println("-------------------------------------------------------");
            //is engine ready for next move?
            System.out.println("isready = "+uci.get_ReadyOk(false));
            
            //black play
            System.out.println("Black thinking 5 seconds wait please....");
            uci.go_Think_MoveTime(5000); //search next move during 5 seconds
            String rep2=uci.get_BestMove(true);  //read best move
            System.out.println("---------------info on best move-----------------------");
            System.out.println("Number of infos lines = "+uci.get_Number_DetailedInfo());
            System.out.format("%-50s\n","Info Details");
            System.out.println("-------------------------------------------------------");
            for (int i=0;i<uci.get_Number_DetailedInfo();i++)
            {
                System.out.format("Step "+i+" Calculate Nodes = %-50s\n",uci.get_DetailedInfo(i).getNodes());
            }
            System.out.println("-------------------------------------------------------");
            System.out.println("Black play = "+rep2); //draw black turn
            System.out.println("Black ponder = "+uci.get_Ponder()); //best white next move
            uci.move_FromSTART("e2e4 "+rep+" g1f3 "+rep2,false); //make move

            System.out.println("-------------------TEST SQUARE--------------------------");
            
            //white play
            String whiteMove="e2e4";
            String startPos="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
            uci.move_FromFEN(startPos, whiteMove, true);
            String fenWhite=ChessBoard.moveOnFen(startPos, whiteMove);
            ChessBoard.show_chessboard();
            
            //black response
            uci.go_Think();
            String blackMove=uci.get_BestMove(true);
             System.out.println("black move "+blackMove);
             uci.move_FromFEN(fenWhite, blackMove, true);
             String fenBlack=ChessBoard.moveOnFen(fenWhite, blackMove);
             ChessBoard.show_chessboard();
             


             
//bye bye...
            System.out.println("Bye Bye!");
            uci.stop_Engine();
           
}

    
    public static void main(String[] args) {
        TestChess testChess = new TestChess();
    }
    
}
