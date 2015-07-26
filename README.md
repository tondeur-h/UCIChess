# UCIChess
UCI chess protocol API for Java applying to GUI chess clients.

This Simple API, implement Java methods to exploit Chess Engine with UCI (Universal Chess Interface) protocol compatible.
Like StockFish(https://stockfishchess.org), protector (http://sourceforge.net/projects/protector/), etc...

This API is specifically developed for integer into GUI clients Chess.

A this time, you can exploit half of UCI possibilities:
- uci [ask option name, test uciok, get engine name, get author(s) name(s)]
- get options list from uci command [Number of options and détails (id, type, default value, values admitted)]
- isready [test if engine is ready]
- go [ask engine to search bestmove]
- read best move and ponder move
- set ponderhit command
- accept move from start and a list of moves
- accept move from fen position and a list of moves
- You can register, set option, and make complex go commands with the method "send_cmd". 

nb : Info responses management are not implemented at this time.
this API did not deal with "copyprotection" and "registration" responses from engine (rarely used).

Tondeur H.
