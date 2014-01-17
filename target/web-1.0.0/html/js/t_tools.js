function rbl_onoff(s)
{
    var div  = document.getElementById(s) ;
    
	var fSets = document.getElementsByTagName('fieldset') ;
	
	for(var no=0;no<fSets.length;no++){
		fSets[no].style.display = "none";
	}
		
  	div.style.display = "" ;
	    
}