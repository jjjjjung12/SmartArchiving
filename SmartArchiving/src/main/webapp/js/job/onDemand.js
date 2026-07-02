var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetBatchList',
		pageSize: 10,
		columns: [
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
	});
	ArchiveGrid.wirePage(grid);
});
var jsonObj = new Object();

var init_dir = '';
var init_dbServer = '';
var init_port = '';
var init_dbName = '';
var init_userID = '';
var init_passwd = '';
var init_dbms = '';

function fn_popup(id) {
    var popup = "#" + id;
    $(id).modal("show");
}



var sqlResultGrid = ArchiveGrid.createLocal({
	columns: [],
	bodySelector: '#sqlResultBody',
	pagingSelector: '#sqlResultPaging',
	countSelector: '#sqlResultCount',
	pageSize: 100,
	data: []
});

// SQL 테스트 결과를 동적 컬럼(pqGrid colModel 형식)으로 ArchiveGrid 컬럼/헤더에 반영
function titleload(sModel) {
	var cols = (sModel || []).map(function(c) {
		return { name: c.dataIndx || c.name, label: c.title || c.dataIndx || c.name, align: 'center' };
	});
	sqlResultGrid.columns = cols;
	sqlResultGrid.visibleCols = cols.filter(function(c) { return !c.hidden; });
	var headHtml = '<tr>' + cols.map(function(c) { return '<th>' + (c.label == null ? '' : c.label) + '</th>'; }).join('') + '</tr>';
	$('#sqlResultHead').html(headHtml);
}

function bodyload(sData) {
	ArchiveGrid.setRows(sqlResultGrid, sData || []);
}
	


function refreshData(job_id) {
	
	console.log(job_id);

    try {
        jsonObj.__job_id = job_id;
        jsonObj.__job_nm = "";
        jsonObj.__rows    = '20';
        jsonObj.__page    = '1';

        $.ajax({
            url:"GetBatchList",
            data:{param:JSON.stringify(jsonObj)},
            type:"post",
            dataType:"json",
            success: function(json_data) {
                if(json_data.result == 'OK') {
//                	console.log("data:" + json_data.rows);
 //               	console.log("F_JOB_NM:" + json_data.rows[0].JOB_NM);
	            	$("input#F_JOB_NM"        	).val(json_data.rows[0].JOB_NM); 
	            	$("input#F_JOB_PATH"		).val(json_data.rows[0].JOB_PATH);
	            	$("textarea#F_DESCRIPTION"	).val(json_data.rows[0].DESCRIPTION);
	            	$("textarea#F_SRC_SQL"		).val(json_data.rows[0].SRC_SQL);
	            	$("input#F_SRC_IP"			).val(json_data.rows[0].SRC_IP);
	            	$("input#F_SRC_PORT"		).val(json_data.rows[0].SRC_PORT);
	            	$("input#F_SRC_DB"			).val(json_data.rows[0].SRC_DB);
	            	$("input#F_SRC_USER"		).val(json_data.rows[0].SRC_USER);
	            	$("input#F_SRC_PASSWD"		).val(json_data.rows[0].SRC_PASSWD);
	            	if(json_data.rows[0].SRC_DBMS_CD == "1")
	            		$('select#F_SRC_DBMS_CD option:eq(0)').prop('selected', true);
	            	if(json_data.rows[0].SRC_DBMS_CD == "2")
	            		$('select#F_SRC_DBMS_CD option:eq(1)').prop('selected', true);
	            	if(json_data.rows[0].SRC_DBMS_CD == "3")
            			$('select#F_SRC_DBMS_CD option:eq(2)').prop('selected', true);
	            	if(json_data.rows[0].SRC_DBMS_CD == "4")
            			$('select#F_SRC_DBMS_CD option:eq(3)').prop('selected', true);
	            	if(json_data.rows[0].SRC_DBMS_CD == "5")
            			$('select#F_SRC_DBMS_CD option:eq(4)').prop('selected', true);
	            	if(json_data.rows[0].SRC_DBMS_CD == "6")
            			$('select#F_SRC_DBMS_CD option:eq(5)').prop('selected', true);
	            	
	            	if(json_data.rows[0].JOB_STATUS_CD == "E")
	            		$('select#F_JOB_STATUS_CD option:eq(0)').prop('selected', true);
	            	if(json_data.rows[0].JOB_STATUS_CD == "F")
	            		$('select#F_JOB_STATUS_CD option:eq(1)').prop('selected', true);
	            	if(json_data.rows[0].JOB_STATUS_CD == "S")
	            		$('select#F_JOB_STATUS_CD option:eq(1)').prop('selected', true);
	            	if(json_data.rows[0].JOB_STATUS_CD == "W")
	            		$('select#F_JOB_STATUS_CD option:eq(1)').prop('selected', true);

	            	
//                    bodyload(json_data.rows);
	            } else {
	                console.log('NOTFOUND');
//                    bodyload(json_data.rows);
	            }
            },
            error : function(json_data, status){
                alert("[알림] 처리시오류가 발생하였습니다\n" );
                console.log("[알림] 처리시오류가 발생하였습니다\n" + json_data.responseText);
            }
        });
    } catch (error){
        alert("[알림] 처리시오류가 발생하였습니다\n" + error.message);
        $('#btnQuery').attr("disabled", false);
    };

};


