var BULK_COLS = [
	{name:'START_DATE', align:'center', label:'조회시작일'},
	{name:'END_DATE', align:'center', label:'조회종료일'},
	{name:'ACCOUNT_NO', align:'center', label:'계좌번호'},
	{name:'USER_ID', align:'center', label:'사용자ID'},
	{name:'CUST_REG_NO', align:'center', label:'고객번호'},
	{name:'TEL_NO', align:'center', label:'전화번호'},
	{name:'USER_IP', align:'center', label:'IP'},
	{name:'ORG_FILE_NM', align:'center', label:'요청파일명'},
	{name:'U_FILE_NM', hidden:true, label:'저장파일명'}
];
var columnNames = BULK_COLS.filter(function(c) { return !c.hidden; }).map(function(c) { return c.label; });
var gridLocal1 = ArchiveGrid.createLocal({ bodySelector: '#gridBody', pagingSelector: '.wr_page', pageSize: 20, columns: BULK_COLS });
ArchiveGrid.register('jqGrid1', gridLocal1);
ArchiveGrid.wireLocal(gridLocal1);

function hasGridData() {
	return ArchiveGrid.getRows(gridLocal1).length > 0;
}

function applyBulkRows(jsonData) {
	ArchiveGrid.clearRows(gridLocal1);
	if (!jsonData.rows) return;
	if (jsonData.rows.length > 10) {
		alert('요청조건을다시 확인해주세요');
		return;
	}
	for (var i = 0; i < jsonData.rows.length; i++) {
		var r = jsonData.rows[i];
		if (!r.fromDate || !r.toDate) {
			alert('조회시작일, 조회종료일을 다시 확인해주세요.');
			ArchiveGrid.clearRows(gridLocal1);
			return;
		}
		var sy = r.fromDate.substring(0, 4), sm = r.fromDate.substring(4, 6), sd = r.fromDate.substring(6, 8);
		var ey = r.toDate.substring(0, 4), em = r.toDate.substring(4, 6), ed = r.toDate.substring(6, 8);
		var diffDays = (new Date(ey, em, ed) - new Date(sy, sm, sd)) / (1000 * 60 * 60 * 24);
		if (diffDays > 365) {
			alert('요청조건을다시 확인해주세요.');
			ArchiveGrid.clearRows(gridLocal1);
			return;
		}
		ArchiveGrid.addRowData(gridLocal1, {
			START_DATE: r.fromDate,
			END_DATE: r.toDate,
			ACCOUNT_NO: r.accountNo,
			USER_ID: r.userId,
			CUST_REG_NO: r.custRegNo,
			TEL_NO: r.telNo,
			USER_IP: r.userIp,
			ORG_FILE_NM: r.fileName,
			U_FILE_NM: r.uFileName
		});
	}
}

function uploadBulkExcel(form) {
	var frmData = new FormData(form);
	$.ajax({
		enctype: 'multipart/form-data',
		type: 'post',
		url: 'ParseBulkInquiryJobReq',
		processData: false,
		contentType: false,
		cache: false,
		data: frmData,
		dataType: 'json',
		success: function(jsonData) {
			applyBulkRows(jsonData);
		},
		error: function() {
			alert('실패');
		}
	});
}

$(document).ready(function() {
	getServer($('#C_SERVER_ID'), 1);

	$('#btnImpExcel').change(function() {
		uploadBulkExcel($('#SetBulkForm')[0]);
	});

	$('#exportSample').click(function() {
		var fileName = 'SampleBulk.csv';
		var dummyData = [
			{START_DATE: '20250201', END_DATE: '20250228', ACCOUNT_NO: '', USER_ID: 'TEST1234', CUST_REG_NO: '12345678', TEL_NO: '0102131234', USER_IP: '10.20.30.111'},
			{START_DATE: '20250301', END_DATE: '20250331', ACCOUNT_NO: '', USER_ID: 'TEST5678', CUST_REG_NO: '12345678', TEL_NO: '0102131234', USER_IP: '10.20.30.222'},
			{START_DATE: '20250301', END_DATE: '20250328', ACCOUNT_NO: '', USER_ID: 'TEST9102', CUST_REG_NO: '12345678', TEL_NO: '0102131234', USER_IP: '10.20.30.333'}
		];
		var csv = [columnNames.slice(0, 7).join(',')];
		dummyData.forEach(function(row) {
			var rowData = [];
			for (var key in row) rowData.push(row[key]);
			csv.push(rowData.join(','));
		});
		var csvContent = '\uFEFF' + csv.join('\n');
		var csvFile = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
		var a = document.createElement('a');
		a.href = URL.createObjectURL(csvFile);
		a.download = fileName;
		a.click();
	});

	$('#btnSave').click(function() {
		if (!hasGridData()) {
			alert('데이터가 존재하지 않습니다.');
			return false;
		}
		var bulkJsonData = ArchiveGrid.getRows(gridLocal1);
		$('#bulkExcelJsonData').val(JSON.stringify(bulkJsonData));
		var popWidth = document.body.clientWidth;
		var popHeight = document.body.clientHeight;
		var pageName = window.location.href.split('/').pop().split('?')[0];
		openArchivePopup('userAuthApplyPop?&userId=' + sessionsabun + '&userCd=' + sessionUserCd
			+ '&userNm=' + sessionUserNm + '&teamNm=' + sessionGroupId + '&ssoId=' + sessionSsoId + '&pageName=' + pageName
			+ '&gubun=bulk', {
			width: popWidth - 850,
			height: popHeight - 520,
			keyboard: false
		});
	});

	$('#btnSaveExcel').click(function() {
		uploadBulkExcel($('#SetBulkForm')[0]);
	});
});

function fillZero(width, str) {
	return str.length >= width ? str : new Array(width - str.length + 1).join('0') + str;
}
