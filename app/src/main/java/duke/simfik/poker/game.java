package duke.simfik.poker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class game extends AppCompatActivity {
TextView scoresView,statusView,bankView;
SeekBar seekBar;
boolean gameover;
Button button, call, fold, reiz;
int playersCount, bank, turn,stage,check;
Player[] players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        bank = 0;
        turn = 0;
        stage=0;
        gameover = false;
        initPlayers();
        initButtons();
        updateStatus();
        hideButtons();
        first();
    }
    protected void initPlayers(){
        playersCount = Integer.parseInt(getIntent().getExtras().getString("playersCount"));
        players = new Player[playersCount];
        for (int i=0;i<playersCount;i++){
            players[i] = new Player("Игрок "+String.valueOf(i+1), getIntent().getIntExtra(String.valueOf(i),1000));
        }
    }
    protected void initButtons(){
        scoresView = (TextView)findViewById(R.id.textView3);
        statusView = (TextView)findViewById(R.id.textView4);
        button = (Button)findViewById(R.id.button2);
        bankView = (TextView)findViewById(R.id.bank);
        fold = (Button)findViewById(R.id.fold);
        call = (Button)findViewById(R.id.call);
        reiz = (Button)findViewById(R.id.reiz);
        seekBar = (SeekBar) findViewById(R.id.seekBar2);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                players[turn].cash -= (minLastBet()-players[turn].getLastBet());
                bank += minLastBet()-players[turn].getLastBet();
                players[turn].setLastBet(minLastBet());
                updateStatus();
                second(turn+1);
            }
        });
        fold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                players[turn].setFolded(true);
                updateStatus();
                second(turn+1);
            }
        });
        reiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                players[turn].cash -= (-players[turn].getLastBet()+minLastBet()+seekBar.getProgress()+1);
                bank +=(-players[turn].getLastBet()+minLastBet()+seekBar.getProgress()+1);
                players[turn].setLastBet(seekBar.getProgress()+1+minLastBet());
                updateStatus();
                seekBar.setProgress(0);
                check = playersCount-1;
                second(turn+1);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(gameover){
                    bankView.setText("Победитель: "+players[progress].name);
                }
                else {
                    int cal = minLastBet()-players[turn].getLastBet();
                    if(cal>0)call.setText("КОЛЛ("+String.valueOf(minLastBet()-players[turn].getLastBet())+")");
                    else call.setText("ЧЕК");
                    reiz.setText("РЕЙЗ("+String.valueOf(progress+minLastBet()+1-players[turn].getLastBet())+")");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus();
                if (gameover){
                    players[seekBar.getProgress()].cash += bank;
                    bank = 0;
                    turn = 0;
                    stage = 0;
                    gameover = false;
                    first();
                }
                else {
                    Intent intent = new Intent(game.this, info.class);
                    startActivity(intent);
                }
            }
        });
    }
    protected void updateStatus(){
        String status = "Счет: \n";
        for(int i=0; i<playersCount; i++){
            status += players[i].name + " имеет " + String.valueOf(players[i].cash)+" фишек";
            if(!players[i].isFolded()){
                if(players[i].getLastBet() > 0){
                    status += "  Ставка: " + String.valueOf(players[i].getLastBet());
                }
            }
            else {
                status += "  ПАСС";
            }
            status+="\n";
        }
        scoresView.setText(status);
        bankView.setText("Банк: "+String.valueOf(bank));
    }
    protected void hideButtons(){
        seekBar.setVisibility(View.INVISIBLE);
        call.setVisibility(View.INVISIBLE);
        fold.setVisibility(View.INVISIBLE);
        reiz.setVisibility(View.INVISIBLE);
        bankView.setVisibility(View.INVISIBLE);
        scoresView.setVisibility(View.INVISIBLE);
        statusView.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
    }
    protected void showButtons(){
        seekBar.setVisibility(View.VISIBLE);
        call.setVisibility(View.VISIBLE);
        fold.setVisibility(View.VISIBLE);
        reiz.setVisibility(View.VISIBLE);
        bankView.setVisibility(View.VISIBLE);
        scoresView.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
    }

    protected void first(){
        for(int i=0; i<playersCount; i++){
            players[i].setFolded(false);
            players[i].setLastBet(0);
        }
        button.setText("ИНФО");
        hideButtons();
        AlertDialog.Builder a_builder = new AlertDialog.Builder(game.this);
        a_builder.setMessage("Раздайте по две карты каждому игроку").setCancelable(false)
                .setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        second(0);
                        updateStatus();
                        showButtons();
                    }
                });
        AlertDialog alertDialog = a_builder.create();
        alertDialog.setTitle("Начинается игра на "+String.valueOf(playersCount)+" человек");
        alertDialog.show();
    }
    protected void clearBets(){
        for(int i=0; i<playersCount; i++){
            players[i].setLastBet(0);
        }
    }
    protected int minLastBet(){
        int minBet = 10;
        for(int i=0; i<playersCount; i++){
            if(players[i].getLastBet()>minBet){
                minBet = players[i].getLastBet();
            }
        }
        return minBet;
    }
    protected boolean betEq(){
        int minB = 100000;
        int maxB = 0;
        for(int i=0; i<playersCount; i++) {
            if (!players[i].isFolded()) {
                if (players[i].getLastBet() < minB) {
                    minB = players[i].getLastBet();
                }
                if (players[i].getLastBet() > maxB) {
                    maxB = players[i].getLastBet();
                }
            }
        }
        if(minB==maxB) return true;
        else return false;
    }
    protected void second(int player){
        if(!allPassed()){
            if(player < playersCount) {
                if (players[player].isFolded()){
                    second(player+1);
                }
                else {
                    turn = player;
                    check--;
                    int cal = minLastBet() - players[turn].getLastBet();
                    if (cal > 0)
                        call.setText("КОЛЛ(" + String.valueOf(minLastBet() - players[turn].getLastBet()) + ")");
                    else call.setText("ЧЕК");
                    reiz.setText("РЕЙЗ(" + String.valueOf(seekBar.getProgress() + minLastBet() + 1 - players[turn].getLastBet()) + ")");
                    statusView.setText(players[player].name + " ставит");
                    seekBar.setMax(players[player].cash);
                }
            }
            else {
                if(betEq() && check<=0) {
                    clearBets();
                    updateStatus();
                    String mess1 = "Следующий этап";
                    String mess2 = "";
                    if(stage==0){
                        mess2 = "Выложите на стол 3 карты";
                    }
                    else if(stage==1){
                        mess2 = "Выложите на стол 4-ю карту";
                    }
                    else if(stage==2){
                        mess2 = "Выложите на стол 5-ю карту";
                    }
                    else if(stage==3){
                        mess2 = "Партия закончена!";
                    }
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(game.this);
                    a_builder.setMessage(mess2).setCancelable(false)
                            .setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(stage < 3){
                                        hideButtons();
                                        stage++;
                                        second(stage%playersCount);
                                        updateStatus();
                                        showButtons();
                                    }
                                    else {
                                        gameover=true;
                                        gameover();
                                    }
                                }
                            });
                    AlertDialog alertDialog = a_builder.create();
                    alertDialog.setTitle(mess1);
                    alertDialog.show();
                }
                else second(0);
            }
        }
        else {
            final int winner = whoNotPassed();
            hideButtons();
            AlertDialog.Builder a_builder = new AlertDialog.Builder(game.this);
            a_builder.setMessage("Партия закончена!").setCancelable(false)
                    .setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            players[winner].cash += bank;
                            bank = 0;
                            turn = 0;
                            stage = 0;
                            first();
                        }
                    });
            AlertDialog alertDialog = a_builder.create();
            alertDialog.setTitle("Победитель: " + players[winner].name);
            alertDialog.show();
        }
    }
    protected boolean allPassed(){
        int gamers = 0;
        for(int i=0; i<playersCount; i++){
            if (!players[i].isFolded()){
                gamers++;
            }
        }
        if (gamers>1){
            return false;
        }
        return true;
    }
    protected int whoNotPassed(){
        for(int i=0; i<playersCount; i++){
            if (!players[i].isFolded()){
                return i;
            }
        }
        return 0;
    }
    protected void gameover(){
        statusView.setText("Укажите победителя");
        fold.setVisibility(View.INVISIBLE);
        call.setVisibility(View.INVISIBLE);
        reiz.setVisibility(View.INVISIBLE);
        seekBar.setMax(playersCount-1);
        button.setText("ВЫБРАТЬ");
    }
}