function loadInit() {
	// ArchivingWeb 원본도 db.init.xml 파일이 없어 브라우저에서 404가 발생한다.
	// SmartArchiving 설정은 서버 application.yml 기준이므로 화면 신규값은 빈 값으로 시작한다.
}


$('#btnAdd').click(function (e) {
	$("#F_JOB_ID").val(""); 
	$("#F_JOB_NM").val(""); 
	$("#F_JOB_PATH").val(init_dir);
	$("#F_DESCRIPTION").val("");
	$("#F_SRC_SQL").val("");
	$("#F_SRC_IP").val(init_dbServer);
	$("#F_SRC_PORT").val(init_port);
	$("#F_SRC_DB").val(init_dbName);
	$("#F_SRC_USER").val(init_userID);
	$("#F_SRC_PASSWD").val(init_passwd);
	$("#F_LST_UPD_TM").val("");	
	$('select#F_SRC_DBMS_CD').val(init_dbms);
	$('select#F_JOB_STATUS_CD option:eq(3)').prop('selected', true);
});

$('#btnSqlSave').click(function (e) {
	var formData = new FormData();
	$(e.target).attr("disabled", true);
	
	try {
		
		var jobj = new Object();

		jobj.__job_id   	= $("select#F_JOB_ID option:selected").val(); 
		jobj.__job_nm   	= $("input#F_JOB_NM").val(); 
		//jobj.__job_path     = $("input#F_JOB_PATH").val();
		jobj.__job_user_id  = sessionuserid;
		//jobj.__description  = $("textarea#F_DESCRIPTION").val();
		jobj.__src_sql   	= $("textarea#F_SRC_SQL").val();
		//jobj.__src_ip       = $("input#F_SRC_IP").val();
		//jobj.__src_port	    = $("input#F_SRC_PORT").val();
		//jobj.__src_dbms_cd  = $('select#F_SRC_DBMS_CD option:selected').val();   
		jobj.__job_status_cd  = $('select#F_JOB_STATUS_CD option:selected').val();   
		//jobj.__src_db       = $("input#F_SRC_DB").val();
		//jobj.__src_user     = $("input#F_SRC_USER").val();
		//jobj.__src_passwd   = $("input#F_SRC_PASSWD").val();
		jobj.__lst_upd_tm	= $("input#F_LST_UPD_TM	").val();

		if(jobj.__job_nm == ''){
			alert("[알림] 작업명을 입력하세요");
			$("input#F_JOB_NM").focus();
			$(e.target).attr("disabled", false); 
			return;
		}

		/*if(jobj.__job_path == ''){
			alert("[알림] 작업명을 입력하세요");
			$("input#F_JOB_PATH").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		
		if(jobj.__db_server == ''){
			alert("[알림] 서버IP를입력하세요");
			$("input#F_SRC_IP").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		if(jobj.__port == ''){
			alert("[알림] PORT를입력하세요");
			$("input#F_SRC_PORT").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		if(jobj.__pool_name == ''){
			alert("[알림] DBMS를선택하세요");
			$("input#F_SRC_DBMS_CD").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		if(jobj.__db_name == ''){
			alert("[알림] DB를입력하세요");
			$("input#F_SRC_DB").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		if(jobj.__user_id == ''){
			alert("[알림] 사용자를 입력하세요");
			$("input#F_SRC_USER").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		if(jobj.__passwd == ''){
			alert("[알림] 패스워드를입력하세요");
			$("input#F_SRC_PASSWD").focus();
			$(e.target).attr("disabled", false);
			return;
		}*/
		if(jobj.__src_sql == ''){
			alert("[알림] SQL을입력하세요");
			$("input#F_SRC_SQL").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		
		$("#SetScheduleForm").ajaxForm({
			url : 'SetBatch',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(jobj)},
			success: function(json_data) {
				
			//	console.log(json_data.result); 
		        if(json_data.result == 'OK') {
					alert("정상적으로처리 되었습니다");
					$(e.target).attr("disabled", false);
				} else {
					console.log(json_data.result); 
					alert("[알림] 처리시오류가 발생하였습니다\n" + json_data.message);
				}
		        
			},
			error : function(data, status){
		    	if (data != null){
		    		if (data.error == 2) { // 임의 JSON 형식의 {error:2} 값을 서버에서 전달
		    			alert("이미 등록되어 있는 아이디입니다");
		    		} else {
		    			alert("[알림] 처리시오류가 발생하였습니다\n" + data.responseText);
		    		}
		    	}
		    	$(e.target).attr("disabled", false);
			}
		});	
		$("#SetScheduleForm").submit() ;
	} catch (error){
		alert("[알림] 처리시오류가 발생하였습니다\n" + error.message);
		$(e.target).attr("disabled", false);
	}
	
});


