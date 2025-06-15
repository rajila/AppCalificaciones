package uah.rdajila.appcalificaciones.entidades;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import uah.rdajila.appcalificaciones.contract.CalificacionContract;

public class Calificacion {
    private Long id;
    private String name;
    private double nota;

    /**
     * Contructor que crea un contacto sin ID
     */
    public Calificacion(String name, double nota)
    {
        this.name = name;
        this.nota = nota;
    }

    /**
     * Contructor que crea un contacto a partir de base de datos
     * @param cursor
     */
    @SuppressLint("Range")
    public Calificacion(Cursor cursor)
    {
        id = Long.parseLong(cursor.getString(cursor.getColumnIndex(CalificacionContract._ID)));
        name = cursor.getString(cursor.getColumnIndex(CalificacionContract._NAME));
        nota = cursor.getDouble(cursor.getColumnIndex(CalificacionContract._NOTA));
    }

    /**
     * Función que prepara el contenedor con los respectivos valores para insertar en base de datos
     * @return
     */
    public ContentValues createContentValue()
    {
        ContentValues _contentVal = new ContentValues();
        _contentVal.put(CalificacionContract._NAME, name);
        _contentVal.put(CalificacionContract._NOTA, nota);
        return _contentVal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
