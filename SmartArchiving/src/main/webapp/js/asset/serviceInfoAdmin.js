var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

var SERVICE_COLS = [
	{name:'application_group_id', align:'center', className:'col-channel', label:'채널구분'},
	{name:'service_id', align:'center', className:'col-service-id', label:'서비스 ID'},
	{name:'sel_gubun', align:'center', className:'col-sel-gubun', label:'조회/기타 구분'},
	{name:'del_yn', align:'center', className:'col-del-yn', label:'삭제여부'}
];
var columnNames = SERVICE_COLS.map(function(c) { return c.label; });

var gridLocal1 = ArchiveGrid.createLocal({
	bodySelector: '#gridBody',
	pagingSelector: '.wr_page',
	countSelector: '#totalCnt',
	pageSize: 10,
	columns: SERVICE_COLS
});
ArchiveGrid.register('jqGrid1', gridLocal1);
ArchiveGrid.wireLocal(gridLocal1);

function setSaveEnabled(enabled) {
	var btnSave = document.getElementById('btnSave');
	if (btnSave) btnSave.disabled = !enabled;
}

function load_data(data) {
	ArchiveGrid.loadRows(gridLocal1, data || []);
	setSaveEnabled(ArchiveGrid.getRows(gridLocal1).length > 0);
}

function searchData() {
	var Obj = {
		F_SP_APPLICATION_GROUP_ID: '',
		__server_id: '*',
		__rows: '20',
		__page: '1'
	};
	$.ajax({
		url: archiveCtx + '/GetServiceInfo',
		type: 'POST',
		dataType: 'json',
		data: { param: JSON.stringify(Obj) },
		success: function(data) {
			if (data.result && data.result !== 'OK' && data.result !== 'NOTFOUND') {
				alert(data.result);
				return;
			}
			load_data(data.rows || []);
		},
		error: function(xhr) {
			if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
				return;
			}
			alert('조회 중 오류가 발생했습니다.');
		}
	});
}

function uploadServiceFile(file) {
	if (!file) return;
	ArchiveGrid.clearRows(gridLocal1);
	var frmData = new FormData();
	frmData.append('btnImpExcel', file);
	$('.loading_bg').addClass('on');
	$.ajax({
		url: archiveCtx + '/ParseServiceInfoReq',
		type: 'post',
		processData: false,
		contentType: false,
		cache: false,
		data: frmData,
		dataType: 'json',
		success: function(jsonData) {
			$('.loading_bg').removeClass('on');
			if (!jsonData || jsonData.result !== 'OK') {
				alert('업로드 파일을 읽지 못했습니다.');
				return;
			}
			$('#bulkUniqFileName').val(jsonData.uniqFileName || '');
			load_data(jsonData.rows || []);
		},
		error: function(xhr) {
			$('.loading_bg').removeClass('on');
			if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
				return;
			}
			alert('파일 업로드에 실패했습니다.');
		}
	});
}

function saveServiceInfoAdmin() {
	var rows = ArchiveGrid.getRows(gridLocal1);
	if (!rows.length) {
		alert('데이터가 존재하지 않습니다.');
		return;
	}
	if (!confirm('기존에 등록된 서비스 데이터는 모두 삭제됩니다. 삭제하시겠습니까?')) {
		return;
	}
	$('.loading_bg').addClass('on');
	$.ajax({
		url: archiveCtx + '/SetServiceInfoAdmin',
		type: 'post',
		dataType: 'json',
		data: { param: JSON.stringify({ serviceInfoList: JSON.stringify(rows) }) },
		success: function(json) {
			$('.loading_bg').removeClass('on');
			if (json.result === 'OK') {
				alert('정상적으로 등록되었습니다.');
				searchData();
				return;
			}
			alert(json.msg || '저장에 실패했습니다.');
		},
		error: function(xhr) {
			$('.loading_bg').removeClass('on');
			if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
				return;
			}
			alert('저장 중 오류가 발생했습니다.');
		}
	});
}

$(document).ready(function() {
	setSaveEnabled(false);

	$('#btnSaveExcel').on('click', function(e) {
		e.preventDefault();
		$('#btnImpExcel').val('').trigger('click');
	});

	$('#btnImpExcel').on('change', function() {
		var file = this.files && this.files[0];
		uploadServiceFile(file);
		this.value = '';
	});

	$('#btnQuery').on('click', function(e) {
		e.preventDefault();
		searchData();
	});

	$('#btnSave').on('click', function(e) {
		e.preventDefault();
		saveServiceInfoAdmin();
	});

	$('#exportSample').on('click', function(e) {
		e.preventDefault();
		var fileName = 'SampleServiceInfo.csv';
		var dummyData = [
			{application_group_id: 'EFBL', service_id: 'EFBLSCID001', sel_gubun: '0', del_yn: 'A'},
			{application_group_id: 'EFBL', service_id: 'EFBLSCID002', sel_gubun: '0', del_yn: 'A'},
			{application_group_id: 'EFBL', service_id: 'EFBLSCID003', sel_gubun: '0', del_yn: 'A'}
		];
		var csv = [columnNames.join(',')];
		dummyData.forEach(function(row) {
			csv.push([row.application_group_id, row.service_id, row.sel_gubun, row.del_yn].join(','));
		});
		var blob = new Blob(['\uFEFF' + csv.join('\n')], { type: 'text/csv;charset=utf-8;' });
		var a = document.createElement('a');
		a.href = URL.createObjectURL(blob);
		a.download = fileName;
		a.click();
		URL.revokeObjectURL(a.href);
	});
});
