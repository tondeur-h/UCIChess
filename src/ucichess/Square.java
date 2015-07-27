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

/**
 *
 * @author tondeur herve GPL v3.0
 */
/**************************
 * A square of a chessboard
 * @author tondeur herve 2015 GPL V3.0
 **************************/
public final class Square{
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

 
 /*************************************
  * Convert moves list into fen String.
  * @param moves A moves list in Algebraic Notation.
  * @return A String containing the FEN format.
  *************************************/
 public String movesToFen(String moves){
     String fen="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
 return fen;
 }
 
 
 /*****************************************
  * Convert a string chessboard coordinate
  * into a numeric convention.
  * ie e2e4 give 5 2 5 4
  * @param coord A String with Square coordonate.
  *****************************************/
    public static void convert (String coord){
        //example g1f3 give colFrom=7 rowFrom=1 colTo=6 rowTo=3
            //default values
            rowFrom=0;
            rowTo=0;
            colFrom=0;
            colTo=0;
            promote="";
        if (coord.length()>=4){
        try{
            //translate coord
            colFrom=coord.charAt(0)-96;
            rowFrom=coord.charAt(1)-48;
            colTo=coord.charAt(2)-96;
            rowTo=coord.charAt(3)-48;
            
            if (coord.length()==5){promote=coord.substring(4);}
            
        }catch (Exception e){  
            rowFrom=0;
           rowTo=0;
           colFrom=0;
           colTo=0;
           promote="";
        }
        
        }
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
    
} //end of Square class
