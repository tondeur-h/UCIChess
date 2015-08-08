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
package ucichess;

import java.util.ArrayList;

/**************************
 * A representation of a virtual chessboard
 * @author tondeur herve 2015 GPL V3.0
 **************************/
public final class ChessBoard {
/**naming convention
 * ==================
 * On a normal chessboard
 * a=1;b=2;c=3;d=4;e=5;f=6;g=7;h=8
 * row is letters from a to h
 * col is numbers from 1 to 8
 ****************************/    
 private static int rowFrom;
 private static int colFrom;
 private static int rowTo;
 private static int colTo;
 private static String promote;
 private static String FEN;
 private static String color;
 private static String castle;

 //a virtual chessboard
 private static String [][]chessboard;
 private static ArrayList<Position> listOfMove;

 //start position
 public static final String STARTPOSITION="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
 
    //hide the default constructor
    private ChessBoard() {
    }
 
 
 /*************************************
  * Apply a move on a fen string.
  * @param startFEN start position in FEN format
  * @param move A move in Algebraic Notation.
  * @return A String containing the new FEN format.
  *************************************/
 public static String moveFromFEN(String startFEN,String move){
//if move is null return FEN start position
     if (move==null){
 FEN=startFEN;
     }
//create virtual chess board
chessboard=new String [8][8];
//parse FEN and assign to chessboard
assign_chessboard(startFEN);
//convert move in coordinate
moveToCoord(move);
//make move on chessboard
//deal with castle movements 
chessboard[colTo][rowTo]=chessboard[colFrom][rowFrom];
chessboard[colFrom][rowFrom]=null;
//castle movements
//e1g1 => h1f1
//e1c1 =>a1d1
//e8g8 =>h8f8
//e8c8 =>a8d8
if (move.compareTo("e1g1")==0){
    //small white castle
moveToCoord("h1f1");
chessboard[colTo][rowTo]=chessboard[colFrom][rowFrom];
chessboard[colFrom][rowFrom]=null;
}
if (move.compareTo("e1c1")==0){
    //big white castle
moveToCoord("a1d1");
chessboard[colTo][rowTo]=chessboard[colFrom][rowFrom];
chessboard[colFrom][rowFrom]=null;
}
if (move.compareTo("e8g8")==0){
    //small black castle
moveToCoord("h8f8");
chessboard[colTo][rowTo]=chessboard[colFrom][rowFrom];
chessboard[colFrom][rowFrom]=null;
}
if (move.compareTo("e8g8")==0){
    //big black castle
moveToCoord("a8d8");
chessboard[colTo][rowTo]=chessboard[colFrom][rowFrom];
chessboard[colFrom][rowFrom]=null;
}

//construct FEN return
FEN="";
String val;  //read piece value
int count=0; //count empty square
for (int r=7;r>-1;r--){
    for(int c=0;c<8;c++){
        //read piece values
    val=chessboard[c][r];
    //if piece is null =>count empty ChessBoard
    if (val==null) {count++;}
    //if piece is not null
    if (val!=null) {
        //if counter>0 then write counter and reset counter 
        if (count>0) {FEN=FEN+count;count=0;} 
        //write piece value also
        FEN=FEN+val;
    }
 }
    //on change row write count empty square if necessary
    if (count>0) {FEN=FEN+count;count=0;}
    FEN=FEN+"/"; //add slash change line
    
}
return FEN;
 } // end moveFromFEN
 
 
 /***************************************
  * draw the chessboard on an out console
 Black ChessBoard are represented by # character<br>
 White ChessBoard by a space charactere.<br>
  * I keep conventionnal notation for chess piece<br>
  * r black root<br>
  * n black knight<br>
  * b black bishop<br>
  * q black queen<br>
  * k black king<br>
  * p black pawn<br>
  * R white Root<br>
  * N white Knight<br>
  * B white Bishop<br>
  * Q white Queen<br>
  * K white King<br>
  * P white Pawn<br>
  ***************************************/
 public static void show_chessboard(){
     //draw coordinate on top
     System.out.println("*  a  b  c  d  e  f  g  h  *");
     for (int row=7;row>=0;row--){
         for (int col=0;col<8;col++){
             //draw number coordinate on left
             if (col==0) System.out.print((row+1)+" ");
             String val=chessboard[col][row];  //read piece
             //if square is black and empty put a # char
             if (val==null && square_is_black(col,row)==true) {val="#";} else 
             //if square is white and empty put space char
             {if (val==null) val=" ";}
             //write piece value
             System.out.print(" "+val+" ");
             //draw right coordinate
             if (col==7) {System.out.print(" "+(row+1));}
         }
         //on new row
         System.out.print("\n");
     }
     //draw coordinate on bottom
      System.out.println("*  a  b  c  d  e  f  g  h  *\n");
 }//end show_chessboard
 

  /***************************************
  * draw the chessboard on an out console in a large board.
 Black ChessBoard are represented by ## character<br>
 White ChessBoard by a two space charactere.<br>
  * I keep conventionnal long notation for chess piece<br>
  * br black root<br>
  * bn black knight<br>
  * bb black bishop<br>
  * bq black queen<br>
  * bk black king<br>
  * wp black pawn<br>
  * wR white Root<br>
  * wN white Knight<br>
  * wB white Bishop<br>
  * wQ white Queen<br>
  * wK white King<br>
  * wP white Pawn<br>
  ***************************************/
 public static void show_wide_chessboard(){
     String color=" ";
     //draw coordinate on top
     System.out.println("     a    b    c    d    e    f    g    h\n  .----.----.----.----.----.----.----.----.");
     for (int row=7;row>=0;row--){
         for (int col=0;col<8;col++){
             //draw number coordinate on left
             if (col==0) System.out.print((row+1)+" ");
             String val=chessboard[col][row];  //read piece
             //if square is black and empty put a # char
             color=" ";
             if (val==null && square_is_black(col,row)==true) {color="##";}
             if (val==null && square_is_black(col,row)!=true) {color="  ";}
             if (val!=null) {
                 if (pieceIsBlack(col, row)) color="b"; else color="w";
                 val=color+val;
             } else val=color;
             //write piece value
             System.out.print("| "+val+" ");
             //draw right coordinate
             if (col==7) {System.out.print("| "+(row+1));}
         }
         //on new row
         System.out.print("\n  .----.----.----.----.----.----.----.----.\n");
     }
     //draw coordinate on bottom
      System.out.println("     a    b    c    d    e    f    g    h\n");
 }//end show_chessboard

 
 
