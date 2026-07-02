var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(document).ready(function() {
	
	function searchData()  
	{		
		var obj = new Object();
		obj.user_cd 	= "";
		obj.__server_id = "*" ;
		obj.subject		= "";
		obj.subject_detail="";
		obj.file_search	= "";
		
		var columns = [
			{name:'SUBJECT'         , width: 100 , align:'left'		},
			{name:'USER_NM'         , width: 100 , align:'center'		},
			{name:'REG_DATE'        , width: 100 , align:'center' 	,
        		formatter : function(cellValue,rowObject,options) {	   		 			
   		 			return toDatePoint2(cellValue);	   		 			
   	 			}
			},
			{name:'REG_END_DATE'    , width: 110 , align:'center'  ,
        		formatter : function(cellValue,rowObject,options) {	   		 			
   		 			return toDatePoint2(cellValue);	   		 			
   	 			}, hidden:true
			},
			{name:'SERIAL_NUMBER'   , width: 100 , hidden:true		},
	    	{name:'REG_USER_CD'     , width: 110 , hidden:true		},
	    	{name:'REG_START_DATE'  , width: 110 , hidden:true   	}
	    ];		
		
		
		
	};	

	w2popup.on('open', function(event) {
		event.onComplete = function () {
			$("#noticeTableDivID").replaceWith(function () {
				  return "<div id='noticeTableDivID'><table id='noticeJqGrid'></table><div id='noticeJqGridPager'></div></div>";
			});
		    if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
        }
	    
	});
		
});
