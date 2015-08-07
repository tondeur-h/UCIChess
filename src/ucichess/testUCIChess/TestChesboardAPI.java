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
import ucichess.ChessBoard;

/**
 * This class is just here to test the ChessBoard class and all methods.
 * @author tondeur-h
 */
public class TestChesboardAPI {

    String FEN=ChessBoard.STARTPOSITION;
    
    public TestChesboardAPI() {
    //test assign and show
        System.out.println("Test Assign chessboard with this FEN value "+ FEN+"\n");
    ChessBoard.assign_chessboard(FEN);
    ChessBoard.show_chessboard();
        System.out.println("FEN as not change : "+FEN+"\n");
    
    //test moveFromFEN
        System.out.println("Apply the e2e4 move on FEN startpos");
    FEN=ChessBoard.moveFromFEN(FEN, "e2e4");
    System.out.println("Return FEN changes : "+FEN+"\n");
    ChessBoard.show_chessboard();
    
    //test moveToCoord
        System.out.println("Transform e7e8Q move into chessboard coordinate");
    ChessBoard.moveToCoord("e7e8Q");
    System.out.println("From : "+ChessBoard.getColFrom()+"-"+ChessBoard.getRowFrom()+" To : "+ChessBoard.getColTo()+"-"+ChessBoard.getRowTo());
    System.out.println("Promote = "+ChessBoard.getPromote()+"\n");
    
    //test coordToMove
        System.out.println("Transform coordinate 6-7 5-5 into a move representation");
        System.out.println("move is : "+ChessBoard.coordToMove(6, 7, 5, 5,""));
  
        
    //test positions
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
       System.out.println("\"3k2rr/pPp4p/nPbP3P/3P4/P6b/4P3/pp1N2N1/4KQBR\", 5, 0 => white Queen possible Moves?");
        ChessBoard.assign_chessboard("3k2rr/pPp4p/nPbP3P/3P4/P6b/4P3/pp1N2N1/4KQBR");
        ChessBoard.show_chessboard();
        System.out.println("Possible moves for white Queen f1 :");
       ArrayList<ChessBoard.Position> ar=ChessBoard.get_list_of_valid_moves("3k2rr/pPp4p/nPbP3P/3P4/P6b/4P3/pp1N2N1/4KQBR", 5, 0);
                for (int i=0;i<ar.size();i++){
            System.out.println(((ChessBoard.Position) ar.get(i)).getCol()+"-"+((ChessBoard.Position) ar.get(i)).getRow());
        }
        
    }

    
    public static void main(String[] args) {
        new TestChesboardAPI();
    }
    
}
