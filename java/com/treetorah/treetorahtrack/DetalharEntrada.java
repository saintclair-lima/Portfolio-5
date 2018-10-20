package com.treetorah.treetorahtrack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.treetorah.treetorahtrack.database.BancoSQL;
import com.treetorah.treetorahtrack.database.Entrada;

public class DetalharEntrada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar_entrada);
        Intent k = getIntent();
        String ano = k.getStringExtra("ano");
        String estado = k.getStringExtra("estado");

        TextView anoEstadoLabel = (TextView) findViewById(R.id.anoEstadoLabel);
        anoEstadoLabel.setText(estado + " | " + ano);
        final Entrada entrada = new BancoSQL(DetalharEntrada.this).selectEntrada(ano, estado);

        TextView cortadasLabel = (TextView) findViewById(R.id.cortadasLabel);
        TextView cortadasValor = (TextView) findViewById(R.id.cortadasValor);
        cortadasValor.setText(Integer.toString(entrada.getCortadas()));
        TextView plantadasLabel = (TextView) findViewById(R.id.plantadasLabel);
        TextView plantadasValor = (TextView) findViewById(R.id.plantadasValor);
        plantadasValor.setText(Integer.toString(entrada.getRepostas()));
        TextView volumeLabel = (TextView) findViewById(R.id.volumeLabel);
        TextView volumeValor = (TextView) findViewById(R.id.volumeValor);
        volumeValor.setText(Float.toString(entrada.getVolume()));
        TextView aPagarLabel = (TextView) findViewById(R.id.aPagarLabel);
        TextView aPagarValor = (TextView) findViewById(R.id.aPagarValor);
        aPagarValor.setText(Double.toString(entrada.getValorPagar()));

        Button btnExcluirEntrada = (Button) findViewById(R.id.btnExcluirEntrada);
        btnExcluirEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetalharEntrada.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Excluir Entrada")
                        .setMessage("Deseja realmente excluir essa entrada?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean deletou = new BancoSQL(DetalharEntrada.this).deleteEntrada(Integer.toString(entrada.getAno()),entrada.getUf());
                                if (deletou){
                                    Toast.makeText(DetalharEntrada.this, "Entrada Excluída",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(DetalharEntrada.this, "Entrada não excluída",
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        Button btnFecharEntrada = (Button) findViewById(R.id.btnFecharEntrada);
        btnFecharEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
