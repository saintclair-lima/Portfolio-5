package com.treetorah.treetorahtrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.treetorah.treetorahtrack.database.BancoSQL;
import com.treetorah.treetorahtrack.database.Entrada;

import java.util.List;

public class Listar extends AppCompatActivity {
    ArrayAdapter adapter;
    ListView listaEntradas;
    List<Entrada> entradas;
    String[] anoEstado;
    String[] estado;
    int[] ano;

    private void atualizarDados(){
        this.entradas = new BancoSQL(Listar.this).selectEntradas();
        this.anoEstado = new String[entradas.size()];
        this.ano = new int[entradas.size()];
        this.estado = new String[entradas.size()];

        int contador=0;
        for (Entrada e : this.entradas){
            this.anoEstado[contador] = e.getUf() + " - " + e.getAno();
            this.ano[contador]=e.getAno();
            this.estado[contador]=e.getUf();
            contador ++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        atualizarDados();

        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.anoEstado);
        this.listaEntradas = findViewById(R.id.listaAnoEstado);
        this.listaEntradas.setAdapter(adapter);

        this.listaEntradas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent k = new Intent(Listar.this, DetalharEntrada.class);
                    k.putExtra("ano", Integer.toString(ano[position]));
                    k.putExtra("estado",estado[position]);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarDados();

        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.anoEstado);
        this.listaEntradas = findViewById(R.id.listaAnoEstado);
        this.listaEntradas.setAdapter(adapter);

        this.listaEntradas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent k = new Intent(Listar.this, DetalharEntrada.class);
                    k.putExtra("ano", Integer.toString(ano[position]));
                    k.putExtra("estado",estado[position]);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
