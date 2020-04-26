package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class setLevel extends Activity {
    private ImageButton easy, medium, hard, back;
    private Button creativity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        easy = (ImageButton) findViewById(R.id.btn_easy);
        medium = (ImageButton) findViewById(R.id.btn_medium);
        hard = (ImageButton) findViewById(R.id.btn_hard);
        back = (ImageButton) findViewById(R.id.Btn_Back);
        creativity = (Button) findViewById(R.id.creativity_btn);

        easy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setLevel.this, activity_gameType.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setLevel.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
