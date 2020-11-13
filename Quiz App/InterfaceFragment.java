package alyss.example.quizapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class InterfaceFragment extends Fragment {

    Button mButton;
    TextView q, tv1, tv2, tv3, tv4;
    int selected = 0;
    int countQ = 0;
    String[] questions, answers;
    int[] choice;
    MyFragmentInterface mCallback;
    private SharedPreferences.Editor mEditor;


    public InterfaceFragment() {
        // Required empty public constructor
    }

    public static InterfaceFragment newInstance() {
        InterfaceFragment fragment = new InterfaceFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (MyFragmentInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interface, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // assign callbacks, etc
        super.onViewCreated(view, savedInstanceState);

        q = view.findViewById(R.id.tv_main);

        questions = getResources().getStringArray(R.array.questions);
        answers = getResources().getStringArray(R.array.answers);
        choice = getResources().getIntArray(R.array.choice);


        mButton = view.findViewById(R.id.button_pressme);
        tv1 = view.findViewById(R.id.textView1);
        tv2 = view.findViewById(R.id.textView2);
        tv3 = view.findViewById(R.id.textView3);
        tv4 = view.findViewById(R.id.textView4);

        Application.updateQs(q, questions, countQ);
        Application.updateChoices(tv1, tv2, tv3, tv4, answers, countQ);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countQ<choice.length && selected==choice[countQ]) mCallback.questionCorrect();

                countQ++;
                Application.updateQs(q, questions, countQ);
                Application.updateChoices(tv1, tv2, tv3, tv4, answers, countQ);
                Application.refresh(tv1, tv2, tv3, tv4, mButton);

                mCallback.onFragmentInteraction();
            }
        });

        MyListener hello = new MyListener();
        tv1.setOnClickListener(hello);
        tv2.setOnClickListener(hello);
        tv3.setOnClickListener(hello);
        tv4.setOnClickListener(hello);
    }

    public static final class Application {

        private Application() {}

        public static void updateQs(TextView q, String[] questions, int countQ){
            if(countQ<questions.length) {
                q.setText(questions[countQ]);
            }
            else {//finished all questions!
                q.setText("Congratulations! You finished! Swipe right for leaderboard.");
            }
        }

        public static void updateChoices(TextView tv1, TextView tv2, TextView tv3, TextView tv4, String[] answers, int countQ) {
            if(countQ*4<answers.length) {
                tv1.setText(answers[countQ * 4]);
                tv2.setText(answers[countQ * 4 + 1]);
                tv3.setText(answers[countQ * 4 + 2]);
                tv4.setText(answers[countQ * 4 + 3]);
            }
            else{ //finished all questions!
                tv1.setText("");
                tv2.setText("");
                tv3.setText("");
                tv4.setText("");

            }
        }

        public static void refresh(TextView tv1, TextView tv2, TextView tv3, TextView tv4, Button mButton) {
            tv1.setTextColor(tv1.getResources().getColor(R.color.black));
            tv2.setTextColor(tv2.getResources().getColor(R.color.black));
            tv3.setTextColor(tv3.getResources().getColor(R.color.black));
            tv4.setTextColor(tv4.getResources().getColor(R.color.black));
            mButton.setText("Select an answer or click to skip");
        }
    }

    class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v){

            if(v.getId()== R.id.textView1) {
                selected=1;
                tv1.setTextColor(getResources().getColor(R.color.blue));

                tv2.setTextColor(getResources().getColor(R.color.black));
                tv3.setTextColor(getResources().getColor(R.color.black));
                tv4.setTextColor(getResources().getColor(R.color.black));

                mButton.setText("SUBMIT");

            }
            else if(v.getId()== R.id.textView2) {
                selected=2;
                tv2.setTextColor(getResources().getColor(R.color.blue));

                tv1.setTextColor(getResources().getColor(R.color.black));
                tv3.setTextColor(getResources().getColor(R.color.black));
                tv4.setTextColor(getResources().getColor(R.color.black));
                mButton.setText("SUBMIT");


            }
            else if(v.getId()== R.id.textView3) {
                selected=3;
                tv3.setTextColor(getResources().getColor(R.color.blue));

                tv1.setTextColor(getResources().getColor(R.color.black));
                tv2.setTextColor(getResources().getColor(R.color.black));
                tv4.setTextColor(getResources().getColor(R.color.black));
                mButton.setText("SUBMIT");


            }
            else if(v.getId()== R.id.textView4){
                selected=4;
                tv4.setTextColor(getResources().getColor(R.color.blue));

                tv1.setTextColor(getResources().getColor(R.color.black));
                tv2.setTextColor(getResources().getColor(R.color.black));
                tv3.setTextColor(getResources().getColor(R.color.black));
                mButton.setText("SUBMIT");


            }
        }
    }

    public interface MyFragmentInterface {
        void onFragmentInteraction();
        void questionCorrect();
    }


}