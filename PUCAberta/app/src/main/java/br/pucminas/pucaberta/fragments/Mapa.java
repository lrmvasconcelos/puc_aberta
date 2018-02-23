package br.pucminas.pucaberta.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import br.pucminas.pucaberta.R;
import br.pucminas.pucaberta.tools.Tools;
import br.pucminas.pucaberta.util.IO_file;


/**
 * Created by lucas on 05/05/17.
 */

public class Mapa extends Fragment implements  OnMapReadyCallback {

    private final static String TAG = "Mapa";

    private GoogleMap mMap;

    private MapView mMapView;

    private Tools mTools;

    private IO_file mFile;

    private Switch mSwitch;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapview);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        mTools = new Tools(getActivity());

        mFile = new IO_file(getActivity());

        mSwitch = (Switch) rootView.findViewById(R.id.switch_cantina);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                addMarcadoresCantinas(checked);

            }
        });


        return rootView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng receptivo = new LatLng(-19.924542, -43.993056);

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.setBuildingsEnabled(true);

        mMap.isIndoorEnabled();

        mMap.getUiSettings().setZoomControlsEnabled(true);


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        if (!mTools.isOnline()) {
            Toast.makeText(getActivity(), "Você não possui conexão com a internet, pode não ser possível carregar o mapa. Favor habilitar e abrir mapa novamente ", Toast.LENGTH_LONG).show();
        }

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                if (!mTools.isGPSEnabled()) {
                    Toast.makeText(getActivity(), "GPS está desabilitado, favor habilitar para que seja possível orientá-lo no campus", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });


        CameraPosition mCameraPosition = CameraPosition.builder(mMap.getCameraPosition())
                .target(receptivo)
                .bearing(310)
                .zoom(17)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));

        addMarcadoresPrincipais();


    }

    private void addMarcadoresPrincipais() {

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.924542, -43.993056)).title("Receptivo")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_receptivo))

        ).showInfoWindow();


        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.922306, -43.993384))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_feira_de_cursos))

        );

        if (mFile.checkFile(IO_file.FILE_CONFIGURACAO)) {

            if (mFile.recuperar(IO_file.FILE_CONFIGURACAO).equals("grupo_a")) {
                addMarcadoresGrupoA();
            }
            if (mFile.recuperar(IO_file.FILE_CONFIGURACAO).equals("grupo_b")) {
                addMarcadoresGrupoB();
            }
            if (mFile.recuperar(IO_file.FILE_CONFIGURACAO).equals("grupo_c")) {
                addMarcadoresGrupoC();
            }
            if (mFile.recuperar(IO_file.FILE_CONFIGURACAO).equals("grupo_d")) {
                addMarcadoresGrupoD();
            }
            if (mFile.recuperar(IO_file.FILE_CONFIGURACAO).equals("grupo_e")) {
                addMarcadoresGrupoE();
            }
            if (mFile.recuperar(IO_file.FILE_CONFIGURACAO).equals("grupo_f")) {
                addMarcadoresGrupoF();
            }
            if (mFile.recuperar(IO_file.FILE_CONFIGURACAO).equals("grupo_g")) {
                addMarcadoresGrupoG();
            }
            if (mFile.recuperar(IO_file.FILE_CONFIGURACAO).equals("grupo_h")) {
                addMarcadoresGrupoH();
            }
            if (mFile.recuperar(IO_file.FILE_CONFIGURACAO).equals("grupo_i")) {
                addMarcadoresGrupoI();
            }
            if (mFile.recuperar(IO_file.FILE_CONFIGURACAO).equals("grupo_j")) {
                addMarcadoresGrupoJ();
            }

        } else {
            mMap.clear();

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(-19.924542, -43.993056)).title("Receptivo")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_receptivo))

            ).showInfoWindow();


            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(-19.922306, -43.993384))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_feira_de_cursos))

            );

        }

    }

    private void addMarcadoresCantinas(boolean visible) {


        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923973, -43.993499)).title("Cantina Sinhazinha")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cantinas))
        );
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923878, -43.994025))
                .title("Trailer de Lanches")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cantinas))
        );
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923375, -43.993772))
                .title("Cantina Divin Gout")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cantinas))
        );
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.922603, -43.992991))
                .title("Cantina Shuffner")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cantinas))
        );
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923024, -43.991382))
                .title("Cantina Sodexo")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cantinas))
        );

        if (!visible) {
            mMap.clear();
            addMarcadoresPrincipais();
        }

    }

    private void addMarcadoresGrupoA() {

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923171, -43.991035))
                .title("Grupo A - 13h30 às 15h30 - Teatro João Paulo II")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_auditorios))

        ).showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.924144, -43.99296))
                .title("Instituto Politécnico - IPUC ")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_institutos_e_faculdades))
        );
    }

    private void addMarcadoresGrupoB() {

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923265, -43.992904))
                .title("Grupo B - 13h30 às 15h30 - Auditório I")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_auditorios))
        ).showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.922958, -43.992619))
                .title("Faculdade Mineira de Direito")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_institutos_e_faculdades))
        );
    }

    private void addMarcadoresGrupoC() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.922958, -43.992619))
                .title("Grupo C - 13h30 às 15h30 - Auditório II")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_auditorios))
        ).showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.922651, -43.991751))
                .title("Instituto de Ciências Econômicas e Gerenciais - ICEG")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_institutos_e_faculdades))
        );
    }

    private void addMarcadoresGrupoD() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923527, -43.99335))
                .title("Grupo D - 13h30 às 15h30 - Auditório III")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_auditorios))
        ).showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.924401, -43.994458))
                .title("Instituto de Ciências Biológicas e da Saúde - ICBS")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_institutos_e_faculdades))
        );
    }

    private void addMarcadoresGrupoE() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923527, -43.993354))
                .title("Grupo E - 13h30 às 15h30 - Auditório Multiuso")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_auditorios))
        ).showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.92308, -43.992009))
                .title("Instituto de Ciências Humanas - ICH")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_institutos_e_faculdades))
        );
    }

    private void addMarcadoresGrupoF() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923171, -43.991035))
                .title("Grupo F - 16h às 18h - Teatro João Paulo II")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_auditorios))
        ).showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.924401, -43.994458))
                .title("Instituto de Ciências Biológicas e da Saúde - ICBS")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_institutos_e_faculdades))
        );
    }

    private void addMarcadoresGrupoG() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923265, -43.992904))
                .title("Grupo G - 16h às 18h - Auditório I")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_auditorios))
        ).showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.922651, -43.992424))
                .title("Faculdade de Psicologia")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_institutos_e_faculdades))
        );
    }

    private void addMarcadoresGrupoH() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.922958, -43.992619))
                .title("Grupo H - 16h às 18h - Auditório II")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_auditorios))
        ).showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.922442, -43.992665))
                .title("Faculdade de Comunicação e Artes")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_institutos_e_faculdades))
        );
    }

    private void addMarcadoresGrupoI() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923527, -43.993354))
                .title("Grupo I - 16h às 18h - Auditório III")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_auditorios))
        ).showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.925006, -43.993402))
                .title("Instituto de Ciências Sociais - ICS")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_institutos_e_faculdades))
        );
    }

    private void addMarcadoresGrupoJ() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923527, -43.993354))
                .title("Grupo J - 16h às 18h - Auditório Multiuso")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_auditorios))
        ).showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-19.923045, -43.994409))
                .title("Instituto de Ciências Exatas e Informática - ICEI")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_institutos_e_faculdades))
        );
    }


    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onStart() {
        mMapView.onStart();
        super.onStart();
    }

    @Override
    public void onStop() {
        mMapView.onStop();
        super.onStop();
    }
}
