var DETAIL_COLS = [
	{name:'P_TRX_DTIME', align:'center'},
	{name:'P_MESSAGE_SER_NO', align:'center'},
	{name:'P_MESSAGE_ID', align:'center'},
	{name:'P_REQ_RES_TYPE_NM', align:'center'},
	{name:'P_REQ_RES_TYPE', hidden:true},
	{name:'P_TRX_TRACKING_NO', hidden:true},
	{name:'P_MESSAGE_DATA', hidden:true},
	{name:'P_USER_ID', hidden:true},
	{name:'P_CUST_REG_NO', hidden:true},
	{name:'P_IP', hidden:true},
	{name:'P_MESSAGE_RES', hidden:true}
];
var META_COLS = [
	{name:'P_REG_DTM'},
	{name:'P_CHG_DTM'},
	{name:'P_TGRM_SNO', hidden:true},
	{name:'P_REPR_TGRM_ID'},
	{name:'P_TGRM_NM_ENG'},
	{name:'P_TGRM_NM_KOR'},
	{name:'P_ETC_FRWD_YN1', hidden:true},
	{name:'P_MSK_PTTRN', hidden:true}
];
var KV_COLS = [
	{name:'P_INDEX', align:'center'},
	{name:'P_KEY', align:'center'},
	{name:'P_NAME', align:'center'},
	{name:'P_VALUE'},
	{name:'P_ID', hidden:true}
];
var gridDetail = ArchiveGrid.createLocal({ bodySelector: '#gridBodyDetail', pagingSelector: '.wr_page_detail', pageSize: 100, columns: DETAIL_COLS,
	onRowDblClick: function(row, idx) { loadInfoDetail(idx); }
});
var gridMeta = ArchiveGrid.createLocal({ bodySelector: '#gridBodyMeta', pagingSelector: '.wr_page_meta', pageSize: 900, columns: META_COLS });
var gridKeyValue = ArchiveGrid.createLocal({ bodySelector: '#gridBodyKeyValue', pagingSelector: '.wr_page_kv', pageSize: 100, columns: KV_COLS });
ArchiveGrid.wireLocal(gridDetail);
ArchiveGrid.wireLocal(gridMeta);
ArchiveGrid.wireLocal(gridKeyValue);

function metaCell(idx, field) {
	var row = ArchiveGrid.getRowData(gridMeta, idx);
	return row[field] != null ? row[field] : '';
}

var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(document).ready(function() {
	
});

var jsonObj = new Object();
var ilogArray = new Array();
var maskYn	= "";
var onRequest = false ; //ykh
var pMsgId="";

w2popup.on('open', function(event) {
	event.onComplete = function () {
		console.log('popup event: ' + event.type, event);
		
		
		if($("#gubun").val() == "jsonDetail") searchDataDetail();
    } 
});

