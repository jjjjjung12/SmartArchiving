var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'MagicView',
		pageSize: 10,
		columns: [
			{name:'SP_TR_YMD', align:'center'},
			{name:'SP_INST_ID', align:'center'},
			{name:'SP_TR_NO', align:'center'},
			{name:'SP_USER_ID', align:'center'},
			{name:'SP_USER_NAME', align:'center'},
			{name:'SP_JUMIN_NO', hidden:true},
			{name:'SP_CUST_NO', align:'center'},
			{name:'SP_IN_IP', align:'center'},
			{name:'SP_SERVICE_ID', align:'center'},
			{name:'SP_SERVICE_NAME', align:'center'},
			{name:'SP_RST_CD', align:'center'},
			{name:'SP_ERR_MSG_CD', align:'center'},
			{name:'SP_GUID', align:'center'},
			{name:'SP_UPMU_SID', align:'center'},
			{name:'SP_TR_ID', align:'center'},
			{name:'SP_TR_NM', align:'center'},
			{name:'SP_BODY_ID', align:'center'},
			{name:'SP_COM_ID', align:'center'},
			{name:'SP_TR_CD', hidden:true},
			{name:'SP_DT_CD', align:'center'},
			{name:'SP_TR_VALUE', hidden:true},
			{name:'SP_STD_DATA', align:'center'},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : { __rows:'50', __page:'1' }; },
		onRowClick: function(row, idx, inst) { loadInfo(row, idx, inst.currentPage); },
	});
	ArchiveGrid.wirePage(grid);
});
$(document).ready(function() {

	var jsonObj = new Object();
	
	
	
	function loadInfo(selectedRow, currentRow, currentPage) {
		
		var selectdRow = selectedRow;

		console.log(selectdRow);
	
		var sSP_STD_DATA = selectdRow['SP_STD_DATA'];			
		$('#F_DETAIL_DATA' ).val(sSP_STD_DATA);
		
		var element = $('.master-panel .tbl_box').first();
		$('#F_DETAIL_DATA').width(element.width());
		$('#F_DETAIL_DATA').height(200);
	};

	$("#export").on("click", function(){
		
		var fileName = "통합로그_" + $('#F_SP_TR_YMD_EXPORT').val() + ".xlsx";
		
		ArchiveGrid.exportToExcel(_archiveGrid, {
			includeLabels : true,
			includeGroupHeader : true,
			includeFooter: true,
			fileName : fileName,
			maxlength : 40 // maxlength for visible string data 
		})
	});	
	
	$('#btnCancel').click(function (e) {
		$('#searchVal').val("");
		$("#searchVal").focus();
	});

	
	$('#btnAdd').click(function (e) {
		$('#F_SERVER_ID'  ).val("");
		$('#F_SERVER_NM'    ).val("");
		$('#F_SERVER_IP'    ).val("");
		$('#F_SERVER_DESC' ).val("");
		$('#F_SERVER_CLASS_CD').val("");
		$('input[name="F_USE_YN"]').val(["Y"]);
        //getAllSelectOptions("F_SERVER_CLASS_CD","SERVER_CLASS_CD","");
        //$('#F_SERVER_CLASS_CD' ).val("");
		$('#F_SERVER_CLASS_CD option:eq(0)').prop("selected", true);

		$('#CRUD'         ).val("C");
		$('#F_SERVER_ID'  ).attr("readonly", true); //설정
	});
	
	$('#btnDelete').click(function (e) {
		var formData = new FormData();
		
		var obj = new Object();
		obj.server_id   = $("input#F_SERVER_ID").val();
		obj.crud        = "D";
		
		console.log('F_SERVER_ID:'+ obj.server_id);
		console.log('sCrud:'+ obj.crud);

		$("#SetServerForm").ajaxForm({
			url : 'SetServer',
			dataType:'json',
			type: 'post',
			data : {param:JSON.stringify(obj)},
			success: function(json_data) {
				alert("정상적으로처리 되었습니다");
				$('#F_SERVER_ID'  ).val("");
				$('#F_SERVER_NM'    ).val("");
				$('#F_SERVER_IP'    ).val("");
				$('#F_SERVER_DESC' ).val("");
				$('#F_SERVER_CLASS_CD option:eq(0)').prop("selected", true);
				$('input[name="F_USE_YN"]').val(["Y"]);
				$('#CRUD'         ).val("C");
				$('#F_SERVER_ID'  ).attr("readonly", true); //설정			
				if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
			},
			error : function(data, status){
		    	if (data != null){
		    		if (data.error == 2) { // 임의 JSON 형식의 {error:2} 값을 서버에서 전달					// data 오브젝트의 error 값이 2일 때의 이벤트 처리
		    			alert("이미 등록되어 있는 아이디입니다");
		    		} else {
		    			alert("Error");
		    		}
		    	}
			}
		});	
		$("#SetServerForm").submit() ;
	});
	
	$('#btnSave').click(function (e) {
		var formData = new FormData();
		
		var obj = new Object();
		obj.server_id   = $("input#F_SERVER_ID").val();
		obj.server_nm   = $("input#F_SERVER_NM").val();
		obj.server_ip   = $("input#F_SERVER_IP").val();
		obj.server_desc = $("textarea#F_SERVER_DESC").val();
		obj.server_class_cd = $("select#F_SERVER_CLASS_CD").val();
		obj.server_class_nm = $('select#F_SERVER_CLASS_CD option:selected').text();
		obj.use_yn = $('input[name="F_USE_YN"]:checked').val()
		obj.crud        = $("input#CRUD").val();
		
		console.log('sCrud:'+ obj.crud);
		console.log('server_id:'+ obj.server_id); 
		console.log('server_nm:'+ obj.server_nm);
		console.log('server_ip:'+ obj.server_ip);
		console.log('server_desc:'+ obj.server_desc);
		console.log('server_class_cd:'+ obj.server_class_cd);
		console.log('server_class_nm:'+ obj.server_class_nm);
		console.log('use_yn:'+ obj.use_yn);
		
		if(obj.server_nm == ''){
			alert("[알림] 서버명을 입력하세요");
			$("input#F_SERVER_NM").focus();
		    return;
		}

		$("#SetServerForm").ajaxForm({
			url : 'SetServer',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(obj)},
			success: function(json_data) {
				if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
				$('#btnAdd').click();
				alert("정상적으로처리 되었습니다");
			},
			error : function(data, status){
		    	if (data != null){
		    		if (data.error == 2) { // 임의 JSON 형식의 {error:2} 값을 서버에서 전달
		    			alert("이미 등록되어 있는 아이디입니다");
		    		} else {
		    			alert("Error");
		    		}
		    	}
			}
		});	
		$("#SetServerForm").submit() ;
	});

	$('input[name="C_USE_YN"]').val(["Y"]);
	
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	
	$("#searchVal").focus();

});
