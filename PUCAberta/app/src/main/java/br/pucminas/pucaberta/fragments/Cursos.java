package br.pucminas.pucaberta.fragments;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import br.pucminas.pucaberta.R;
import br.pucminas.pucaberta.tools.Tools;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cursos extends Fragment {

    private static final String TAG = "Cursos";

    private WebView mWebView;

    private RelativeLayout mRelativeLayout;

    private Tools mTools;

    private FloatingActionButton buttonReload;


    public Cursos() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cursos, container, false);

        mTools = new Tools(getActivity());

        mWebView = (WebView)rootView.findViewById(R.id.webView);

        mRelativeLayout = (RelativeLayout)rootView.findViewById(R.id.relativeLayoutChild);

        buttonReload = (FloatingActionButton)rootView.findViewById(R.id.buttonReload);

        mWebView.getSettings().setJavaScriptEnabled(true);

        layoutVisibility();

        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutVisibility();
            }
        });

        return rootView;
    }

    private void layoutVisibility()
    {
        if(mTools.isOnline())
        {
            Toast.makeText(getActivity(), R.string.conexao_internet_habilitada, Toast.LENGTH_SHORT).show();

            mWebView.setVisibility(View.VISIBLE);
            mWebView.loadUrl("http://www.pucminas.br/Graduacao/Paginas/default.aspx");
            mRelativeLayout.setVisibility(View.GONE);

        }else{
            Toast.makeText(getActivity(), R.string.sem_conexao_internet, Toast.LENGTH_SHORT).show();

            mWebView.setVisibility(View.GONE);
            mRelativeLayout.setVisibility(View.VISIBLE);
        }
    }



}
