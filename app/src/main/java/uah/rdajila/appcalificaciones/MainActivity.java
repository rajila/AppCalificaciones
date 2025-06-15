package uah.rdajila.appcalificaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {



    private static final String TAG = MainActivity.class.getSimpleName();

    private RelativeLayout _layoutContenido;
    private RelativeLayout _layoutInicio;
    private RelativeLayout _layoutPrincipal;
    //private Toolbar _toolbarApp;

    Handler _handler = new Handler();

    /**
     * Hilo secuandario que gestiona la pantalla de inicio de la aplicación
     */
    Runnable _runnable = new Runnable() {
        @Override
        public void run()
        {
            try {
                _layoutContenido.setVisibility(View.VISIBLE);
                _layoutInicio.setVisibility(View.GONE);
                _layoutPrincipal.setBackgroundColor(getResources().getColor(R.color.colorBlanco));

                getSupportFragmentManager().beginTransaction()
                        .add(R.id._contenidoLayout, new ListCalificacionesFragment())
                        .commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _layoutPrincipal = (RelativeLayout)findViewById(R.id._contenedorPrincipal);
        _layoutInicio = (RelativeLayout) findViewById(R.id._contenidoInicio);
        _layoutContenido = (RelativeLayout) findViewById(R.id._contenidoLayout);

        // Efecto de pantalla de inicio
        _handler.postDelayed(_runnable, 2000); //2000 is the timeout for the splash
    }
}