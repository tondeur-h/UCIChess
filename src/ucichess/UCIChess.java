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
 * with Chess Engine than respect Universal Chess Interface
 * protocol communication.<br>
 * You can with this API, run a chess engine, send him
 * some uci commands et read responses from this engine.<br>
 * 
 * Example of use : <br><br>
 *      //test with protector chess engine<br>
 *       UCIChess uci=new UCIChess("C:\\Users\\tondeur-h.CHV\\Downloads\\Protector_1_6_0\\bin\\Protector_Win32.exe");<br>
 *   <br>
 *           //ask uci infos<br>
 *           System.out.println("======================TEST UCI COMMAND======================");<br>
 *          //is uci ok ?<br>
 *           System.out.println("uciok = "+uci.get_uciOk(false));<br>
 *           //engine name and author(s)<br>
 *           System.out.println("Engine Name = "+uci.getEngineName());<br>
 *           System.out.println("Engine Author(s) = "+uci.getEngineAuthor());<br>
 *           System.out.println("==================TEST UCI OPTIONS RETRIEVE=================");<br>
 *           //number of options in uci engine<br>
 *           System.out.println("Numbers of options = "+uci.get_number_options());<br>
 *           //list all uci options (names, type, values)<br>
 *           System.out.format("%-30s %-10s %-20s\n","Name(id)","type","values");<br>
 *           System.out.println("------------------------------------------------------------");<br>
 *           for (int i=0;i&lt;uci.get_number_options();i++)<br>
 *           {<br>
 *               System.out.format("%-30s %-10s %-20s\n",uci.get_option(i).getId(),uci.get_option(i).getType(),uci.get_option(i).getValues() );<br>
 *           }<br>
 *           System.out.println("=====================PLAY A SMALL GAME=====================");<br>
 *           //is engine ready?<br>
 *           System.out.println("isready = "+uci.get_readyOk(false));<br>
 *          <br>
 *           //white play e2e4<br>
 *           System.out.println("White play = e2e4");<br>
 *           uci.move_FromSTART("e2e4 ",false); <br>
 *           System.out.println("-------------------------------------------------------");<br>
 *           //is engine ready for next move?<br>
 *           System.out.println("isready = "+uci.get_readyOk(false));<br>
 *           <br>
 *           //black move (engine play)<br>
 *           uci.send_cmd(UCIChess.GOTHINK); //think for best move<br>
 *           String rep=uci.get_bestMove(false);  //read response<br>
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
 *           System.out.println("Black ponder = "+uci.getPonder()); //best white next move<br>
 *           uci.move_FromSTART("e2e4 "+rep,false); //make move<br>
 *           System.out.println("-------------------------------------------------------");<br>
 *           <br>
 *           //is engine ready for next move?<br>
 *           System.out.println("isready = "+uci.get_readyOk(false));<br>
 *           <br>
 *           //white play g1f3<br>
 *           System.out.println("White play = g1f3");<br>
 *           uci.move_FromSTART("e2e4 "+rep+" g1f3 ",false);<br>
 *           System.out.println("-------------------------------------------------------");<br>
 *           //is engine ready for next move?<br>
 *           System.out.println("isready = "+uci.get_readyOk(false));<br>
 *           <br>
 *           //black play<br>
 *           uci.send_cmd(UCIChess.GOTHINK); //search next move<br>
 *           String rep2=uci.get_bestMove(false);  //read best move<br>
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
 *           System.out.println("Black ponder = "+uci.getPonder()); //best white next move<br>
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
 * @author Tondeur Herve GPL v3.0
 *********************/
public class UCIChess {
    private OutputStream out; //to chess engine
    private BufferedReader in; //from chess engine
    private Process p; //engine runnin Thread
    private String engineName; 
    private String engineAuthor;
    private String ponder;
    private ArrayList<OptionName> listOfOptions;
    private ArrayList<Info> listInfos;
    
    //internal use only
    private boolean isUCICall=false;
    private boolean  isGOCall=false;
    private boolean  isREADYCall=false;
    private boolean isSTOPCall=false;
    private boolean  isQUITCall=false;
    private boolean  isPONDERHITCall=false;
    
    
    
    //list of SIMPLE COMMANDS
    final public static String UCI="uci"; //UCI COMMAND
    final public static String GOTHINK="go";
    final public static String MOVEFROMSTART="position startpos moves ";
    final public static String ISREADY="isready";
    final public static String STOP="stop";
    final public static String QUIT="quit";
    final public static String PONDERHIT="ponderhit";
    
