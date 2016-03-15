package kbc.cognizant.com.kbc1.adapter;

/**
 * Created by cts_mobility5 on 3/15/16.
 */
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.util.ArrayList;

import kbc.cognizant.com.kbc1.R;

import kbc.cognizant.com.kbc1.activities.DetailsScreen;
import kbc.cognizant.com.kbc1.model.NoteItem;
import kbc.cognizant.com.kbc1.utility.Constant;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {
    private static ArrayList<NoteItem> itemsData;



    public ListItemAdapter(ArrayList<NoteItem> itemsData ) {
        this.itemsData = itemsData;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.list_item_layout, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        viewHolder.txtViewTitle.setText(itemsData.get(position).getTitle());
        viewHolder.txtViewNote.setText(itemsData.get(position).getNote());
        viewHolder.txtViewLastModifiedDate.setText(itemsData.get(position).getLastModifiedDate());



    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txtViewTitle;
        public TextView txtViewNote;
        public TextView txtViewLastModifiedDate;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            txtViewNote = (TextView) itemLayoutView.findViewById(R.id.item_note);
            txtViewLastModifiedDate = (TextView) itemLayoutView.findViewById(R.id.item_lastModDate);
//            txtViewTitle.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent detailScreenIntent = new Intent(context , DetailsScreen.class);
            detailScreenIntent.putExtra(Constant.ITEM_ID_KEY,itemsData.get(getAdapterPosition())
                                                                     .getId());
            context.startActivity(detailScreenIntent);
        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }

}
