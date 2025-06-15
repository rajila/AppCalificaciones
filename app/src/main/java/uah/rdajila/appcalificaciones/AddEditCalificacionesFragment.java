package uah.rdajila.appcalificaciones;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uah.rdajila.appcalificaciones.db.CalificacionDbHelper;
import uah.rdajila.appcalificaciones.entidades.Calificacion;
import uah.rdajila.appcalificaciones.util.Constant;
import uah.rdajila.appcalificaciones.util.Util;

import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditCalificacionesFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditCalificacionesFragment extends Fragment {

    private CalificacionDbHelper _db;

    private static final String TAG = AddEditCalificacionesFragment.class.getSimpleName();

    private String _idData = null;

    private Button _btnAceptar;

    private TextInputLayout _tilName;
    private EditText _fieldName;

    private TextInputLayout _tilCalificacion;
    private EditText _fieldCalificacion;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            _idData = getArguments().getString(Constant._KEY_ID_CONTACT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        _db = new CalificacionDbHelper(getActivity());
        // Inflate the layout for this fragment
        View _viewLayout = inflater.inflate(R.layout.fragment_add_edit_calificaciones, container, false);

        _btnAceptar = (Button) _viewLayout.findViewById(R.id._btnSave);
        _btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { saveContact(); }
        });

        _tilName = (TextInputLayout)_viewLayout.findViewById(R.id._til_name);
        _fieldName = (EditText)_viewLayout.findViewById(R.id._field_name);
        _fieldName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { _tilName.setError(null); }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        _tilCalificacion = (TextInputLayout)_viewLayout.findViewById(R.id._til_nota);
        _fieldCalificacion = (EditText)_viewLayout.findViewById(R.id._field_nota);
        _fieldCalificacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { _tilCalificacion.setError(null); }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Carga de datos si existe el idContacto
        if (_idData != null) loadContactDB();

        return _viewLayout;
    }

    /**
     * Carga los datos del contacto desde DB
     */
    private void loadContactDB()
    {
        new GetContactByIdTask().execute();
    }

    /**
     * Guarda el contacto
     */
    private void saveContact()
    {
        Log.i(TAG, "Save Contact!!");

        String _name = _fieldName.getText().toString();
        String _nota = _fieldCalificacion.getText().toString();

        /**
         * Verificamos si los campos son correctos
         */
        boolean _stateValidateName = Util.isNameOK(_name);
        if( !_stateValidateName ) _tilName.setError(getResources().getString(R.string.form_name_error));
        else _tilName.setError(null);

        boolean _stateValidateNota = Util.isNotaOK(_nota);
        if( !_stateValidateNota ) _tilCalificacion.setError(getResources().getString(R.string.form_calificacion_error));
        else _tilCalificacion.setError(null);


        if( _stateValidateName && _stateValidateNota )
        {
            Log.i(TAG, _name);
            Calificacion _contact = new Calificacion(_name, Double.parseDouble(_nota));
            new AddEditContactTask().execute(_contact);
        }else
            Toast.makeText(getActivity(), R.string.form_general_error, Toast.LENGTH_SHORT).show();
    }

    /**
     * Carga la pantalla de listado de contactos
     * @param requery
     */
    private void showListContactScreen(Boolean requery)
    {
        if ( !requery )
        {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else getActivity().setResult(Activity.RESULT_OK);

        getActivity().finish();
    }

    /**
     * Actualiza los valores del formulario con los datos de DB
     * @param data
     */
    private void loadDetailContact(Calificacion data)
    {
        _fieldName.setText(data.getName());
        _fieldCalificacion.setText(data.getNota() + "");
    }

    private void showLoadContactDBError()
    {
        Toast.makeText(getActivity(), R.string.msn_error_load_data, Toast.LENGTH_SHORT).show();
    }

    private void showAddEditError()
    {
        Toast.makeText(getActivity(), R.string.msn_error_save_data, Toast.LENGTH_SHORT).show();
    }

    /**
     * Clase que gestiona la creación y actualizacin del contacto
     */
    private class AddEditContactTask extends AsyncTask<Calificacion, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Calificacion... contacts) {
            if (_idData != null)
            {
                Log.i(TAG,"Update Contact");
                return _db.updateContact(contacts[0], _idData) > 0;
            } else {
                Log.i(TAG,"Create new Contact");
                return _db.insertContact(contacts[0]) > 0;
            }
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            Log.i(TAG,"Show List Contact Screen");
            showListContactScreen(result);
        }
    }

    /**
     * Clase que gestiona la obtención del contacto
     */
    private class GetContactByIdTask extends AsyncTask<Void, Void, Cursor>
    {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return _db.getContactById(_idData);
        }

        @Override
        protected void onPostExecute(Cursor cursor)
        {
            if (cursor != null && cursor.moveToLast()) {
                loadDetailContact(new Calificacion(cursor));
            } else {
                // Si hay error al cargar los datos
                showLoadContactDBError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }
    }
}