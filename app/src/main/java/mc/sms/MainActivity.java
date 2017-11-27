package mc.sms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import fragments.LoadDataFragment;
import utils.MUtil;


public class MainActivity extends ActionBarActivity {

    public myApp myapp;
    //public static CompanyApp company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.myapp = (myApp) getApplicationContext();
        //Start Timer:
        MUtil.startTimer();
        Bundle recieveParams = getIntent().getExtras();
        if (savedInstanceState == null) {
                myapp.setSecondPass();
                if(myapp.isFirstPass()){
                    if(myapp.checkConnection()){
                        myapp.setSecondPass();
                        LoadDataFragment loadDataFragment = LoadDataFragment.newInstance();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.commitAllowingStateLoss();
                        ft.replace(R.id.container, loadDataFragment,"Load");

                    }
                    else {
                        new AlertDialog.Builder(this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Warning")
                                .setMessage("You need to connect to internet at least one time ")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }

                                })
                                .show();

                    }


                }
                else {
                    LoadDataFragment loadDataFragment = LoadDataFragment.newInstance();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, loadDataFragment,"Load");
                    ft.commitAllowingStateLoss();

                }




        }
    }





    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);

    }


    @Override
    public void onBackPressed() {
        // If the fragment exists and has some back-stack entry

        // Let super handle the back press
        super.onBackPressed();


    }

    @Override
    protected void onDestroy() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Log.d("Test", "Back button pressed!");

        }
        else if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            Log.d("Test", "Home button pressed!");
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setBackButton(){

        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    }).create().show();
        }
        else {
            Log.i("NOFRAGMENTCH", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));

        }

    }





}