package alyss.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.autumn_sounds);
        mediaPlayer.start();

    }
    @Override
    public void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}