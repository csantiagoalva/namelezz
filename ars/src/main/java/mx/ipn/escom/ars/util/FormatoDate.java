package mx.ipn.escom.ars.util;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FormatoDate {
	public static String fechaFormatoCorto(Date date){
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(date);
	}
}
