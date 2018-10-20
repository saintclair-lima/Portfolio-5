package com.treetorah.treetorahtrack.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class BancoSQL extends SQLiteOpenHelper {

    private static final int BANCO_VERSAO = 1;
    private static final String BANCO_NOME = "TreeTorahTracker";
    private static final String TABELA_NOME = "entradas";

    private static final String KEY_ANO = "ano";
    private static final String KEY_UF = "uf";
    private static final String KEY_CORTADAS = "cortadas";
    private static final String KEY_VOLUME = "volume";
    private static final String KEY_REPOSTAS = "repostas";
    private static final String KEY_VALOR = "valor";
    private static final String[] COLUMNS = { KEY_ANO, KEY_UF, KEY_CORTADAS, KEY_VOLUME, KEY_REPOSTAS, KEY_VALOR};

    public BancoSQL(Context context) {
        super(context, BANCO_NOME, null, BANCO_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE entradas ( "
                + "ano INTEGER, "
                + "uf TEXT, cortadas INTEGER, volume REAL, "
                + "repostas INTEGER, valor REAL,"
                + "PRIMARY KEY(ano, uf))";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_NOME);
        this.onCreate(db);
    }

    public String[] getEntradas(String parametro){
        String[] valoresRetorno = new String[4];
        List<Entrada> entradas;

        if (parametro != null){
            entradas = selectEntradas(parametro);
        } else {
            entradas = selectEntradas();
        }

        valoresRetorno[0] = parametro;
        int cortadas = 0;
        int repostas = 0;
        double volume = 0;

        for (Entrada e : entradas){
            cortadas += e.getCortadas();
            repostas += e.getRepostas();
            volume += e.getVolume();
        }

        valoresRetorno[1] = Integer.toString(repostas);
        valoresRetorno[2] = Double.toString(volume);
        valoresRetorno[3] = Integer.toString(cortadas);

        return valoresRetorno;
    }

    public long insertEntrada(Entrada entrada) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(KEY_ANO, entrada.getAno());
        valores.put(KEY_UF, entrada.getUf());
        valores.put(KEY_CORTADAS, entrada.getCortadas());
        valores.put(KEY_VOLUME, entrada.getVolume());
        valores.put(KEY_REPOSTAS, entrada.getRepostas());
        valores.put(KEY_VALOR, entrada.getValorPagar());

        // insert
        long linhaInserida = db.insert(TABELA_NOME,null, valores);
        db.close();
        return linhaInserida;
    }

    public List<Entrada> selectEntradas() {
        List<Entrada> entradas = new LinkedList<Entrada>();

        SQLiteDatabase db = this.getReadableDatabase();

        String consulta = "Select * from " + TABELA_NOME;
        Cursor cursor = db.rawQuery(consulta, null);
        Entrada entrada;

        if (cursor.moveToFirst()) {
            do {
                entrada = new Entrada();
                //entrada.setId(Integer.parseInt(cursor.getString(0)));

                entrada.setAno(Integer.parseInt(cursor.getString(0)));
                entrada.setUf(cursor.getString(1));
                entrada.setCortadas(Integer.parseInt(cursor.getString(2)));
                entrada.setVolume(Float.parseFloat(cursor.getString(3)));
                entrada.setRepostas(Integer.parseInt(cursor.getString(4)));
                entrada.setValorPagar(Float.parseFloat(cursor.getString(5)));
                entradas.add(entrada);
            } while (cursor.moveToNext());
        }

        return entradas;
    }

    public List<Entrada> selectEntradas(String parametro) {
        List<Entrada> entradas = new LinkedList<Entrada>();
        SQLiteDatabase db = this.getReadableDatabase();

        String seletor;
        try {
            int ano = Integer.parseInt(parametro);
            seletor = " ano = ?";
        } catch (NumberFormatException e){
            seletor = " uf = ?";
        }

        Cursor cursor = db.query(TABELA_NOME, // a. table
                COLUMNS, // b. column names
                seletor, // c. selections
                new String[] { String.valueOf(parametro) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        Entrada entrada;

        if (cursor.moveToFirst()) {
            do {
                entrada = new Entrada();
                //entrada.setId(Integer.parseInt(cursor.getString(0)));
                entrada.setAno(Integer.parseInt(cursor.getString(0)));
                entrada.setUf(cursor.getString(1));
                entrada.setCortadas(Integer.parseInt(cursor.getString(2)));
                entrada.setVolume(Float.parseFloat(cursor.getString(3)));
                entrada.setRepostas(Integer.parseInt(cursor.getString(4)));
                entrada.setValorPagar(Float.parseFloat(cursor.getString(5)));
                entradas.add(entrada);
            } while (cursor.moveToNext());
        }

        return entradas;
    }

    public Entrada selectEntrada(String ano, String estado) {
        SQLiteDatabase db = this.getReadableDatabase();

        String seletor = " ano = ? and uf = ?";

        Cursor cursor = db.query(TABELA_NOME, // a. table
                COLUMNS, // b. column names
                seletor, // c. selections
                new String[] { String.valueOf(ano), String.valueOf(estado) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        Entrada entrada = new Entrada();

        if (cursor.moveToFirst()) {
            do {
                //entrada.setId(Integer.parseInt(cursor.getString(0)));
                entrada.setAno(Integer.parseInt(cursor.getString(0)));
                entrada.setUf(cursor.getString(1));
                entrada.setCortadas(Integer.parseInt(cursor.getString(2)));
                entrada.setVolume(Float.parseFloat(cursor.getString(3)));
                entrada.setRepostas(Integer.parseInt(cursor.getString(4)));
                entrada.setValorPagar(Float.parseFloat(cursor.getString(5)));
            } while (cursor.moveToNext());
        }

        return entrada;
    }

    public boolean deleteEntrada(String ano, String estado) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABELA_NOME, "ano = ? and uf = ?", new String[]{ano, estado}) > 0;
    }
}
