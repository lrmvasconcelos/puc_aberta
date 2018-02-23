package br.pucminas.pucaberta;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.pucminas.pucaberta.fragments.Cursos;
import br.pucminas.pucaberta.fragments.Mapa;
import br.pucminas.pucaberta.fragments.Programacao;
import br.pucminas.pucaberta.fragments.Sobre;
import br.pucminas.pucaberta.util.IO_file;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private final int REQUEST_PERMISSIONS_CODE = 128;
    public Cursos mCursos;
    private IO_file mFile;
    private boolean permissionGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        mCursos = new Cursos();

        mFile = new IO_file(this);

        if (!(mFile.checkFile(IO_file.FILE_CONFIGURACAO))) {
            Toast.makeText(this, "Bem vindo, vá ao receptivo para habilitar as funcionalidades do app", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager mFragmentManeger = getFragmentManager();

        Fragment mFragment = null;

        switch (item.getItemId()) {
            case R.id.nav_cursos:

                mFragment = new Cursos();

                break;

            case R.id.nav_programacao:

                mFragment = new Programacao();

                break;

            case R.id.nav_mapa:

                mFragment = new Mapa();

                break;


            case R.id.nav_sobre:


                mFragment = new Sobre();


                break;

            default:

                if (permissionRequest()) {

                    mFragment = new Mapa();

                }
                break;
        }

        if (mFragment != null) {
            mFragmentManeger.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.content_frame, mFragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private boolean permissionRequest() {

        if ((ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CAMERA}, REQUEST_PERMISSIONS_CODE);

            } else {
                callDialog("PUC Aberta permissões:" +
                                "\nLocalização -> Necessário para orientá-lo em nosso campus, facilitando o acesso aos prédios de suas palestras."
                        , new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION});

            }
        } else {

            return true;
        }

        return false;
    }

    private void callDialog(String message, final String[] permissions) {

        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Permissão")
                .setMessage(message);

        builder.setPositiveButton("HABILITAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_PERMISSIONS_CODE);

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case REQUEST_PERMISSIONS_CODE:

                // Percorrer todas as permissões
                for (int i = 0; i < permissions.length; i++) {

                    if (permissions[i].equalsIgnoreCase(android.Manifest.permission.ACCESS_FINE_LOCATION)
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        permissionGPS = true;

                        FragmentManager mFragmentManeger = getFragmentManager();

                        mFragmentManeger.beginTransaction()
                                .replace(R.id.content_frame, new Mapa())
                                .commit();

                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onStart() {

        if (permissionRequest()) {

            FragmentManager mFragmentManeger = getFragmentManager();

            mFragmentManeger.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.content_frame, new Mapa())
                    .commit();
        }

        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }

}