function searchDataDetail()  
{		
	console.log("searchDataDetail.........H_CORP" + $('#H_CORP').val() );
	console.log("searchDataDetail.........H_MESSAGE_SER_NO" + $('#H_MESSAGE_SER_NO').val() );
	console.log("searchDataDetail........." + $('#H_TRX_DTIME').val() );
	console.log("searchDataDetail.........H_TRX_TRACKING_NO" + $('#H_TRX_TRACKING_NO').val() );
	
	get_message_ser_no();
};	

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
    	{name:'P_TRX_DTIME'         , width: 160 , align:'center'},
    	{name:'P_MESSAGE_SER_NO'    , width: 200 , align:'center'},
    	{name:'P_MESSAGE_ID'        , width: 170 , align:'center'},
    	{name:'P_REQ_RES_TYPE_NM'   , width: 100 , align:'center'},
    	{name:'P_REQ_RES_TYPE'      , width: 100 , hidden:true   },
    	{name:'P_TRX_TRACKING_NO'   , width: 100 , hidden:true   },
    	{name:'P_MESSAGE_DATA'      , width: 100 , hidden:true   },
    	{name:'P_USER_ID'   		, width: 100 , hidden:true   },
    	{name:'P_CUST_REG_NO'   	, width: 100 , hidden:true   },
    	{name:'P_IP'   				, width: 100 , hidden:true   },
    	{name:'P_MESSAGE_RES'   	, width: 100 , hidden:true   }
   	];
	
	$.ajax({
		url: 'GetSameTranIDData',
		type: 'POST',
		dataType: 'json',
		data: { param: JSON.stringify(Obj) },
		success: function(data) {
			if (data.result == 'OK') {
				ArchiveGrid.loadRows(gridDetail, data.rows || []);
				var trx_time = $('#H_TRX_DTIME').val();
				var message_id = $('#H_MESSSAGE_ID').val();
				var header_data = '';
				var message_res = '';
				for (var i = 0; i < (data.rows || []).length; i++) {
					if (message_id == data.rows[i].P_MESSAGE_ID && data.rows[i].P_REQ_RES_TYPE == $('#H_REQ_RES_TYPE').val()) {
						header_data = data.rows[i].P_MESSAGE_DATA;
						message_res = data.rows[i].P_MESSAGE_RES;
						$('#POP_REQ_RES_TYPE').val(data.rows[i].P_REQ_RES_TYPE);
					}
				}
				if (!header_data) {
					$('#CHK_MESSAGE').attr('style', 'display:block;');
					$("label[for='MESSAGE']").text('전문메타 정보가 존재하지 않습니다.');
				} else {
					$('#F_SP_MESSAGE_SER_NO_POP').val(data.rows[0].P_MESSAGE_SER_NO);
					$('#F_SP_MESSAGE_ID_POP').val(data.rows[0].P_MESSAGE_ID);
					$('#F_REG_CUST_NO_POP').val(data.rows[0].P_CUST_REG_NO);
					$('#F_SP_USER_ID_POP').val(data.rows[0].P_USER_ID);
					$('#F_SP_IN_IP_POP').val(data.rows[0].P_IP);
					$('#CHK_MESSAGE').attr('style', 'display:none;');
					maskYn = data.maskYn;
					get_message_data(trx_time, message_id, header_data, message_res);
				}
			} else {
				$('#CHK_MESSAGE').attr('style', 'display:block;');
				$("label[for='MESSAGE']").text('전문메타 정보가 존재하지 않습니다.');
			}
		}
	});
};

function loadInfoDetail(rowId, currentRow, currentPage) {				
	// 거래시간과인터페이스ID로해당 기간 전문 정보를가져온다
    var selectdRow = ArchiveGrid.getRowData(gridDetail, rowId);
    var sMESSAGE_ID = selectdRow['P_MESSAGE_ID'];	
    var sTRX_DTIME = selectdRow['P_TRX_DTIME'];	
    var sSP_STD_DATA = selectdRow['P_MESSAGE_DATA'];
    var sSP_TRX_TRACKING_NO = selectdRow['P_TRX_TRACKING_NO'];
    var sMESSAGE_RES = selectdRow['P_MESSAGE_RES'];	
    $('#H_TRX_TRACKING_NO').val(sSP_TRX_TRACKING_NO); 	    
    
    //데이터상세 새로 추가(현업요청)
    $('#F_SP_MESSAGE_SER_NO_POP').val(selectdRow['P_MESSAGE_SER_NO']);
    $('#F_SP_MESSAGE_ID_POP').val(selectdRow['P_MESSAGE_ID']);
    $('#F_REG_CUST_NO_POP').val(selectdRow['P_CUST_REG_NO']);
    $('#F_SP_USER_ID_POP').val(selectdRow['P_USER_ID']);
    $('#F_SP_IN_IP_POP').val(selectdRow['P_IP']);
    $('#POP_REQ_RES_TYPE').val(selectdRow['P_REQ_RES_TYPE']);
    
    console.log("##### sTRX_DTIME " + sTRX_DTIME);  
    console.log("##### sMESSAGE_ID " + sMESSAGE_ID);  
    console.log("##### sMESSAGE_RES " + sMESSAGE_RES); 
    //Sub 그리드(영문명 한글명 마스킹여부, 마스킹패턴등등출력)
    get_message_data(sTRX_DTIME, sMESSAGE_ID, sSP_STD_DATA, sMESSAGE_RES);
};

