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

import ucichess.ChessBoard;

/**
 * This class is just here to test the ChessBoard class and all methods.
 * @author tondeur-h
 */
public class TestChesboardAPI {

    String FEN="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    
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
        System.out.println("Transform coordinate 7-8 6-6 into a move representation");
        System.out.println("move is : "+ChessBoard.coordToMove(7, 8, 6, 6,""));
    
    }

    
    public static void main(String[] args) {
        new TestChesboardAPI();
    }
    
}
