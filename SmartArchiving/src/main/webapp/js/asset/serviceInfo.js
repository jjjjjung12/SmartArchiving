var SERVICE_COLS = [
	{name:'application_group_id', align:'center', label:'채널구분'},
	{name:'service_id', align:'center', label:'서비스ID'},
	{name:'sel_gubun', align:'center', label:'조회/기타 구분'},
	{name:'del_yn', align:'center', label:'삭제여부'}
];
var columnNames = SERVICE_COLS.map(function(c) { return c.label; });
var gridLocal1 = ArchiveGrid.createLocal({ bodySelector: '#gridBody', pagingSelector: '.wr_page', pageSize: 20, columns: SERVICE_COLS });
ArchiveGrid.register('jqGrid1', gridLocal1);
ArchiveGrid.wireLocal(gridLocal1);

function buildSearchParam() {
	var Obj = {};
	Obj.F_SP_APPLICATION_GROUP_ID = $('#F_SP_APPLICATION_GROUP_ID').val();
	Obj.__server_id = '*';
	return Obj;
}

function hasGridData() {
	return ArchiveGrid.getRows(gridLocal1).length > 0;
}

function load_data(data) {
	ArchiveGrid.loadRows(gridLocal1, data || []);
}

function searchData() {
	var Obj = buildSearchParam();
	Obj.__corpValue = $('#corpValue').val();
	Obj.__serverValue = $('#serverValue').val();
	$('#CRUD').val('R');
	$.ajax({
		url: 'GetServiceInfo',
		type: 'POST',
		dataType: 'json',
		data: { param: JSON.stringify(Obj) },
		success: function(data) {
			if (data.result && data.result !== 'OK' && typeof data.result !== 'undefined') {
				alert(data.result);
				return;
			}
			ArchiveGrid.loadRows(gridLocal1, data.rows || []);
		},
		error: function() {
			alert('조회 중 오류가 발생했습니다.');
		}
	});
}

$(document).ready(function() {
	getServer($('#C_SERVER_ID'), 1);
	document.getElementById('btnSave').disabled = true;

	$('#btnImpExcel').change(function() {
		var form = $('#SetBulkForm')[0];
		var frmData = new FormData(form);
		$.ajax({
			enctype: 'multipart/form-data',
			type: 'post',
			url: 'ParseServiceInfoReq',
			processData: false,
			contentType: false,
			cache: false,
			data: frmData,
			dataType: 'json',
			success: function(jsonData) {
				$('#bulkUniqFileName').val(JSON.stringify(jsonData.uniqFileName));
				load_data(jsonData.rows);
				document.getElementById('btnSave').disabled = false;
			},
			error: function() {
				alert('실패');
			}
		});
	});

	$('#btnQuery').click(function() {
		document.getElementById('btnSave').disabled = false;
		searchData();
	});

	$('#exportSample').click(function() {
		var fileName = 'SampleServiceInfo.csv';
		var dummyData = [
			{application_group_id: 'EFBL', service_id: 'EFBLSCID001', sel_gubun: '0', del_yn: 'A'},
			{application_group_id: 'EFBL', service_id: 'EFBLSCID002', sel_gubun: '0', del_yn: 'A'},
			{application_group_id: 'EFBL', service_id: 'EFBLSCID003', sel_gubun: '0', del_yn: 'A'}
		];
		var csv = [];
		var header = columnNames.slice(0, 4);
		csv.push(header.join(','));
		dummyData.forEach(function(row) {
			var rowData = [];
			for (var key in row) rowData.push(row[key]);
			csv.push(rowData.join(','));
		});
		var csvContent = '\uFEFF' + csv.join('\n');
		var csvFile = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
		var downloadLink = document.createElement('a');
		downloadLink.href = URL.createObjectURL(csvFile);
		downloadLink.download = fileName;
		downloadLink.click();
	});

	$('#btnSave').click(function() {
		if (!hasGridData()) {
			alert('데이터가 존재하지 않습니다.');
			return false;
		}
		var bulkJsonData = ArchiveGrid.getRows(gridLocal1);
		$('#bulkExcelJsonData').val(JSON.stringify(bulkJsonData));
		var obj = { serviceInfoList: JSON.stringify(bulkJsonData) };
		$('#load_jqGrid1').text('저장중...').css({ left: '40%', width: '10%', 'z-index': 1000 }).show();
		$.ajax({
			url: 'SetServiceInfo',
			dataType: 'json',
			type: 'post',
			data: { param: JSON.stringify(obj) },
			success: function(json_data) {
				$('#load_jqGrid1').hide();
				if (json_data.result == 'OK') {
					alert('정상적으로등록되었습니다');
					closePop();
				}
			},
			error: function(data) {
				$('#load_jqGrid1').hide();
				if (data != null) alert('Error  ' + data.responseText);
			}
		});
	});
});

function fillZero(width, str) {
	return str.length >= width ? str : new Array(width - str.length + 1).join('0') + str;
}
