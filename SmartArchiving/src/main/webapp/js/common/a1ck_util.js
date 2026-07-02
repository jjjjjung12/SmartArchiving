$(document).ready(function() {

	function refreshData()  
	{
		
		console.log("refreshData.........");
		
		var Now = new Date();
		var NowTime =    Now.getHours();
		NowTime +=  Now.getMinutes();
		NowTime +=  Now.getSeconds();

		$('#F_SP_TR_STIME').val(NowTime);
		
	}
	
});




