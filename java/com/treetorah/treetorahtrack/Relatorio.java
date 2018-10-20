package com.treetorah.treetorahtrack;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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

public class Relatorio extends AppCompatActivity {

    static String parametro = "";

    private void ocultarTeclado() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        final List<String> estados = new ArrayList();
        estados.add("");
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

        // Configurando o Spinner
        final Spinner spnEstadosBusca = (Spinner) findViewById(R.id.spnEstadoBusca);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, estados);
        spnEstadosBusca.setAdapter(adapter);
        spnEstadosBusca.setFocusable(true);

        // Configurando o campo de texto
        final EditText txtAnoBusca = findViewById(R.id.txtAnoBusca);

        // Configurando o Botão
        final Button btnGerarRelatorio = findViewById(R.id.btnGerarRelatorio);

        txtAnoBusca.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                spnEstadosBusca.setSelection(0);
                parametro = txtAnoBusca.getText().toString();
                return false;
            }
        });
        txtAnoBusca.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                spnEstadosBusca.setSelection(0);
                parametro = txtAnoBusca.getText().toString();
                return false;
            }
        });

        spnEstadosBusca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtAnoBusca.setText("");
                parametro = spnEstadosBusca.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGerarRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BancoSQL banco = new BancoSQL(Relatorio.this);
                String[] entradas = banco.getEntradas(Relatorio.parametro);

                String mensagem = "Dados relativos a: " + entradas[0]+"\n"
                        + entradas[1] + " Árvores Repostas \n"
                        + "Volume de " + entradas[2] + " m³ \n"
                        + entradas[3] + " Árvores Cortadas";


                AlertDialog.Builder dlg = new AlertDialog.Builder(Relatorio.this);
                dlg.setMessage(mensagem);
                dlg.setPositiveButton("Fechar", null);
                dlg.show();
            }
        });
    }
}
