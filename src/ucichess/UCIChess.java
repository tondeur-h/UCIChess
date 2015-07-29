 /*
 * Copyright (C) 2015 Tondeur Herve
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

import ucichess.testUCIChess.TestChess;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*********************
 * UCIChess API<br>
 * This API help you to communicate
 * with Chess Engine that respect Universal Chess Interface
 * protocol communication.<br>
 * You can with this API, run a chess engine, send him
 * some uci commands and read responses from this engine.<br>
 * 
 * Example of use : <br><br>
 *      //test with protector chess engine<br>
 *       UCIChess uci=new UCIChess("C:\\Users\\tondeur-h.CHV\\Downloads\\Protector_1_6_0\\bin\\Protector_Win32.exe");<br>
 *   <br>
 *           //ask uci infos<br>
 *           System.out.println("======================TEST UCI COMMAND======================");<br>
 *          //is uci ok ?<br>
           System.out.println("uciok = "+uci.get_UciOk(false));<br>
 *           //engine name and author(s)<br>
 *           System.out.println("Engine Name = "+uci.getEngineName());<br>
           System.out.println("Engine Author(s) = "+uci.get_EngineAuthor());<br>
 *           System.out.println("==================TEST UCI OPTIONS RETRIEVE=================");<br>
 *           //number of options in uci engine<br>
           System.out.println("Numbers of options = "+uci.get_Number_Options());<br>
 *           //list all uci options (names, type, values)<br>
 *           System.out.format("%-30s %-10s %-20s\n","Name(id)","type","values");<br>
 *           System.out.println("------------------------------------------------------------");<br>
 *           for (int i=0;i&lt;uci.get_Number_Options();i++)<br>
 *           {<br>
               System.out.format("%-30s %-10s %-20s\n",uci.get_Option(i).getId(),uci.get_Option(i).getType(),uci.get_Option(i).getValues() );<br>
 *           }<br>
 *           System.out.println("=====================PLAY A SMALL GAME=====================");<br>
 *           //is engine ready?<br>
           System.out.println("isready = "+uci.get_ReadyOk(false));<br>
 *          <br>
 *           //white play e2e4<br>
 *           System.out.println("White play = e2e4");<br>
 *           uci.move_FromSTART("e2e4 ",false); <br>
 *           System.out.println("-------------------------------------------------------");<br>
 *           //is engine ready for next move?<br>
           System.out.println("isready = "+uci.get_ReadyOk(false));<br>
 *           <br>
 *           //black move (engine play)<br>
           uci.send_uci_cmd(UCIChess.GOTHINK); //think for best move<br>
           String rep=uci.get_BestMove(false);  //read response<br>
 *           System.out.println("---------------info on best move-----------------------");<br>
 *           System.out.println("Number of infos lines = "+uci.get_number_infos());<br>
 *           System.out.format("%-50s\n","Info lines");<br>
 *           System.out.println("-------------------------------------------------------");<br>
 *           for (int i=0;i&lt;uci.get_number_infos();i++)<br>
 *           {<br>
 *               System.out.format("%-50s\n",uci.get_info(i).getInfo());<br>
 *           }<br>
 *           System.out.println("-------------------------------------------------------");<br>
 *           System.out.println("Black play = "+rep); //draw best move<br>
           System.out.println("Black ponder = "+uci.get_Ponder()); //best white next move<br>
 *           uci.move_FromSTART("e2e4 "+rep,false); //make move<br>
 *           System.out.println("-------------------------------------------------------");<br>
 *           <br>
 *           //is engine ready for next move?<br>
           System.out.println("isready = "+uci.get_ReadyOk(false));<br>
 *           <br>
 *           //white play g1f3<br>
 *           System.out.println("White play = g1f3");<br>
 *           uci.move_FromSTART("e2e4 "+rep+" g1f3 ",false);<br>
 *           System.out.println("-------------------------------------------------------");<br>
 *           //is engine ready for next move?<br>
           System.out.println("isready = "+uci.get_ReadyOk(false));<br>
 *           <br>
 *           //black play<br>
           uci.send_uci_cmd(UCIChess.GOTHINK); //search next move<br>
           String rep2=uci.get_BestMove(false);  //read best move<br>
 *           System.out.println("---------------info on best move-----------------------");<br>
 *           System.out.println("Number of infos lines = "+uci.get_number_infos());<br>
 *           System.out.format("%-50s\n","Info lines");<br>
 *           System.out.println("-------------------------------------------------------");<br>
 *           for (int i=0;i&lt;uci.get_number_infos();i++)<br>
 *           {<br>
 *               System.out.format("%-50s\n",uci.get_info(i).getInfo());<br>
 *           }<br>
 *           System.out.println("-------------------------------------------------------");<br>
 *           System.out.println("Black play = "+rep2); //draw black turn<br>
           System.out.println("Black ponder = "+uci.get_Ponder()); //best white next move<br>
 *           uci.move_FromSTART("e2e4 "+rep+" g1f3 "+rep2,false); //make move<br>
 *           Square.convert("g1f3");<br>
 *           System.out.println(Square.getColFrom());<br>
 *           System.out.println(Square.getRowFrom());<br>
 *           System.out.println(Square.getColTo());<br>
 *           System.out.println(Square.getRowTo());<br>
 *           System.out.println(Square.getPromote());<br>
 *           System.out.println("-------------------------------------------------------");<br>
 *           //bye bye...stop the chess engine<br>
 *           System.out.println("Bye Bye!");<br>
 *           uci.stop_Engine();<br>
 * <br>
 * 
 * @author Tondeur Herve GPL v3.0
 * @version 1.0
 *********************/
