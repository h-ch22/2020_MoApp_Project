package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class activity_gameType extends Activity {
    private ImageButton Btn_Back;
    private Button Btn_Multi, Btn_Single;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplay);

        Btn_Back = (ImageButton) findViewById(R.id.Btn_Back);
        Btn_Multi = (Button) findViewById(R.id.Btn_Multi);
        Btn_Single = (Button) findViewById(R.id.Btn_Single);

        Btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_gameType.this, setLevel.class);
                startActivity(intent);
                finish();
            }
        });

        Btn_Single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_gameType.this, QuestionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
