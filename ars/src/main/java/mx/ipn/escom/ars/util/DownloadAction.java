package mx.ipn.escom.ars.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import antlr.Utils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Result;

public class DownloadAction extends ActionSupport {

	private InputStream fileInputStream;
	private String fileName;

	public HttpHeaders index() throws Exception {

		String zip = (String) ActionContext.getContext().getSession()
				.get(mx.ipn.escom.ars.util.NombreObjetosSesion.RECURSO);

		String crp = (String) ActionContext.getContext().getSession()
				.get(mx.ipn.escom.ars.util.NombreObjetosSesion.CARPETA);

		File crpAux = new File(crp);

		if (crpAux.exists()) {
			ZipHelper.fileToZip(new File(crp), new File(zip), 1);

			File file = new File(zip);
			fileName = file.getName();
			fileInputStream = new FileInputStream(new File(zip));
			return new DefaultHttpHeaders("success").disableCaching();

		} else {
			addActionError("No existen archivos para descargar");
			return new DefaultHttpHeaders("error").disableCaching();
		}

	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
