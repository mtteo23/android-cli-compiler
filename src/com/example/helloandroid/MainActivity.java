package com.example.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;

import android.net.Uri;
import android.media.Ringtone;
import android.media.RingtoneManager;

import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends Activity {

	private boolean running=false;
	private int timeStart = 8*60;
    private int timeLeft = timeStart;  // Start with 300 seconds
    private int timeBell1 = timeStart-60;  
    private int timeBell2 = 60;
    private int timeEndGap = 10; 
    private EditText durationInput;
    private EditText Bell1Input;
    private EditText Bell2Input;
    private Handler handler = new Handler();  // For updating the UI every second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the TextView with ID hello_msg
        TextView helloTextView = (TextView) findViewById(R.id.hello_msg);
        
        durationInput = (EditText) findViewById(R.id.Duration);
        Bell1Input = (EditText) findViewById(R.id.Bell1);
        Bell2Input = (EditText) findViewById(R.id.Bell2);
        
        // Start a loop to update the timer every second
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
				
				String inputText = durationInput.getText().toString();
				if (!inputText.isEmpty())
				{
					int comodo = 60*Integer.parseInt(inputText);
					if(timeStart!=comodo)
					{
						timeStart=comodo;
						timeLeft=timeStart;
					}
				}
				
				inputText = Bell1Input.getText().toString();
				if (!inputText.isEmpty())
				{
					timeBell1=60*Integer.parseInt(inputText);
				}
				
				inputText = Bell2Input.getText().toString();
				if (!inputText.isEmpty())
				{
					timeBell2=60*Integer.parseInt(inputText);
				}


                // Decrement the timer
                if (running) {
                    timeLeft--;
                }
                if((timeLeft==timeBell1 || timeLeft==timeBell2 || (timeLeft<=0 && timeLeft%timeEndGap==0)) && running)
                {
					try {
						Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
						Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
						r.play();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				/*
				// Update the TextView with the countdown and seconds from midnight
				String timeStr=Integer.toString(Math.abs(timeLeft/60));
				if(Math.abs(timeLeft/60)<10)
					timeStr='0'+timeStr;
					
				if(Math.abs(timeLeft%60)<10)
					timeStr=timeStr+":0"+Math.abs(timeLeft%60);
				else
					timeStr=timeStr+":"+Math.abs(timeLeft%60);
				
				if(timeLeft<0)
					timeStr="-"+timeStr;
                helloTextView.setText(timeStr);
                //*/
                String timeStr="              \n              \n              \n              \n              \n              \n              \n";
                int x [] = {3, 4, 5, 6, 6, 6, 5, 4, 3, 2, 1, 0, 0, 0, 1, 2};
                int y [] = {0, 0, 1, 2, 3, 4, 5, 6, 6, 6, 5, 4, 3, 2, 1, 0};
                
                for(int i=0; i<16; i++)
                {
					int place = 2*x[i]+15*y[i];
					String c=" #";
					if(i<(16-(16*timeLeft)/timeStart))
						c=" +";
					if(i<(0-4*(16*timeLeft)/timeStart))
						c=" -";
					timeStr = timeStr.substring(0,place)+c+timeStr.substring(place+2);
				}
				{
				String com=":";
				int place = 7+15*3;
				timeStr = timeStr.substring(0,place)+com+timeStr.substring(place+1);
				
				
				com=Math.abs((timeLeft/600)%10)+""+Math.abs((timeLeft/60)%10);
				place = 5+15*3;
				timeStr = timeStr.substring(0,place)+com+timeStr.substring(place+2);
				
				
				com=Math.abs((timeLeft%60)/10)+""+Math.abs(timeLeft%10);
				place = 8+15*3;
				timeStr = timeStr.substring(0,place)+com+timeStr.substring(place+2);
				
				
				com=" ";
				if(timeLeft<0)
					com="-";
				place = 4+15*3;
				timeStr = timeStr.substring(0,place)+com+timeStr.substring(place+1);
				}
               
                helloTextView.setText(timeStr);
                //*/

                // Run this code again after 1 second (1000 milliseconds)
                handler.postDelayed(this, 1000);
            }
        }, 1000); // Initial delay of 1 second
    }
    
    public void StartFun(View view) {
		 Button StartButton = (Button) findViewById(R.id.StartButton);

        if (running) {
            StartButton.setText("Start");  // Change the button text to "Start"
            Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
        } else {
            StartButton.setText(" Stop ");   // Change the button text to "Stop"
            Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
        }

        // Toggle the running state
        running = !running;
    }
    
    public void ResetFun(View view) {
		timeLeft=timeStart;
		if(running)
			timeLeft++;
		Toast.makeText(this, "Resetted", Toast.LENGTH_SHORT).show();
	}
}

