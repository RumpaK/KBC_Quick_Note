package kbc.cognizant.com.kbc1.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kbc.cognizant.com.kbc1.R;
import kbc.cognizant.com.kbc1.adapter.ListItemAdapter;
import kbc.cognizant.com.kbc1.db.DBHelper;
import kbc.cognizant.com.kbc1.model.NoteItem;
import kbc.cognizant.com.kbc1.utility.Constant;
import kbc.cognizant.com.kbc1.utility.Utils;

/**
 * Created by cts_mobility5 on 3/15/16.
 */
public class DetailsScreen extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText edt_title;
    private EditText edt_desc;
    private EditText edt_last_mod_date;
    private int mItemId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setupWindowAnimations();
        Intent mIntent = getIntent();
        mItemId        = mIntent.getIntExtra(Constant.ITEM_ID_KEY, -1);
        dbHelper       = new DBHelper(this);

        initUI();

    }


    private void initUI(){
        edt_title         = (EditText) findViewById(R.id.et_title);
        edt_desc          = (EditText) findViewById(R.id.et_desc);
        edt_last_mod_date = (EditText) findViewById(R.id.et_date);

        if(mItemId != -1){
            new FetchRecordTask().execute();
        }else{
            edt_last_mod_date.setText(Utils.getCurrentDate());

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_screen_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save:
                //save  or update to perform here
                if(mItemId > -1){
                    dbHelper.updateNote(mItemId , edt_title.getText().toString(),
                                               edt_desc.getText().toString(),
                                               Utils.getCurrentDate());
                }else {
                    dbHelper.insertNote(edt_title.getText().toString(),
                                               edt_desc.getText().toString(),
                                               edt_last_mod_date.getText().toString());
                }
                Toast.makeText(DetailsScreen.this , R.string.item_saved , Toast.LENGTH_SHORT).show();
                DetailsScreen.this.finish();
                return(true);
            case R.id.action_delete:
                //delete  to perform here
                showAlert();
                return(true);

        }
        return(super.onOptionsItemSelected(item));
    }

    /**
     * reads data from db asynchronously
     */
    class FetchRecordTask extends AsyncTask<Void,NoteItem, NoteItem> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(DetailsScreen.this);
            dialog.setMessage(DetailsScreen.this.getResources().getString(R.string.loading));
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected NoteItem doInBackground(Void... params) {

            return dbHelper.getNote(mItemId);
        }

        @Override
        protected void onPostExecute(NoteItem item) {

            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }


            if(item != null){
                edt_title.setText(item.getTitle());
                edt_desc.setText(item.getNote());
                edt_last_mod_date.setText(item.getLastModifiedDate());
            }else{
                edt_last_mod_date.setText(Utils.getCurrentDate());
            }
            super.onPostExecute(item);
        }
    }

    private void showAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsScreen.this);
        alertDialog.setMessage(getResources().getString(R.string.confirm_delete_message));
        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton(
                                          getResources().getString(R.string.yes),
                                          new DialogInterface.OnClickListener() {
                                              public void onClick(DialogInterface dialog, int id) {
                                                  dbHelper.deleteNote(mItemId);
                                                  dialog.cancel();
                                                  DetailsScreen.this.finish();
                                              }
                                          });

        alertDialog.setNegativeButton(
                                          getResources().getString(R.string.no),
                                          new DialogInterface.OnClickListener() {
                                              public void onClick(DialogInterface dialog, int id) {
                                                  dialog.cancel();
                                              }
                                          });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }


    /**
     * activity transition
     */
    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(3000);
            getWindow().setExitTransition(slide);
        }
    }

}
