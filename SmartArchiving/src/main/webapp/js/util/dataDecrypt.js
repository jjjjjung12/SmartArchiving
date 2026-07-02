var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetDataDecRst',
		pageSize: 10,
		columns: [
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
	});
	ArchiveGrid.wirePage(grid);
});
$(document).ready(function() {

	$('#btnDec').click(function (e) {
		
		console.log("btn btnDec.........");
		console.log("searchDataLen........." + $('#F_INPUT_ENC').val().length );
		console.log("searchData........." + $('#F_INPUT_ENC').val() );
		       
		
		var Obj = new Object();		
		Obj.__F_INPUT_TEXT    = $('#F_INPUT_ENC').val();
		Obj.__TYPE = "DEC";
		
		$.ajax({
			url: 'GetDataDecRst',    
			data: {param:JSON.stringify(Obj)},
			type:"post",
			dataType:"json",
			success: function(json_data) {
	
				console.log(json_data);
		        if(json_data.result == 'OK') {		        	
			        $('#F_RST_DEC').val(json_data.OUTPUT);			      			        
					console.log(json_data.OUTPUT);			
					
				} else {
					console.log(json_data.result); 
				}
			}
		});			
	});
});
