package com.treetorah.treetorahtrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.treetorah.treetorahtrack.database.BancoSQL;
import com.treetorah.treetorahtrack.database.Entrada;

import java.util.ArrayList;
import java.util.List;

public class Inserir extends AppCompatActivity {
    private void atualizarValorPagar(){
        EditText txtNumArCortadas = (EditText) findViewById(R.id.txtNumArCortadas);
        EditText txtVolume = (EditText) findViewById(R.id.txtVolume);
        EditText txtNumArRepostas = (EditText) findViewById(R.id.txtNumArRepostas);
        TextView lblValorPagar = (TextView) findViewById(R.id.lblValorPagar);
        TextView txtValorPagar = (TextView) findViewById(R.id.txtValorPagar);

        String cortadas = txtNumArCortadas.getText().toString();
        if (cortadas.matches("")){
            cortadas = "0";
        }

        String repostas = txtNumArRepostas.getText().toString();
        if (repostas.matches("")){
            repostas = "0";
        }

        int numCort = Integer.parseInt(cortadas);
        int numRep = Integer.parseInt(repostas);
        Double saldo = (numRep - numCort) * 0.49;

        if (saldo > 0){
            lblValorPagar.setText("Saldo: ");
            txtValorPagar.setText(Double.toString(saldo));
        } else {

            lblValorPagar.setText("Valor a pagar: ");
            txtValorPagar.setText(Double.toString(saldo));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir);

        final TextView txtAno = (TextView) findViewById(R.id.txtAno);

        final List <String> estados = new ArrayList();
        estados.add("AM");
        estados.add("AC");
        estados.add("AL");
        estados.add("AP");
        estados.add("AM");
        estados.add("BA");
        estados.add("CE");
        estados.add("DF");
        estados.add("ES");
        estados.add("GO");
        estados.add("MA");
        estados.add("MT");
        estados.add("MS");
        estados.add("MG");
        estados.add("PA");
        estados.add("PB");
        estados.add("PR");
        estados.add("PE");
        estados.add("PI");
        estados.add("RJ");
        estados.add("RN");
        estados.add("RS");
        estados.add("RO");
        estados.add("SC");
        estados.add("SP");
        estados.add("SE");
        estados.add("TO");

        final Spinner spnEstados = (Spinner) findViewById(R.id.spnEstado);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, estados);
        spnEstados.setAdapter(adapter);

        final EditText txtNumArCortadas = (EditText) findViewById(R.id.txtNumArCortadas);
        final EditText txtVolume = (EditText) findViewById(R.id.txtVolume);
        final EditText txtNumArRepostas = (EditText) findViewById(R.id.txtNumArRepostas);
        final TextView lblValorPagar = (TextView) findViewById(R.id.lblValorPagar);
        final TextView txtValorPagar = (TextView) findViewById(R.id.txtValorPagar);
        Button   btnSalvar = (Button) findViewById(R.id.btnSalvar);

        txtNumArCortadas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                atualizarValorPagar();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                atualizarValorPagar();
            }

            @Override
            public void afterTextChanged(Editable s) {
                atualizarValorPagar();
            }
        });

        txtNumArRepostas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                atualizarValorPagar();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                atualizarValorPagar();
            }

            @Override
            public void afterTextChanged(Editable s) {
                atualizarValorPagar();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BancoSQL banco = new BancoSQL(Inserir.this);
                String[] valores = {txtAno.getText().toString(),
                        spnEstados.getSelectedItem().toString(),
                        txtNumArCortadas.getText().toString(),
                        txtVolume.getText().toString(),
                        txtNumArRepostas.getText().toString(),
                        txtValorPagar.getText().toString()};

                boolean temValorInvalido = false;
                for (String valor : valores){
                    if(valor.matches("")){
                        temValorInvalido=true;
                        break;
                    }
                }

                if (temValorInvalido){
                    Toast.makeText(Inserir.this, "Preencha todos os campos",
                            Toast.LENGTH_LONG).show();
                } else {
                    long linhaInserida =  banco.insertEntrada(new Entrada(valores));
                    if (linhaInserida != -1) {
                        Toast.makeText(Inserir.this, "Dados salvos",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }else {
                        Toast.makeText(Inserir.this, "Ano e Estado já informados.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