//Sub 그리드(영문명 한글명 마스킹여부, 마스킹패턴등등출력)
function get_message_data(trx_time, message_id, header_data, message_res)
{
	
	
	if(message_id == null || message_id == ""){
		alert("message_id가 존재하지 않습니다.");
		return false;
	}
	pMsgId = message_id;
	console.log("get_message_data start=============  message_id:" + message_id);
	console.log("get_message_data start=============  message_res:" + message_res);
	var Obj = new Object()
	Obj.__F_TRX_DTIME   = trx_time;     
	Obj.__F_MESSAGE_ID  = message_id;   
	Obj.__rows      = "20";
	Obj.__page      = "1" ;
	Obj.req_res_type	= $('#POP_REQ_RES_TYPE').val();
	
	//서버 정보 추가 ykh : 2024.08.08
	Obj.__corpValue = $('#corpValue').val();   
	Obj.__serverValue = $('#serverValue').val();   
	Obj.__detailCorpValue = $('#H_CORP').val() ;
	
	//dummy message_res 추가 2025.02.18
	Obj.__F_MESSAGE_RES  = message_res;   		
	
	$('#CRUD').val("R")
	
    var columns = [	    	
    	{name:'P_REG_DTM'      , width: 100 },        	
    	{name:'P_CHG_DTM'      , width: 100 },        	
    	{name:'P_TGRM_SNO' 	   , width: 100	, hidden: true},
    	{name:'P_REPR_TGRM_ID' , width: 100	, search: true},
    	{name:'P_TGRM_NM_ENG'  , width: 100 , search: true},
    	{name:'P_TGRM_NM_KOR'  , width: 100 },
    	{name:'P_ETC_FRWD_YN1' , width: 0 	},
    	{name:'P_MSK_PTTRN'    , width: 0 	}
   	];
	
	$.ajax({
		url: 'GetLayoutMeta',
		type: 'POST',
		dataType: 'json',
		data: { param: JSON.stringify(Obj) },
		success: function(data) {
			if (data.result == 'OK') {
				$('#load_detailJqGrid').text('전문 layout 구성중입니다...').css({ left: '140%', width: '100%', 'z-index': 1000 }).show();
				$('#CHK_MESSAGE').attr('style', 'display:none;');
				ArchiveGrid.loadRows(gridMeta, data.rows || []);
				gridUpdate(ArchiveGrid.getRows(gridMeta), header_data);
			} else {
				ArchiveGrid.clearRows(gridKeyValue);
				$('#CHK_MESSAGE').attr('style', 'display:block;');
				$("label[for='MESSAGE']").text('전문메타 정보가 존재하지 않습니다.');
			}
		}
	});
}

//transactionMask.jsp(전문 마스킹 화면에서 등록된마스킹패턴을Sub 그리드마스킹패턴을SET
function gridUpdate(rowData, header_data) {	
	jsonObj = {};

	jsonObj.__tgrm_nm_eng = "";
	jsonObj.__rows      = "20";
	jsonObj.__page      = "1" ;
	
	$.ajax({
		url: 'GetTranMaskList',    
		data: {param:JSON.stringify(jsonObj)},
		type:"post",
		dataType:"json",
		success: function(json_data) {
			if(json_data.result == 'OK') {
				var ids = ArchiveGrid.getRows(gridMeta).map(function(r,i){return i+1;});
				var idsLen = ids.length + 1;
				
				for(var i=0; i<json_data.records; i++){
					$.each(rowData, function(index, value) {
						if(json_data.rows[i].TGRM_NM_ENG == value.P_TGRM_NM_ENG){
							ArchiveGrid.setCell(gridMeta, index+1, 'P_MSK_PTTRN', json_data.rows[i].MSK_PTTRN);
							ArchiveGrid.setCell(gridMeta, index+1, 'P_ETC_FRWD_YN1', "Y");
						}
					});
					ArchiveGrid.addRowData(gridMeta, {});
					ArchiveGrid.setCell(gridMeta, idsLen, 'P_TGRM_NM_ENG', json_data.rows[i].TGRM_NM_ENG);
					ArchiveGrid.setCell(gridMeta, idsLen, 'P_TGRM_NM_KOR', json_data.rows[i].TGRM_NM_KOR);
					ArchiveGrid.setCell(gridMeta, idsLen, 'P_MSK_PTTRN', json_data.rows[i].MSK_PTTRN);
					ArchiveGrid.setCell(gridMeta, idsLen, 'P_ETC_FRWD_YN1', "Y");
					ArchiveGrid.setCell(gridMeta, idsLen, 'P_REPR_TGRM_ID', json_data.rows[i].REPR_TGRM_ID);  //ykh
					idsLen++;
				}
			} else {
				console.log("GetTranMaskList :" + json_data.result); 
			}
			
			//데이터상세 트리 그리드
			OnGridConvert(header_data);
		}
	});	
};