$('#btnNewSave').click(function (e) {
	var formData = new FormData();
	$(e.target).attr("disabled", true);
	
	try {
		
		var jobj = new Object();

		jobj.__job_id   	= ""; 
		jobj.__job_nm   	= $("input#F_JOB_NM").val(); 
		jobj.__job_path     = $("input#F_JOB_PATH").val();
		jobj.__job_user_id  = sessionuserid;
		jobj.__description  = $("textarea#F_DESCRIPTION").val();
		jobj.__src_sql   	= $("textarea#F_SRC_SQL").val();
		jobj.__src_ip       = $("input#F_SRC_IP").val();
		jobj.__src_port	    = $("input#F_SRC_PORT").val();
		jobj.__src_dbms_cd  = $('select#F_SRC_DBMS_CD option:selected').val();   
		jobj.__job_status_cd  = $('select#F_JOB_STATUS_CD option:selected').val();   
		jobj.__src_db       = $("input#F_SRC_DB").val();
		jobj.__src_user     = $("input#F_SRC_USER").val();
		jobj.__src_passwd   = $("input#F_SRC_PASSWD").val();
		jobj.__lst_upd_tm	= $("input#F_LST_UPD_TM	").val();

		if(jobj.__job_nm == ''){
			alert("[알림] 작업명을 입력하세요");
			$("input#F_JOB_NM").focus();
			$(e.target).attr("disabled", false); 
			return;
		}

		if(jobj.__job_path == ''){
			alert("[알림] 작업명을 입력하세요");
			$("input#F_JOB_PATH").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		
		if(jobj.__db_server == ''){
			alert("[알림] 서버IP를입력하세요");
			$("input#F_SRC_IP").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		if(jobj.__port == ''){
			alert("[알림] PORT를입력하세요");
			$("input#F_SRC_PORT").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		if(jobj.__pool_name == ''){
			alert("[알림] DBMS를선택하세요");
			$("input#F_SRC_DBMS_CD").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		if(jobj.__db_name == ''){
			alert("[알림] DB를입력하세요");
			$("input#F_SRC_DB").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		if(jobj.__user_id == ''){
			alert("[알림] 사용자를 입력하세요");
			$("input#F_SRC_USER").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		if(jobj.__passwd == ''){
			alert("[알림] 패스워드를입력하세요");
			$("input#F_SRC_PASSWD").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		if(jobj.__src_sql == ''){
			alert("[알림] SQL을입력하세요");
			$("input#F_SRC_SQL").focus();
			$(e.target).attr("disabled", false);
			return;
		}
		
		$("#SetScheduleForm").ajaxForm({
			url : 'SetBatch',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(jobj)},
			success: function(json_data) {
				
			//	console.log(json_data.result); 
		        if(json_data.result == 'OK') {
					alert("정상적으로처리 되었습니다");
					$('#btnAdd').click();
					$(e.target).attr("disabled", false);
				} else {
					console.log(json_data.result); 
					alert("[알림] 처리시오류가 발생하였습니다\n" + json_data.message);
				}
		        
			},
			error : function(data, status){
		    	if (data != null){
		    		if (data.error == 2) { // 임의 JSON 형식의 {error:2} 값을 서버에서 전달
		    			alert("이미 등록되어 있는 아이디입니다");
		    		} else {
		    			alert("[알림] 처리시오류가 발생하였습니다\n" + data.responseText);
		    		}
		    	}
		    	$(e.target).attr("disabled", false);
			}
		});	
		$("#SetScheduleForm").submit() ;
	} catch (error){
		alert("[알림] 처리시오류가 발생하였습니다\n" + error.message);
		$(e.target).attr("disabled", false);
	}
	
});



$('#btnSaveDocumentAssociation1').click(function (e) {
	$("#DocumentListPartial1").modal("hide");	    
});

 
$('#btnConnTest').click(function (e) {
	var jobj = new Object();

	jobj.__db_server = $("input#F_SRC_IP").val();
	jobj.__port	     = $("input#F_SRC_PORT").val();
	jobj.__pool_name = $('select#F_SRC_DBMS_CD option:selected').val();    
	jobj.__db_name   = $("input#F_SRC_DB").val();
	jobj.__user_id   = $("input#F_SRC_USER").val();
	jobj.__passwd	 = $("input#F_SRC_PASSWD").val();
    
	
	/*if(jobj.__db_server == ''){
		alert("[알림] 서버IP를입력하세요");
		$("input#F_SRC_IP").focus();
		$(e.target).attr("disabled", false);
		return;
	}
	if(jobj.__port == ''){
		alert("[알림] PORT를입력하세요");
		$("input#F_SRC_PORT").focus();
		$(e.target).attr("disabled", false);
		return;
	}
	if(jobj.__pool_name == ''){
		alert("[알림] DBMS를선택하세요");
		$("input#F_SRC_DBMS_CD").focus();
		$(e.target).attr("disabled", false);
		return;
	}
	if(jobj.__db_name == ''){
		alert("[알림] DB를입력하세요");
		$("input#F_SRC_DB").focus();
		$(e.target).attr("disabled", false);
		return;
	}
	if(jobj.__user_id == ''){
		alert("[알림] 사용자를 입력하세요");
		$("input#F_SRC_USER").focus();
		$(e.target).attr("disabled", false);
		return;
	}
	if(jobj.__passwd == ''){
		alert("[알림] 패스워드를입력하세요");
		$("input#F_SRC_PASSWD").focus();
		$(e.target).attr("disabled", false);
		return;
	}*/
	
	try {
        $.ajax({
			url : 'GetDBConnectivity',
			dataType:'json',
			type: 'post',
			data : {param:JSON.stringify(jobj)},
			success: function(json_data) {
		        if(json_data.result == 'OK') {					
					alert("정상적으로처리 되었습니다");
					console.log(json_data.rows); 
					$(e.target).attr("disabled", false);
				} else {
					if(json_data.messageCode == 66363) {
						alert("[알림] DBMS 드라이버 요류\n" + json_data.message);
					} else {
						console.log(json_data.messageCode); 
						console.log(json_data.message); 
						alert("[알림] 처리시오류가 발생하였습니다.\n" + json_data.message);
					}
				}
			},
			error : function(data, status){
		    	if (data != null){
	    			alert("[알림] 처리시오류가 발생하였습니다.\n" + data.responseText);
		    	}
		    	$(e.target).attr("disabled", false);
			}
		});	
		
	} catch (error){
		alert("[알림] 처리시오류가 발생하였습니다\n" + error.message);
		$(e.target).attr("disabled", false);
	}			
	
});




$('#btnSQLTest').click(function (e) {
	var jobj = new Object();

	jobj.__sql       = $("textarea#F_SRC_SQL").val();
	jobj.__db_server = $("input#F_SRC_IP").val();
	jobj.__port	     = $("input#F_SRC_PORT").val();
	jobj.__pool_name = $('select#F_SRC_DBMS_CD option:selected').val();    
	jobj.__db_name   = $("input#F_SRC_DB").val();
	jobj.__user_id   = $("input#F_SRC_USER").val();
	jobj.__passwd	 = $("input#F_SRC_PASSWD").val();
    
	if(jobj.__sql == ''){
		alert("[알림] SQL을입력하세요");
		$("textarea#F_SRC_SQL").focus();
		$(e.target).attr("disabled", false);
		return;
	}	
	/*if(jobj.__db_server == ''){
		alert("[알림] 서버IP를입력하세요");
		$("input#F_SRC_IP").focus();
		$(e.target).attr("disabled", false);
		return;
	}
	if(jobj.__port == ''){
		alert("[알림] PORT를입력하세요");
		$("input#F_SRC_PORT").focus();
		$(e.target).attr("disabled", false);
		return;
	}
	if(jobj.__pool_name == ''){
		alert("[알림] DBMS를선택하세요");
		$("input#F_SRC_DBMS_CD").focus();
		$(e.target).attr("disabled", false);
		return;
	}
	if(jobj.__db_name == ''){
		alert("[알림] DB를입력하세요");
		$("input#F_SRC_DB").focus();
		$(e.target).attr("disabled", false);
		return;
	}
	if(jobj.__user_id == ''){
		alert("[알림] 사용자를 입력하세요");
		$("input#F_SRC_USER").focus();
		$(e.target).attr("disabled", false);
		return;
	}
	if(jobj.__passwd == ''){
		alert("[알림] 패스워드를입력하세요");
		$("input#F_SRC_PASSWD").focus();
		$(e.target).attr("disabled", false);
		return;
	}*/
    
	try {
        $.ajax({
			url : 'GetSQLTest',
			dataType:'json',
			type: 'post',
			data : {param:JSON.stringify(jobj)},
			success: function(json_data) {
		        if(json_data.result == 'OK') {					
					console.log(json_data); 
                    titleload(json_data.cols);
                    bodyload(json_data.rows);
//					alert("정상적으로처리 되었습니다");
					$(e.target).attr("disabled", false);
				} else {
					if(json_data.messageCode == 66363) {
						alert("[알림] DBMS 드라이버 요류\n" + json_data.message);
					} else if(json_data.messageCode == 42601) {
							alert("[알림] SQL 문법오류입니다\n" + json_data.message);
					} else {
						console.log(json_data.messageCode); 
						console.log(json_data.message); 
						alert("[알림] 처리시오류가 발생하였습니다.\n" + json_data.message);
					}
				}
			},
			error : function(data, status){
		    	if (data != null){
	    			alert("[알림] 처리시오류가 발생하였습니다.\n" + data.responseText);
		    	}
		    	$(e.target).attr("disabled", false);
			}
		});	
		
	} catch (error){
		alert("[알림] 처리시오류가 발생하였습니다\n" + error.message);
		$(e.target).attr("disabled", false);
	}			
	
});


$("input#F_JOB_NM").focus();
$('#btnQuery').attr("disabled", false);

  
$('select#F_SRC_DBMS_CD option:eq(1)').prop('selected', true);
$('select#F_JOB_STATUS_CD option:eq(3)').prop('selected', true);

refreshData($("input#F_JOB_ID").val());
loadInit();
