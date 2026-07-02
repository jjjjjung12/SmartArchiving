$(function() {
	if (sessionUserCd.indexOf('ef') > -1 || sessionUserCd.indexOf('df') > -1) $('#export').hide();
	function appendBreadcrumbSuffix(suffix) {
		if (!suffix) return;
		var $active = $('#archive-breadcrumb .breadcrumb li.active strong');
		if ($active.length) $active.append(' ' + suffix);
	}

	function applyCorpSuffix() {
		if ($('#corpValue').val() === 'efblview') appendBreadcrumbSuffix('(은행');
		else if ($('#corpValue').val() === 'efsbview') appendBreadcrumbSuffix('(상호)');
	}

	applyCorpSuffix();
	$(document).on('archiveMenuReady', applyCorpSuffix);

	ArchiveApp.initYmdPicker('#F_SP_START_TR_YMD');
	ArchiveApp.initYmdPicker('#F_SP_END_TR_YMD', {
		onSelect: function() {
			var endDate = $('#F_SP_END_TR_YMD').val();
			if (endDate <= '20250418') {
				if ($('#F_TR_ACNO').val()) {
					alert('2025.1.29 ~ 2025.4.18 기간에는 출금계좌번호 조회가 불가합니다');
					$('#F_TR_ACNO').val('');
				}
				$('#F_TR_ACNO').prop('disabled', true);
			} else $('#F_TR_ACNO').prop('disabled', false);
		}
	});
	$('#F_SP_START_TR_YMD').datepicker('setDate', 'today');
	$('#F_SP_END_TR_YMD').datepicker('setDate', 'today');
	refreshData();

	var grid = ArchiveGrid.create({
		url: 'GetIlogHeader',
		pageSize: 10,
		columns: [
			{name:'TRX_DTIME', align:'center'},
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
			{name:'CORP_NM', align:'center'},
			{name:'CORP', hidden:true},
			{name:'REQ_RES_TYPE', hidden:true},
			{name:'TRX_TRACKING_NO', hidden:true}
		],
		getPostData: buildSearchParam,
		onRowClick: function(row) { openDetail(row); }
	});

	$('#btnQuery').on('click', function() {
		if (!validateSearch()) return;
		ArchiveGrid.load(grid);
	});

	$('#export').on('click', function() {
		if (!grid.allRows || !grid.allRows.length) { alert('데이터가 존재하지 않습니다.'); return false; }
		var pageName = location.pathname.split('/').pop().split('?')[0];
		ArchiveGrid.openPopup('downloadReqPop?userId=' + sessionSsoId + '&userCd=' + sessionUserCd + '&userNm=' + sessionUserNm + '&teamNm=' + sessionGroupId + '&ssoId=' + sessionSsoId + '&temp=' + $('#F_USER_LINE').val() + '&pageName=' + pageName + '&gubun=downloadReqPop', { width: 600, height: 500 });
	});

	$("input[type='text']").on('keyup', function(e) { if (e.keyCode === 13) if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid); });
});

function refreshData() {
	var now = new Date();
	now.setMinutes(now.getMinutes() - 10);
	var st = ('0' + now.getHours()).slice(-2) + ('0' + now.getMinutes()).slice(-2);
	now = new Date();
	var et = ('0' + now.getHours()).slice(-2) + ('0' + now.getMinutes()).slice(-2);
	$('#F_SP_TR_STIME').val(st);
	$('#F_SP_TR_ETIME').val(et);
}

function validateSearch() {
	if ($('#F_SP_START_TR_YMD').val().length !== 8 || $('#F_SP_END_TR_YMD').val().length !== 8 ||
		$('#F_SP_TR_STIME').val().length !== 4 || $('#F_SP_TR_ETIME').val().length !== 4) {
		alert('날짜 시간을확인해주세요.'); return false;
	}
	var cnt = ['F_LOG_CR_INFO','F_SP_SCRN_ID','F_SP_MESSAGE_ID','F_SP_REG_CUST_NO','F_SP_USER_ID','F_TEL_NO','F_TR_ACNO','F_IN_ACNO','F_SP_MESSAGE_SER_NO','F_SP_IN_IP','F_MAC'].filter(function(id){ return $('#'+id).val(); }).length;
	if (cnt < 1) { alert('최소 1개이상의 조건을입력해주세요'); return false; }
	return true;
}

function buildSearchParam() {
	var s = $('#F_SP_START_TR_YMD').val() + $('#F_SP_TR_STIME').val();
	var e = $('#F_SP_END_TR_YMD').val() + $('#F_SP_TR_ETIME').val();
	var fmt = function(v) { return v.substr(0,4)+'-'+v.substr(4,2)+'-'+v.substr(6,2)+' '+v.substr(8,2)+':'+v.substr(10,2); };
	return {
		__F_SP_TR_YMD_S: fmt(s), __F_SP_TR_YMD_E: fmt(e),
		__F_LOG_CR_INFO: $('#F_LOG_CR_INFO').val(), __F_SP_SCRN_ID: $('#F_SP_SCRN_ID').val(),
		__F_SP_MESSAGE_SER_NO: $('#F_SP_MESSAGE_SER_NO').val(), __F_SP_USER_ID: $('#F_SP_USER_ID').val(),
		__F_SP_REG_CUST_NO: $('#F_SP_REG_CUST_NO').val(), __F_SP_IN_IP: $('#F_SP_IN_IP').val(),
		__F_SP_MESSAGE_ID: $('#F_SP_MESSAGE_ID').val(), __F_MAC: $('#F_MAC').val(),
		__F_TR_ACNO: $('#F_TR_ACNO').val(), __F_IN_ACNO: $('#F_IN_ACNO').val(), __F_TEL_NO: $('#F_TEL_NO').val(),
		__server_id: '*', __rows: '20', __page: '1',
		__corpValue: $('#corpValue').val(), __serverValue: $('#serverValue').val()
	};
}

function openDetail(row) {
	var s = buildSearchParam();
	$('#H_CORP').val(row.CORP); $('#H_MESSAGE_SER_NO').val(row.MESSAGE_SER_NO);
	$('#H_TRX_DTIME').val(row.TRX_DTIME); $('#H_MESSSAGE_ID').val(row.MESSAGE_ID);
	$('#H_TRX_TRACKING_NO').val(row.TRX_TRACKING_NO); $('#H_SP_TR_YMD_S').val(s.__F_SP_TR_YMD_S);
	$('#H_SP_TR_YMD_E').val(s.__F_SP_TR_YMD_E); $('#H_REQ_RES_TYPE').val(row.REQ_RES_TYPE);
	var url = (sessionUserCd === 'newbie' ? 'jsonDetailYkh' : 'jsonDetail') + '?corp=' + $('#corpValue').val() + '&server=' + $('#serverValue').val() + '&user_cd=' + sessionUserCd + '&gubun=jsonDetail';
	ArchiveGrid.openPopup(url);
}

function handleEndDate() {
	var endDate = $('#F_SP_END_TR_YMD').val();
	$('#F_TR_ACNO').prop('disabled', endDate <= '20250418');
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
}
