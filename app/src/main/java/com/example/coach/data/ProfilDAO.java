package com.example.coach.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.coach.model.Profil;
import java.util.Date;

public class ProfilDAO extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "coach.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PROFIL = "profil";
    private static final String COL_POIDS = "poids";
    private static final String COL_AGE = "age";
    private static final String COL_TAILLE = "taille";
    private static final String COL_SEXE = "sexe";
    private static final String COL_DATE_MESURE = "dateMesure";

    public ProfilDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PROFIL + " (" +
                COL_DATE_MESURE + " INTEGER PRIMARY KEY, " + // stocké en timestamp
                COL_POIDS + " INTEGER, " +
                COL_TAILLE + " INTEGER, " +
                COL_AGE + " INTEGER, " +
                COL_SEXE + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFIL);
        onCreate(db);
    }

    public void insertProfil(Profil profil) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE_MESURE, profil.getDateMesure().getTime());
        values.put(COL_POIDS, profil.getPoids());
        values.put(COL_TAILLE, profil.getTaille());
        values.put(COL_AGE, profil.getAge());
        values.put(COL_SEXE, profil.getSexe());
        db.insert(TABLE_PROFIL, null, values);
        db.close();
    }

    public Profil getLastProfil() {
        SQLiteDatabase db = this.getReadableDatabase();
        Profil profil = null;
        String query = "SELECT * FROM " + TABLE_PROFIL + " ORDER BY " + COL_DATE_MESURE + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int poids = cursor.getInt(cursor.getColumnIndexOrThrow(COL_POIDS));
            int taille = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TAILLE));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow(COL_AGE));
            int sexe = cursor.getInt(cursor.getColumnIndexOrThrow(COL_SEXE));
            long dateMesure = cursor.getLong(cursor.getColumnIndexOrThrow(COL_DATE_MESURE));

            profil = new Profil(poids, taille, age, sexe, new Date(dateMesure));
        }
        cursor.close();
        db.close();
        return profil;
    }
}