//데이터상세 트리 그리드및관련데이터생성
function OnGridConvert(header_data)
{
	
	ArchiveGrid.clearRows(gridKeyValue);

	var list = ArchiveGrid.getRows(gridDetail).map(function(r,i){return i+1;});
	var tempNmKor= "";
	var tempetcFrwdYn1 = "";
	var tempmaskPttrn = "";
	var data = "";
	var tempMsgId ="";
	
	var tree_index = 0;
	var array_parent_index = 0;
	var parent_index=0;
	
	var sOutMsgId ="";
	var beforeLevel = "0" ;
    var gap = 0;
	
	
	for(var i = 1; i<= list.length ; i++)
	{					
		var cellValue = (ArchiveGrid.getRowData(gridDetail, i).P_TRX_TRACKING_NO || '');
		var curValue = $('#H_TRX_TRACKING_NO').val();	
		//console.log("cellvalue : " + cellValue + " curValue : " + curValue)
		if(curValue == curValue)
		{
			//var message_data2= $("#detailJqGrid").getCell(i, "P_MESSAGE_DATA");
			var message_data= header_data;
			//console.log("message_data: " + message_data);
			//console.log("message_data2: " + message_data2);
			
			ilogArray.length = 0;
			ilogDataParsing( JSON.parse(message_data), "", 0); // KEY VALUE Object List Create
			
			OnKeyValue();
			 
			for(let j = 0; j < ilogArray.length; j++) {
				var rowId = ArchiveGrid.getRows(gridKeyValue).length;
				
				//console.log("ilogArray[j] : " + ilogArray[j]);
				
				var keyVaue = ilogArray[j].split('|');
				console.log("###### key :" + keyVaue);
				var curLevel = keyVaue[2];
				if( keyVaue[0].indexOf("OUT") > -1){
					sOutMsgId =  keyVaue[0].substring(0,  keyVaue[0].indexOf("_")) ;
					console.log("OUT SSSSSSSSSSSSSS#####################" + sOutMsgId);
				}
				tempNmKor = "";
				
				
			/** filter  기능 안됨
					if( sOutMsgId !="" ){
						var filters = {
								groupOp: "AND",
								rules:[
									{field:"P_TGRM_NM_ENG", op:"eq", data: keyVaue[0] },
									{field:"P_REPR_TGRM_ID", op:"cn", data: sOutMsgId}
								]
						};
						
						
						
						console.log("OUT SSSSSSSSSSSSS fileredData#########" + filteredData );
						var filteredData = 	ArchiveGrid.getRows(gridMeta);
						console.log("OUT SSSSSSSSSSSSS fileredData#########" + filteredData );
						console.log("OUT  RETURN #####################" + keyVaue[0] + "###" + sOutMsgId +"###" + $("#metaJqGrid").getCell(1, "P_REPR_TGRM_ID") );
					}else{
						var filters = {
								groupOp: "AND",
								rules:[
									{field:"P_TGRM_NM_ENG", op:"eq", data: keyVaue[0] },
									{field:"P_REPR_TGRM_ID", op:"cn", data: pMsgId}
								]
						};
						
						
						console.log("keyValue################" +  keyVaue[0]  ) ;
						console.log("pMsgId################" +  pMsgId  ) ;
						var filteredData = 	ArchiveGrid.getRows(gridMeta);
						console.log("fileredData#########" + filteredData );
						console.log("RETURN #####################" + keyVaue[0] + "###" + pMsgId +"###" + $("#metaJqGrid").getCell(1, "P_REPR_TGRM_ID") );
					}
			**/	
				//console.log("keyVaue[0] : " + keyVaue[0] + " : " + $("#metaJqGrid").getGridParam('records'));
				var jsonArr = ArchiveGrid.getRows(gridMeta);
				var resultArr = null ;
				if( sOutMsgId !="" ){
					resultArr = $.grep( jsonArr, function( n, i ) { var aa = "" ;if ( n.P_REPR_TGRM_ID != null  && n.P_REPR_TGRM_ID.length > 12){ aa = n.P_REPR_TGRM_ID.substring(10) ; } return n.P_TGRM_NM_ENG ===  keyVaue[0] && aa === sOutMsgId ; });
					
					
				}else{
					
					resultArr = $.grep( jsonArr, function( n, i ) { return n.P_TGRM_NM_ENG === keyVaue[0] && n.P_REPR_TGRM_ID === pMsgId; });
					
				}
				console.log("#### search result###" + JSON.stringify(resultArr));
				if( resultArr.length > 0 ){
					tempNmKor = resultArr[0].P_TGRM_NM_KOR ;
					tempetcFrwdYn1 =  resultArr[0].P_ETC_FRWD_YN1;
					tempmaskPttrn =  resultArr[0].P_MSK_PTTRN;
					tempMsgId  =  resultArr[0].P_REPR_TGRM_ID;
				}
				/** full search 오래 걸립니다.
				$.each(ArchiveGrid.getRows(gridMeta).map(function(r,i){return i+1;}), function(index) {
					//console.log(keyVaue[0] + " ::: " + $("#metaJqGrid").getCell(index+1, "P_TGRM_NM_ENG") + " :: "  +  $("#metaJqGrid").getCell(index, "P_TGRM_NM_ENG"));
					if(keyVaue[0] == (ArchiveGrid.getRowData(gridMeta, index+1).P_TGRM_NM_ENG || ''))
					{
						//console.log("OUT#####################" + sOutMsgId +"###" + $("#metaJqGrid").getCell(index+1, "P_REPR_TGRM_ID") );
						if( sOutMsgId !="" ){
							
							if ( metaCell(index+1, 'P_REPR_TGRM_ID').indexOf( sOutMsgId) > -1 ){
								tempNmKor = metaCell(index+1, 'P_TGRM_NM_KOR');
								tempetcFrwdYn1 = metaCell(index+1, 'P_ETC_FRWD_YN1');
								tempmaskPttrn = metaCell(index+1, 'P_MSK_PTTRN');
								tempMsgId  = metaCell(index+1, 'P_REPR_TGRM_ID');
								//console.log("OUT  RETURN #####################" + keyVaue[0] + "###" + sOutMsgId +"###" + $("#metaJqGrid").getCell(index+1, "P_REPR_TGRM_ID") );
								return ;
							}
							
						}else{
							tempNmKor = metaCell(index+1, 'P_TGRM_NM_KOR');
							tempetcFrwdYn1 = metaCell(index+1, 'P_ETC_FRWD_YN1');
							tempmaskPttrn = metaCell(index+1, 'P_MSK_PTTRN');
							tempMsgId  = metaCell(index+1, 'P_REPR_TGRM_ID');
							//console.log("OUT  RETURN #####################" + keyVaue[0] + "###" + sOutMsgId +"###" + $("#metaJqGrid").getCell(index+1, "P_REPR_TGRM_ID") );
							return ;
						}
						

					}
		   		});
		   		
		   		
				**/
				
				tree_index++;
				if(keyVaue[1].replace(/ /g,"") == "Object" && keyVaue[1].replace(/ /g,"") != "Array" && isNaN(keyVaue[0].replace(/ /g,""))){
					array_parent_index = tree_index;
					parent_index = tree_index;
					/*if(j == (ilogArray.length-1)){
						break;
					}*/
					if(typeof ilogArray[j+1] != "undefined") {
						if(ilogArray[j+1].split('|')[1].replace(/ /g,"") != "Object" && keyVaue[0].replace(/ /g,"") != "header"){
							data = { P_INDEX: tree_index, lv:1, pt: 1, If: false, ex: false, P_KEY:keyVaue[0], P_NAME: tempNmKor, P_VALUE:masking(keyVaue[1],tempmaskPttrn,tempNmKor,tempetcFrwdYn1) ,P_ID:tempMsgId  };
						}else{
							if(keyVaue[0].replace(/ /g,"") == "header"){
								data = { P_INDEX: tree_index, lv:0, pt: null, If: false, ex: true, P_KEY:keyVaue[0], P_NAME: tempNmKor, P_VALUE:masking(keyVaue[1],tempmaskPttrn,tempNmKor,tempetcFrwdYn1) ,P_ID:tempMsgId };
							}else{
								data = { P_INDEX: tree_index, lv:1, pt: 1, If: true, ex: true, P_KEY:keyVaue[0], P_NAME: tempNmKor, P_VALUE:masking(keyVaue[1],tempmaskPttrn,tempNmKor,tempetcFrwdYn1) ,P_ID:tempMsgId };
							}
						}
					}
					else{
						if(ilogArray[j].split('|')[1].replace(/ /g,"") != "Object" && keyVaue[0].replace(/ /g,"") != "header"){
							data = { P_INDEX: tree_index, lv:1, pt: 1, If: false, ex: false, P_KEY:keyVaue[0], P_NAME: tempNmKor, P_VALUE:masking(keyVaue[1],tempmaskPttrn,tempNmKor,tempetcFrwdYn1),P_ID:tempMsgId  };
						}else{
							if(keyVaue[0].replace(/ /g,"") == "header"){
								data = { P_INDEX: tree_index, lv:0, pt: null, If: false, ex: true, P_KEY:keyVaue[0], P_NAME: tempNmKor, P_VALUE:masking(keyVaue[1],tempmaskPttrn,tempNmKor,tempetcFrwdYn1),P_ID:tempMsgId  };
							}else{
								data = { P_INDEX: tree_index, lv:1, pt: 1, If: true, ex: true, P_KEY:keyVaue[0], P_NAME: tempNmKor, P_VALUE:masking(keyVaue[1],tempmaskPttrn,tempNmKor,tempetcFrwdYn1),P_ID:tempMsgId  };
							}
						}
					}
					
				}else if(keyVaue[1].replace(/ /g,"") == "Array"){
					if(typeof ilogArray[j+1] == "undefined"){
						data = { P_INDEX: tree_index, lv:2, pt: array_parent_index, If: true, ex: true, P_KEY:keyVaue[0], P_NAME: tempNmKor, P_VALUE:masking(keyVaue[1],tempmaskPttrn,tempNmKor,tempetcFrwdYn1),P_ID:tempMsgId  };
					}else{
						data = { P_INDEX: tree_index, lv:1, pt: array_parent_index, If: false, ex: false, P_KEY:keyVaue[0], P_NAME: tempNmKor, P_VALUE:masking(keyVaue[1],tempmaskPttrn,tempNmKor,tempetcFrwdYn1),P_ID:tempMsgId  };
					}
					parent_index = tree_index;
				}else{
					 console.log("### beforeLevel: " + beforeLevel + "### curLeveel:" + curLevel + "##gap:" + gap );

					if( parseInt(beforeLevel) >  parseInt(curLevel)  ) gap = curLevel ; 
					//if( parseInt(beforeLevel) >  parseInt(curLevel)  > 1 ||  curLevel == 3){
					if(  curLevel == 4 ){
						data = { P_INDEX: tree_index, lv:3, pt: parent_index, If: true, ex: true, P_KEY:keyVaue[0], P_NAME: tempNmKor, P_VALUE:masking(keyVaue[1],tempmaskPttrn,tempNmKor,tempetcFrwdYn1),P_ID:tempMsgId  };
					}else if(  curLevel == 3 ){
						
						data = { P_INDEX: tree_index, lv:2, pt: parent_index, If: true, ex: true, P_KEY:keyVaue[0], P_NAME: tempNmKor, P_VALUE:masking(keyVaue[1],tempmaskPttrn,tempNmKor,tempetcFrwdYn1),P_ID:tempMsgId  };
					}else if( curLevel == 2 ){
						 data = { P_INDEX: tree_index, lv:1, pt: parent_index, If: true, ex: true, P_KEY:keyVaue[0], P_NAME: tempNmKor, P_VALUE:masking(keyVaue[1],tempmaskPttrn,tempNmKor,tempetcFrwdYn1),P_ID:tempMsgId  };

					}else{
						data = { P_INDEX: tree_index, lv:3, pt: parent_index, If: true, ex: true, P_KEY:keyVaue[0], P_NAME: tempNmKor, P_VALUE:masking(keyVaue[1],tempmaskPttrn,tempNmKor,tempetcFrwdYn1),P_ID:tempMsgId  };
					}
				}
				
				ArchiveGrid.addRowData(gridKeyValue, data);
				beforeLevel = keyVaue[2];
			}
			break;
			
		}
	}
	//console.log("fn_tree: " + JSON.stringify(fn_tree(aa)));
	OnKeyValue();
	
	console.log("##################### hide #################")
	$("#load_detailJqGrid").hide();
	console.log("##################### hide#################")
}

