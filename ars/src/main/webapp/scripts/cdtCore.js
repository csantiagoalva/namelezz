// #ifndef CDT_CREATE_REQUEST
// #def    CDT_CREATE_REQUEST
//if( !cdtCreateRequest ){	
	// Document cdtCreateRequest(String url){
	function cdtCreateRequest(url){
		var req;
		
		try{
		    if(XMLHttpRequest){
		    	// W3C Standard.
		    	return new XMLHttpRequest();
		    }else if (window.XMLHttpRequest) {
		    	// IE7 o posterior
				return new window.XMLHttpRequest;
    		}else{
    			// IE anterior a IE7
	            return new ActiveXObject("MSXML2.XMLHTTP.3.0");
        	}
		}catch(err){
			return null;
		}
	}
//}

 
// #ifndef CDT_REQUEST_FILE
// #def    CDT_REQUEST_FILE
//if( !cdtRequestFile ){
	var included = new Array();
	
	// Document cdtRequestFile(String url){
	function cdtRequestFile(url){
		var doc;
		
		try{
			req = cdtCreateRequest();
			req.open("GET", url, false);
			if(req.status==200)
				return req.responseText;
			else
				return null;	
		}catch(err){
			return null;
		}
	}
//}

// #ifndef CDTINCLUDE
// #def    CDTINCLUDE
//if( !cdtInclude ){
	var included = new Array();
	
	// int CdtInclude(){
	function cdtInclude(url){
		var doc;
		
		try{
			var head= document.getElementsByTagName('head')[0];
			var script= document.createElement('script');
			script.type= 'text/javascript';
			script.src= url;
			head.appendChild(script);

//			doc = cdtRequestFile();
//			if(doc != null){
//				var scr = document.createElement("script");	
//				scr.text=doc;
//				document.getElementsByTagName("head")[0].appendChild(src);
//				scr.type="text/javascript";
//				scr.setAttribute("src",url);
//			}else{
//				alert("Error alintentar obtener el documento: "+url);
//			}
		}catch(err){
			alert("cdtInclude: "+err);
			return null;
		}
	}
//}
