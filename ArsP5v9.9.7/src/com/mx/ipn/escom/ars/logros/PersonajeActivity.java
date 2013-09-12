package com.mx.ipn.escom.ars.logros;

import com.ArsMovil.R;
import com.mx.ipn.escom.ars.recorrido.ExposicionesListadoActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import min3d.animation.AnimationObject3d;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.CameraVo;
import min3d.vos.Light;
import min3d.vos.Number3d;

public class PersonajeActivity extends RendererActivity {
	private AnimationObject3d objt3D;	
	String url;
	Button elegir;
	
	public static final String PREFS_NAME = "Configuracion";
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		elegir = (Button) findViewById(R.id.elegir);
		 Bundle datos = this.getIntent().getExtras();
		  url = datos.getString("rutaArchivo");
		  
			elegir.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					settings = getSharedPreferences(PREFS_NAME, 0);
					editor=settings.edit();
					editor.putString("modelDefault", url);
					editor.commit();
					
					Toast toast = Toast.makeText(getApplicationContext(), url +" elegido", Toast.LENGTH_SHORT);
					toast.show();
					
				}
			});
		}
	@Override
	protected void onCreateSetContentView()
	{
		setContentView(R.layout.personaje);
		RelativeLayout ll = (RelativeLayout) this.findViewById(R.id.scene1Holder);		
		ll.addView(_glSurfaceView);

	}
	
	@Override
	public void initScene() {
		IParser parser = Parser.createParser(Parser.Type.MD2,
				getResources(), url, true);
				
		parser.parse();

		objt3D = parser.getParsedAnimationObject(2);
		objt3D.position().x = -1;
		objt3D.position().x = 0;
		objt3D.position().y = 1;
		objt3D.scale().x = objt3D.scale().y = objt3D.scale().z = .15f;
		objt3D.rotation().z = -90;
		objt3D.rotation().x = -90;
		scene.addChild(objt3D);		
		Light light = new Light();
    	scene.lights().add(light);

    	scene.camera().position.y = 1;
    	scene.camera().target = new Number3d(0, 1, 0);

    	objt3D.setFps(10);
    	objt3D.play(17,43,true);
//    	objt3D.play();    	
	}
}