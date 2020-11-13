package alyss.example.quizapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends FragmentActivity implements InterfaceFragment.MyFragmentInterface{

    private ViewPager2 mVP;
    private FragmentStateAdapter pageAdapter;
    private TextView mTextView;
    private int count=1;
    private int score=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVP = findViewById(R.id.pages);
        pageAdapter = new ScreenSlidePagerAdapter(this);
        mVP.setAdapter(pageAdapter);
        mTextView = findViewById(R.id.counter);
        mTextView.setText("Question Number 1    Number Correct 0");


    }

    @Override
    public void onFragmentInteraction() {

        if(count< getResources().getStringArray(R.array.questions).length) count++; //count not larger than total question count
        mTextView.setText( "Question Number " + count + "   Number Correct " + score);
    }

    @Override
    public void questionCorrect() {
        score++;
    }

    public int getScore(){
        return score;
    }


    @Override
    public void onBackPressed() {
        if (mVP.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mVP.setCurrentItem(mVP.getCurrentItem() - 1);
        }
    }


    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch(position) {

                case 0: return new InterfaceFragment();
                case 1: return ArgumentFragment.newInstance();
                default: return new InterfaceFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2; //number of pages
        }
    }

}
