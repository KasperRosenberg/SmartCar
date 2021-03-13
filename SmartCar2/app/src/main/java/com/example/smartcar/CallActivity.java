package com.example.smartcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class CallActivity extends AppCompatActivity {

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        btn = findViewById(R.id.button5);
        btn.setOnClickListener(new View.OnClickListener() {
            //start execution of ssh commands
            @Override
            public void onClick(View v){
                new AsyncTask<Integer, Void, Void>(){
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            executeSSHcommand();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });
    }

    public void executeSSHcommand(){
        String user = "pi";
        String password = "raspberry";
        String host = "192.168.0.39";
        int port=22;
        try{

            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(10000);
            session.connect();
            ChannelExec channel = (ChannelExec)session.openChannel("exec");
            channel.setCommand("tdtool --on 1");
            channel.connect();
            channel.disconnect();
            // show success in UI with a snackbar alternatively use a toast
            Toast.makeText(this, "Succes", Toast.LENGTH_SHORT).show();

//            Snackbar.make(getActivity().findViewById(android.R.id.content),
//                    "Success!", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
        }
        catch(JSchException e){
            // show the error in the UI
            Toast.makeText(this, "Check WIFI or Server! Error : "+e.getMessage(), Toast.LENGTH_LONG)
                    .show();

//            Snackbar.make(getActivity().findViewById(android.R.id.content),
//                    "Check WIFI or Server! Error : "+e.getMessage(),
//                    Toast.LENGTH_LONG)
//                    .setDuration(20000).setAction("Action", null).show();
        }

    }
}
