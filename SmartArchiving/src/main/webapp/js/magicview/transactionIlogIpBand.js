var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetIlogIpBandHeader',
		pageSize: 10,
		columns: [
			{name:'TRX_DTIME', align:'center'},
			{name:'LOG_CR_INFO', hidden:true},
			{name:'LOG_CR_INFO_NM', align:'center'},
			{name:'SCRN_ID', align:'center'},
			{name:'MESSAGE_ID', align:'center'},
			{name:'REQ_RES_TYPE_NM', align:'center'},
			{name:'MESSAGE_NAME', align:'center'},
			{name:'REG_CUST_NO', align:'center'},
			{name:'USER_ID', align:'center'},
			{name:'TEL_NO', align:'center'},
			{name:'TR_ACNO', align:'center'},
			{name:'IN_ACNO', align:'center'},
			{name:'ERROR_CODE', align:'center'},
			{name:'MESSAGE_SER_NO', align:'center'},
			{name:'IP', align:'center'},
			{name:'CORP', hidden:true},
			{name:'CORP_NM', align:'center'},
			{name:'REQ_RES_TYPE', hidden:true},
			{name:'TRX_TRACKING_NO', hidden:true},
			{name:'OS', hidden:true},
			{name:'MAC', hidden:true},
			{name:'PROFILE', hidden:true},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : { __rows:'50', __page:'1' }; },
		onRowClick: function(row, idx, inst) { loadInfo(row, idx, inst.currentPage); },
	});
	ArchiveGrid.wirePage(grid);
});
$(document).ready(function() {
	
	
	
	if( sessionUserCd.indexOf('ef') > -1 || sessionUserCd.indexOf('df') > -1 ){
			$("#export").hide();
	}
	if($('#corpValue').val() == "efblview"){
		$('#scr_nm').text("은행");
	}else if($('#corpValue').val() == "efsbview"){
		$('#scr_nm').text("상호");
	}
	
		console.log("btn searchData.........");
		if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	
	$("#export").on("click", function(){
		if(!window._archiveGrid || ArchiveGrid.getRows(window._archiveGrid).length === 0){
			alert("데이터가 존재하지 않습니다.");
			return false;
		}
		
		var obj = new Object();
		obj.user_cd = sessionUserCd;
		
		var popWidth = document.body.clientWidth;
	    var popHeight = document.body.clientHeight;
	    
	    var pageName = "";
	    var tempPageName= window.location.href;
	    var strPageName	= tempPageName.split("/");
	    pageName = strPageName[strPageName.length-1].split("?")[0];
	    
		openArchivePopup('downloadReqPop?&userId=' + sessionSsoId + '&userCd=' + sessionUserCd
			+ '&userNm=' + sessionUserNm + '&teamNm=' + sessionGroupId + '&ssoId=' + sessionSsoId + '&temp=' + $('#F_USER_LINE').val()
			+ '&pageName=' + pageName + '&gubun=downloadReqPop', {
			width: popWidth - 850,
			height: popHeight - 670,
			keyboard: false
		});
	});	
	
	
	$('#btnMasking').click(function (e) {
		var popWidth = document.body.clientWidth;
	    var popHeight = document.body.clientHeight;
	    
	    var pageName = "";
	    var tempPageName= window.location.href;
	    var strPageName	= tempPageName.split("/");
	    pageName = strPageName[strPageName.length-1].split("?")[0];
	    
		openArchivePopup('userAuthApplyPop?&userId=' + sessionsabun + '&userCd=' + sessionUserCd
			+ '&userNm=' + sessionUserNm + '&teamNm=' + sessionGroupId + '&ssoId=' + sessionSsoId + '&temp=' + $('#F_USER_LINE').val()
			+ '&pageName=' + pageName + '&gubun=masking', {
			width: popWidth - 850,
			height: popHeight - 520,
			keyboard: false
		});
	});
		
	$('input[name="C_USE_YN"]').val(["Y"]);
	
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	
	$("#searchVal").focus();

	
});

function handleEndDate(event){
	//console.log('#### endDate' + event.target.value);
	
	var endDate =  $('#F_SP_END_TR_YMD').val() ;
      //2025.4.18 입출금 계좌 누락으로 인해 ALERT 생성
      if( endDate <= '20250418'){
        	
        	 if( $('#F_TR_ACNO').val() !=''){
        		alert('2025.1.29 ~ 2025.4.18 기간에는 출금계좌번호를통한 로그 조회가 불가합니다');
        		$('#F_TR_ACNO').val('') ;
        	 }
        	 $('#F_TR_ACNO').attr('disabled',true);
      }else{
      	 $('#F_TR_ACNO').attr('disabled',false);
      }
}

		
	
