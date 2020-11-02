package com.example.androidpokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    private Toolbar toolbar2;
    private TextView txtLatitude, txtLongitude, txtCidade, txtEstado, txtPais;
    private Address endereco;
    Button btnGps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        toolbar2.setTitle("Pokemon API");
        setSupportActionBar(toolbar2);

        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);
        txtCidade = (TextView) findViewById(R.id.txtCidade);
        txtEstado = (TextView) findViewById(R.id.txtEstado);
        txtPais = (TextView) findViewById(R.id.txtPais);

        btnGps = (Button) findViewById(R.id.btnGps);
        btnGps.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                pedirPermissoes();
            }
        });

        final Button button = findViewById(R.id.btnAPI);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        final Button button2 = findViewById(R.id.btnInt1);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("https://bulbapedia.bulbagarden.net/wiki/Main_Page");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        final Button button3 = findViewById(R.id.btnAPILINK);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri2 = Uri.parse("https://api.pokemon.com/us/");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent2);
            }
        });

        final Button button4 = findViewById(R.id.btnGGMAPS);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        final Button button5 = findViewById(R.id.btnMAIL);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentA4 = new Intent(MainMenu.this, MainEmail.class);
                startActivity(intentA4);
            }
        });
        final Button button6 = findViewById(R.id.btnPhone);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentA5 = new Intent(MainMenu.this, MainPhone.class);
                startActivity(intentA5);
            }
        });
    }

    private void pedirPermissoes() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
            configurarServico();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configurarServico();
                } else {
                    Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void configurarServico() {
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    atualizar(location);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) { }

                public void onProviderEnabled(String provider) { }

                public void onProviderDisabled(String provider) { }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void atualizar(Location location)
    {
        Double latPoint = location.getLatitude();
        Double lngPoint = location.getLongitude();

        txtLatitude.setText(latPoint.toString());
        txtLongitude.setText(lngPoint.toString());

        try{
            endereco = buscarEndereco(latPoint,lngPoint);

            txtCidade.setText("Cidade: "+ endereco.getLocality());
            txtEstado.setText("Estado: "+ endereco.getAdminArea());
            txtPais.setText("País: "+ endereco.getCountryName());
            txtPais.setText("País: "+ endereco.getCountryName());
        } catch (IOException e){
            Log.i("GPS", e.getMessage());
        }
    }

    private Address buscarEndereco(Double latPoint, Double lngPoint)
        throws IOException {
        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());

        addresses = geocoder.getFromLocation(latPoint, lngPoint, 1);
        if (addresses.size() > 0) ;
        address = addresses.get(0);
        return address;
    }
}