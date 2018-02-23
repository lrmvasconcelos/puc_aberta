package br.pucminas.pucaberta.fragments;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.pucminas.pucaberta.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sobre extends Fragment {


    public Sobre() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sobre, container, false);


        return rootView;
    }

}
