package br.pucminas.pucaberta.fragments;


import android.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import br.pucminas.pucaberta.R;
import br.pucminas.pucaberta.util.CardContent;
import br.pucminas.pucaberta.util.RecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Programacao extends Fragment {

    private RecyclerView mRecyclerView;

    private List<CardContent> mList;


    public Programacao() {/*Required empty public constructor*/}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_programacao, container, false);

        initializeData();

        mRecyclerView = ( RecyclerView )rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize( true );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( linearLayoutManager );

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter( mList );
        mRecyclerView.setAdapter( recyclerViewAdapter );

        Toast.makeText(getActivity(),"Deslize para ver toda a programação", Toast.LENGTH_SHORT).show();

        return rootView;

    }

    private void initializeData()
    {
        mList = new ArrayList<>();

        mList.add(new CardContent("GRUPO A - TEATRO - PRÉDIO 30 –\n" +
                "IPUC - 13h30 às 15h30", R.drawable.grupo_a));

        mList.add(new CardContent("GRUPO B - AUDITÓRIO 1 - PRÉDIO 4 -\n" +
                "FMD - 13h30 às 15h30", R.drawable.grupo_b));

        mList.add(new CardContent("GRUPO C - AUDITÓRIO 2 - PRÉDIO 5 –\n" +
                "ICEG - 13h30 às 15h30", R.drawable.grupo_c));

        mList.add(new CardContent("GRUPO D - AUDITÓRIO 3 - PRÉDIO 43 –\n" +
                "ICBS - 13h30 às 15h30", R.drawable.grupo_d));

        mList.add(new CardContent("GRUPO E - AUDITÓRIO MULTIUSO - PRÉDIO 43 –\n" +
                "ICH - 13h30 às 15h30", R.drawable.grupo_e));

        mList.add(new CardContent("GRUPO F - TEATRO - PRÉDIO 30 –\n" +
                "ICBS - 16h às 18h", R.drawable.grupo_f));

        mList.add(new CardContent("GRUPO G - AUDITÓRIO 1 - PRÉDIO 4 -\n" +
                "FAPSI - 16h às 18h", R.drawable.grupo_g));

        mList.add(new CardContent("GRUPO H - AUDITÓRIO 2 PRÉDIO 5 -\n" +
                "FCA - 16h às 18h", R.drawable.grupo_h));

        mList.add(new CardContent("GRUPO I - AUDITÓRIO 3  - PRÉDIO 43 -\n" +
                " ICS  - 16h às 18h", R.drawable.grupo_i));

        mList.add(new CardContent("GRUPO J - AUDITÓRIO MULTIUSO - PRÉDIO 43 – \n" +
                "ICEI - 16h às 18h", R.drawable.grupo_j));


    }
}
