# UCIChess
UCI chess protocol API for Java can be apply to construct GUI chess clients.

This Simple API, implement Java methods to exploit Chess Engine with UCI (Universal Chess Interface) protocol compatible.
Like StockFish(https://stockfishchess.org), protector (http://sourceforge.net/projects/protector/), etc...

This API is specifically developed for integer into GUI clients Chess.

A this time, you can exploit most of UCI possibilities:
- uci [ask option name, test uciok, get engine name, get author(s) name(s)]
- get options list from uci command [Number of options and détails (id, type, default value, values admitted)]
- isready [test if engine is ready]
- go [ask engine to search bestmove]
- read best move and ponder move
- read info from go command search.
- read detailled infos from go command search too.
- set ponderhit command
- accept move from start and a list of moves
- accept move from fen position and a list of moves
- You can register, set option, and make complex go commands with the method "send_cmd". 

Warning : this API did not deal with "copyprotection" and "registration" responses from engine (rarely used).

see javadoc for more details...

Tondeur H.