function buildSearchParam() {
	if($('#F_SP_START_TR_YMD').val() > $('#F_SP_END_TR_YMD').val()){
		alert("기간을바르게입력해주세요.");
		return;
	}
	
	if($('#F_SP_START_TR_YMD').val()+ $('#F_SP_TR_STIME').val() > $('#F_SP_END_TR_YMD').val()+$('#F_SP_TR_ETIME').val()){
		alert("시간을바르게입력해주세요.");
		return;
	}
	//날짜계산 3개월 이내
	const startDate = new Date( $('#F_SP_START_TR_YMD').val().substring(0,4),$('#F_SP_START_TR_YMD').val().substring(4,6),$('#F_SP_START_TR_YMD').val().substring(6,8) );
	const endDate = new Date(  $('#F_SP_END_TR_YMD').val().substring(0,4),$('#F_SP_END_TR_YMD').val().substring(4,6),$('#F_SP_END_TR_YMD').val().substring(6,8) );
	
	if( endDate <  startDate ){
		alert("가입일자검색조건시작일은 종료일보다이전이여야합니다");
		return false ;
	}
		const difffTime = endDate - startDate ;
		const diffDays = difffTime/( 1000*60*60*24); //밀리초 => 일수
		console.log("##########조회 기간 일자####" + diffDays);
		if( diffDays > 90){
		
			alert("기간을90입이내로설정해주세요");
			return;
		}
	var searchCnt = 0;
	
	if($('#F_SP_IP_START').val().trim() == "" || $('#F_SP_IP_END').val().trim() == ""){
		alert("IP은행조회  조건을입력해주세요");
		return;
	}
	
	
	var Obj = new Object();
	
	var startDateTime = $('#F_SP_START_TR_YMD').val() + $('#F_SP_TR_STIME').val();
	var endDateTime =  $('#F_SP_END_TR_YMD').val() + $('#F_SP_TR_ETIME').val();		
	
	var sendStartDateTime = startDateTime.substring(0,4) + "-" +startDateTime.substring(4,6) + "-" +startDateTime.substring(6,8) + " " +startDateTime.substring(8,10) +":" + startDateTime.substring(10,12) ;
	var sendEndDateTime = endDateTime.substring(0,4) + "-" +endDateTime.substring(4,6) + "-" +endDateTime.substring(6,8) + " " +endDateTime.substring(8,10) +":" + endDateTime.substring(10,12) ;
	Obj.__F_SP_TR_YMD_S       = sendStartDateTime;
	Obj.__F_SP_TR_YMD_E       = sendEndDateTime;
	
	Obj.__F_SP_IP_START       = $('#F_SP_IP_START').val();
	Obj.__F_SP_IP_END 		= $('#F_SP_IP_END').val();
	
	
	Obj.__server_id = "*" ;
	Obj.__rows      = "20";
	Obj.__page      = "1" ;
	//서버 정보 추가 ykh : 2024.08.07
	Obj.__corpValue = $('#corpValue').val();   
	Obj.__serverValue = $('#serverValue').val();   
					
	console.log("send Data:" + JSON.stringify(Obj));
	$('#CRUD').val("R");
	return Obj;
    }

function loadInfo(selectedRow, currentRow, currentPage, sendStartDateTime, sendEndDateTime) {
	var selectdRow = selectedRow;

	var sCorp = selectdRow['CORP'];	
	var sSP_MESSAGE_SER_NO = selectdRow['MESSAGE_SER_NO'];	
	var sSP_TRX_DTIME = selectdRow['TRX_DTIME'];
	var sTRX_TRACKING_NO = selectdRow['TRX_TRACKING_NO'];
	var sSP_MESSAGE_ID = selectdRow['MESSAGE_ID'];
	var sReq_res_type = selectdRow['REQ_RES_TYPE'];
	
	
	$('#H_CORP' ).val(sCorp);
	$('#H_MESSAGE_SER_NO' ).val(sSP_MESSAGE_SER_NO);
	$('#H_TRX_DTIME' ).val(sSP_TRX_DTIME);
	$('#H_MESSSAGE_ID' ).val(sSP_MESSAGE_ID);
	$('#H_TRX_TRACKING_NO' ).val(sTRX_TRACKING_NO);
	$('#H_SP_TR_YMD_S' ).val(sendStartDateTime); 
	$('#H_SP_TR_YMD_E' ).val(sendEndDateTime);
	$('#H_REQ_RES_TYPE' ).val(sReq_res_type);
	
    var popWidth = document.body.clientWidth;
    var popHeight = document.body.clientHeight;
    
    var sServer = $('#serverValue').val() ;
    
    var sCorp =  $('#corpValue').val() ;
    var sUrl = "";
    var user_id =  sessionUserCd;
    console.log("###########userid:" + user_id);
    if( user_id == 'newbie'){
    	sUrl = 'jsonDetailYkh?&corp=' + sCorp + '&server=' +  sServer + '&user_cd=' +  sessionUserCd + '&gubun=jsonDetail';     	
    }else{
    	sUrl = 'jsonDetail?&corp=' + sCorp + '&server=' +  sServer + '&user_cd=' +  sessionUserCd + '&gubun=jsonDetail';     	    
    }
    openArchivePopup(sUrl, {
        width: popWidth - 250,
        height: popHeight - 80
    });
};

$("input[type='text']").on("keyup",function(key){
	if(key.keyCode==13){
		if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	}
});
