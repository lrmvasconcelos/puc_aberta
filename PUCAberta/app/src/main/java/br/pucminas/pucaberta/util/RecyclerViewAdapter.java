package br.pucminas.pucaberta.util;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.pucminas.pucaberta.R;

/**
 * Created by lucas on 23/04/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.LocalViewHolder> {

    private List<CardContent> mList;

    public RecyclerViewAdapter(List<CardContent> mList){
        this.mList = mList;
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate(R.layout.programacao_card_view, parent, false);
        LocalViewHolder localViewHolder = new LocalViewHolder( view );
        return localViewHolder;    }


    @Override
    public void onBindViewHolder(LocalViewHolder holder, int position) {

        holder.txt_cardView.setText(mList.get(position).getHead());
        holder.image_cardView.setImageResource(mList.get(position).getImage());


    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class LocalViewHolder extends  RecyclerView.ViewHolder {

        CardView cardView;
        TextView txt_cardView;
        ImageView image_cardView;



        LocalViewHolder(View itemView) {
            super(itemView);

            cardView = ( CardView )itemView.findViewById( R.id.cardView );
            txt_cardView = ( TextView )itemView.findViewById( R.id.txt_cardView );
            image_cardView = (ImageView)itemView.findViewById( R.id.image_cardView );

        }

    }

}
