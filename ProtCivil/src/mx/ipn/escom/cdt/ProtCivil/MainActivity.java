package mx.ipn.escom.cdt.ProtCivil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mx.ipn.escom.cdt.ProtCivil.model.Criterio;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dalvik.bytecode.Opcodes;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	List<Criterio> criteriosEvaluacion;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new UpdateTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class UpdateTask extends AsyncTask {
		@Override
		protected Object doInBackground(Object... arg0) {
			receive();
			return null;
		}

		protected void onPostExecute(Object result) {
			updateView();
		}
	}

	private class SendTask extends AsyncTask {
		@Override
		protected Object doInBackground(Object... arg0) {
			try {
				send();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	public void updateView() {
		ScrollView scroll = (ScrollView) LayoutInflater.from(this).inflate(
				R.layout.scrollview, null);
		TableLayout tabla = (TableLayout) LayoutInflater.from(this).inflate(
				R.layout.table, null);
		TableRow row = null;
		TableRow radio = null;		
		TableRow number = null;

		for (int n = 0; n < criteriosEvaluacion.size(); n++) {
			row = (TableRow) LayoutInflater.from(this).inflate(R.layout.row,
					null);
			TextView nombre = (TextView) row.findViewById(R.id._nombre);
			nombre.setText(criteriosEvaluacion.get(n).getNombre());

			// spi = (TableRow)
			// LayoutInflater.from(this).inflate(R.layout.spinner,null);
			// Spinner spinner = (Spinner) spi.findViewById(R.id.spinner);
			// List<String> list = new ArrayList<String>();
			// list.add("0");
			// list.add("1");
			// ArrayAdapter<String> dataAdapter = new
			// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
			// list);
			// dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// spinner.setAdapter(dataAdapter);
			// spinner.setOnItemSelectedListener(new
			// CustomOnItemSelectedListener(criteriosEvaluacion.get(n)));
			
			if(criteriosEvaluacion.get(n).getTipo()==1){
			radio = (TableRow) LayoutInflater.from(this).inflate(R.layout.opcradio, null);

			RadioGroup rg = (RadioGroup) radio.findViewById(R.id.radioGroup);
			final Integer i=n;
			rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					int checkedRadioButton = group.getCheckedRadioButtonId();

					switch (checkedRadioButton) {
					case R.id.radioSi:
						criteriosEvaluacion.get(i).setValor(1);
						break;
					case R.id.radioNo:
						criteriosEvaluacion.get(i).setValor(0);
						break;
					}

				}
			});
			}else{
				number=(TableRow) LayoutInflater.from(this).inflate(R.layout.number, null);
				EditText edNumber=(EditText) number.findViewById(R.id.editNumber);
				final Integer i=n;
				edNumber.addTextChangedListener(new TextWatcher() {
					@Override
					public void afterTextChanged(Editable ed) {
						criteriosEvaluacion.get(i).setValor(Integer.parseInt(ed.toString()));
					}

					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {						
					}
					@Override
					public void onTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {						
					}					 
				});
			}
			tabla.addView(row);
			
			if(criteriosEvaluacion.get(n).getTipo()==1)
			tabla.addView(radio);
			else
			tabla.addView(number);

		}
		scroll.addView(tabla);
		
		Button btnEnv=new Button(this);
		btnEnv.setText("Enviar");
		tabla.addView(btnEnv);
		setContentView(scroll);

		btnEnv.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new SendTask().execute();
			}
		});
	}

	public void receive() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(
				"http://192.168.226.195:8081/proteccioncivil/rest/criterioGet");
		getData.setHeader("content-type", "application/json");
		try {
			HttpResponse resp = httpClient.execute(getData);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject jsonResponse = new JSONObject(new String(
					respStr.getBytes("ISO-8859-1"), "UTF-8"));
			Log.v("datos","");
			JSONArray data = jsonResponse.getJSONArray("criterio");
			
			criteriosEvaluacion = new ArrayList<Criterio>();

			for (int i = 0; i < data.length(); i++) {
				JSONObject obj = data.getJSONObject(i);

				Criterio c = new Criterio();
				c.setId(obj.getInt("id"));
				c.setNombre(obj.getString("nombre"));
				c.setTipo(obj.getInt("idTipo"));

				criteriosEvaluacion.add(c);
			}

		} catch (Exception e) {
			Log.e("Servicio Rest", "Error!", e);
		}

	}

	public void send() throws JSONException, ClientProtocolException,
			IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				"http://192.168.226.197:8081/proteccioncivil/rest/criterioPost");
		post.setHeader("content-type", "application/json");

		JSONArray dato = new JSONArray();

		for (Criterio c : criteriosEvaluacion) {
			JSONObject exp = new JSONObject();
			exp.put("id", c.getId());
			exp.put("nombre", c.getNombre());
			exp.put("valor", c.getValor());
			dato.put(exp);

		}

		Log.v("datos", dato.toString());
		StringEntity entity = new StringEntity(dato.toString());
		post.setEntity(entity);
		HttpResponse resp = httpClient.execute(post);
		String respStr = EntityUtils.toString(resp.getEntity());
		Log.v("envio", respStr);
	}

}
