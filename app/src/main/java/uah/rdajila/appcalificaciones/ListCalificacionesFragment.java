package uah.rdajila.appcalificaciones;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import uah.rdajila.appcalificaciones.contract.CalificacionContract;
import uah.rdajila.appcalificaciones.db.CalificacionDbHelper;
import uah.rdajila.appcalificaciones.util.Constant;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListCalificacionesFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListCalificacionesFragment extends Fragment {

    private static final String TAG = ListCalificacionesFragment.class.getSimpleName();

    private CalificacionDbHelper _db;
    private ListView _listContact;
    private CalificacionCursorAdapter _contactAdaptador;

    private FloatingActionButton _btnAdd;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        _db = new CalificacionDbHelper(getActivity());
        // Inflate the layout for this fragment
        View _viewLayout = inflater.inflate(R.layout.fragment_list_calificaciones, container, false);
        _listContact = (ListView) _viewLayout.findViewById(R.id._listData);
        _contactAdaptador = new CalificacionCursorAdapter(getActivity(),null);
        _listContact.setAdapter(_contactAdaptador);

        _listContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("Range")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor _currentData = (Cursor) _contactAdaptador.getItem(i);
                showDetailScreen(_currentData.getString(_currentData.getColumnIndex(CalificacionContract._ID)));
            }
        });

        _btnAdd = (FloatingActionButton)_viewLayout.findViewById(R.id._btnAdd);
        _btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { actionAddContact(); }
        });

        loadContacts();

        return _viewLayout;
    }

    /**
     * Función que levanta la actividad New Contacto
     */
    private void actionAddContact()
    {
        Intent _intentView = new Intent( getActivity(), AddEditCalificacion.class );
        startActivityForResult( _intentView, Constant._REQUEST_ADD_CONTACT ); // Constant._REQUEST_ADD_CONTACT valor del resultCode
    }

    /**
     * Función que levanta la actividad edit Contacto
     * @param idContact
     */
    private void showDetailScreen(String idContact)
    {
        Intent intent = new Intent(getActivity(), AddEditCalificacion.class);
        intent.putExtra(Constant._KEY_ID_CONTACT, idContact);
        startActivityForResult(intent, Constant._REQUEST_SHOW_CONTACT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (Activity.RESULT_OK == resultCode)
        {
            switch (requestCode)
            {
                case Constant._REQUEST_ADD_CONTACT:
                    showSavedMessage();
                    break;
            }
        }else if(Constant._REQUEST_EDIT_CONTACT == resultCode){
            showUpdatedMessage();
        }
        loadContacts();
    }

    /**
     * Mensaje de guardado exitoso
     */
    private void showSavedMessage()
    {
        Toast.makeText(getActivity(), R.string.msn_save_curso, Toast.LENGTH_SHORT).show();
    }

    private void showUpdatedMessage()
    {
        Toast.makeText(getActivity(), R.string.msn_update_curso, Toast.LENGTH_SHORT).show();
    }

    /**
     * Carga el listado de contactos
     */
    private void loadContacts()
    {
        new CursoLoadTask().execute();
    }

    /**
     * Clase que controla la carga de todos los contactos
     */
    private class CursoLoadTask extends AsyncTask<Void, Void, Cursor>
    {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return _db.getAllContacts(); // Consulta todos los contactos
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            _contactAdaptador.swapCursor(cursor); // El adaptador de la lista pinta los datos en la lista
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}