package ucichess;

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
 * UCIChess API
 * @author tondeur-h
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
    
    
    private boolean isUCICall=false;
    private boolean  isGOCall=false;
    private boolean  isREADYCall=false;
    private boolean isSTOPCall=false;
    private boolean  isQUITCall=false;
    private boolean  isPONDERHITCall=false;
    
    
    
    //list of SIMPLE COMMANDS
    final static String UCI="uci";
    final static String GOTHINK="go";
    final static String MOVEFROMSTART="position startpos moves ";
    final static String ISREADY="isready";
    final static String STOP="stop";
    final static String QUIT="quit";
    final static String PONDERHIT="ponderhit";
    
    /***************************
    * construct the API UCIChess
    *
    * @param engine 
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
     * stop engine and clear datas
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
     * send cmd move FEN format to chess engine
     * @param fen 
     * @param moves 
     * @param trace 
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
     * send cmd move from start to chess engine
     * @param moves 
     * @param trace 
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
     * send simple cmd to chess engine
     * with no cmd parameter
     * like go, uci, isready etc...
     * @param cmd  
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
  * check if uci is ready
  * break condition is "readyok"
  * @param trace
  * @return 
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
  * check if uci is ok
  * and get name & author
  * break condition is "uciok"
  * @param trace
  * @return 
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
  * ask best move
  * only use after GO command
  * @param trace
  * @return 
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
        * return the ponder value
        * must run go command before
        * @return 
        ***************************************/
       public String getPonder(){
           if (!isGOCall) ponder="0000";
           if (ponder==null) ponder="0000";
           return ponder;
       }

       
       /***************************************
        * return the name of the running engine
        * must run uci command before
        * @return 
        ***************************************/
       public String getEngineName(){
           if (!isUCICall) engineName="empty";
           if (engineName==null) engineName="NoName";
           return engineName;
       }

       /***************************************
        * return the author of the running engine
        * must run uci command before
        * @return 
        ***************************************/       
       public String getEngineAuthor(){
           if (!isUCICall) engineAuthor="empty";
           if (engineAuthor==null) engineAuthor="NoName";
           return engineAuthor;
       }

       
    /********************************************
     * parse option response from Engine and
     * construct a ArrayList<OptionName> objects
     * @param line 
     ********************************************/
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
     * return max number options
     * @return 
     ***************************/
    public int get_number_options(){
        if (!isUCICall) return 0;
        return listOfOptions.size();
    }
    
    
      /***************************
     * return max number infos
     * @return 
     ***************************/
    public int get_number_infos(){
        if (!isGOCall) return 0;
        return listInfos.size();
    }
    
    
    /**************************
     * return option number X
     * @param Number
     * @return
     **************************/
    public OptionName get_option(int Number){
        if (!isUCICall) return null;
        if (Number>=listOfOptions.size()) return null; //number must begin from 0 to size-1
        return listOfOptions.get(Number);
    }

    
    /**************************
     * return info number X
     * @param Number
     * @return
     **************************/
    public Info get_info(int Number){
        if (!isGOCall) return null;
        if (Number>=listInfos.size()) return null; //number must begin from 0 to size-1
        return listInfos.get(Number);
    }

    
} //end UCIChess


/*******************************
 * class encapsulate option name
 * @author tondeur-h
 *******************************/
final class OptionName{
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
final class Info {
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

/**************************
 * a square of a chessboard
 * @author tondeur-h
 **************************/
final class Square{
/**naming convention
 * ==================
 * an a normal chessboard
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
  * convert moves list into fen String
  * @param moves
  * @return 
  *************************************/
 public String movesToFen(String moves){
     String fen="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
 return fen;
 }
 
 
 /*****************************************
  * Convert a string chessboard coordinate
  * into a numeric convention
  * @param coord 
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
         * get Row From coord
         * using convention
         * * a=1;b=2;c=3;d=4;e=5;f=6;g=7;h=8
         * @return 
         ***********************************/
    public static int getRowFrom() {
        return rowFrom;
    }

        /***********************************
         * get col From coord
         * using convention
         * * 1=1;2=2;3=3;4=4;5=5;6=6;7=7;8=8
         * @return 
         ************************************/    
    public static int getColFrom() {
        return colFrom;
    }

        /************************************
         * get Row To coord
         * using convention
         * * a=1;b=2;c=3;d=4;e=5;f=6;g=7;h=8
         * @return 
         ************************************/
    public static int getRowTo() {
        return rowTo;
    }

        /***********************************
         * get col To coord
         * using convention
         * * 1=1;2=2;3=3;4=4;5=5;6=6;7=7;8=8
         * @return 
         ************************************/
    public static int getColTo() {
        return colTo;
    }

    /************************
     * get promote value
     * a chess piece letter
     * or empty if no promote
     * @return 
     *************************/
    public static String getPromote() {
        return promote;
    }
    
} //end of Square class


