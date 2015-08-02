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
chessboard[colTo-1][rowTo-1]=chessboard[colFrom-1][rowFrom-1];
chessboard[colFrom-1][rowFrom-1]=null;
//castle movements
//e1g1 => h1f1
//e1c1 =>a1d1
//e8g8 =>h8f8
//e8c8 =>a8d8
if (move.compareTo("e1g1")==0){
    //small white castle
moveToCoord("h1f1");
chessboard[colTo-1][rowTo-1]=chessboard[colFrom-1][rowFrom-1];
chessboard[colFrom-1][rowFrom-1]=null;
}
if (move.compareTo("e1c1")==0){
    //big white castle
moveToCoord("a1d1");
chessboard[colTo-1][rowTo-1]=chessboard[colFrom-1][rowFrom-1];
chessboard[colFrom-1][rowFrom-1]=null;
}
if (move.compareTo("e8g8")==0){
    //small black castle
moveToCoord("h8f8");
chessboard[colTo-1][rowTo-1]=chessboard[colFrom-1][rowFrom-1];
chessboard[colFrom-1][rowFrom-1]=null;
}
if (move.compareTo("e8g8")==0){
    //big black castle
moveToCoord("a8d8");
chessboard[colTo-1][rowTo-1]=chessboard[colFrom-1][rowFrom-1];
chessboard[colFrom-1][rowFrom-1]=null;
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
 }
 
 
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
             if (val==null && is_black(col,row)==true) {val="#";} else 
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
 }
 
 
 /**********************************
  * test is square is a black square
  * call by show_chessboard
  * @param c
  * @param r
  * @return 
  ***********************************/
 private static boolean is_black(int c,int r){
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
 }
 
 
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
 }
 
 
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
            colFrom=move.charAt(0)-96;
            rowFrom=move.charAt(1)-48;
            colTo=move.charAt(2)-96;
            rowTo=move.charAt(3)-48;
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
    }
 
    
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
        return Character.toString((char)(96+cFrom))+(rFrom)+Character.toString((char)(96+cTo))+(rTo)+promotion;
    }
    
    
        /************************************
         * Get Row-From coordanate
         * using convention
         * * a=1;b=2;c=3;d=4;e=5;f=6;g=7;h=8
         * @return An integer
         ***********************************/
    public static int getRowFrom() {
        return rowFrom;
    }

        /***********************************
         * Get-col From coordonate
         * using convention
         * * 1=1;2=2;3=3;4=4;5=5;6=6;7=7;8=8
         * @return An integer
         ************************************/    
    public static int getColFrom() {
        return colFrom;
    }

        /************************************
         * Get-Row To coordonate
         * using convention
         * * a=1;b=2;c=3;d=4;e=5;f=6;g=7;h=8
         * @return An integer
         ************************************/
    public static int getRowTo() {
        return rowTo;
    }

        /***********************************
         * Get-col To coordonate
         * using convention
         * * 1=1;2=2;3=3;4=4;5=5;6=6;7=7;8=8
         * @return An integer
         ************************************/
    public static int getColTo() {
        return colTo;
    }

    /************************
     * Get promote value<br>
     * a chess piece letter
     * or empty if no promote
     * @return String as a letter.
     *************************/
    public static String getPromote() {
        return promote;
    }
    
} //end of ChessBoard class
