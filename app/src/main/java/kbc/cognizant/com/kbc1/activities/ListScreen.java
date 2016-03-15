package kbc.cognizant.com.kbc1.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.AbsListView;
import android.transition.Slide;
import android.os.Build;

import java.util.ArrayList;
import kbc.cognizant.com.kbc1.R;
import kbc.cognizant.com.kbc1.db.DBHelper;
import kbc.cognizant.com.kbc1.model.NoteItem;
import kbc.cognizant.com.kbc1.adapter.ListItemAdapter;


public class ListScreen extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingAddButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWindowAnimations();
        initUI();

    }

    @Override
    protected void onResume() {
        new FetchRecordTask().execute();
        super.onResume();
    }

    /**
     * initialize the ui of the list screen
     */
    private void initUI(){
        mRecyclerView       = (RecyclerView) findViewById(R.id.recyclerView);
        mFloatingAddButton  = (FloatingActionButton) findViewById(R.id.add);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && mFloatingAddButton.isShown())
                    mFloatingAddButton.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    mFloatingAddButton.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });



        mFloatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailScreenIntent = new Intent(ListScreen.this , DetailsScreen.class);
                startActivity(detailScreenIntent);
            }
        });


    }

    /**
     * reads data from db asynchronously
     */
      class FetchRecordTask extends AsyncTask<Void,ArrayList<NoteItem>, ArrayList<NoteItem>>{
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ListScreen.this);
            dialog.setMessage(ListScreen.this.getResources().getString(R.string.loading));
            dialog.show();
            super.onPreExecute();
        }

        @Override
         protected ArrayList<NoteItem> doInBackground(Void... params) {

             DBHelper dbHelper = new DBHelper(ListScreen.this);
             return dbHelper.getAllNotes();
         }

        @Override
        protected void onPostExecute(ArrayList<NoteItem> list) {

            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            if(list.size() > 0){
                ListItemAdapter mAdapter = new ListItemAdapter(list);
                mRecyclerView.setAdapter(mAdapter);
            }
            super.onPostExecute(list);
        }
    }




    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(3000);
            getWindow().setExitTransition(slide);
        }
    }

}
