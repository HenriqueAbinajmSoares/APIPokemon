package com.example.androidpokemon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.androidpokemon.Model.Pokemon;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.dbPokemon";
    public static final String POKEMON_TABLE_NAME = "pokemon";
    public static final String POKEMON_COLUMN_ID = "idPokemon";
    public static final String POKEMON_COLUMN_NAME = "name";
    public static final String POKEMON_COLUMN_IMG = "img";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase dbPokemon) {
        dbPokemon.execSQL(
                "create table pokemon " +
                        "(idPokemon integer primary key, name text, img text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbPokemon, int oldVersion, int newVersion) {
        dbPokemon.execSQL("DROP TABLE IF EXISTS pokemon");
        onCreate(dbPokemon);
    }

    public boolean insertPokemon (Pokemon pokemon) {
        SQLiteDatabase dbPokemon = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POKEMON_COLUMN_NAME, pokemon.getName());
        contentValues.put(POKEMON_COLUMN_ID, pokemon.getId());
        contentValues.put(POKEMON_COLUMN_IMG, pokemon.getImg());
        dbPokemon.insert(POKEMON_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int idPokemon) {
        SQLiteDatabase dbPokemon = this.getReadableDatabase();
        Cursor res =  dbPokemon.rawQuery( "select * from pokemon where idPokemon="+idPokemon+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase dbPokemon = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(dbPokemon, POKEMON_TABLE_NAME);
        return numRows;
    }

    public boolean updatePokemon (Pokemon pokemon) {
        SQLiteDatabase dbPokemon = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POKEMON_COLUMN_NAME, pokemon.getName());
        contentValues.put(POKEMON_COLUMN_ID, pokemon.getId());
        contentValues.put(POKEMON_COLUMN_IMG, pokemon.getImg());
        dbPokemon.update(POKEMON_TABLE_NAME, contentValues, "idPokemon = ? ", new String[] { Integer.toString(pokemon.getId()) } );
        return true;
    }

    public Integer deletePokemon (Integer idPokemon) {
        SQLiteDatabase dbPokemon = this.getWritableDatabase();
        return dbPokemon.delete(POKEMON_TABLE_NAME,
                "idPokemon = ?",
                new String[] { Integer.toString(idPokemon) });
    }

    public Integer deleteAll () {
        SQLiteDatabase dbPokemon = this.getWritableDatabase();
        return dbPokemon.delete(POKEMON_TABLE_NAME,
                null,
                null);
    }

    public ArrayList<String> getAllPokemon() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase dbPokemon = this.getReadableDatabase();
        Cursor res =  dbPokemon.rawQuery( "select * from pokemon", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(POKEMON_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Pokemon> getPokemonList() {
        ArrayList<Pokemon> lista = new ArrayList<Pokemon>() ;

        SQLiteDatabase dbPokemon = this.getReadableDatabase();
        Cursor res =  dbPokemon.rawQuery( "select * from pokemon", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Pokemon pokemon = new Pokemon();
            pokemon.setName(res.getString(res.getColumnIndex(POKEMON_COLUMN_NAME)));
            pokemon.setId(Integer.parseInt(res.getString(res.getColumnIndex(POKEMON_COLUMN_ID))));
            pokemon.setName(res.getString(res.getColumnIndex(POKEMON_COLUMN_NAME)));
            pokemon.setImg(res.getString(res.getColumnIndex(POKEMON_COLUMN_IMG)));

            lista.add(pokemon);
            res.moveToNext();
        }
        return lista;
    }
}
