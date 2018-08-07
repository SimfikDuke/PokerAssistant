package duke.simfik.poker;

import android.content.Intent;
import android.graphics.Color;
import android.media.ImageWriter;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class info extends AppCompatActivity implements ViewSwitcher.ViewFactory {
    Button button;
    ImageView imageView;
    ScrollView scrollView;
    int counter;
    TextSwitcher mTextSwitcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        scrollView = (ScrollView) findViewById(R.id.scr);
        imageView = (ImageView) findViewById(R.id.imageView2);
        counter = 2;
        mTextSwitcher = (TextSwitcher) findViewById(R.id.infotext);
        mTextSwitcher.setFactory(this);

        Animation inAnimation = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        Animation outAnimation = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        mTextSwitcher.setInAnimation(inAnimation);
        mTextSwitcher.setOutAnimation(outAnimation);
        mTextSwitcher.setText(getString(R.string.rules1));
        scrollView.setVisibility(View.INVISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.VISIBLE);
            }
        });
        mTextSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter==1){
                    mTextSwitcher.setText(getString(R.string.rules1));
                    counter++;
                    imageView.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                }
                else if(counter==2){
                    mTextSwitcher.setText(getString(R.string.rules2));
                    counter++;
                }
                else if(counter==3){
                    mTextSwitcher.setText(getString(R.string.rules3));
                    counter++;
                }
                else {
                    mTextSwitcher.setText(getString(R.string.rules4));
                    counter=1;
                }
            }
        });

        button = (Button)findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public View makeView() {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        return textView;
    }
}
