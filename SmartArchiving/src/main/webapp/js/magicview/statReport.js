$(function() {
	ArchiveApp.initYmdPicker('#F_SP_START_TR_YMD');
	ArchiveApp.initYmdPicker('#F_SP_END_TR_YMD', { maxDate: '+0D' });
	$('#F_SP_START_TR_YMD').datepicker('setDate', '-1M');
	$('#F_SP_END_TR_YMD').datepicker('setDate', 'today');

	var grid = ArchiveGrid.create({
		url: 'GetServiceReport',
		pageSize: 20,
		columns: [
			{name: 'APPLICATION_GROUP_ID', label: '채널명', align: 'center'},
			{name: 'SEL_GUBUN', label: '조회구분', align: 'center'},
			{name: 'DEL_YN', label: '집계포함여부', align: 'center', formatter: formatDelYn},
			{name: 'TOTAL_SUCCESS_COUNT', label: '성공건수', align: 'center', formatter: formatNumber},
			{name: 'TOTAL_FAIL_COUNT', label: '실패건수', align: 'center', formatter: formatNumber}
		],
		getPostData: buildSearchParam
	});

	window._archiveGrid = grid;

	$('#btnQuery').on('click', function(e) {
		e.preventDefault();
		if (!validateSearch()) return;
		ArchiveGrid.load(grid);
	});

	$('#export').on('click', function(e) {
		e.preventDefault();
		if (!grid.allRows || !grid.allRows.length) {
			alert('데이터가 존재하지 않습니다.');
			return false;
		}
		ArchiveGrid.exportToExcel(grid, {
			fileName: '서비스통계_' + getTodayNoPoint() + '.csv',
			includeLabels: true
		});
	});

	$('input[type="text"]').on('keyup', function(e) {
		if (e.keyCode !== 13) return;
		if (!validateSearch()) return;
		ArchiveGrid.load(grid);
	});
});

function validateSearch() {
	if (!$('#F_MEDIA_CATEGORY_CD').val()) {
		alert('조회할 채널을 선택하세요.');
		return false;
	}
	if ($('#F_SP_START_TR_YMD').val().length !== 8 || $('#F_SP_END_TR_YMD').val().length !== 8) {
		alert('조회기간을 확인해주세요.');
		return false;
	}
	return true;
}

function buildSearchParam() {
	return {
		F_MEDIA_CATEGORY_CD: $('#F_MEDIA_CATEGORY_CD').val(),
		F_SEL_GUBUN: $('#F_SEL_GUBUN').val(),
		F_DEL_YN: $('#F_DEL_YN').val(),
		F_SP_START_TR_YMD: toDashDate($('#F_SP_START_TR_YMD').val()),
		F_SP_END_TR_YMD: toDashDate($('#F_SP_END_TR_YMD').val()),
		__server_id: '*',
		__rows: '20',
		__page: '1',
		__serverValue: $('#serverValue').val()
	};
}

function toDashDate(value) {
	if (!value || value.length !== 8) return '';
	return value.substring(0, 4) + '-' + value.substring(4, 6) + '-' + value.substring(6, 8);
}

function formatDelYn(value) {
	if (value === 'Y') return '여';
	if (value === 'N') return '부';
	return value;
}

function formatNumber(value) {
	if (value == null || value === '') return '';
	var num = Number(value);
	return isNaN(num) ? value : num.toLocaleString();
}

function getTodayNoPoint() {
	var now = new Date();
	var mm = ('0' + (now.getMonth() + 1)).slice(-2);
	var dd = ('0' + now.getDate()).slice(-2);
	return now.getFullYear() + mm + dd;
}
