/* archive local grids */
var gridLocal1 = ArchiveGrid.createLocal({ bodySelector: "#gridBody", pagingSelector: ".wr_page", pageSize: 10, columns: [] });
ArchiveGrid.register("jqGrid1", gridLocal1);

var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(document).ready(function() {

	
	// 초기화	
	

	//그리드그리기
	var columns = [
		{name:'START_DATE'	, width: 80, align:'center'},		
   		{name:'END_DATE'   	, width: 80, align:'center'},
   		{name:'ACCOUNT_NO', width: 80, hidden:false},
   		{name:'USER_ID',align:'center', width: 80},
   		{name:'CUST_REG_NO', align:'center', width: 100},
   		{name:'TEL_NO'	, width:  80, align:'center'},
   		{name:'USER_IP'	, width:  80, align:'center'},
   		{name:'ORG_FILE_NM'	, width:  80, hidden:false},
   		{name:'U_FILE_NM'	, width:  80, hidden:false},
   		{name:'FILE_URL'	, width:  80, hidden:false}
		
   	];
	
	var columnNames = ['조회시작일', '조회종료일', '계좌번호', '이용자ID', '고객번호', '전화번호', 'IP', '요청파일명', '저장파일명','다운로드'];
	
	
	
	
			
	search();
	

});

function search(){
	
	var obj = new Object();
	obj.APPROVAL_REQ_ID = $("input#reqId").val();
	console.log(" ##### reqId#####" + $("input#reqId").val() );
	
	//그리드그리기
	var columns = [
		{name:'START_DATE'	, width: 80, align:'center'},		
   		{name:'END_DATE'   	, width: 80, align:'center'},
   		{name:'ACCOUNT_NO', width: 80, hidden:false},
   		{name:'USER_ID',align:'center', width: 80},
   		{name:'CUST_REG_NO', align:'center', width: 100},
   		{name:'TEL_NO'	, width:  80, align:'center'},
   		{name:'USER_IP'	, width:  80, align:'center'},
   		{name:'ORG_FILE_NM'	, width:  80, hidden:false},
   		{name:'U_FILE_NM'	, width:  80, hidden:true},
   		{name:'FILE_URL'	, width:  80, hidden:false
   			,formatter: function(cellValue, options, rowObject){
				
				if( rowObject.FILE_URL !="" && rowObject.FILE_URL != null){
					console.log("FILE_URL=========>" + rowObject.FILE_URL )
					console.log("## url:" + rowObject.FILE_URL.substring(rowObject.FILE_URL.lastIndexOf("bulk")) ) ;
					var dnUrl = rowObject.FILE_URL.substring(rowObject.FILE_URL.lastIndexOf("bulk")) ;
					var fileNm = rowObject.FILE_URL.substring(rowObject.FILE_URL.lastIndexOf("/")) ;
				
					//return "<a href='javascript:void(0);' onclick='popUp(" + rowObject.APPROVAL_REQ_ID + ")' >[상세 배치 내용 조회]"+ cellValue + "</a>" ;
					return "<a href='" + dnUrl + "' >"+ fileNm + "&nbsp;&nbsp;</a>" ;
				}else{
					return cellValue ;
				}
			}	
   		
   		}
		
   	];
	
	var columnNames = ['조회시작일', '조회종료일', '계좌번호', '이용자ID', '고객번호', '전화번호', 'IP', '요청파일명', '저장파일명','다운로드'];
	
	// 초기화	
	
	
	
	
	
	
	
}

    

function search_(){
	var obj = new Object();
	obj.APPROVAL_REQ_ID = $("input#reqId").val();
	console.log(" ##### reqId#####" + $("input#reqId").val() );
    $.ajax({
        url:"GetBulkReqJobList",
        data:{param:JSON.stringify(obj)},
	//	processData : false,
	//	cache : false,
        dataType:"json",
        success: function(jsonData) {
        	console.log(jsonData);
			//초기화
			ArchiveGrid.clearRows(gridLocal1);
			
			var idsLen = 1 ;
			if( jsonData.rows != undefined){
				for(var i=0; i < jsonData.rows.length; i++){
					ArchiveGrid.addRowData(gridLocal1, {});	
					var rowData = ArchiveGrid.getRowData(gridLocal1, i);
					console.log("###### START_DATE:" + jsonData.rows[i].START_DATE );
					rowData.START_DATE  = jsonData.rows[i].START_DATE; 
					rowData.END_DATE  = jsonData.rows[i].END_DATE; 
					rowData.ACCOUNT_NO = jsonData.rows[i].ACCOUNT_NO; 
					rowData.USER_ID = jsonData.rows[i].USER_ID; 
					rowData.CUST_REG_NO = jsonData.rows[i].CUST_REG_NO; 
					rowData.TEL_NO = jsonData.rows[i].TEL_NO; 
					rowData.USER_IP = jsonData.rows[i].USER_IP; 
					rowData.ORG_FILE_NM = jsonData.rows[i].ORG_FILE_NM; 
					rowData.U_FILE_NM = jsonData.rows[i].U_FILE_NM; 
				//	if( jsonData.rows[i].FILE_URL != null && jsonData.rows[i].FILE_URL != "null")
						rowData.FILE_URL = jsonData.rows[i].FILE_URL; 
					ArchiveGrid.setRowData(gridLocal1, i, rowData);	
				
					/*
					ArchiveGrid.addRowData(gridLocal1, {});
					ArchiveGrid.setCell(gridLocal1, idsLen, 'START_DATE', jsonData.rows[i].fromDate);
					ArchiveGrid.setCell(gridLocal1, idsLen, 'END_DATE', jsonData.rows[i].toDate);
					ArchiveGrid.setCell(gridLocal1, idsLen, 'ACCOUNT_NO', jsonData.rows[i].accountNo);
					ArchiveGrid.setCell(gridLocal1, idsLen, 'USER_ID', jsonData.rows[i].userId);
					ArchiveGrid.setCell(gridLocal1, idsLen, 'CUST_REG_NO', jsonData.rows[i].custRegNo);  //ykh
					ArchiveGrid.setCell(gridLocal1, idsLen, 'TEL_NO', jsonData.rows[i].telNo);  //ykh
					ArchiveGrid.setCell(gridLocal1, idsLen, 'USER_IP', jsonData.rows[i].userIp);  //ykh
					ArchiveGrid.setCell(gridLocal1, idsLen, 'FILE_NAME', jsonData.rows[i].fileName);  //ykh
					ArchiveGrid.setCell(gridLocal1, idsLen, 'U_FILE_NAME', jsonData.rows[i].uFileName);  //ykh
				
					idsLen++;
					*/
				}
				//jQuery("#jqGrid1");
				//jQuery("#jqGrid1").setGridParam({rowNum:10});
				
			}
			
			
			var bulkJsonData = ArchiveGrid.getRows(gridLocal1);
			
			
			console.log("###### getRowData:" + JSON.stringify(bulkJsonData) );
        },
        error : function(json_data, status){
            alert("[알림] 처리시오류가 발생하였습니다\n" );
            console.log("[알림] 처리시오류가 발생하였습니다\n" + json_data.responseText);
        }
    });
    

}