public class UCIChess {
    //some variables for internal uses
    private OutputStream out; //to chess engine
    private BufferedReader in; //from chess engine
    private Process p; //engine running Thread
    private String engineName; 
    private String engineAuthor;
    private String ponder; //ponder read value
    private ArrayList<OptionName> listOfOptions; //keep a list of options
    private ArrayList<InfoSimple> listInfoSimple; ////keep a list of infos lines
    private ArrayList<InfoDetailed> listInfoDetail; //keep a list of detailed infos
            
    //boolean values for internal use only
    private boolean isUCICall=false;
    private boolean  isGOCall=false;
    private boolean  isREADYCall=false;
    private boolean isSTOPCall=false;
    private boolean  isQUITCall=false;
    private boolean  isPONDERHITCall=false;
    
    
    
    //list of SIMPLE COMMANDS, can be used with send_uci_cmd()
    final public static String UCI="uci"; //UCI COMMAND
    final public static String GOTHINK="go"; //GO command
    final public static String MOVEFROMSTART="position startpos moves ";
    final public static String ISREADY="isready";
    final public static String STOP="stop";
    final public static String QUIT="quit";
    final public static String PONDERHIT="ponderhit";
    final public static String UCINEWGAME="ucinewgame";
    
    /***************************
    * construct the API UCIChess<br>
    * Start the chess engine in a ProcessBuilder
    * and connect in and out streams communications
    * with this process.<br>
    * @param engine is the full absolute path of the chess engine<br>
    * Make sure you are using a engine that implement an uci protocol.
    ***************************/
    public UCIChess(String engine) {
        try {
            //build & start chess engine
            p=new ProcessBuilder(engine).start();
            //get in & out streams
            out=p.getOutputStream();
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            //prepare optionsList
            listOfOptions=new ArrayList<>();
            //prepare simple line info list
            listInfoSimple=new ArrayList<>();
        
            //prepare detailed info list
            listInfoDetail=new ArrayList<>();
                  
        } catch (IOException ex) {
            Logger.getLogger(UCIChess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /******************************
     * stop the chess engine and close stream with the process.
     ******************************/
    public void stop_Engine(){
        try {
            //close process engine jdk8
            if (p.isAlive()){p.destroyForcibly();}
            //closes streams (but must be close with process)
            out.close();
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(UCIChess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //===============================MOVES AND POSITIONS==============================
    
     /*****************************************
     * Send some moves commands from a FEN format to chess engine<br>
     * FEN format example : rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1<br>
     * see Notation Forsyth-Edwards (FEN) format<br>
     * @param fen A String that give the board state in fen format.
     * @param moves A list of moves in Algebraic Notation format ie e2e4 separate by space.
     * @param trace A boolean value that print the text command.
     ******************************************/
        public final void move_FromFEN(String fen, String moves, boolean trace){
        try {
            String cmd="position fen "+fen+" moves "+moves+"\n"; //the cmd to send
            //send command
             if (trace) System.out.println(cmd);
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(TestChess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
     /*****************************************
     * Send moves commands from start position to chess engine.
     * @param moves A list of moves in Algebraic Notation format ie e2e4 separate by space.
     * @param trace A boolean value that print the text command.
     ******************************************/
        public final void move_FromSTART(String moves,boolean trace){
        try {
            String cmd="position startpos moves "+moves+"\n"; //cmd to send
            if (trace) System.out.println(cmd);
            //send command
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(TestChess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*****************************************
     * This Method, make the engine the move is pondering
     *****************************************/   
    public void send_ponderhit(){
        send_uci_cmd(PONDERHIT);
    }

    /*****************************************
     * This Method, tell engine to think on a new game position.<br>
     * see UCI protocol documentation.
     *****************************************/   
    public void send_uciNewGame(){
        send_uci_cmd(UCINEWGAME);
    }
        
//======================SIMPLY SEND COMMANDS====================        
        
    /*********************************
     * Send simple command to chess engine<br>
     * like go, uci, isready, position etc...<br>
     * See uci commands documentations for more informations.
     * @param cmd A String with the command to send to the chess engine.
     *********************************/
        public final void send_uci_cmd(String cmd){
        try {
            cmd=cmd+"\n"; //add crlf or cr
            //send command
            //identify command...
            isGOCall = cmd.startsWith("go");
            isUCICall=cmd.startsWith("uci");
            isSTOPCall=cmd.startsWith("stop");
            isQUITCall=cmd.startsWith("quit");
            isPONDERHITCall=cmd.startsWith("ponderhit");
            isREADYCall=cmd.startsWith("ponderhit");
            
            out.write(cmd.getBytes());
            out.flush();            
        } catch (IOException ex) {
            Logger.getLogger(TestChess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
 
//========================ENGINE STATES==============================
 /******************************
  * Check if uci is ready for another command.
  * @param trace A boolean value that print the responses engine.
  * @return A boolean value, true if engine is ready otherwise false.
  ******************************/
    public final boolean get_ReadyOk(boolean trace){
        //call cmd isready before
        send_uci_cmd(UCIChess.ISREADY);
        //test response
        String line; //temp String
        try {
            while ((line=in.readLine())!=null) {
                if (trace){
                    System.out.println(line);
                }
                //check uciok condition de sortie 
                if (line.compareToIgnoreCase("readyok")==0) return true;
            }   } catch (IOException ex) {
            Logger.getLogger(TestChess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
 
        
        
 /*****************************
  * Check if uci engine is ok and ready<br>
  * you must run this method before get name and author of the chess engine
  * and get supported options<br>
  * This method must be the first execute after instanciate the UCIChess object. 
  * @param trace A boolean that print the full trace of the Chess engine Responses.
  * @return A boolean value true when uci is ready otherwise false.
  *****************************/
    public final boolean get_UciOk(boolean trace){
        //call uci command before
        send_uci_cmd(UCIChess.UCI);
        isUCICall=true;
        String line; //temp String
        try {
            while ((line=in.readLine())!=null) {
                if (trace){
                    System.out.println(line);
                }
                try ( //parser ligne id
                        Scanner sc = new Scanner(line)) {
                        sc.useDelimiter(" ");
                        parse_Option(line);
                        // find name engine
                        if (sc.findInLine("id name")!=null){engineName=sc.nextLine();}
                        // find author engine
                        if (sc.findInLine("id author")!=null){engineAuthor=sc.nextLine();}
                }
                //check uciok breaking condition 
                if (line.compareToIgnoreCase("uciok")==0) return true;
            }   } catch (IOException ex) {
            Logger.getLogger(TestChess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
       
       /***************************************
        * Return the name of the running engine.<br>
        * This method must be run only after the UCI command.<br>
        * Instead of, this method return NoName or empty string.
        * @return A String value containing the chess engine name.
        ***************************************/
       public String getEngineName(){
           if (!isUCICall) engineName="empty";
           if (engineName==null) engineName="NoName";
           return engineName;
       }

       
       /***************************************
        * Return the name(s) of the author(s) of the running chess engine
        * you must execute the uci command before
        * @return A String value containing the chess engine author(s) name(s).
        ***************************************/       
       public String get_EngineAuthor(){
           if (!isUCICall) engineAuthor="empty";
           if (engineAuthor==null) engineAuthor="NoName";
           return engineAuthor;
       }

       
    /* ******************************************
      parse option response from Engine and
      construct a ArrayList<OptionName> objects
      @param line 
     ****************************************** */
    private void parse_Option(String line) {
       String name="";
       String next;
       String type="";
       String values="";
       
       try{
        Scanner sc=new Scanner(line);
       //find "option name" string
        if (sc.findInLine("option name")!=null){
           //add words in name variable until words like "type"
           do{
             next=sc.next(); //read next
             if (next.compareTo("type")!=0){
               name=name+" "+next; //if not equal type add words name
             }
             else
             { //treats type value
                 type=sc.next();
               //treats defaults
                 sc.next(); //read default key
                 do{
                     values=values+" "+sc.next();
                 } while (sc.hasNext());
             }
           }while(sc.hasNext());
           listOfOptions.add(new OptionName(name, type, values));
       }
       sc.close();
       }catch(NoSuchElementException nsee){listOfOptions.add(new OptionName(name, type, values));}
    }
    
    /***************************
     * Return the max number of options supported by the engine.<br>
     * You must execute the uci command before used this method.
     * @return An integer containing the count return.
     ***************************/
    public int get_Number_Options(){
        if (!isUCICall) return 0;
        return listOfOptions.size();
    }

    
    /**************************
     * Return an option supported by the engine by is number.<br>
     * OptionName class is a bean for manipulate Options.<br>
     * This class contain 3 methods<br>
     * String getId(); retrieve the name of the option<br>
     * String getType(); retrieve the type of the option<br>
     * String getValues(); retrieve the default and possibles values for this option<br>
     * <br>
     * This Method must be call only after a uci command.<br>
     * <br>
     * @param Number A integer identified the option number in the list of options.
     * @return A OptionName object
     **************************/
    public OptionName get_Option(int Number){
        if (!isUCICall) return null;
        if (Number>=listOfOptions.size()) return null; //number must begin from 0 to size-1
        return listOfOptions.get(Number);
    }

    
    /*************************************
     * set option name for option with no value, 
     * like "Clear Hash" for example.
     * @param id Is the name of the option.
     * @return A boolean true if option name is sent otherwise false.
     *************************************/
    public boolean send_Option_Name_NoValue(String id){
     if (id==null) return false;
        send_uci_cmd("setoption name "+id );
        return true;
    }
    
    
    /**************************************
     * set option name for option with values, 
     * like "Nullmove" waiting for a boolean value true/false for example.
     * @param id Is the name of the option.
     * @param values Is the values to set for this option.
     * @return A boolean true if option name is sent otherwise false.
     **************************************/
    public boolean send_Option_Name_WithValue(String id, String values){
        if (id==null) return false;
        if (values==null) return false;
        send_uci_cmd("setoption name "+id+" value "+values);
        return true;
    }
    
//======================ENGINE THINKING==============================
    /*****************************************
     * This Method, make the engine searching the bestmove and return it as soon as possible
     *****************************************/
    public void go_Think(){
        send_uci_cmd(GOTHINK); //just thinking...
    }
   
    
    /*****************************************
     * This Method, make the engine searching the bestmove and return it as soon as possible
     * when the depth search is reaching.
     * @param depth This value must be between 1 and 32.
     *****************************************/   
    public void go_Think_Depth(int depth){
         if (depth<1) depth=1; //min depth 1
         if (depth>32) depth=32; //max depth 32
        send_uci_cmd(GOTHINK+" depth "+depth);
    }
   
    /*****************************************
     * This Method, make the engine searching the bestmove and return it as soon as possible
     * when the number of calculating nodes search is reach.
     * @param nodes This value must be between 1 and 100.000.000.
     *****************************************/   
    public void go_Think_Nodes(long nodes){
          if (nodes<1) nodes=1; //min 1 nodes
          if (nodes>100000000) nodes=100000000; //max 100000000 nodes
        send_uci_cmd(GOTHINK+" nodes "+nodes);
    }
   
    /*****************************************
     * This Method, make the engine searching the bestmove and return it as soon as possible
     * when the "mate in x turns" search is reaching.
     * @param mateIn This value must be between 1 and 500.
     *****************************************/   
    public void go_Think_Mate_In(int mateIn){
        if (mateIn<1) mateIn=1; //min in one turns
        if (mateIn>500) mateIn=500; //max in 500 turns
        send_uci_cmd(GOTHINK+" mate "+mateIn);
    }
   
    /*****************************************
     * This Method, make the engine searching the bestmove and return it as soon as possible
     * when the depth search is reaching.
     * @param miliSec This value must be between 1 and 12.000.000 (2 hours).
     *****************************************/   
    public void go_Think_MoveTime(long miliSec){
        if (miliSec<1) miliSec=1; //min 1 milisecond
        if (miliSec>(120*60*1000)) miliSec=(120*60*1000); //max 2 hours
        send_uci_cmd(GOTHINK+" movetime "+miliSec);
    }
   
    /*****************************************
     * This Method, make the engine searching the bestmove and return it in a infinite time...
     *****************************************/   
    public void go_Think_Infinite(){
        send_uci_cmd(GOTHINK+" infinite");
    }
   
    /*****************************************
     * This Method, make the engine searching in pondering mode and return it in a infinite time...
     *****************************************/   
    public void go_Think_Ponder(){
        send_uci_cmd(GOTHINK+" ponder");
    }
    
 /***********************************
  * Ask the best move calculate by the chess engine.<br>
  * This method can only be uses after a GO command.
  * @param trace A boolean values that print the full chess engine responses.
  * @return A String values contains the best move in an Algebraic Notation.
  *************************************/
    
       public final String get_BestMove(boolean trace){
         try {
             if (!isGOCall) return "0000";
             String line;
             String bestmove;
            while ((line=in.readLine())!=null) {
                if (trace) {
                    System.out.println(line);
                }
                
                //keep infos lines  into an ArrayList
                if (line.startsWith("info")){
                    listInfoSimple.add(new InfoSimple(line.replaceFirst("info ", "")));
                    //parse detailed infos line
                    parse_Info_Line(line);
                }
                
                
                //breaking condition "bestmove"
                if (line.startsWith("bestmove")){
                    try (Scanner sc = new Scanner(line)) {
                        sc.useDelimiter(" "); //space as delimiter
                        sc.next(); //read sttring bestmove
                        bestmove=sc.next(); //read bestmove
                        try{
                        sc.next(); //read ponder string
                        ponder=sc.next(); //read ponder value
                        } catch (NoSuchElementException nse){ponder="0000";}
                    } 
                    return bestmove; //return move
                } //end if bestmove
            }
              } catch (IOException ex) {
            Logger.getLogger(TestChess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "0000";
    }
 
       
       /*******************************
        * parse infoline in small object.
        *******************************/
       private void parse_Info_Line(String line){
           String key; //variable that keep the key code name
           String value; //variable that keep the value
       //the first key is "info" so 
           //make an InfoDetailed object
           InfoDetailed id=new InfoDetailed();
           Scanner sc=new Scanner(line); //sc for read each words of the line
           key=sc.next(); //read key "info" and lose it
           value=sc.next(); //read next key the first
           while (sc.hasNext()){
               //test if value is a key
               //if yes so key=value and read next value
               if (value.compareTo("score")==0) {key="";value="";} //delete score key not usefull
               if (isKey(value)){key=value;value=sc.next();}
               //else do nothing keep key and value as it is
               
               //test key and add value
                if (key.startsWith("depth")){
                    id.setDepth(id.getDepth()+" "+value);
                }
               if (key.startsWith("seldepth")){ 
                    id.setSelDepth(id.getSelDepth()+" "+value);
                }
               if (key.startsWith("time")){
                    id.setTime(id.getTime()+" "+value);
                }
               if (key.startsWith("nodes")){
                    id.setNodes(id.getNodes()+" "+value);
                }
               if (key.startsWith("pv")){
                    id.setPv(id.getPv()+" "+value);
                }
                if (key.startsWith("multipv")){
                    id.setMultiPV(id.getMultiPV()+" "+value);
                }
               if (key.startsWith("cp")){
                    id.setScoreCP(id.getScoreCP()+" "+value);
                }
               if (key.startsWith("mate")){
                    id.setScoreMate(id.getScoreMate()+" "+value);
                }
               if (key.startsWith("lowerbound")){
                    id.setScoreLowerBound(id.getScoreLowerBound()+" "+value);
                }
               if (key.startsWith("upperbound")){
                    id.setScoreUpperBound(id.getScoreUpperBound()+" "+value);
                }
                if (key.startsWith("currmove")){
                    id.setCurrmove(id.getCurrmove()+" "+value);
                }
               if (key.startsWith("currmovenumber")){
                    id.setCurrmoveNumber(id.getCurrmoveNumber()+" "+value);
                }
               if (key.startsWith("hashfull")){
                    id.setHashfull(id.getHashfull()+" "+value);
                }
               if (key.startsWith("nps")){
                    id.setNps(id.getNps()+" "+value);
                }
               if (key.startsWith("tbhits")){
                    id.setTbhits(id.getTbhits()+" "+value);
                }
                if (key.startsWith("sbhits")){
                    id.setSbhits(id.getSbhits()+" "+value);
                }
               if (key.startsWith("cpuload")){
                    id.setCpuLoad(id.getCpuLoad()+" "+value);
                }
               if (key.startsWith("string")){
                    id.setStr(id.getStr()+" "+value);
                }
               if (key.startsWith("refutation")){
                    id.setRefutation(id.getRefutation()+" "+value);
                }
               if (key.startsWith("currline")){
                    id.setCurrLine(id.getCurrLine()+" "+value);
                }
               if (sc.hasNext()) value=sc.next(); //read next value if possible
           } //end while
           //add the InfoDetailed object to the Array
           listInfoDetail.add(id);
           
           sc.close();
       }
       
       
       /***********************************
        * Method used for internal use
        * call by parse_Info_Line()
        * test if val is a know word => so it is a key
        * @param val
        * @return 
        ***********************************/
       private boolean isKey(String val){
           String words="depth seldepth time nodes pv multipv cp mate lowerbound upperbound currmove currmovenumber hashfull nps tbhits sbhits cpuload string refutation currline";
           return words.contains(val);
       }
       
       
       /***************************************
        * Return the ponder value after a GO command.<br>
        * This method must be run only after a GO command.
        * @return A String contains the ponder values or "mate" key if a mate is reach.
        ***************************************/
       public String get_Ponder(){
           if (!isGOCall) ponder="0000";
           if (ponder==null) ponder="0000";
           return ponder;
       }

    
    /***************************
     * Return the max number of infos after a GO command.<br>
     * You must execute the GO command before used this method.
     * @return An integer containing the count return.
     ***************************/
    public int get_Number_SimpleInfo(){
        if (!isGOCall) return 0;
        return listInfoSimple.size();
    }
    
    
    /**************************
     * Return an info line by is number from the responses engine after a GO command.<br>
     * Info class is a bean for manipulate Infos lines.<br>
     * This class contain 1 method<br>
     * String getInfo(); retrieve the String info<br>
     * <br>
     * This Method must be call only after a GO command.<br>
     * <br>
     * @param Number A integer identified the info line number in the list of infos.
     * @return A Info object
     **************************/
    public InfoSimple get_SimpleInfo(int Number){
        if (!isGOCall) return null;
        if (Number>=listInfoSimple.size()) return null; //number must begin from 0 to size-1
        return listInfoSimple.get(Number);
    }
    

    /***************************
     * Return the max number of infos after a GO command.<br>
     * You must execute the GO command before used this method.
     * @return An integer containing the count return.
     ***************************/
    public int get_Number_DetailedInfo(){
        if (!isGOCall) return 0;
        return listInfoDetail.size();
    }
    
    
    /**************************
     * Return some detailed infos line by is number from the responses engine after a GO command.<br>
     * InfoDetailed class is a bean for manipulate Infos structures.<br>
     * <br>
     * This Method must be call only after a GO command.<br>
     * <br>
     * @param Number A integer identified the info line number in the list of infos.
     * @return A Detailed Info object with all posible return from the Engine
     **************************/
    public InfoDetailed get_DetailedInfo(int Number){
        if (!isGOCall) return null;
        if (Number>=listInfoDetail.size()) return null; //number must begin from 0 to size-1
        return listInfoDetail.get(Number);
    }
    
    
//===============================BEANS CLASS DEFINITIONS==========================    
/*******************************
 * class that encapsulate the "option name" return values from uci
 * @author tondeur-h
 *******************************/
public final class OptionName{
    String id; //list of option id
    String type; //type of the option
    String values; //defaut and min and max values

    public OptionName(String id, String type, String values) {
        this.id = id;
        this.type = type;
        this.values = values;
    }

    // getters and setters for this beans
    /**
     * Return the name of the option
     * @return Name of the option
     */
    public String getId() {
        return id;
    }

    /**
     * Return the type of the option
     * @return Name of the option
     */
    public String getType() {
        return type;
    }

    /**
     * Return the values default value, and min and max values for this option if so!
     * @return Values of the option
     */
    public String getValues() {
        return values;
    }
   
} //end of OptionName class


/********************
 * class for managed Infos return by a GO command wich is cut in details
 * @author tondeur-h
 ********************/
public class InfoDetailed{

    //variables for managed the infos values
    private String depth;
    private String selDepth;
    private String time;
    private String nodes;
    private String pv;
    private String multiPV;
    private String scoreCP;
    private String scoreMate;
    private String scoreLowerBound;
    private String scoreUpperBound;
    private String currmove;
    private String currmoveNumber;
    private String hashfull;
    private String nps;
    private String tbhits;
    private String sbhits;
    private String cpuLoad;
    private String str;
    private String refutation;
    private String currLine;

        /***************
         * make an empty InfoDetailred object
         ****************/
        public InfoDetailed() {
            depth="";
            selDepth="";
            time="";
            nodes = "";
            pv = "";
            multiPV = "";
            scoreCP = "";
            scoreMate = "";
            scoreLowerBound = "";
            scoreUpperBound = "";
            currmove = "";
            currmoveNumber = "";
            hashfull = "";
            nps = "";
            tbhits = "";
            sbhits = "";
            cpuLoad = "";
            str = "";
            refutation = "";
            currLine = "";
        }
    
        private void setDepth(String depth) {
            this.depth = depth;
        }

        private void setSelDepth(String selDepth) {
            this.selDepth = selDepth;
        }

        private void setTime(String time) {
            this.time = time;
        }

        private void setNodes(String nodes) {
            this.nodes = nodes;
        }

        private void setPv(String pv) {
            this.pv = pv;
        }

        private void setMultiPV(String multiPV) {
            this.multiPV = multiPV;
        }

        private void setScoreCP(String scoreCP) {
            this.scoreCP = scoreCP;
        }

        private void setScoreMate(String scoreMate) {
            this.scoreMate = scoreMate;
        }

        private void setScoreLowerBound(String scoreLowerBound) {
            this.scoreLowerBound = scoreLowerBound;
        }

        private void setScoreUpperBound(String scoreUpperBound) {
            this.scoreUpperBound = scoreUpperBound;
        }

        private void setCurrmove(String currmove) {
            this.currmove = currmove;
        }

        private void setCurrmoveNumber(String currmoveNumber) {
            this.currmoveNumber = currmoveNumber;
        }

        private void setHashfull(String hashfull) {
            this.hashfull = hashfull;
        }

        private void setNps(String nps) {
            this.nps = nps;
        }

        private void setTbhits(String tbhits) {
            this.tbhits = tbhits;
        }

        private void setSbhits(String sbhits) {
            this.sbhits = sbhits;
        }

        private void setCpuLoad(String cpuLoad) {
            this.cpuLoad = cpuLoad;
        }

        private void setStr(String str) {
            this.str = str;
        }

        private void setRefutation(String refutation) {
            this.refutation = refutation;
        }

        private void setCurrLine(String currLine) {
            this.currLine = currLine;
        }
   
        /**
         * Get depth info value
         * @return depth value
         */
        public String getDepth() {
            return depth;
        }

         /**
         * Get seldepth info value
         * @return seldepth value
         */

        public String getSelDepth() {
            return selDepth;
        }

        /**
         * Get time info value
         * @return time value
         */
        public String getTime() {
            return time;
        }
        
        /**
         * Get nodes info value
         * @return nodes value
         */
        public String getNodes() {
            return nodes;
        }
        
        /**
         * Get pv info value
         * @return pv value
         */
        public String getPv() {
            return pv;
        }
        
        /**
         * Get multipv info value
         * @return multipv value
         */
        public String getMultiPV() {
            return multiPV;
        }

        /**
         * Get score cp info value
         * @return score cp value
         */
        public String getScoreCP() {
            return scoreCP;
        }
        
        /**
         * Get score mate value
         * @return score mate value
         */
        public String getScoreMate() {
            return scoreMate;
        }
        
        /**
         * Get score lowerbound info value
         * @return Depth value
         */
        public String getScoreLowerBound() {
            return scoreLowerBound;
        }
        
        /**
         * Get score upperbound info value
         * @return score upperbound value
         */
        public String getScoreUpperBound() {
            return scoreUpperBound;
        }
        
        /**
         * Get currmove info value
         * @return currmove value
         */
        public String getCurrmove() {
            return currmove;
        }
 
        /**
         * Get currmovenumber info value
         * @return currmovenumber value
         */
        public String getCurrmoveNumber() {
            return currmoveNumber;
        }

        /**
         * Get hashfull info value
         * @return hashfull value
         */
        public String getHashfull() {
            return hashfull;
        }

        /**
         * Get nps info value
         * @return nps value
         */
        public String getNps() {
            return nps;
        }
        
        /**
         * Get tbhits info value
         * @return tbhits value
         */
        public String getTbhits() {
            return tbhits;
        }

        /**
         * Get sbhits info value
         * @return sbhits value
         */
        public String getSbhits() {
            return sbhits;
        }
       
        /**
         * Get cpuload info value
         * @return cpuload value
         */
        public String getCpuLoad() {
            return cpuLoad;
        }
       
        /**
         * Get string info value
         * @return string value
         */
        public String getStr() {
            return str;
        }
       
        /**
         * Get refutation info value
         * @return refutation value
         */
        public String getRefutation() {
            return refutation;
        }
       
        /**
         * Get currline info value
         * @return currline value
         */
        public String getCurrLine() {
            return currLine;
        }
        
} //end class Infos


/********************
 * class for managed Infos return by a GO command wich is a simple line return
 * @author tondeur-h
 ********************/
public final class InfoSimple {
    String info; //string info to get

    /**
     * Return the info line
     * @return 
     */
    public String getInfo() {
        return info;
    }

    private void setInfo(String info) {
        this.info = info;
    }

    /**
     * construct a InfoSimple object
     * @param info 
     */
    public InfoSimple(String info) {
        this.info = info;
    }
    
} //end of info class

} //end UCIChess
