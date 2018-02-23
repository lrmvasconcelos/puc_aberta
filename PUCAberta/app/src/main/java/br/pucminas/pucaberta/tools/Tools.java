package br.pucminas.pucaberta.tools;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;

/**
 * Created by lucas on 22/04/17.
 */

public class Tools {

    private Context mContext;

    private LocationManager mLocationManeger;

    public Tools() {}

    public Tools(Context context)
    {
        mContext = context;

        mLocationManeger = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

    }

    public boolean isOnline()
    {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);


        if (mConnectivityManager.getActiveNetworkInfo() != null
                && mConnectivityManager.getActiveNetworkInfo().isAvailable()
                && mConnectivityManager.getActiveNetworkInfo().isConnected()) {

            return true;

        }
        else
        {

            return false;

        }
    }

    public boolean isGPSEnabled()
    {
        return mLocationManeger.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
