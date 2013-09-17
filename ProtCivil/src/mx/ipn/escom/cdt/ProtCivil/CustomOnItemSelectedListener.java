package mx.ipn.escom.cdt.ProtCivil;

import mx.ipn.escom.cdt.ProtCivil.model.Criterio;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {

	private Criterio criterio;
	
	public CustomOnItemSelectedListener(Criterio criterio){
		this.criterio=criterio;
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		Toast.makeText(parent.getContext(),"OnItemSelectedListener : "+ parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
		
		this.criterio.setValor(Integer.parseInt(parent.getItemAtPosition(pos).toString()));
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}