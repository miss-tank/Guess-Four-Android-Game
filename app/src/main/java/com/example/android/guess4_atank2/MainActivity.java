package com.example.android.guess4_atank2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Ankita Tank
 * CS 478
 * Project 4
 */

public class MainActivity extends AppCompatActivity {

    /**
     * Required global variables.
     **/


    Button start_button;

    String wrongNumbers="";
    String rightNumbers="";

    public  String Final="";

    int d1,d2,d3,d4;

    ArrayList<String> array_list1 = new ArrayList<String>(20);
    ArrayList<String> array_list2 = new ArrayList<String>(20);

    //Flags used for game
    private boolean gameInProgress;
    private boolean gameEnded;



    public int computation_count=0;

    public boolean WIN=false;

    String player1_number_string;
    String player2_number_string;


    public static final int TURN_FINISHED_P1 = 0;
    public static final int TURN_FINISHED_P2 = 1;

    public Handler UI_Handler;
    public Handler player1_Handler;
    public Handler player2_Handler;


    public Player player1;
    public Player player2;


    public int p1_number;
    public int p2_number;

    public int p1_alg_number;
    public int p2_alg_number;

    public String p1_alg_number_string;
    public String p2_alg_number_string;

     ArrayAdapter<String> arrayAdapter1;
     ArrayAdapter<String> arrayAdapter2 ;


    /**
     * retrive all the views from XML
     */

    ListView list_view1;
    ListView list_view2;

    TextView win1;
    TextView win2;

    TextView player1_guess_number;
    TextView player2_guess_number;

    TextView player1_number;
    TextView player2_number;


    TextView player1_score;
    TextView player2_score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * set all the view from XML to manipulate data accordindly.
         */

        start_button= (Button)findViewById(R.id.start_button);

        start_button.setOnClickListener(startlistener);

        player1_guess_number=(TextView)findViewById(R.id.Player1_guess);
        player2_guess_number=(TextView)findViewById(R.id.Player2_guess);

        player1_number=(TextView) findViewById(R.id.Player1_chosen_number);
        player2_number=(TextView) findViewById(R.id.Player2_chosen_number);



        win1=(TextView) findViewById(R.id.winner1);
        win2=(TextView) findViewById(R.id.winner2);


        list_view1 = (ListView) findViewById(R.id.list1);

        list_view2 = (ListView) findViewById(R.id.list2);




