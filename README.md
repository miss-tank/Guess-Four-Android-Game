# Guess-Four-Android-Game


In this game, each of two players sets up a secret sequence of four decimal digits, where each digit is not
repeated. For instance, 2017 is a legal sequence, but 2012 is not because the digit 2 is repeated. The two
players take turns guessing the sequence of digits of their opponent. Each guess consists of a 4 digit number, 
without repeated digits. A player responds to an opponent’s guess by specifying the number of digits that were
successfully guessed in the correct position and the number of digits that were successfully guessed but in the
wrong positions. Thus, if a players chosen number is 2017 and the opponent guesses 1089, the opponent would be
told that one digit was guessed correctly in the correct position (i.e., the 0), and another digit was guessed
in the wrong position, (i.e., the 1). This application has two background threads play against each other while
also updating the user interface with their moves. The first thread to correctly guess the opposing thread’s
number wins the game. 
