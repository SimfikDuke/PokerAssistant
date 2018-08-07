package duke.simfik.poker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class winner extends AppCompatActivity {
    CheckBox[] winnersCheck;
    int playersCount;
    Button button;
    boolean[] winners;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        winnersCheck = new CheckBox[10];
        winnersCheck[0] = (CheckBox)findViewById(R.id.player1);
        winnersCheck[1] = (CheckBox)findViewById(R.id.player2);
        winnersCheck[2] = (CheckBox)findViewById(R.id.player3);
        winnersCheck[3] = (CheckBox)findViewById(R.id.player4);
        winnersCheck[4] = (CheckBox)findViewById(R.id.player5);
        winnersCheck[5] = (CheckBox)findViewById(R.id.player6);
        winnersCheck[6] = (CheckBox)findViewById(R.id.player7);
        winnersCheck[7] = (CheckBox)findViewById(R.id.player8);
        winnersCheck[8] = (CheckBox)findViewById(R.id.player9);
        winnersCheck[9] = (CheckBox)findViewById(R.id.player10);
        playersCount = getIntent().getIntExtra("playersCount",2);
        boolean[] foldedPlayers  = getIntent().getBooleanArrayExtra("foldedPlayers");
        for(int i = 0; i < playersCount; i++){
            if(foldedPlayers[i]){
                winnersCheck[i].setVisibility(View.INVISIBLE);
            }
        }
        for(int i = playersCount; i < 10; i++){
            winnersCheck[i].setVisibility(View.INVISIBLE);
        }
        button = (Button) findViewById(R.id.button4);
        winners = new boolean[playersCount];
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override public void onBackPressed(){
        for(int i = 0; i<playersCount; i++){
            if (winnersCheck[i].isChecked()){
                winners[i] = true;
            }
            else {
                winners[i] = false;
            }
        }
        Intent winnerIntent = new Intent();
        winnerIntent.putExtra("Winners",winners);
        setResult(RESULT_OK,winnerIntent);
        finish();
    }

}
