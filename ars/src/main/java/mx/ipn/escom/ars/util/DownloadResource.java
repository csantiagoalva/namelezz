package mx.ipn.escom.ars.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;


import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.core.io.FileSystemResource;

import antlr.Utils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Result;

public class DownloadResource extends ActionSupport {

	private InputStream fileInputStream;
	private String fileName;
	private Long contentLength;
	private Integer idMuseoSel;
		
	public HttpHeaders index() throws Exception {
		String url;
		String url2;
		if(idMuseoSel!=null){
			url="Repo/Museo_"+idMuseoSel+"/sounds";
			url2="Repo/Museo_"+idMuseoSel+"/recursos.zip";
			}else{
				url="Repo/Museos/Recursos";
				url2="Repo/Museos/generales.zip";
			}
		
		ZipHelper.fileToZip(new File(url), new File(url2), 1);		
		
		File file=new File(url2);		
		fileName=file.getName();								
		fileInputStream = new FileInputStream(new File(url2));
		
		contentLength=file.length();				
		
		return new DefaultHttpHeaders("success").disableCaching();
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

	public Long getContentLength() {
		return contentLength;
	}

	public void setContentLength(Long contentLength) {
		this.contentLength = contentLength;
	}

	public Integer getIdMuseoSel() {
		return idMuseoSel;
	}

	public void setIdMuseoSel(Integer idMuseoSel) {
		this.idMuseoSel = idMuseoSel;
	}

	


	
}
