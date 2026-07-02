var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(document).ready(function() {
	var jsonObj = new Object();
	var ilogArray = new Array();
	
    //글로벌 트랜젝션 거래 리스트그리드
	function get_message_ser_no()
	{		
		var Obj = new Object();
		Obj.__F_SP_TR_YMD_S     = $('#H_SP_TR_YMD_S').val() ;
		Obj.__F_SP_TR_YMD_E     = $('#H_SP_TR_YMD_E').val() ;		
		Obj.__F_TRX_DTIME       = $('#H_TRX_DTIME').val();     
		Obj.__F_MESSAGE_SER_NO  = $('#H_MESSAGE_SER_NO').val();     
		Obj.__F_TRX_TRACKING_NO = $('#H_TRX_TRACKING_NO').val();   
		Obj.__F_USER_CD_DETAIL	= $('#user_cd_detail').val();
		
		Obj.__server_id = "*" ;
		Obj.__rows      = "20";
		Obj.__page      = "1" ;		
		
		//서버 정보 추가 ykh : 2024.08.08
		Obj.__corpValue = $('#corpValue').val();   
		Obj.__serverValue = $('#serverValue').val();   
		Obj.__detailCorpValue = $('#H_CORP').val() ;
		
		console.log("send Data:" + JSON.stringify(Obj));
		
		$('#CRUD').val("R")
		
	    var columns = [
	    	{name:'P_TRX_TRACKING_NO'   , width: 160 , align:'center'},
	    	{name:'P_TRX_DTIME'         , width: 110 , align:'center'},        	
	    	{name:'P_MESSAGE_ID'        , width: 120 , align:'center', hidden:true},
	    	{name:'P_MESSAGE_NAME'      , width: 120 , align:'center', hidden:true},
	    	{name:'P_MESSAGE_SER_NO'   	, width: 100 , hidden:true   },
	    	{name:'P_MESSAGE_HEADER_DATA', width: 100 , hidden:true   },
	    	{name:'P_MESSAGE_BODY_DATA' , width: 100 , hidden:true   },
	    	{name:'P_MESSAGE_BODY_DATA2', width: 100 , hidden:true   },
	    	{name:'P_MESSAGE_BODY_DATA3', width: 100 , hidden:true   },
	    	{name:'P_MESSAGE_BODY_DATA4', width: 100 , hidden:true   },
	    	{name:'P_MESSAGE_BODY_DATA5', width: 100 , hidden:true   },
	    	{name:'P_USER_ID'   		, width: 100 , hidden:true   },
	    	{name:'P_CUST_REG_NO'   	, width: 100 , hidden:true   },
	    	{name:'P_IP'   				, width: 100 , hidden:true   },
	    	{name:'P_TR_ACNO'   		, width: 100 , hidden:true   },
	    	{name:'P_IN_ACNO'   		, width: 100 , hidden:true   },
	    	{name:'P_SRCH_ACNO'   		, width: 100 , hidden:true   },
	    	{name:'P_ERROR_CODE'   		, width: 100 , hidden:true   },
	    	{name:'P_CHANNEL_CODE'   	, width: 100 , hidden:true   },
	    	{name:'P_LOG_LEVEL'   		, width: 100 , hidden:true   }
	   	];		
		
		
		
	};
	
	function loadInfo(rowId, currentRow, currentPage) {				
		// 거래시간과인터페이스ID로해당 기간 전문 정보를가져온다
		var selectdRow = ArchiveGrid.getRowData(gridDetail, rowId);
	    var sSP_TRX_TRACKING_NO = selectdRow['P_TRX_TRACKING_NO'];
	    $('#H_TRX_TRACKING_NO').val(sSP_TRX_TRACKING_NO); 	    
	    
	    //데이터상세 새로 추가(현업요청)
	    $('#F_SP_MESSAGE_SER_NO_POP').val(selectdRow['P_MESSAGE_SER_NO']);
	    $('#F_SP_MESSAGE_ID_POP').val(selectdRow['P_MESSAGE_ID']);
	    $('#F_SP_CUST_NO_POP').val(selectdRow['P_CUST_REG_NO']);
	    $('#F_SP_TR_ACNO_POP').val(selectdRow['P_TR_ACNO']);
	    $('#F_SP_IN_ACNO_POP').val(selectdRow['P_IN_ACNO']);
	    $('#F_SP_USER_ID_POP').val(selectdRow['P_USER_ID']);
	    $('#F_SP_IP_POP').val(selectdRow['P_IP']);
	    $('#F_SP_HEADER_POP').val(selectdRow['P_MESSAGE_HEADER_DATA']);
	    $('#F_SP_DATA_POP').val(selectdRow['P_MESSAGE_BODY_DATA']);
	    
	    var sMESSAGE_ID 		= selectdRow['P_MESSAGE_ID'];	
	    var sTRX_DTIME 			= selectdRow['P_TRX_DTIME'];	
        var sMESSAGE_HEADER_DATA= selectdRow['P_MESSAGE_HEADER_DATA'];
	    var sLOG_LEVEL			= selectdRow['P_LOG_LEVEL'];
        
        //Sub 그리드(영문명 한글명 마스킹여부, 마스킹패턴등등출력)
        get_message_deta(sTRX_DTIME, sMESSAGE_ID, sMESSAGE_HEADER_DATA, sLOG_LEVEL );
    };
	
	//Sub 그리드(영문명 한글명 마스킹여부, 마스킹패턴등등출력)
	function get_message_deta(trx_time, message_id, message_header_data, log_level)
	{
		$("#keyValueTableDivID").replaceWith(function () {
			return "<div id='keyValueTableDivID'><table id='keyValueJqGrid'></table><div id='keyValueJqGridPager'></div></div>";
   		});
		
		if(message_id == null || message_id == ""){
			alert("message_id가 존재하지 않습니다.");
			return false;
		}
		
		console.log("get_message_deta start");
		var Obj = new Object()
		Obj.__F_TRX_DTIME   		= trx_time;     
		Obj.__F_MESSAGE_ID  		= message_id;   
		Obj.__rows      			= "20";
		Obj.__page      			= "1" ;
		Obj.__F_MESSAGE_HEADER_DATA = message_header_data;
		Obj.__F_LOG_LEVEL  			= log_level;
		
		//서버 정보 추가 ykh : 2024.08.08
		Obj.__corpValue = $('#corpValue').val();   
		Obj.__serverValue = $('#serverValue').val();   
		Obj.__detailCorpValue = $('#H_CORP').val() ;
								
		var columns = [	    	
	    	{name:'VERSION'      		, width: 100, hidden: true},        	
	    	{name:'MESSAGE_FIELD_ID'	, width: 100},        	
	    	{name:'MESSAGE_FIELD_NAME' 	, width: 100},
	    	{name:'DATA' 				, width: 100},
	    	{name:'SORT_ORDER' 			, width: 100, hidden: true},
	    	{name:'DATA_LENGTH'  		, width: 100, hidden: true}
	    ];
		
		
				
	}
	
	w2popup.on('open', function(event) {
		event.onComplete = function () {
			console.log('popup event: ' + event.type, event);
			
			
			get_message_ser_no();
        }
	    
	});
	

});
