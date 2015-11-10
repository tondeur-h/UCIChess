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
package ucichess;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tondeur-h
 */
public class ChessBoardTest {

    public ChessBoardTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of moveFromFEN method, of class ChessBoard.
     */
    @Test
    public void testMoveFromFEN() {
        System.out.println("moveFromFEN");
        String startFEN = "";
        String move = "";
        String expResult = "";
        String result = ChessBoard.moveFromFEN(startFEN, move);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of show_chessboard method, of class ChessBoard.
     */
    @Test
    public void testShow_chessboard() {
        System.out.println("show_chessboard");
        ChessBoard.show_chessboard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of show_wide_chessboard method, of class ChessBoard.
     */
    @Test
    public void testShow_wide_chessboard() {
        System.out.println("show_wide_chessboard");
        ChessBoard.show_wide_chessboard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of square_is_black method, of class ChessBoard.
     */
    @Test
    public void testSquare_is_black() {
        System.out.println("square_is_black");
        int c = 0;
        int r = 0;
        boolean expResult = false;
        boolean result = ChessBoard.square_is_black(c, r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of assign_chessboard method, of class ChessBoard.
     */
    @Test
    public void testAssign_chessboard() {
        System.out.println("assign_chessboard");
        String lineFEN = "";
        String[][] expResult = null;
        String[][] result = ChessBoard.assign_chessboard(lineFEN);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveToCoord method, of class ChessBoard.
     */
    @Test
    public void testMoveToCoord() {
        System.out.println("moveToCoord");
        String move = "";
        ChessBoard.moveToCoord(move);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of coordToMove method, of class ChessBoard.
     */
    @Test
    public void testCoordToMove() {
        System.out.println("coordToMove");
        int cFrom = 0;
        int rFrom = 0;
        int cTo = 0;
        int rTo = 0;
        String promotion = "";
        String expResult = "";
        String result = ChessBoard.coordToMove(cFrom, rFrom, cTo, rTo, promotion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRowFrom method, of class ChessBoard.
     */
    @Test
    public void testGetRowFrom() {
        System.out.println("getRowFrom");
        int expResult = 0;
        int result = ChessBoard.getRowFrom();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColFrom method, of class ChessBoard.
     */
    @Test
    public void testGetColFrom() {
        System.out.println("getColFrom");
        int expResult = 0;
        int result = ChessBoard.getColFrom();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRowTo method, of class ChessBoard.
     */
    @Test
    public void testGetRowTo() {
        System.out.println("getRowTo");
        int expResult = 0;
        int result = ChessBoard.getRowTo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColTo method, of class ChessBoard.
     */
    @Test
    public void testGetColTo() {
        System.out.println("getColTo");
        int expResult = 0;
        int result = ChessBoard.getColTo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPromote method, of class ChessBoard.
     */
    @Test
    public void testGetPromote() {
        System.out.println("getPromote");
        String expResult = "";
        String result = ChessBoard.getPromote();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_list_of_valid_moves method, of class ChessBoard.
     */
    @Test
    public void testGet_list_of_valid_moves() {
        System.out.println("get_list_of_valid_moves");
        String fen = "";
        int colPiece = 0;
        int rowPiece = 0;
        ArrayList<ChessBoard.Position> expResult = null;
        ArrayList<ChessBoard.Position> result = ChessBoard.get_list_of_valid_moves(fen, colPiece, rowPiece);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of square_is_Empty method, of class ChessBoard.
     */
    @Test
    public void testSquare_is_Empty() {
        System.out.println("square_is_Empty");
        int c = 0;
        int r = 0;
        boolean expResult = false;
        boolean result = ChessBoard.square_is_Empty(c, r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pieceIsWhite method, of class ChessBoard.
     */
    @Test
    public void testPieceIsWhite() {
        System.out.println("pieceIsWhite");
        int c = 0;
        int r = 0;
        boolean expResult = false;
        boolean result = ChessBoard.pieceIsWhite(c, r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pieceIsBlack method, of class ChessBoard.
     */
    @Test
    public void testPieceIsBlack() {
        System.out.println("pieceIsBlack");
        int c = 0;
        int r = 0;
        boolean expResult = false;
        boolean result = ChessBoard.pieceIsBlack(c, r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of whiteKingIsThreat method, of class ChessBoard.
     */
    @Test
    public void testWhiteKingIsThreat() {
        System.out.println("whiteKingIsThreat");
        int c = 0;
        int r = 0;
        boolean expResult = false;
        boolean result = ChessBoard.whiteKingIsThreat(c, r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pieceIN method, of class ChessBoard.
     */
    @Test
    public void testPieceIN() {
        System.out.println("pieceIN");
        int c = 0;
        int r = 0;
        String pieces = "";
        boolean expResult = false;
        boolean result = ChessBoard.pieceIN(c, r, pieces);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of blackKingIsThreat method, of class ChessBoard.
     */
    @Test
    public void testBlackKingIsThreat() {
        System.out.println("blackKingIsThreat");
        int c = 0;
        int r = 0;
        boolean expResult = false;
        boolean result = ChessBoard.blackKingIsThreat(c, r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
