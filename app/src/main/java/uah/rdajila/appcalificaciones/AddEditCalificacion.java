package uah.rdajila.appcalificaciones;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uah.rdajila.appcalificaciones.util.Constant;

public class AddEditCalificacion extends AppCompatActivity {

    private static final String TAG = AddEditCalificacion.class.getSimpleName();
    private String _idContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        _idContact = getIntent().getStringExtra(Constant._KEY_ID_CONTACT); // Capturamos el id del contacto para editar la informacion

        setTitle( (_idContact == null)?R.string.title_add_contact:R.string.title_edit_contact ); // Actualiza el titulo de la actividad

        // Inflamos el fragmento que gestiona la creacion y edicion de contacto
        AddEditCalificacionesFragment _fragment = new AddEditCalificacionesFragment();
        Bundle args = new Bundle();
        args.putString(Constant._KEY_ID_CONTACT, _idContact);
        _fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .add(R.id._contenidoLayout, _fragment)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}