//데이터상세 트리 그리드
function OnKeyValue()
{
	var columns = [
    	{name:'P_INDEX'       , width: 20, key:true, align:'center' },
    	{name:'P_KEY'         , width: 40, align:'center'},        	
    	{name:'P_NAME'        , width: 40, align:'center' },        	
    	{name:'P_VALUE'       , width: 60},
    	{name:'P_ID'       , width: 60 , hidden:true}
   	];
	
	
	
};

/*EFBL.TB_EF_BL_MESSAGE_LOG_DATA(은행은행, EFBL.TB_DF_SL_MESSAGE_LOG_DATA(은행상호), EFBL.TB_DF_SL_MESSAGE_LOG_DATA(은행상호)
 테이블에 존재하는 MESSAGE_DATA 를parsing 한다.*/
function ilogDataParsing( message_data, key_name, level )
{
	$.each(message_data, function(key, value){
		//console.log("key : " + key + " :value : " + value);
		 if ( typeof value === 'object' &&	 !Array.isArray(value) &&  value !== null ) 
		 {	
			 //console.log("key :" + key +" Object" );
			 ilogArray.push( key +"|Object"  + "|" +level);
			 ilogDataParsing(value, key_name, level+1);					 
		 }else if (typeof value === 'object' &&	 Array.isArray(value) &&  value !== null)
		 {
			 //console.log("########### Array : " + key +" : "+ JSON.stringify(value));
			 //var jsonArray = JSON.parse( JSON.stringify(value));	
			 ilogArray.push( key +"|Array" + "|" +level);
			 //console.log("arrry : " + value + " :key_name: " + key_name);
			 ilogDataParsing(value, key_name, level+1);				
			 

		 }else
		 {
			 //if(key_name == key) { getData = value;}
			 //console.log("key :" + key +", value: " + value);
			 ilogArray.push( key +"|"+value + "|" +level);
		 }
	});
};