 /**********************************
  * test is square is a black square
  * call by show_chessboard
  * @param c column value zero based
  * @param r row value zero based
  * @return true if square is black otherwise false. 
  ***********************************/
 public static boolean square_is_black(int c,int r){
     //even row 8 6 4 2
     if (((r+1) % 2)==0){
        //col is even b d f h
         if (((c+1) % 2)==0) {return true;}
     }
     //odd row 7 5 3 1
     if (((r+1) % 2)!=0){
            //col is odd a c e g
         if (((c+1) % 2)!=0) {return true;}
     }
     return false;
 } //end square_is_black
 
 
 /**************************
  * Assign a string FEN format to a virtual chessboard<br>
  * Call this method before using show_chessboard() method.
  * @param lineFEN A string containing a FEN position
     * @return A Sring Array 8x8 that represent the chessboard coordinate (base 0)
  **************************/
 public static String[][] assign_chessboard(String lineFEN){
    /* rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 */
     //if lettre is in (r n b q k / p 1 2 3 4 5 6 7 8 P R N B Q K) then deal with it
     chessboard=new String [8][8];
     
     int indexChar=0;
     int indexSquare=0; //0 between 63
     int col,row;
     
     //begin by row 8 on the chessboard because FEN begin by black chess pieces
     col=0;
     row=7;
     //until reach row 1
     while (row>-1){
         //read the letter
         char letter=lineFEN.charAt(indexChar);
         switch (letter){
             //if if a chess piece add in virtual chesboard
             case 'r':chessboard[col][row]="r";col++;break;
             case 'n':chessboard[col][row]="n";col++;break;
             case 'b':chessboard[col][row]="b";col++;break;
             case 'q':chessboard[col][row]="q";col++;break;
             case 'k':chessboard[col][row]="k";col++;break;
             case 'p':chessboard[col][row]="p";col++;break;
             case 'R':chessboard[col][row]="R";col++;break;
             case 'N':chessboard[col][row]="N";col++;break;
             case 'B':chessboard[col][row]="B";col++;break;
             case 'Q':chessboard[col][row]="Q";col++;break;
             case 'K':chessboard[col][row]="K";col++;break;
             case 'P':chessboard[col][row]="P";col++;break;
             //if it is a break row decrease the row number
             case '/':row--;col=0;break;
             //if it a empty square ignore chessboard places
             //we assume that square where there are no piece
             //must have a null value.
             case '1':col=col+1;break;
             case '2':col=col+2;break;
             case '3':col=col+3;break;
             case '4':col=col+4;break;
             case '5':col=col+5;break;
             case '6':col=col+6;break;
             case '7':col=col+7;break;
             case '8':col=col+8;break;                 
         }
         
         if (col>7 && row==0){row--;}
         indexChar++;
     }
 
     //end assigning
     return chessboard;
 } //end assign_chessboard
 
 
 /*****************************************
  * Convert a string chessboard coordinate
  * into a numeric convention.
  * ie e2e4 give 5 2 5 4
  * @param move A String with ChessBoard coordonate.
  *****************************************/
    public static void moveToCoord (String move){
        //example g1f3 give colFrom=7 rowFrom=1 colTo=6 rowTo=3
            //default values
            rowFrom=0;
            rowTo=0;
            colFrom=0;
            colTo=0;
            promote="";
        if (move.length()>=4){
        try{
            //translate move
            colFrom=move.charAt(0)-97;
            rowFrom=move.charAt(1)-49;
            colTo=move.charAt(2)-97;
            rowTo=move.charAt(3)-49;
            //look for promotion
            if (move.length()==5){promote=move.substring(4);} else {promote="";}
            
        }catch (Exception e){  
            rowFrom=0;
           rowTo=0;
           colFrom=0;
           colTo=0;
           promote="";
        }
        
        }
    } //end moveToCoord
 
    
    /****************************************
     * convert chessboard coordinate to algebraic notation move<br>
     * take care to promote event.
     * @param cFrom number of the column from where the move begin 
     * @param rFrom number of the row from where the move begin
     * @param cTo number of the column to where the move finish
     * @param rTo number of the row to where the move finish
     * @param promotion give the promotion letter rnbq RNBQ
     * @return A String contains the move in algebraic notation.
     *******************************************/
    public static String coordToMove(int cFrom, int rFrom, int cTo, int rTo, String promotion){
        return Character.toString((char)(97+cFrom))+(rFrom+1)+Character.toString((char)(97+cTo))+(rTo+1)+promotion;
    } //end coordToMove
    
    
        /************************************
         * Get Row-From coordanate
         * using convention
         * * a=1;b=2;c=3;d=4;e=5;f=6;g=7;h=8
         * @return An integer
         ***********************************/
    public static int getRowFrom() {
        return rowFrom;
    } //end getRowFrom

    
        /***********************************
         * Get-col From coordonate
         * using convention
         * * 1=1;2=2;3=3;4=4;5=5;6=6;7=7;8=8
         * @return An integer
         ************************************/    
    public static int getColFrom() {
        return colFrom;
    } //end getColFrom

    
        /************************************
         * Get-Row To coordonate
         * using convention
         * * a=1;b=2;c=3;d=4;e=5;f=6;g=7;h=8
         * @return An integer
         ************************************/
    public static int getRowTo() {
        return rowTo;
    } //end getRowTo

    
        /***********************************
         * Get-col To coordonate
         * using convention
         * * 1=1;2=2;3=3;4=4;5=5;6=6;7=7;8=8
         * @return An integer
         ************************************/
    public static int getColTo() {
        return colTo;
    } //end getColTo

    
    /************************
     * Get promote value<br>
     * a chess piece letter
     * or empty if no promote
     * @return String as a letter.
     *************************/
    public static String getPromote() {
        return promote;
    } //end getPromote
    
    
    /**
     * Get a complete list of valid move for the piece in the FEN chess proposed.
     * @param fen Give the fen format chessboard position
     * @param colPiece Give the column number for the piece to analyse
     * @param rowPiece give the row number for the piece to analyse
     * @return An ArrayList of Position Object with all valid moves.
     */
    public static ArrayList<Position> get_list_of_valid_moves(String fen,int colPiece,int rowPiece){
       int upDown;
       int leftright;
       boolean stop;
       
    //prepare listofmove
        if (listOfMove==null){
            listOfMove=new ArrayList<>();
        }
        else
        {
            listOfMove.clear();
        }
        
        //coordonate are not correct return an empty list of moves
        if (colPiece<0 || colPiece>7 || rowPiece<0 || rowPiece>7) return listOfMove;
       
        
    //assign chessboard to split fen into coordinate
        assign_chessboard(fen);
    
    // read the piece to move
    String piece=chessboard[colPiece][rowPiece];
    
    //if Square is empty return a empty moves list.
    if (piece==null) return listOfMove;  //return an empty list
    
    //else treat each case...
    switch(piece){
        //===================================BLACK ROOT================================
        case "r":
            // black root move/take (row--,col), (row++,col), (row,col--), (row,col++)
            //test on the left
            leftright=-1;
            stop=false;
            while ((colPiece+leftright)>=0 && (square_is_Empty((colPiece+leftright), rowPiece) || pieceIsWhite((colPiece+leftright), rowPiece)) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece));
                if (pieceIsWhite((colPiece+leftright), rowPiece)) stop=true;
                leftright--;
            }
             //test on the right
            leftright=1;
            stop=false;
            while ((colPiece+leftright)<=7 && (square_is_Empty((colPiece+leftright), rowPiece) || pieceIsWhite((colPiece+leftright), rowPiece)) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece));
                 if (pieceIsWhite((colPiece+leftright), rowPiece)) stop=true;
                leftright++;
            }
              //test to the bottom
            upDown=-1;
            stop=false;
            while ((rowPiece+upDown)>=0 && (square_is_Empty(colPiece, (rowPiece+upDown)) || pieceIsWhite(colPiece,(rowPiece+upDown)) && !stop)){
                listOfMove.add(new Position(colPiece, rowPiece+upDown));
                 if (pieceIsWhite(colPiece, (rowPiece+upDown))) stop=true;
                upDown--;
            }
              //test to the top
            upDown=1;
            stop=false;
            while ((rowPiece+upDown)<=7 && (square_is_Empty(colPiece, (rowPiece+upDown)) || pieceIsWhite(colPiece,(rowPiece+upDown)) && !stop)){
                listOfMove.add(new Position(colPiece, rowPiece+upDown));
                if (pieceIsWhite(colPiece, (rowPiece+upDown))) stop=true;
                upDown++;
            }            
            break;
        //===================================BLACK KNIGHT==============================
        case "n":  
            /*black knight : move/take (row+2,col-1),(row+2,col+1),
                                       (row+1,col-2),(row+1,col+2),
                                       *(row-2,col-1),*(row-2,col+1),
                                       *(row-1,col-2) *(row-1,col+2)*/
            //down1 left (col-2,row-1)
            if ((colPiece-2)>=0 && (rowPiece-1)>=0){
                if(square_is_Empty(colPiece-2,rowPiece-1) || pieceIsWhite(colPiece-2,rowPiece-1)){
                    listOfMove.add(new Position(colPiece-2, rowPiece-1));
                }
            }
            //down2 left (col-1,row-2)
            if ((colPiece-1)>=0 && (rowPiece-2)>=0){
                if(square_is_Empty(colPiece-1,rowPiece-2) || pieceIsWhite(colPiece-1,rowPiece-2)){
                    listOfMove.add(new Position(colPiece-1, rowPiece-2));
                }
            }
            //down1 right (col+2,row-1)
            if ((colPiece+2)<=7 && (rowPiece-1)>=0){
                if(square_is_Empty(colPiece+2,rowPiece-1) || pieceIsWhite(colPiece+2,rowPiece-1)){
                    listOfMove.add(new Position(colPiece+2, rowPiece-1));
                }
            }
            //down2 right (col+1,row-2)
            if ((colPiece+1)<=7 && (rowPiece-2)>=0){
                if(square_is_Empty(colPiece+1,rowPiece-2) || pieceIsWhite(colPiece+1,rowPiece-2)){
                    listOfMove.add(new Position(colPiece+1, rowPiece-2));
                }
            }
            //up1 left (col-2,row+1)
            if ((colPiece-2)>=0 && (rowPiece+1)<=7){
                if(square_is_Empty(colPiece-2,rowPiece+1) || pieceIsWhite(colPiece-2,rowPiece+1)){
                    listOfMove.add(new Position(colPiece-2, rowPiece+1));
                }
            }
            //up2 left (col-1,row+2)
            if ((colPiece-1)>=0 && (rowPiece+2)<=7){
                if(square_is_Empty(colPiece-1,rowPiece+2) || pieceIsWhite(colPiece-1,rowPiece+2)){
                    listOfMove.add(new Position(colPiece-1, rowPiece+2));
                }
            }
            //up1 right (col+2,row+1)
            if ((colPiece+2)<=7 && (rowPiece+1)<=7){
                if(square_is_Empty(colPiece+2,rowPiece+1) || pieceIsWhite(colPiece+2,rowPiece+1)){
                    listOfMove.add(new Position(colPiece+2, rowPiece+1));
                }
            }
            //up2 right (col+1,row+2)
            if ((colPiece+1)<=7 && (rowPiece+2)<=7){
                if(square_is_Empty(colPiece+1,rowPiece+2) || pieceIsWhite(colPiece+1,rowPiece+2)){
                    listOfMove.add(new Position(colPiece+1, rowPiece+2));
                }
            }
            break;
            //===================================BLACK BISHOP==============================
        case "b": 
            // black bishop move/take (row--,col--), (row--,col++), (row++,col--), (row++,col++)
            //test down-left direction
            upDown=-1;
            leftright=-1;
            stop=false;
            while ((colPiece+leftright)>=0 && (rowPiece+upDown)>=0 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown--;
                leftright--;
            }
            //test down-right direction
            upDown=-1;
            leftright=1;
            stop=false;
            while ((colPiece+leftright)<=7 && (rowPiece+upDown)>=0 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown--;
                leftright++;
            }
            //test up-left direction
            upDown=1;
            leftright=-1;
            stop=false;
            while ((colPiece+leftright)>=0 && (rowPiece+upDown)<=7 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown++;
                leftright--;
            }
            //test up-right direction
            upDown=1;
            leftright=1;
            stop=false;
            while ((colPiece+leftright)<=7 && (rowPiece+upDown)<=7 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown++;
                leftright++;
            }
            break;
            //===================================BLACK QUEEN===============================
        case "q": 
            // black queen move/take (row--,col), (row++,col), (row,col--), (row,col++)
            //                       (row--,col--), (row--,col++), (row++,col--), (row++,col++)
            //test on the left
            leftright=-1;
            stop=false;
            while ((colPiece+leftright)>=0 && (square_is_Empty((colPiece+leftright), rowPiece) || pieceIsWhite((colPiece+leftright), rowPiece)) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece));
                if (pieceIsWhite((colPiece+leftright), rowPiece)) stop=true;
                leftright--;
            }
             //test on the right
            leftright=1;
            stop=false;
            while ((colPiece+leftright)<=7 && (square_is_Empty((colPiece+leftright), rowPiece) || pieceIsWhite((colPiece+leftright), rowPiece)) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece));
                 if (pieceIsWhite((colPiece+leftright), rowPiece)) stop=true;
                leftright++;
            }
              //test to the bottom
            upDown=-1;
            stop=false;
            while ((rowPiece+upDown)>=0 && (square_is_Empty(colPiece, (rowPiece+upDown)) || pieceIsWhite(colPiece,(rowPiece+upDown)) && !stop)){
                listOfMove.add(new Position(colPiece, rowPiece+upDown));
                 if (pieceIsWhite(colPiece, (rowPiece+upDown))) stop=true;
                upDown--;
            }
              //test to the top
            upDown=1;
            stop=false;
            while ((rowPiece+upDown)<=7 && (square_is_Empty(colPiece, (rowPiece+upDown)) || pieceIsWhite(colPiece,(rowPiece+upDown)) && !stop)){
                listOfMove.add(new Position(colPiece, rowPiece+upDown));
                if (pieceIsWhite(colPiece, (rowPiece+upDown))) stop=true;
                upDown++;
            }
              //test down-left direction
            upDown=-1;
            leftright=-1;
            stop=false;
            while ((colPiece+leftright)>=0 && (rowPiece+upDown)>=0 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown--;
                leftright--;
            }
            //test down-right direction
            upDown=-1;
            leftright=1;
            stop=false;
            while ((colPiece+leftright)<=7 && (rowPiece+upDown)>=0 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown--;
                leftright++;
            }
            //test up-left direction
            upDown=1;
            leftright=-1;
            stop=false;
            while ((colPiece+leftright)>=0 && (rowPiece+upDown)<=7 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown++;
                leftright--;
            }
            //test up-right direction
            upDown=1;
            leftright=1;
            stop=false;
            while ((colPiece+leftright)<=7 && (rowPiece+upDown)<=7 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsWhite((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown++;
                leftright++;
            }
            break;
            //====================================BLACK KING===============================
        case "k": 
            /* black king : move (row+1,col-1) (row+1,col) (row+1,col+1)
                                 (row,col-1)       k       (row,col+1)
                                 (row-1,col-1) (row-1,col) (row-1,col-1)
            A king cannot make a move if is will be in danger, so test if the move is possible.
            to reach this goal you must test each valid destination
            */
            //test up-left direction
            upDown=1;
            leftright=-1;
            if ((colPiece+leftright)>=0 && (rowPiece+upDown)<=7 && (square_is_Empty((colPiece+leftright),(rowPiece+upDown)) || pieceIsWhite((colPiece+leftright),(rowPiece+upDown)))){
                if (!blackKingIsThreat((colPiece+leftright),(rowPiece+upDown))) listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
            }
            //test up-right direction
            upDown=1;
            leftright=1;
            if ((colPiece+leftright)<=7 && (rowPiece+upDown)<=7 && (square_is_Empty((colPiece+leftright),(rowPiece+upDown)) || pieceIsWhite((colPiece+leftright),(rowPiece+upDown)))){
                if (!blackKingIsThreat((colPiece+leftright),(rowPiece+upDown))) listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
            }
            //test down-left direction
            upDown=-1;
            leftright=-1;
            if ((colPiece+leftright)>=0 && (rowPiece+upDown)>=0 && (square_is_Empty((colPiece+leftright),(rowPiece+upDown)) || pieceIsWhite((colPiece+leftright),(rowPiece+upDown)))){
                if (!blackKingIsThreat((colPiece+leftright),(rowPiece+upDown))) listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
            }
            //test down-right direction
            upDown=-1;
            leftright=1;
            if ((colPiece+leftright)<=7 && (rowPiece+upDown)>=0 && (square_is_Empty((colPiece+leftright),(rowPiece+upDown)) || pieceIsWhite((colPiece+leftright),(rowPiece+upDown)))){
                if (!blackKingIsThreat((colPiece+leftright),(rowPiece+upDown))) listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
            }
            //test left direction
            leftright=-1;
            if ((colPiece+leftright)>=0 && (square_is_Empty((colPiece+leftright),(rowPiece)) || pieceIsWhite((colPiece+leftright),(rowPiece)))){
                if (!blackKingIsThreat((colPiece+leftright),(rowPiece))) listOfMove.add(new Position(colPiece+leftright, rowPiece));
            }
            //test right direction
            leftright=1;
            if ((colPiece+leftright)<=7 && (square_is_Empty((colPiece+leftright),(rowPiece)) || pieceIsWhite((colPiece+leftright),(rowPiece)))){
                if (!blackKingIsThreat((colPiece+leftright),(rowPiece))) listOfMove.add(new Position(colPiece+leftright, rowPiece));
            }
            //test bottom direction
            upDown=-1;
            if ((rowPiece+upDown)>=0 && (square_is_Empty((colPiece),(rowPiece+upDown)) || pieceIsWhite((colPiece),(rowPiece+upDown)))){
                if (!blackKingIsThreat((colPiece),(rowPiece+upDown))) listOfMove.add(new Position(colPiece, rowPiece+upDown));
            }
            //test top direction
            upDown=1;
            if ((rowPiece+upDown)>=0 && (square_is_Empty((colPiece),(rowPiece+upDown)) || pieceIsWhite((colPiece),(rowPiece+upDown)))){
                if (!blackKingIsThreat((colPiece),(rowPiece+upDown))) listOfMove.add(new Position(colPiece, rowPiece+upDown));
            }
            //if e8=k && f8=empty && g8=empty && h8=r then return g8
            if (pieceIN(4,7,"k") && square_is_Empty(5,7) && square_is_Empty(6,7) && pieceIN(7,7,"r")){
                listOfMove.add(new Position(6, 7));
            }
            //if e8=k && d8=empty && c8=empty && b8=empty && a8=r then return b8
            if (pieceIN(4,7,"k") && square_is_Empty(3,7) && square_is_Empty(2,7) && square_is_Empty(1,7) && pieceIN(0,7,"r")){
                listOfMove.add(new Position(1, 7));
            }
            break;
            //====================================BLACK PAWN===============================
        case "p":
             // black pawn : move (row-1,col),(row-2,col)(if row is 6), take(row-1,col+1), (row-1,col-1)
            //move one step
            if ((rowPiece-1)>=0){
                if(square_is_Empty(colPiece,rowPiece-1)){
                    listOfMove.add(new Position(colPiece, rowPiece-1));
                }
            }
            //move two step
            if ((rowPiece-2)>=0){
                if(square_is_Empty(colPiece,rowPiece-1) && square_is_Empty(colPiece,rowPiece-2) && rowPiece==6){
                    listOfMove.add(new Position(colPiece, rowPiece-2));
                }
            }
            //take left
            if ((colPiece-1)>=0){
                if(pieceIsWhite(colPiece-1,rowPiece-1)){
                    listOfMove.add(new Position(colPiece-1, rowPiece-1));
                }
            }
            //take right
            if ((colPiece+1)<=7){
                if(pieceIsWhite(colPiece+1,rowPiece-1)){
                    listOfMove.add(new Position(colPiece+1, rowPiece-1));
                }
            }
            //TODO => black "prise en passant" <=
            break;
            //===================================WHITE ROOT==============================
        case "R": 
            // white root move/take (row--,col), (row++,col), (row,col--), (row,col++)
            //test on the left
            leftright=-1;
            stop=false;
            while ((colPiece+leftright)>=0 && (square_is_Empty((colPiece+leftright), rowPiece) || pieceIsBlack((colPiece+leftright), rowPiece)) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece));
                if (pieceIsBlack((colPiece+leftright), rowPiece)) stop=true;
                leftright--;
            }
             //test on the right
            leftright=1;
            stop=false;
            while ((colPiece+leftright)<=7 && (square_is_Empty((colPiece+leftright), rowPiece) || pieceIsBlack((colPiece+leftright), rowPiece)) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece));
                 if (pieceIsBlack((colPiece+leftright), rowPiece)) stop=true;
                leftright++;
            }
              //test to the bottom
            upDown=-1;
            stop=false;
            while ((rowPiece+upDown)>=0 && (square_is_Empty(colPiece, (rowPiece+upDown)) || pieceIsBlack(colPiece,(rowPiece+upDown)) && !stop)){
                listOfMove.add(new Position(colPiece, rowPiece+upDown));
                 if (pieceIsBlack(colPiece, (rowPiece+upDown))) stop=true;
                upDown--;
            }
              //test to the top
            upDown=1;
            stop=false;
            while ((rowPiece+upDown)<=7 && (square_is_Empty(colPiece, (rowPiece+upDown)) || pieceIsBlack(colPiece,(rowPiece+upDown)) && !stop)){
                listOfMove.add(new Position(colPiece, rowPiece+upDown));
                if (pieceIsBlack(colPiece, (rowPiece+upDown))) stop=true;
                upDown++;
            }
            break;
            //===================================WHITE KNIGHT==============================
        case "N": 
            /*white knight : move/take (row+2,col-1),(row+2,col+1),
                                       (row+1,col-2),(row+1,col+2),
                                       (row-2,col-1),(row-2,col+1),
                                       (row-1,col-2) (row-1,col+2)*/
            if ((colPiece-2)>=0 && (rowPiece-1)>=0){
                if(square_is_Empty(colPiece-2,rowPiece-1) || pieceIsBlack(colPiece-2,rowPiece-1)){
                    listOfMove.add(new Position(colPiece-2, rowPiece-1));
                }
            }
            //down2 left (col-1,row-2)
            if ((colPiece-1)>=0 && (rowPiece-2)>=0){
                if(square_is_Empty(colPiece-1,rowPiece-2) || pieceIsBlack(colPiece-1,rowPiece-2)){
                    listOfMove.add(new Position(colPiece-1, rowPiece-2));
                }
            }
            //down1 right (col+2,row-1)
            if ((colPiece+2)<=7 && (rowPiece-1)>=0){
                if(square_is_Empty(colPiece+2,rowPiece-1) || pieceIsBlack(colPiece+2,rowPiece-1)){
                    listOfMove.add(new Position(colPiece+2, rowPiece-1));
                }
            }
            //down2 right (col+1,row-2)
            if ((colPiece+1)<=7 && (rowPiece-2)>=0){
                if(square_is_Empty(colPiece+1,rowPiece-2) || pieceIsBlack(colPiece+1,rowPiece-2)){
                    listOfMove.add(new Position(colPiece+1, rowPiece-2));
                }
            }
            //up1 left (col-2,row+1)
            if ((colPiece-2)>=0 && (rowPiece+1)<=7){
                if(square_is_Empty(colPiece-2,rowPiece+1) || pieceIsBlack(colPiece-2,rowPiece+1)){
                    listOfMove.add(new Position(colPiece-2, rowPiece+1));
                }
            }
            //up2 left (col-1,row+2)
            if ((colPiece-1)>=0 && (rowPiece+2)<=7){
                if(square_is_Empty(colPiece-1,rowPiece+2) || pieceIsBlack(colPiece-1,rowPiece+2)){
                    listOfMove.add(new Position(colPiece-1, rowPiece+2));
                }
            }
            //up1 right (col+2,row+1)
            if ((colPiece+2)<=7 && (rowPiece+1)<=7){
                if(square_is_Empty(colPiece+2,rowPiece+1) || pieceIsBlack(colPiece+2,rowPiece+1)){
                    listOfMove.add(new Position(colPiece+2, rowPiece+1));
                }
            }
            //up2 right (col+1,row+2)
            if ((colPiece+1)<=7 && (rowPiece+2)<=7){
                if(square_is_Empty(colPiece+1,rowPiece+2) || pieceIsBlack(colPiece+1,rowPiece+2)){
                    listOfMove.add(new Position(colPiece+1, rowPiece+2));
                }
            }
            break;
            //===================================WHITE BISHOP==============================
        case "B": 
            // white bishop move/take (row--,col--), (row--,col++), (row++,col--), (row++,col++)
            //test down-left direction
            upDown=-1;
            leftright=-1;
            stop=false;
            while ((colPiece+leftright)>=0 && (rowPiece+upDown)>=0 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown--;
                leftright--;
            }
            //test down-right direction
            upDown=-1;
            leftright=1;
            stop=false;
            while ((colPiece+leftright)<=7 && (rowPiece+upDown)>=0 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown--;
                leftright++;
            }
            //test up-left direction
            upDown=1;
            leftright=-1;
            stop=false;
            while ((colPiece+leftright)>=0 && (rowPiece+upDown)<=7 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown++;
                leftright--;
            }
            //test up-right direction
            upDown=1;
            leftright=1;
            stop=false;
            while ((colPiece+leftright)<=7 && (rowPiece+upDown)<=7 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown++;
                leftright++;
            }
            break;
            //===================================WHITE QUEEN==============================
        case "Q": 
            // white queen move/take (row--,col), (row++,col), (row,col--), (row,col++)
            //                       (row--,col--), (row--,col++), (row++,col--), (row++,col++)
            //test on the left
            leftright=-1;
            stop=false;
            while ((colPiece+leftright)>=0 && (square_is_Empty((colPiece+leftright), rowPiece) || pieceIsBlack((colPiece+leftright), rowPiece)) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece));
                if (pieceIsBlack((colPiece+leftright), rowPiece)) stop=true;
                leftright--;
            }
             //test on the right
            leftright=1;
            stop=false;
            while ((colPiece+leftright)<=7 && (square_is_Empty((colPiece+leftright), rowPiece) || pieceIsBlack((colPiece+leftright), rowPiece)) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece));
                 if (pieceIsBlack((colPiece+leftright), rowPiece)) stop=true;
                leftright++;
            }
              //test to the bottom
            upDown=-1;
            stop=false;
            while ((rowPiece+upDown)>=0 && (square_is_Empty(colPiece, (rowPiece+upDown)) || pieceIsBlack(colPiece,(rowPiece+upDown)) && !stop)){
                listOfMove.add(new Position(colPiece, rowPiece+upDown));
                 if (pieceIsBlack(colPiece, (rowPiece+upDown))) stop=true;
                upDown--;
            }
              //test to the top
            upDown=1;
            stop=false;
            while ((rowPiece+upDown)<=7 && (square_is_Empty(colPiece, (rowPiece+upDown)) || pieceIsBlack(colPiece,(rowPiece+upDown)) && !stop)){
                listOfMove.add(new Position(colPiece, rowPiece+upDown));
                if (pieceIsBlack(colPiece, (rowPiece+upDown))) stop=true;
                upDown++;
            }
            //test down-left direction
            upDown=-1;
            leftright=-1;
            stop=false;
            while ((colPiece+leftright)>=0 && (rowPiece+upDown)>=0 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown--;
                leftright--;
            }
            //test down-right direction
            upDown=-1;
            leftright=1;
            stop=false;
            while ((colPiece+leftright)<=7 && (rowPiece+upDown)>=0 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown--;
                leftright++;
            }
            //test up-left direction
            upDown=1;
            leftright=-1;
            stop=false;
            while ((colPiece+leftright)>=0 && (rowPiece+upDown)<=7 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown++;
                leftright--;
            }
            //test up-right direction
            upDown=1;
            leftright=1;
            stop=false;
            while ((colPiece+leftright)<=7 && (rowPiece+upDown)<=7 && (square_is_Empty((colPiece+leftright), (rowPiece+upDown)) || pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) && !stop){
                listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
                if (pieceIsBlack((colPiece+leftright), (rowPiece+upDown))) stop=true;
                upDown++;
                leftright++;
            }
            break;
            //===================================WHITE KING==============================
        case "K": 
            /* white king : move (row+1,col-1) (row+1,col) (row+1,col+1)
                                 (row,col-1)       K       (row,col+1)
                                 (row-1,col-1) (row-1,col) (row-1,col-1)
            A king cannot make a move if is will be in danger, so test if the move is possible.
            */
            //test up-left direction
            upDown=1;
            leftright=-1;
            if ((colPiece+leftright)>=0 && (rowPiece+upDown)<=7 && (square_is_Empty((colPiece+leftright),(rowPiece+upDown)) || pieceIsBlack((colPiece+leftright),(rowPiece+upDown)))){
                if (!whiteKingIsThreat((colPiece+leftright),(rowPiece+upDown))) listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
            }
            //test up-right direction
            upDown=1;
            leftright=1;
            if ((colPiece+leftright)<=7 && (rowPiece+upDown)<=7 && (square_is_Empty((colPiece+leftright),(rowPiece+upDown)) || pieceIsBlack((colPiece+leftright),(rowPiece+upDown)))){
                if (!whiteKingIsThreat((colPiece+leftright),(rowPiece+upDown))) listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
            }
            //test down-left direction
            upDown=-1;
            leftright=-1;
            if ((colPiece+leftright)>=0 && (rowPiece+upDown)>=0 && (square_is_Empty((colPiece+leftright),(rowPiece+upDown)) || pieceIsBlack((colPiece+leftright),(rowPiece+upDown)))){
                if (!whiteKingIsThreat((colPiece+leftright),(rowPiece+upDown))) listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
            }
            //test down-right direction
            upDown=-1;
            leftright=1;
            if ((colPiece+leftright)<=7 && (rowPiece+upDown)>=0 && (square_is_Empty((colPiece+leftright),(rowPiece+upDown)) || pieceIsBlack((colPiece+leftright),(rowPiece+upDown)))){
                if (!whiteKingIsThreat((colPiece+leftright),(rowPiece+upDown))) listOfMove.add(new Position(colPiece+leftright, rowPiece+upDown));
            }
            //test left direction
            leftright=-1;
            if ((colPiece+leftright)>=0 && (square_is_Empty((colPiece+leftright),(rowPiece)) || pieceIsBlack((colPiece+leftright),(rowPiece)))){
                if (!whiteKingIsThreat((colPiece+leftright),(rowPiece))) listOfMove.add(new Position(colPiece+leftright, rowPiece));
            }
            //test right direction
            leftright=1;
            if ((colPiece+leftright)<=7 && (square_is_Empty((colPiece+leftright),(rowPiece)) || pieceIsBlack((colPiece+leftright),(rowPiece)))){
                if (!whiteKingIsThreat((colPiece+leftright),(rowPiece))) listOfMove.add(new Position(colPiece+leftright, rowPiece));
            }
            //test bottom direction
            upDown=-1;
            if ((rowPiece+upDown)>=0 && (square_is_Empty((colPiece),(rowPiece+upDown)) || pieceIsBlack((colPiece),(rowPiece+upDown)))){
                if (!whiteKingIsThreat((colPiece),(rowPiece+upDown))) listOfMove.add(new Position(colPiece, rowPiece+upDown));
            }
            //test top direction
            upDown=1;
            if ((rowPiece+upDown)<=7 && (square_is_Empty((colPiece),(rowPiece+upDown)) || pieceIsBlack((colPiece),(rowPiece+upDown)))){
                if (!whiteKingIsThreat((colPiece),(rowPiece+upDown))) listOfMove.add(new Position(colPiece, rowPiece+upDown));
            }
            //if e1=K && f1=empty && g1=empty && h1=R then return g1
            if (pieceIN(4,0,"K") && square_is_Empty(5,0) && square_is_Empty(6,0) && pieceIN(7,0,"R")){
                listOfMove.add(new Position(6, 0));
            }
            //if e1=K && d1=empty && c1=empty && b1=empty && a1=R then return b1
            if (pieceIN(4,0,"K") && square_is_Empty(3,0) && square_is_Empty(2,0) && square_is_Empty(1,0) && pieceIN(0,0,"R")){
                listOfMove.add(new Position(1, 0));
            }            
            break;
            //===================================WHITE PAWN==============================
        case "P": 
             // white pawn : move (row+1,col),(row+2,col)(if row is 2), take(row+1,col+1), (row+1,col-1)
            //move one step
            if ((rowPiece+1)<=7){
                if(square_is_Empty(colPiece,rowPiece+1)){
                    listOfMove.add(new Position(colPiece, rowPiece+1));
                }
            }
            //move two step
            if ((rowPiece+2)<=7){
                if(square_is_Empty(colPiece,rowPiece+1) && square_is_Empty(colPiece,rowPiece+2) && rowPiece==1){
                    listOfMove.add(new Position(colPiece, rowPiece+2));
                }
            }
            //take left
            if ((colPiece-1)>=0){
                if(pieceIsBlack(colPiece-1,rowPiece+1)){
                    listOfMove.add(new Position(colPiece-1, rowPiece+1));
                }
            }
            //take right
            if ((colPiece+1)<=7){
                if(pieceIsBlack(colPiece+1,rowPiece+1)){
                    listOfMove.add(new Position(colPiece+1, rowPiece+1));
                }
            }
            //TODO => white "prise en passant" <= 
            break;
            //===================================CLEAR SQUARE==============================
        default :
            listOfMove.clear();
            break;
    }

    return listOfMove;
    } //end get_list_of_valid_moves
    
    
    /**
     * test if Square is empty
     * @param c column value zero based
     * @param r row value zero based
     * @return true if square is empty otherwise false. 
     */
    public static boolean square_is_Empty(int c,int r){
        return chessboard[c][r]==null;
    } //end square_is_Empty
    
    
    /**
     * test if piece on this square is white
     * @param c column value zero based
     * @param r row value zero based
     * @return a boolean value true if this piece is white else false.
     */
    public static boolean pieceIsWhite(int c, int r){
        if (chessboard[c][r]==null) return false;
        if (chessboard[c][r].compareTo("R")==0) return true;
        if (chessboard[c][r].compareTo("N")==0) return true;
        if (chessboard[c][r].compareTo("B")==0) return true;
        if (chessboard[c][r].compareTo("Q")==0) return true;
        if (chessboard[c][r].compareTo("P")==0) return true;       
       return false;
    } //end pieceIsWhite
    
    
    /**
     * test if piece on this square is black
     * @param c column value zero based
     * @param r row value zero based
     * @return a boolean value true if this piece is black else false.
     */
    public static boolean pieceIsBlack(int c, int r){
        if (chessboard[c][r]==null) return false;
        if (chessboard[c][r].compareTo("r")==0) return true;
        if (chessboard[c][r].compareTo("n")==0) return true;
        if (chessboard[c][r].compareTo("b")==0) return true;
        if (chessboard[c][r].compareTo("q")==0) return true;
        if (chessboard[c][r].compareTo("p")==0) return true;       
       return false;
    } //end pieceIsBlack
    
    
    /**
     * Test is white King is Threated
     * @param c column value zero based
     * @param r row value zero based
     * @return a boolean value true if king is threated else false. 
     */
    public static boolean whiteKingIsThreat(int c,int r){
        int leftRight;
        int upDown;
        /* white king : move 
                             (row+2,col-1)(n)                     (row+2,col-1)(n)  
            (row+1,col-2)(n) (row+1,col-1)(pk) (row+1,col)(k) (row+1,col+1)(pk) (row+1,col+2)(n)
                             (row,col-1)(k)        K            (row,col+1)(k)
            (row-1,col-2)(n) (row-1,col-1)(k)  (row-1,col)(k) (row-1,col-1)(k)(row-1,col-2)(n)
                             (row-2,col-1)(n)                     (row-2,col-1)(n)        
            */   
        //black pawn threat
        if ((c-1)>=0 && (r+1)<=7 && pieceIN(c-1, r+1, "p")) return true;
        if ((c+1)<=7 && (r+1)<=7 && pieceIN(c+1, r+1, "p")) return true;
        //black king threat 
        if ((c-1)>=0 && pieceIN(c-1, r, "k")) return true;
        if ((c+1)<=7 && pieceIN(c+1, r, "k")) return true;
        if ((r-1)>=0 && pieceIN(c, r-1, "k")) return true;
        if ((r+1)<=7 && pieceIN(c, r+1, "k")) return true;
        if ((c-1)>=0 && (r+1)<=7 && pieceIN(c-1, r+1, "k")) return true;
        if ((c+1)<=7 && (r+1)<=7 && pieceIN(c+1, r+1, "k")) return true;
        if ((c-1)>=0 && (r-1)>=0 && pieceIN(c-1, r-1, "k")) return true;
        if ((c+1)<=7 && (r-1)>=0 && pieceIN(c+1, r-1, "k")) return true;
        //black knight threat
        if ((c-1)>=0 && (r+2)<=7 && pieceIN(c-1, r+2, "n")) return true;
        if ((c+1)<=7 && (r+2)<=7 && pieceIN(c+1, r+2, "n")) return true;
        if ((c-2)>=0 && (r+1)<=7 && pieceIN(c-2, r+1, "n")) return true;
        if ((c+2)<=7 && (r+1)<=7 && pieceIN(c+2, r+1, "n")) return true;
        if ((c-1)>=0 && (r-2)>=0 && pieceIN(c-1, r-2, "n")) return true;
        if ((c+1)<=7 && (r-2)>=0 && pieceIN(c+1, r-2, "n")) return true;
        if ((c-2)>=0 && (r-1)>=0 && pieceIN(c-2, r-1, "n")) return true;
        if ((c+2)<=7 && (r-1)>=0 && pieceIN(c+2, r-1, "n")) return true;
        //black root/bishop/queen threat
        //test to the left
        leftRight=-1;
        while ((c+leftRight)>=0){
             if (chessboard[c+leftRight][r]!=null){
              if (pieceIN(c+leftRight,r,"rq")) return true;  
             }
            leftRight--;     
        }
        //test to the right
        leftRight=1;
        while ((c+leftRight)<=7){
             if (chessboard[c+leftRight][r]!=null){
              if (pieceIN(c+leftRight,r,"rq")) return true;  
             }
            leftRight++;     
        }
        //test to the bottom
        upDown=-1;
        while ((r+upDown)>=0){
             if (chessboard[c][r+upDown]!=null){
              if (pieceIN(c,r+upDown,"rq")) return true;  
             }
            upDown--;     
        }
        //test to the top
        upDown=1;
        while ((r+upDown)<=7){
             if (chessboard[c][r+upDown]!=null){
              if (pieceIN(c,r+upDown,"rq")) return true;  
             }
            upDown++;     
        }
        //test to the left bottom
        leftRight=-1;
        upDown=-1;
        while ((c+leftRight)>=0 && (r+upDown)>=0){
             if (chessboard[c+leftRight][r+upDown]!=null){
              if (pieceIN(c+leftRight,r+upDown,"bq")) return true;  
             }
            leftRight--;
            upDown--;
        }
        //test to the left top
        leftRight=-1;
        upDown=1;
        while ((c+leftRight)>=0 && (r+upDown)<=7){
             if (chessboard[c+leftRight][r+upDown]!=null){
              if (pieceIN(c+leftRight,r+upDown,"bq")) return true;  
             }
            leftRight--;
            upDown++;
        }
        //test to the right bottom
        leftRight=1;
        upDown=-1;
        while ((c+leftRight)<=7 && (r+upDown)>=0){
             if (chessboard[c+leftRight][r+upDown]!=null){
              if (pieceIN(c+leftRight,r+upDown,"bq")) return true;  
             }
            leftRight++;
            upDown--;
        }
        //test to the right top
        leftRight=1;
        upDown=1;
        while ((c+leftRight)<=7 && (r+upDown)<=7){
             if (chessboard[c+leftRight][r+upDown]!=null){
              if (pieceIN(c+leftRight,r+upDown,"bq")) return true;  
             }
            leftRight++;
            upDown++;
        }
        return false;
    } //end whiteKingIsThreat
    
    
    /**
     * test if the square as a piece in pieces list.
     * @param c column value zero based
     * @param r row value zero based
     * @param pieces list of pieces to test
     * @return true if is in list otherwise false.
     */
    private static boolean pieceIN(int c,int r,String pieces){
        if (chessboard[c][r]==null) return false;
        return pieces.contains(chessboard[c][r]);
    } //end pieceIN
    
    
    /**
     * test if black King is Threated  
     * @param c column value zero based
     * @param r row value zero based
     * @return a boolean value true if king is threated else false. 
     */
    public static boolean blackKingIsThreat(int c,int r){
        int leftRight;
        int upDown;
        /* black king : move 
                             (row+2,col-1)(N)                (row+2,col-1)(N)  
            (row+1,col-2)(n) (row+1,col-1)(K) (row+1,col)(K) (row+1,col+1)(K) (row+1,col+2)(N)
                             (row,col-1)(K)        k         (row,col+1)(K)
            (row-1,col-2)(n) (row-1,col-1)(PK)(row-1,col)(K) (row-1,col-1)(PK)(row-1,col-2)(N)
                             (row-2,col-1)(N)                     (row-2,col-1)(N)        
            */   
        //white pawn threat
        if ((c-1)>=0 && (r-1)>=0 && pieceIN(c-1, r+1, "P")) return true;
        if ((c+1)<=7 && (r-1)>=0 && pieceIN(c+1, r+1, "P")) return true;
        //white king threat 
        if ((c-1)>=0 && pieceIN(c-1, r, "K")) return true;
        if ((c+1)<=7 && pieceIN(c+1, r, "K")) return true;
        if ((r-1)>=0 && pieceIN(c, r-1, "K")) return true;
        if ((r+1)<=7 && pieceIN(c, r+1, "K")) return true;
        if ((c-1)>=0 && (r+1)<=7 && pieceIN(c-1, r+1, "K")) return true;
        if ((c+1)<=7 && (r+1)<=7 && pieceIN(c+1, r+1, "K")) return true;
        if ((c-1)>=0 && (r-1)>=0 && pieceIN(c-1, r-1, "K")) return true;
        if ((c+1)<=7 && (r-1)>=0 && pieceIN(c+1, r-1, "K")) return true;
        //white knight threat
        if ((c-1)>=0 && (r+2)<=7 && pieceIN(c-1, r+2, "N")) return true;
        if ((c+1)<=7 && (r+2)<=7 && pieceIN(c+1, r+2, "N")) return true;
        if ((c-2)>=0 && (r+1)<=7 && pieceIN(c-2, r+1, "N")) return true;
        if ((c+2)<=7 && (r+1)<=7 && pieceIN(c+2, r+1, "N")) return true;
        if ((c-1)>=0 && (r-2)>=0 && pieceIN(c-1, r-2, "N")) return true;
        if ((c+1)<=7 && (r-2)>=0 && pieceIN(c+1, r-2, "N")) return true;
        if ((c-2)>=0 && (r-1)>=0 && pieceIN(c-2, r-1, "N")) return true;
        if ((c+2)<=7 && (r-1)>=0 && pieceIN(c+2, r-1, "N")) return true;
        //white root threat
        //test to the left
        leftRight=-1;
        while ((c+leftRight)>=0){
             if (chessboard[c+leftRight][r]!=null){
              if (pieceIN(c+leftRight,r,"RQ")) return true;  
             }
            leftRight--;     
        }
        //test to the right
        leftRight=1;
        while ((c+leftRight)<=7){
             if (chessboard[c+leftRight][r]!=null){
              if (pieceIN(c+leftRight,r,"RQ")) return true;  
             }
            leftRight++;     
        }
        //test to the bottom
        upDown=-1;
        while ((r+upDown)>=0){
             if (chessboard[c][r+upDown]!=null){
              if (pieceIN(c,r+upDown,"RQ")) return true;  
             }
            upDown--;     
        }
        //test to the top
        upDown=1;
        while ((r+upDown)<=7){
             if (chessboard[c][r+upDown]!=null){
              if (pieceIN(c,r+upDown,"RQ")) return true;  
             }
            upDown++;     
        }
        //test to the left bottom
        leftRight=-1;
        upDown=-1;
        while ((c+leftRight)>=0 && (r+upDown)>=0){
             if (chessboard[c+leftRight][r+upDown]!=null){
              if (pieceIN(c+leftRight,r+upDown,"BQ")) return true;  
             }
            leftRight--;
            upDown--;
        }
        //test to the left top
        leftRight=-1;
        upDown=1;
        while ((c+leftRight)>=0 && (r+upDown)<=7){
             if (chessboard[c+leftRight][r+upDown]!=null){
              if (pieceIN(c+leftRight,r+upDown,"BQ")) return true;  
             }
            leftRight--;
            upDown++;
        }
        //test to the right bottom
        leftRight=1;
        upDown=-1;
        while ((c+leftRight)<=7 && (r+upDown)>=0){
             if (chessboard[c+leftRight][r+upDown]!=null){
              if (pieceIN(c+leftRight,r+upDown,"BQ")) return true;  
             }
            leftRight++;
            upDown--;
        }
        //test to the right top
        leftRight=1;
        upDown=1;
        while ((c+leftRight)<=7 && (r+upDown)<=7){
             if (chessboard[c+leftRight][r+upDown]!=null){
              if (pieceIN(c+leftRight,r+upDown,"BQ")) return true;  
             }
            leftRight++;
            upDown++;
        }
        return false;
    } //end blackKingIsThreat
    
    
 
    /**
     * beans class for Square position dealing
     */
    public static class Position{
        int col;
        int row;

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }

        public Position(int col, int row) {
            this.col = col;
            this.row = row;
        }
        
    } //end Position class
    
    
} //end of ChessBoard class