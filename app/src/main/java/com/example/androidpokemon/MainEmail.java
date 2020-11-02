package com.example.androidpokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainEmail extends AppCompatActivity {

    Button btnEMAIL;
    EditText txtEMAIL;
    private Toolbar toolbar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_email);
        toolbar3 = (Toolbar) findViewById(R.id.toolbar3);
        toolbar3.setTitle("Email");
        setSupportActionBar(toolbar3);

        inicializarComponentes();
        eventosButtons();
        }

        private void eventosButtons() {
            btnEMAIL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String destinatario = txtEMAIL.getText().toString().trim();
                    Intent mail = new Intent(Intent.ACTION_SEND);
                    mail.putExtra(Intent.EXTRA_EMAIL, new String[]{destinatario});
                    mail.putExtra(Intent.EXTRA_SUBJECT, "Assunto: Poke API");
                    mail.putExtra(Intent.EXTRA_TEXT, "Corpo: Poke API");
                    mail.setType("message/rfc822");
                    startActivity(Intent.createChooser(mail, ""));
                }
            });
        }

        private void inicializarComponentes() {
            btnEMAIL = (Button) findViewById(R.id.btnEMAIL);
            txtEMAIL = (EditText) findViewById(R.id.txtEMAIL);
        }
    }
