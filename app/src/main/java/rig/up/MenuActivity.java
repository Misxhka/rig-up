/*package rig.up;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        CardView cardInfo, cardLow, cardMid, cardHi, cardPsu;

        cardInfo = findViewById(R.id.cardInfo);
        cardLow = findViewById(R.id.cardLow);
        cardMid = findViewById(R.id.cardMid);
        cardHi = findViewById(R.id.cardHi);
        cardPsu = findViewById(R.id.cardPsu);

        cardInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuickInfoActivity.class);
                startActivity(intent);
            }
        });


    }
}
*/