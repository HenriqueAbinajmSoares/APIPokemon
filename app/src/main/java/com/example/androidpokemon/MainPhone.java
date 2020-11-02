package com.example.androidpokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainPhone extends AppCompatActivity {

    Button btnTEL;
    EditText txtTEL;
    private Toolbar toolbar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_phone);
        toolbar4 = (Toolbar) findViewById(R.id.toolbar4);
        toolbar4.setTitle("Telefone");
        setSupportActionBar(toolbar4);

        inicializarComponentes();
        eventosButtons();
    }

    private void eventosButtons() {
        btnTEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fone = txtTEL.getText().toString().trim();
                try {
                    Intent call = new Intent(Intent.ACTION_DIAL);
                    call.setData(Uri.parse("tel:"+fone));
                    startActivity(call);
                } catch (ActivityNotFoundException e) {

                    Log.e("sample call in android", "Erro na ligação", e);
                }
            }
        });
    }
    private void inicializarComponentes() {
        btnTEL = (Button) findViewById(R.id.btnTEL);
        txtTEL = (EditText) findViewById(R.id.txtTEL);
    }
}
