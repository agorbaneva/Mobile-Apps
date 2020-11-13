package alyss.example.quizapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ArgumentFragment extends Fragment implements View.OnClickListener{

    //its named ArgumentFragment but it doesnt use arguments haha

    TextView mLeaderBoard, tv11, tv12, tv21, tv22, tv31, tv32;
    Button mButton; String name;
    SharedPreferences mySharedPrefs; Gson gson;
    private SharedPreferences.Editor mEditor;

    public ArgumentFragment() {
        // Required empty public constructor
    }

    public static ArgumentFragment newInstance() {
        ArgumentFragment fragment = new ArgumentFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_argument, container, false);

        mLeaderBoard = view.findViewById(R.id.leaderboard);
        mButton = view.findViewById(R.id.button);
        tv11 = view.findViewById(R.id.tv11);
        tv12 = view.findViewById(R.id.tv12);
        tv21 = view.findViewById(R.id.tv21);
        tv22 = view.findViewById(R.id.tv22);
        tv31 = view.findViewById(R.id.tv31);
        tv32 = view.findViewById(R.id.tv32);

        mButton.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        // Create an alert builder
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Name for Leaderboard");

        final EditText input = new EditText(getActivity());
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                name = ""+input.getText();
                MainActivity activity = (MainActivity) getActivity();
                int score = activity.getScore();
                leaderBoard(name, score);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gson = new GsonBuilder().create();
        mySharedPrefs = this.getActivity().getSharedPreferences("Storage", Context.MODE_PRIVATE);
        mEditor = mySharedPrefs.edit();

        Leaderboard dummy = new Leaderboard("dummy", -1);
        String dummy2 = new Gson().toJson(dummy);
        update(dummy2);
    }


    private void leaderBoard(String name, int score) {
        Leaderboard object = new Leaderboard(name, score);
        compare(object);
    }

    private void compare(Leaderboard object) {
        Leaderboard dummy = new Leaderboard("dummy", -1);
        String dummy2 = new Gson().toJson(dummy);
        Leaderboard one = gson.fromJson(mySharedPrefs.getString("1", dummy2), Leaderboard.class);
        Leaderboard two = gson.fromJson(mySharedPrefs.getString("2", dummy2), Leaderboard.class);
        Leaderboard three = gson.fromJson(mySharedPrefs.getString("3", dummy2), Leaderboard.class);
        Gson gson = new Gson();
        String stringed = gson.toJson(object);
        if(object.compareTo(one)>0){
            if(!mySharedPrefs.contains("1")) mEditor.putString("1", stringed);
            else if(!mySharedPrefs.contains("2")){
                mEditor.putString("2", mySharedPrefs.getString("1", dummy2));
                mEditor.putString("1", stringed);
            }
            else{
                mEditor.putString("3", mySharedPrefs.getString("2", dummy2));
                mEditor.putString("2", mySharedPrefs.getString("1", dummy2));
                mEditor.putString("1", stringed);
            }
            mEditor.apply();
        }
        else if(object.compareTo(two)>0){
            if(mySharedPrefs.contains("2")) {
                mEditor.putString("3", mySharedPrefs.getString("2", dummy2));
                mEditor.putString("2", stringed);
            }
            else mEditor.putString("2", stringed);
            mEditor.apply();
        }
        else if(object.compareTo(three)>0){
            mEditor.putString("3", stringed);
            mEditor.apply();
        }
        update(dummy2);
    }

    private void update(String dummy){
        Leaderboard one = gson.fromJson(mySharedPrefs.getString("1", dummy), Leaderboard.class);
        Leaderboard two = gson.fromJson(mySharedPrefs.getString("2", dummy), Leaderboard.class);
        Leaderboard three = gson.fromJson(mySharedPrefs.getString("3", dummy), Leaderboard.class);
        if(one.getScore()!=-1){
            tv11.setText(""+one.getName());
            tv12.setText(""+one.getScore());
        }
        if(two.getScore()!=-1){
            tv21.setText(""+two.getName());
            tv22.setText(""+two.getScore());
        }
        if(three.getScore()!=-1){
            tv31.setText(""+three.getName());
            tv32.setText(""+three.getScore());
        }

    }
}