    /***************************
    * construct the API UCIChess<br>
    * Start the chess engine in a ProcessBuilder
    * and connect in and out stream communication
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
            listInfos=new ArrayList<>();
        } catch (IOException ex) {
            Logger.getLogger(UCIChess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /******************************
     * stop the chess engine and close stream with the process.
     ******************************/
    public void stop_Engine(){
        try {
            if (p.isAlive()){p.destroyForcibly();}
            out.close();
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(UCIChess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
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
            String cmd="position fen "+fen+" moves "+moves+"\n"; //add crlf or cr
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
            String cmd="position startpos moves "+moves+"\n"; //add crlf or cr
            if (trace) System.out.println(cmd);
            //send command
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(TestChess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        
        
    /*********************************
     * Send simple command to chess engine<br>
     * like go, uci, isready, position etc...<br>
     * See uci commands documentations for more informations.
     * @param cmd A String with the command to send to the chess engine.
     *********************************/
        public final void send_cmd(String cmd){
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
 
 
 /******************************
  * Check if uci is ready for another command.
  * @param trace A boolean value that print the responses engine.
  * @return A boolean value, true if engine is ready otherwise false.
  ******************************/
    public final boolean get_readyOk(boolean trace){
        //call cmd isready before
        send_cmd(UCIChess.ISREADY);
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
    public final boolean get_uciOk(boolean trace){
        //call uci command before
        send_cmd(UCIChess.UCI);
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
                        parse_option(line);
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
    
    
    
    
 /***********************************
  * Ask the best move calculate by the chess engine.<br>
  * This method can only be uses after a GO command.
  * @param trace A boolean values that print the full chess engine responses.
  * @return A String values contains the best move in an Algebraic Notation.
  *************************************/
    
       public final String get_bestMove(boolean trace){
         try {
             if (!isGOCall) return "0000";
             String line;
             String bestm;
            while ((line=in.readLine())!=null) {
                if (trace) {
                    System.out.println(line);
                }
                
                //keep info into an ArrayList
                if (line.startsWith("info")){
                    listInfos.add(new Info(line.replaceFirst("info ", "")));
                }
                //breaking condition "bestmove"
                if (line.startsWith("bestmove")){
                    try (Scanner sc = new Scanner(line)) {
                        sc.useDelimiter(" "); //space as delimiter
                        sc.next(); //read sttring bestmove
                        bestm=sc.next(); //read bestmove
                        try{
                        sc.next(); //read ponder string
                        ponder=sc.next(); //read ponder value
                        } catch (NoSuchElementException nse){ponder="mate";}
                    } 
                    return bestm; //return move
                } //end if bestmove
            }
              } catch (IOException ex) {
            Logger.getLogger(TestChess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "0000";
    }
 

       /***************************************
        * Return the ponder value after a GO command.<br>
        * This method must be run only after a GO command.
        * @return A String contains the ponder values or "mate" word if a mate is reach.
        ***************************************/
       public String getPonder(){
           if (!isGOCall) ponder="0000";
           if (ponder==null) ponder="0000";
           return ponder;
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
       public String getEngineAuthor(){
           if (!isUCICall) engineAuthor="empty";
           if (engineAuthor==null) engineAuthor="NoName";
           return engineAuthor;
       }

       
    /* ******************************************
      parse option response from Engine and
      construct a ArrayList<OptionName> objects
      @param line 
     ****************************************** */
    private void parse_option(String line) {
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
                 sc.next(); //read default word
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
    public int get_number_options(){
        if (!isUCICall) return 0;
        return listOfOptions.size();
    }
    
    
      /***************************
     * Return the max number of infos after a GO command.<br>
     * You must execute the GO command before used this method.
     * @return An integer containing the count return.
     ***************************/
    public int get_number_infos(){
        if (!isGOCall) return 0;
        return listInfos.size();
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
    public OptionName get_option(int Number){
        if (!isUCICall) return null;
        if (Number>=listOfOptions.size()) return null; //number must begin from 0 to size-1
        return listOfOptions.get(Number);
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
    public Info get_info(int Number){
        if (!isGOCall) return null;
        if (Number>=listInfos.size()) return null; //number must begin from 0 to size-1
        return listInfos.get(Number);
    }
    

/*******************************
 * class encapsulate option name
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
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
   
} //end of OptionName class


/********************
 * class info moves
 * @author tondeur-h
 ********************/
public final class Info {
    String info; //string info to get

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Info(String info) {
        this.info = info;
    }
    
} //end of info class

} //end UCIChess
