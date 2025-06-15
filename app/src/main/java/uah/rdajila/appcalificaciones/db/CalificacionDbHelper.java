package uah.rdajila.appcalificaciones.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import uah.rdajila.appcalificaciones.contract.CalificacionContract;
import uah.rdajila.appcalificaciones.entidades.Calificacion;

public class CalificacionDbHelper extends SQLiteOpenHelper {
    private static final int _VERSION = 1;
    private static final String _NAME = "CalificacionDb.db";

    public CalificacionDbHelper(Context context)
    {
        super(context, _NAME, null, _VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(
                "CREATE TABLE " + CalificacionContract._TABLE_NAME + " ("
                        + CalificacionContract._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                        + CalificacionContract._NAME + " VARCHAR(100) NOT NULL,"
                        + CalificacionContract._NOTA + " DOUBLE NOT NULL)"
        );
    }

    /**
     * Función que carga datos al crear la base de datos
     * @param db
     */
    private void initData(SQLiteDatabase db)
    {
        insertContactInit(db, new Calificacion("Materia 1", 9.3));
        insertContactInit(db, new Calificacion("Materia 2", 6.3));
    }

    /**
     * Inserta los datos a DB
     * @param db
     * @param eData
     * @return
     */
    private long insertContactInit(SQLiteDatabase db, Calificacion eData)
    {
        long _id = db.insert(CalificacionContract._TABLE_NAME,null, eData.createContentValue());
        db.close();
        return _id;
    }

    /**
     * Inserta el curso
     * @param eData
     * @return
     */
    public long insertContact(Calificacion eData)
    {
        SQLiteDatabase _db = getReadableDatabase();
        long _id = _db.insert(CalificacionContract._TABLE_NAME,null, eData.createContentValue());
        _db.close();
        return _id;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + CalificacionContract._TABLE_NAME);
        onCreate(db);
    }

    /**
     * Obtiene todos los cursos
     * @return
     */
    public Cursor getAllContacts()
    {
        return getReadableDatabase()
                .query(
                        CalificacionContract._TABLE_NAME,
                        CalificacionContract._GET_ALL_DATA,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    /**
     * Obtiene un curso por ID
     * @param idData
     * @return
     */
    public Cursor getContactById(String idData)
    {
        Cursor _cursorDB = getReadableDatabase().query(
                CalificacionContract._TABLE_NAME,
                CalificacionContract._GET_ALL_DATA,
                CalificacionContract._ID + " LIKE ?",
                new String[]{ idData },
                null,
                null,
                null);
        return _cursorDB;
    }

    /**
     * Actualiza la información del curso
     * @param data
     * @param idData
     * @return
     */
    public int updateContact(Calificacion data, String idData)
    {
        return getWritableDatabase().update(
                CalificacionContract._TABLE_NAME,
                data.createContentValue(),
                CalificacionContract._ID + " LIKE ?",
                new String[]{idData}
        );
    }

    /**
     * Elimina un curso de DB
     * @param idData
     * @return
     */
    public int deleteContact(String idData)
    {
        return getWritableDatabase().delete(
                CalificacionContract._TABLE_NAME,
                CalificacionContract._ID + " LIKE ?",
                new String[]{idData});
    }
}
