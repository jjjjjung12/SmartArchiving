var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetUserList',
		pageSize: 10,
		columns: [
			{name:'USER_CD', align:'center'},
			{name:'USER_NM', align:'center'},
			{name:'BRC', hidden:true},
			{name:'BRNM', align:'center'},
			{name:'USER_GRP_NM', align:'center'},
			{name:'IP_ADDRESS', align:'center'},
			{name:'EXPIRE_DATE', align:'center'},
			{name:'LOGIN_DATE', align:'center'},
			{name:'USER_GRP_ID', hidden:true},
			{name:'TELEPHONE', hidden:true},
			{name:'PASSWORD', hidden:true},
			{name:'PASSWORDORG', hidden:true},
			{name:'EMAIL', hidden:true},
			{name:'USER_ID', hidden:true},
			{name:'APPROWAITCNT', hidden:true},
			{name:'USE_YN', align:'center'},
			{name:'PICTURE', hidden:true},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
		onRowClick: function(row, idx, inst) { loadInfo(row, idx, inst.currentPage); },
	});
	ArchiveGrid.wirePage(grid);
});
function buildSearchParam() {
	var Obj = {};

		Obj.__user_cd = '*';
		Obj.__user_nm =  $('#C_USER_NM').val();
	return Obj;
}

$(document).ready(function() {
	

	

    
		if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);	
		$('#CRUD').val("C")
	});
    
    $("#export").on("click", function(){
		
		var fileName = "사용자조회" + toDayNoPoint + ".xlsx";
		
		ArchiveGrid.exportToExcel(_archiveGrid, {
			includeLabels : true,
			includeGroupHeader : true,
			includeFooter: true,
			fileName : fileName,
			maxlength : 40 // maxlength for visible string data 
		})
	});	

	$('#btnAdd').click(function (e) {
        $('#F_USER_ID'  ).val("");
        $('#F_USER_CD'  ).val("");
        $('#F_USER_NM'  ).val("");
        $('#F_TELEPHONE').val("");
        $('#F_PASSWORD' ).val("");
        $('#F_PASSWORDORG').val("XXXXX");
        $('#F_EXPIRE_DATE'  ).val("");
        $('#F_EMAIL'  ).val("");
        $('#F_APPROWAITCNT').val("");
        $('input[name="F_USE_YN"]').val(["Y"]);        
        $('#CRUD'     ).val("C");
        $('#F_USER_CD'  ).attr("readonly", false ); //설정
        $('#F_PICTURE'  ).attr("src", "assets/images/200x150.png" ); //설정	
        $('#F_BRC'  ).val("");
        $('#F_BRNM'  ).val("");
        $('#F_IP_ADDRESS'  ).val("");
	});
	
	$('#btnDelete').click(function (e) {
		$('#CRUD'     ).val("D");
		
		var obj = new Object();
		obj.user_id     = $("input#F_USER_ID").val();
		obj.user_cd     = $("input#F_USER_CD").val();
		obj.user_nm     = $("input#F_USER_NM").val();
		obj.crud		= $('#CRUD'     ).val();
		
		if(obj.user_id == ''){
			alert("[알림] 삭제할사용자를 선택하세요");
		    return;
		}

		var input = confirm('사용자['+ obj.user_nm + ']를삭제하면복구가 불가능합니다 삭제하시겠습니까?');
		if (!input) return;
		
		
		$("#SetUserForm").ajaxForm({
			url : 'SetUser',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(obj)},
			success: function(json_data) {
				if(json_data.result == "OK"){
					if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
					alert("정상적으로처리 되었습니다");
					$('#btnAdd').click();
				}
				else{
					alert("저장이 실패하였습니다");
				}
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
		$("#SetUserForm").submit() ;
	});

	
	$('#btnSave').click(function (e) {
		var obj = new Object();
		obj.user_id     = $("input#F_USER_ID").val();
		obj.user_cd     = $("input#F_USER_CD").val();
		obj.user_nm     = $("input#F_USER_NM").val();
		obj.password    = $("input#F_PASSWORD").val();
		obj.passwordorg = $("input#F_PASSWORDORG").val();
		obj.expire_date = $("input#F_EXPIRE_DATE").val();
		obj.telephone   = $("input#F_TELEPHONE").val();
		obj.email    	= $("input#F_EMAIL").val();
		obj.use_yn    	= $('input[name="F_USE_YN"]:checked').val();     
		obj.crud		= $('#CRUD'     ).val();
		obj.brc    		= $("input#F_BRC").val();
		obj.brnm    	= $("input#F_BRNM").val();
		obj.ip_address  = $("input#F_IP_ADDRESS").val();
		
		if(obj.user_cd == ''){
			alert("[알림] 사용자ID를입력하세요");
			$("input#F_USER_CD").focus();
		    return;
		}

		if(obj.user_nm == ''){
			alert("[알림] 사용자이름을 입력하세요");
			$("input#F_USER_NM").focus();
		    return;
		}

		if(obj.password == ''){
			alert("[알림] 비밀번호를 입력하세요");
			$("input#F_PASSWORD").focus();
		    return;
		}
		
		if(obj.use_yn == '' || typeof obj.use_yn == "undefined") {
		    alert("[알림] 사용여부를선택하세요");
		    return;
		}

		$("#SetUserForm").ajaxForm({
			url : 'SetUser',
			dataType:'json',
			type: 'post',
			data:{param:JSON.stringify(obj)},
			success: function(json_data) {
				if(json_data.result == "OK"){
					if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
					alert("정상적으로처리 되었습니다");
					$('#btnAdd').click();
				}
				else{
					alert("저장이 실패하였습니다");
				}
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
		$("#SetUserForm").submit() ;
	});

	function loadInfo(selectedRow, currentRow, currentPage) { // user_id, user_cd, user_nm, save_preq_cd, save_preq, description, use_yn) {
		
		var selectdRow = selectedRow;

		console.log( "rowId:" + rowId);

		$('#ROWID').val(currentRow || '');
		
        $('#F_USER_CD'  ).val(selectdRow.USER_CD);
        $('#F_USER_NM'  ).val(selectdRow.USER_NM);
        $('#F_TELEPHONE').val(selectdRow.TELEPHONE);
        $('#F_EMAIL').val(selectdRow.EMAIL);
        $('#F_PASSWORD' ).val(selectdRow.PASSWORD);
        
        if(selectdRow.PASSWORDORG == "" || selectdRow.PASSWORDORG == null){
        	$('#F_PASSWORDORG').val("XXXXX");
        }else{
        	$('#F_PASSWORDORG').val(selectdRow.PASSWORDORG);
        }
		
        $('#F_EXPIRE_DATE'  ).val(selectdRow.EXPIRE_DATE);
        
        $('#F_USER_ID'  ).val(selectdRow.USER_ID);
        $('#F_APPROWAITCNT').val(selectdRow.APPROWAITCNT);
		$('input[name="F_USE_YN"]').val([selectdRow.USE_YN]);
        $('#CRUD'     ).val("U");
        $('#F_USER_CD'  ).attr("readonly", true ); //설정		   
        
        console.log( "selectdRow.PICTURE:" + selectdRow.PICTURE.trim());
        
        if ( selectdRow.PICTURE.trim() == "" || selectdRow.PICTURE == 'null' ) { 
        	//console.log('111111');
        	$('#F_PICTURE').attr('src', "assets/images/" + "200x150.png");
        	$('.fileinput .fileinput-preview img').attr('src', "assets/images/" + "200x150.png");
        } else {
        	//console.log('222222');
        	$('#F_PICTURE').attr('src', "userImages/" + selectdRow.PICTURE);
        	$('.fileinput .fileinput-preview img').attr('src', "userImages/" + selectdRow.PICTURE);
        }
        
        $('#F_BRC'  ).val(selectdRow.BRC);
        $('#F_BRNM'  ).val(selectdRow.BRNM);
        $('#F_IP_ADDRESS'  ).val(selectdRow.IP_ADDRESS);
        
	};	
	
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	$('#CRUD').val("U")
	
	$("#searchVal").focus();
