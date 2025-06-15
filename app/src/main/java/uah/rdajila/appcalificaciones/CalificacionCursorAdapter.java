package uah.rdajila.appcalificaciones;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import uah.rdajila.appcalificaciones.contract.CalificacionContract;

public class CalificacionCursorAdapter extends CursorAdapter {
    public CalificacionCursorAdapter(Context context, Cursor c)
    {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.list_item_calificaciones, parent, false);
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        // Icono
        ImageView _avatar = (ImageView) view.findViewById(R.id.iv_avatar);

        // Nombre curso
        TextView _name = (TextView) view.findViewById(R.id._nameList);
        _name.setText(cursor.getString(cursor.getColumnIndex(CalificacionContract._NAME)));

        // Nota curso
        TextView _nota = (TextView) view.findViewById(R.id._notaList);
        _nota.setText(cursor.getString(cursor.getColumnIndex(CalificacionContract._NOTA)));

    }
}