       arrayAdapter1  = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, array_list1);


        arrayAdapter2  = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, array_list2);


        /**
         * initailise flags for game progress
         */
        gameInProgress = false;
        gameEnded = false;

    }

    /**
     * Random number generation function.
     * @return int
     */
    public int getRandomNumber()
    {

        int number=0;

            Random r = new Random();
            d1 = 1;
            d2 = r.nextInt(9);
            d3 = r.nextInt(6);
            d4 = r.nextInt(4);
            while (d1 == d2 || d1 == d3 || d1 == d4 || d2 == d3 || d2 == d4 || d3 == d4) {
                if (d1 == d2 || d2 == d3 || d2 == d4) {
                    d2 = r.nextInt(3);
                }
                if (d1 == d3 || d2 == d3 || d3 == d4) {
                    d3 = r.nextInt(5);

                }
                if (d1 == d4 || d2 == d4 || d3 == d4) {
                    d4 = r.nextInt(7);
                }
            }

            d1=d1*1000;
            d2=d2*100;
            d3=d3*10;

            number = d1+d2+d3+d4;
        return number;
    }

    /**
     * assign player a random number for the opponent to guess
     * @return
     */
    public void generatePlayerNumbers()
    {
        p1_number = getRandomNumber();
        System.out.println("This is the p1_number generated " + p1_number);

        p2_number = getRandomNumber();
        System.out.println("This is the p1_number generated " + p2_number);

        p2_number = getRandomNumber();
        p1_number = getRandomNumber();
        //p1_number= 1023;
        //p2_number= 1223;

        p1_alg_number=p2_number-19;
        p2_alg_number=getRandomNumber();


        //p1_alg_number=1119;
        //p2_alg_number=1019;


        list_view1.setAdapter(arrayAdapter1);
        list_view2.setAdapter(arrayAdapter2);
    }

    /**
     * Start the game
     */
    public View.OnClickListener startlistener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            if(gameInProgress==false && gameEnded==false)
            {
                gameInProgress=true;
                generatePlayerNumbers();
                initUIHandler();
                initializeGame();
            }
            else {
                System.out.println("uoi clicked while not running ");
                if (gameEnded == false) {
                    resetGame();
                }

                gameInProgress = true;
                gameEnded = false;
            }
        }
    };

    /**
     * Iniltilize the game  by setting player 1 first
     * @return
     */
    public void initializeGame()
    {

        player1 = new Player();
        player2 = new Player();

        player1.start();
        player2.start();

        player1_Handler = player1.getPlayerHandler();
        player2_Handler = player2.getPlayerHandler();

        setViews();

        player1_Handler.post(new Runnable() {
            public void run() {
                player1.player1_move();
                Message m = UI_Handler.obtainMessage(TURN_FINISHED_P1);
                UI_Handler.sendMessage(m);
            }
        });
    }

    /**
     * Update the UI
     * @return
     */
    public void setViews()
    {
        player1_number_string = Integer.toString(p1_number);
        player2_number_string = Integer.toString(p2_number);

        player1_number.setText(player1_number_string);
        player2_number.setText(player2_number_string);

        p1_alg_number_string = Integer.toString(p1_alg_number);
        p2_alg_number_string = Integer.toString(p2_alg_number);

        player1_guess_number.setText(p1_alg_number_string);
        player2_guess_number.setText(p2_alg_number_string);
    }

    /**
     * Reset game
     * set all attributes to 0
     * @return void
     */

    private void resetGame()
    {
        Message m1 = player1_Handler.obtainMessage(player1.END_OF_GAME);
        Message m2 = player2_Handler.obtainMessage(player2.END_OF_GAME);
        player1_Handler.sendMessage(m1);
        player2_Handler.sendMessage(m2);

        UI_Handler.removeCallbacksAndMessages(null);

        WIN=false;

        Final="";
        gameInProgress = true;
        gameEnded = false;

        player1_number.setText("0000");
        player2_number.setText("0000");

        player1_guess_number.setText("0000");
        player2_guess_number.setText("0000");

        win1.setText("");
        win2.setText("");



        generatePlayerNumbers();
        initUIHandler();
        initializeGame();

        array_list1.clear();
        array_list2.clear();

        arrayAdapter1  = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, array_list1);


        arrayAdapter2  = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, array_list2);

        list_view1.setAdapter(arrayAdapter1);
        list_view2.setAdapter(arrayAdapter2);

    }

    /**
     * Keep switching between the player ising the thread msg
     */

    void initUIHandler()
    {
        UI_Handler = new Handler()
        {
            public void handleMessage(Message mess)
            {
                int message = mess.what;
                switch(message)
                {
                    case TURN_FINISHED_P1:
                        if(WIN==false) {
                            if (guess_correct(p2_alg_number, p1_number)) {
                                win2.setText("YOU WON!!");
                                win1.setText("YOU LOSE!!");

                                compute(p1_alg_number, p2_number, 1);
                                guess_update(1);
                                WIN = true;

                            } else {

                                compute(p2_alg_number, p1_number, 1);
                                guess_update(1);

                                Message m2 = player2_Handler.obtainMessage(player2.BEGIN_TURN_P2);
                                player2_Handler.sendMessage(m2);
                            }
                        }
                        break;

                    case TURN_FINISHED_P2:

                        if(WIN==false) {
                            if (guess_correct(p1_alg_number, p2_number)) {
                                WIN = true;
                                win1.setText("YOU WON!!");
                                win2.setText("YOU LOSE!!");
                                compute(p2_alg_number, p1_number, 2);
                                guess_update(2);

                            } else {
                                compute(p1_alg_number, p2_number, 2);
                                guess_update(2);

                                Message m1 = player1_Handler.obtainMessage(player2.BEGIN_TURN_P1);
                                player1_Handler.sendMessage(m1);
                            }


                        }
                        break;
                }
            }
        };
    }


    /**
     * function computing the numbers from guess and player number
     */
    public void compute(int a, int b,int p) {
        int[] a_array = new int[4];
        int[] b_array = new int[4];

        String number_a = String.valueOf(a);
        String number_b = String.valueOf(b);

        for (int i = 0; i < number_a.length(); i++) {
            int j = Character.digit(number_a.charAt(i), 10);
            a_array[i] = j;
        }

        for (int i = 0; i < number_b.length(); i++) {
            int j = Character.digit(number_b.charAt(i), 10);
            b_array[i] = j;
        }


        int wrong = 0;
        int right = 0;


        for (int i = 0; i < a_array.length; i++) {
            if (a_array[i] == b_array[i]) {
                right++;
                int x = a_array[i];
                rightNumbers += "," + Integer.valueOf(x);

            } else {
                int x = a_array[i];
                wrong++;
                wrongNumbers += "," + Integer.valueOf(x);

            }
        }

        Final = " " + rightNumbers + " ✔️  || " + wrongNumbers + " X";

        System.out.println("this my final string " + Final);
        wrongNumbers = "";
        rightNumbers = "";
    }

    public void guess_update(int player)
    {
            if(player==1)
            {
                p1_alg_number_string= Integer.toString(p1_alg_number);
                player1_guess_number.setText(p1_alg_number_string);
                array_list2.add(String.valueOf(p2_alg_number)+"  ||  " + Final);
            }

            if(player==2)
            {
                p2_alg_number_string= Integer.toString(p2_alg_number);
                player2_guess_number.setText(p2_alg_number_string);
                array_list1.add(String.valueOf(p1_alg_number) +"  || " + Final);
            }

        arrayAdapter1.notifyDataSetChanged();
        arrayAdapter2.notifyDataSetChanged();

        try {  Thread.sleep(900); }
        catch (InterruptedException e)
        {
            System.out.println("Broken Thread Sync");
        }
    }

    /**
     *Check the numbers
     */
    public boolean guess_correct(int alg_number, int player_number)
    {
        if(alg_number==player_number)
            return true;
        else
            return false;
    }

    /**
     * player class to assign each player an attribute
     */
    private class Player extends Thread {
        private Handler player_Handler;

        int score;
        int guess_number;

        //Flags used for Handler
        public static final int BEGIN_TURN_P1 = 0;
        public static final int BEGIN_TURN_P2 = 1;
        public static final int END_OF_GAME = 2;

        public Player() {
            guess_number = 0;
            score = 0;
        }

        public void run() {
            initPlayerHandler();
        }

        /**
         * assign each player a thread message.
         * sent messages to the handler.
         */
        public void initPlayerHandler() {
            Looper.prepare();
            synchronized (this) {
                player_Handler = new Handler() {
                    public void handleMessage(Message msg) {
                        if (computation_count < 40 && WIN==false)
                        {
                            computation_count++;
                            int what = msg.what;
                            switch (what)
                            {
                                case BEGIN_TURN_P1:
                                    player1_move();
                                    Message m1 = UI_Handler.obtainMessage(TURN_FINISHED_P1);
                                    UI_Handler.sendMessage(m1);
                                    break;

                                case BEGIN_TURN_P2:
                                    player2_move();
                                    Message m2 = UI_Handler.obtainMessage(TURN_FINISHED_P2);
                                    UI_Handler.sendMessage(m2);
                                    break;

                                case END_OF_GAME:
                                    System.out.println("GAME STOP");

                                    player_Handler.getLooper().quit();
                                    player_Handler.removeCallbacksAndMessages(null);
                                    break;
                                default:
                            }
                        }
                    }
                };
                notifyAll();
            }
            Looper.loop();
        }

        public synchronized Handler getPlayerHandler() {
            while (player_Handler == null) {
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }
            return player_Handler;
        }

        /**
         * player 1 will win no matter what
         * thats just how i have set it
         * I dont want both of them to lose :/
         */
        public void player1_move() {
            p1_alg_number++;
        }

        /**
         * player 2 will generate random numbers from 1000-1999
         */
        public void player2_move() {
           p2_alg_number= getRandomNumber();
            //p2_alg_number++;
        }

    }
}
