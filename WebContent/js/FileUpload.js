function handleFiles(files){
	var file = files[0];
	var sizeinbytes = file.size;
	
	var fSExt = new Array('Bytes', 'KB', 'MB', 'GB');
    fSize = sizeinbytes; i=0;while(fSize>900){fSize/=1024;i++;}

    
    if(sizeinbytes>5242880){
    	alert("Max Size exceed : " + (Math.round(fSize*100)/100)+' '+fSExt[i]);
    	document.location.reload(true);
    	//reload if file to heavy
    }
}