/*-------------------마스킹패턴 Strart-------------------*/
var accChk = /(^[0-9]+)$/;
var valChk = /^[0-9]*$/;
var numChk = /^[0-9]{1,10}$/;
var juminChk = /^[0-9]{6}[1-4]{1}[0-9]{6}$/;
var ipChk = /(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}/;
var cardChk =	 /^[0-9]{4}[-\s\.]?[0-9]{4}[-\s\.]?[0-9]{4}[-\s\.]?[0-9]{4}$/;
var cardChkSec = /^[0-9]{4}[-\s\.]?[0-9]{4}[-\s\.]?[0-9]{4}[-\s\.]?[0-9]{3}$/;
var nameChk = /^[가-힣a-zA-Z]+$/;

function masking(val, idx, tempNmKor, tempetcFrwdYn1){
	val = val.trim();
	//마스킹해제 결재를받았으면 마스킹처리를하지 않는다
	if(maskYn == "Y"){
		return val;
	}
	
	if(tempNmKor == "" || tempNmKor == null){
		return val;
	}
	
	//console.log("val : " + val);
	if(val == "" || val == null || val == 0){
		return val;
	}
	
	if(tempetcFrwdYn1 != "Y"){
		return val;
	}

	switch(idx){
		//주민등록번호
		case '1':
		case '2':	
			if(juminChk.test(val.replace(/-/gi,"")) || numChk.test(val.replace(/-/gi,"")) ){
				for(var i=val.length-1; i>=0;i--){
					if(val.charAt(i) != "-" && (val.split('*').length-1) < 6){
						val = val.substring(0,i)+"*"+val.substring(i+1);
					}
				}
			}
			break;
		//여권번호, 개인번호
		case '4':
		case '13':
			if(numChk.test(val.replace(/-/gi,"")) ){
				for(var i=val.length-1; i>=0;i--){
					if(val.charAt(i) != "-" && (val.split('*').length-1) < 4){
						val = val.substring(0,i)+"*"+val.substring(i+1);
					}
				}
			}
			break;
		//이름
		case '5':
			if(nameChk.test(val.replace(/-/gi,"")) ){
				if(val.length == 2){
					val = val.substring(0, 1) + "*".repeat(1);
				}else{
					val = val.substring(0, 1) + "*".repeat((val.length-1)-1) + val.substring((val.length-1));
				}
			}
			
			break;
		//영문성명
		case '6':
			if(val.length <= 4){
				val = val.substring(0, 2) + "*".repeat(val.length-2);
			}else{
				for(var i=val.length-1; i>=0;i--){	
					if(val.charAt(i) != " " && (val.split('*').length-1) <  (val.length-6) ){
						val = val.substring(0,i)+"*"+val.substring(i+1);
					}
				}
			}
			
			break;
		//주소
		case '7':
			if(val.substring((val.lastIndexOf('힣')+1),(val.lastIndexOf('힣')+2)) == ' ' ){
				val = val.substring(0,val.lastIndexOf('힣')+2) + "*".repeat(val.length-(val.lastIndexOf('힣')+2) );
			}
			
			if(val.substring((val.lastIndexOf('길')+1),(val.lastIndexOf('길')+2)) == ' ' ){
				val = val.substring(0,val.lastIndexOf('길')+2) + "*".repeat(val.length-(val.lastIndexOf('길')+2) );
			}
			break;
		//핸드폰번호		case '8':
			val = val.replace(/-/gi,"");
			if(val.length == 11){
				val = val.substring(0,5) + "**" + val.substring(7,9) +  "**";
			}
			else {
				val = val.substring(0,4) + "**" + val.substring(6,8) +  "**";
			}
			break;
		//이메일		case '9':
			var tempVal = val.substring(val.lastIndexOf('@'));
			val = val.substring(0,val.lastIndexOf('@'));
			for(var i=0; i<=val.length-1;i++){	
				if(val.charAt(i) != "-" && (val.split('*').length-1) < 3){
					val = val.substring(0,i)+"*"+val.substring(i+1);
				}
			}
			val = val + tempVal;
			break;
		//아이디 전자납부번호(
		case '10':
		case '20':
			if(numChk.test(val.replace(/-/gi,"")) ){
				for(var i=val.length-1; i>=0;i--){	
					if(val.charAt(i) != "-" && (val.split('*').length-1) < 3){
						val = val.substring(0,i)+"*"+val.substring(i+1);
					}
				}
			}
			break;
		//CI
		case '14':
			if(val.replace(/!/gi,"").length == 15){
				val = val.substring(0, 7) + "*".repeat((val.length-7));
			}
			
			break;
		//IP주소
		case '15':
			if(ipChk.test(val) ){
				for(var i=0; i<=val.length-1;i++){
					if(val.charAt(i) != "." && (val.split('*').length-1) < 3){
						val = val.substring(0,i)+"*"+val.substring(i+1);
					}
				}
			}
			break;	
			
		//계좌번호(증권번호), 고객번호, I-PIN, 운전면허번호
		case '3':
		case '11':
		case '12':
		case '16':
		
			if(accChk.test(val.replace(/-/gi,"")) ){
				for(var i=val.length-1; i>=0;i--){
					if(val.charAt(i) != "-" && (val.split('*').length-1) < 5){
						val = val.substring(0,i)+"*"+val.substring(i+1);
					}
				}
			}
			
			break;
		//카드번호
		case '17':
			if(cardChk.test(val.replace(/-/gi,"")) )	{
				if(val.replace(/-/gi,"").length == 16){
					val = val.toString().replace(val,val.toString().replace(/(\d{4})-(\d{2})(\d{2})-(\d{4})-(\d{4})/gi,"$1-$2**-****-$5") );
				}else{
					val = val.toString().replace(val,val.toString().replace(/(\d{4})-(\d{2})(\d{2})-(\d{4})-(\d{3})/gi,"$1-$2**-****-$5") );
				}
			}
			break;
		//카드유효기간
		case '18':
			if(valChk.test(val.replace(/\//gi,"")) )	{
				val = val.toString().replace(val,val.toString().replace(/(\d{2})\/(\d{2})/gi,"**\/**") );
			}
			break;
		default:
			//val = "";
	}
	return val;
}
/*-------------------마스킹패턴 End-------------------*/
