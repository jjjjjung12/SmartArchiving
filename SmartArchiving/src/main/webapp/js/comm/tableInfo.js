var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
var gridLocal2;

function resolveServerId() {
	var serverId = $('#C_SERVER_ID').val();
	if (!serverId || serverId === '0') return '*';
	return serverId;
}

function buildSearchParam() {
	return {
		__serverId: resolveServerId(),
		__use_yn: $('input[name="C_USE_YN"]:checked').val() || 'Y',
		__gb: 'A',
		__table_id: '*',
		__table_nm: $.trim($('#C_TABLE_NM').val() || '')
	};
}

function loadServerCombo(onDone) {
	var $sel = $('#C_SERVER_ID');
	$.ajax({
		url: archiveCtx + '/GetServerCombo',
		dataType: 'json',
		type: 'get',
		data: { param: JSON.stringify({ __server_id: '*' }) },
		success: function(json) {
			var list = (json && json.data) ? json.data : [];
			list.forEach(function(item) {
				$sel.append(
					$('<option></option>').val(item.SERVER_ID).text(item.SERVER_NM)
				);
			});
			if (!$sel.val() || $sel.val() === '0') {
				$sel.val('*');
			}
			if (typeof onDone === 'function') onDone(list);
		},
		error: function(xhr) {
			if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
				return;
			}
			$sel.val('*');
			if (typeof onDone === 'function') onDone([]);
		}
	});
}

function loadTableGrid(opts) {
	opts = opts || {};
	if (opts.clearSelection) clearTableSelection();
	if (window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
}

function showTableDetailForm(show) {
	var $panel = $('.table-info-detail-panel');
	if (show) {
		$panel.addClass('table-info-detail-panel--active');
	} else {
		$panel.removeClass('table-info-detail-panel--active');
	}
}

function syncColumnPanelVisibility() {
	var show = !!(window._selectedTableRow && window._selectedTableRow.TABLE_ID);
	$('.table-info-bottom').toggleClass('table-info-bottom--hidden', !show);
}

function trimTableVal(v) {
	if (v == null) return '';
	return String(v).trim();
}

function clearTableDetail() {
	window._selectedTableRow = null;
	$('#CRUD1').val('C');
	$('#F_SERVER_ID').val('');
	$('#F_TABLE_ID').val('').prop('readonly', false);
	$('#F_TABLE_CD').val('');
	$('#F_TABLE_NM').val('');
	$('#F_TABLE_JOIN_NM').val('');
	$('#F_SAVE_PREQ').val('');
	$('#F_EXP_PREQ').val('');
	$('#F_DESCRIPTION').val('');
	$('#F_SAVE_PREQ_CD option:eq(0)').prop('selected', true);
	$('#F_EXP_PREQ_CD option:eq(0)').prop('selected', true);
	$('input[name="F_USE_YN"]').val(['Y']);
	showTableDetailForm(false);
	syncColumnPanelVisibility();
}

function loadTableDetail(row) {
	if (!row || !row.TABLE_ID) {
		clearTableDetail();
		return;
	}
	window._selectedTableRow = row;
	showTableDetailForm(true);
	$('#CRUD1').val('U');
	$('#F_SERVER_ID').val(trimTableVal(row.SERVER_ID));
	$('#F_TABLE_ID').val(trimTableVal(row.TABLE_ID)).prop('readonly', true);
	$('#F_TABLE_CD').val(trimTableVal(row.TABLE_CD));
	$('#F_TABLE_NM').val(trimTableVal(row.TABLE_NM));
	$('#F_TABLE_JOIN_NM').val(trimTableVal(row.TABLE_JOIN_NM));
	$('#F_SAVE_PREQ_CD').val(trimTableVal(row.SAVE_PREQ_CD));
	$('#F_SAVE_PREQ').val(trimTableVal(row.SAVE_PREQ));
	$('#F_EXP_PREQ_CD').val(trimTableVal(row.EXP_PREQ_CD));
	$('#F_EXP_PREQ').val(trimTableVal(row.EXP_PREQ));
	$('#F_DESCRIPTION').val(trimTableVal(row.DESCRIPTION));
	$('input[name="F_USE_YN"]').val([trimTableVal(row.USE_YN) || 'Y']);
}

function resetTableDetailForAdd() {
	var serverId = resolveServerId();
	if (serverId === '*' || !serverId) {
		alert('[알림] 테이블 등록 시 서버를 선택하세요');
		return;
	}
	clearTableDetail();
	window._selectedTableRow = null;
	clearColumnGrid();
	showTableDetailForm(true);
	$('#F_SERVER_ID').val(serverId);
	$('#F_TABLE_ID').focus();
	syncColumnPanelVisibility();
}

function saveTableDetail() {
	var obj = {
		table_id: trimTableVal($('#F_TABLE_ID').val()),
		table_cd: trimTableVal($('#F_TABLE_CD').val()),
		table_nm: trimTableVal($('#F_TABLE_NM').val()),
		table_join_nm: trimTableVal($('#F_TABLE_JOIN_NM').val()),
		save_preq_cd: trimTableVal($('#F_SAVE_PREQ_CD').val()),
		save_preq_nm: $('#F_SAVE_PREQ_CD option:selected').text(),
		save_preq: trimTableVal($('#F_SAVE_PREQ').val()),
		exp_preq_cd: trimTableVal($('#F_EXP_PREQ_CD').val()),
		exp_preq_nm: $('#F_EXP_PREQ_CD option:selected').text(),
		exp_preq: trimTableVal($('#F_EXP_PREQ').val()),
		description: trimTableVal($('#F_DESCRIPTION').val()),
		use_yn: $('input[name="F_USE_YN"]:checked').val(),
		crud: $('#CRUD1').val(),
		serverId: trimTableVal($('#F_SERVER_ID').val())
	};

	if (obj.table_id && isNaN(obj.table_id)) {
		alert('[알림] 테이블아이디는 숫자입니다');
		$('#F_TABLE_ID').focus();
		return;
	}
	if (!obj.table_id) {
		alert('[알림] 테이블아이디를 입력하세요');
		$('#F_TABLE_ID').focus();
		return;
	}
	if (!obj.table_cd) {
		alert('[알림] 테이블영문명을 입력하세요');
		$('#F_TABLE_CD').focus();
		return;
	}
	if (!obj.table_nm) {
		alert('[알림] 테이블한글명을 입력하세요');
		$('#F_TABLE_NM').focus();
		return;
	}
	if (!obj.serverId || obj.serverId === '0' || obj.serverId === '*') {
		alert('[알림] 테이블 등록 시 서버를 선택하세요');
		return;
	}

	$.ajax({
		url: archiveCtx + '/SetTable',
		type: 'post',
		data: { param: JSON.stringify(obj) },
		dataType: 'json',
		success: function() {
			var editedId = obj.table_id;
			var wasCreate = obj.crud === 'C';
			ArchiveGrid.load(window._archiveGrid, { keepPage: true });
			if (wasCreate) {
				clearTableDetail();
				clearColumnGrid();
			} else if (window._selectedTableRow && String(window._selectedTableRow.TABLE_ID) === String(editedId)) {
				reloadColumnList();
			}
			alert('정상적으로 처리 되었습니다');
		},
		error: function(xhr) {
			if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
				return;
			}
			if (xhr && xhr.responseJSON && xhr.responseJSON.error === 2) {
				alert('이미 등록되어 있는 아이디입니다');
			} else {
				alert('처리 시 오류가 발생하였습니다');
			}
		}
	});
}

function clearTableSelection() {
	clearTableDetail();
	clearColumnGrid();
}

function clearColumnGrid() {
	if (gridLocal2) {
		ArchiveGrid.clearRows(gridLocal2);
		ArchiveGrid.clearChecked(gridLocal2);
	}
	$('#attrEmptyHint').show();
	syncAttrChkAll(gridLocal2);
}

function loadColumnList(tableRow) {
	if (!tableRow || !tableRow.TABLE_ID) {
		clearColumnGrid();
		syncColumnPanelVisibility();
		return;
	}
	window._selectedTableRow = tableRow;
	syncColumnPanelVisibility();
	$('#attrEmptyHint').hide();

	$.ajax({
		url: archiveCtx + '/GetTableList',
		type: 'POST',
		dataType: 'json',
		data: {
			param: JSON.stringify({
				__serverId: resolveServerId(),
				__use_yn: $('input[name="C_USE_YN"]:checked').val() || 'Y',
				__gb: 'C',
				__table_id: tableRow.TABLE_ID,
				__table_nm: '*'
			})
		},
		success: function(data) {
			ArchiveGrid.loadRows(gridLocal2, data.rows || []);
			syncAttrChkAll(gridLocal2);
		},
		error: function(xhr) {
			if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
				return;
			}
			alert('컬럼 목록을 불러오지 못했습니다.');
		}
	});
}

function onTableRowClick(row) {
	loadTableDetail(row);
	loadColumnList(row);
}

function reloadColumnList() {
	loadColumnList(window._selectedTableRow);
}

function openTableAttrPop(row) {
	var tableRow = window._selectedTableRow;
	if (!tableRow || !tableRow.TABLE_ID) {
		alert('[알림] 테이블을 먼저 선택하세요');
		return;
	}
	window._attrPopRow = row || null;
	window._attrPopTableId = tableRow.TABLE_ID;
	openArchivePopup('tableAttrPop', {
		width: 640,
		height: 580,
		onReady: function() {
			if (typeof ensureTableAttrPopFilled === 'function') {
				ensureTableAttrPopFilled();
			}
		}
	});
}

function findTableRowById(tableId) {
	var grid = window._tableInfoGrid;
	if (!grid || !grid.allRows) return null;
	var found = null;
	grid.allRows.forEach(function(r) {
		if (String(r.TABLE_ID) === String(tableId)) found = r;
	});
	return found;
}

function syncTableChkAll(grid) {
	var $chkAll = $('#chkAllTable');
	if (!$chkAll.length || !grid) return;
	var $rows = $(grid.bodySelector).find('.archive-grid-chk');
	if (!$rows.length) {
		$chkAll.prop('checked', false).prop('indeterminate', false);
		return;
	}
	var checkedCount = $rows.filter(':checked').length;
	$chkAll.prop('checked', checkedCount === $rows.length);
	$chkAll.prop('indeterminate', checkedCount > 0 && checkedCount < $rows.length);
}

function syncAttrChkAll(grid) {
	var $chkAll = $('#chkAllAttr');
	if (!$chkAll.length || !grid) return;
	var $rows = $(grid.bodySelector).find('.archive-grid-chk');
	if (!$rows.length) {
		$chkAll.prop('checked', false).prop('indeterminate', false);
		return;
	}
	var checkedCount = $rows.filter(':checked').length;
	$chkAll.prop('checked', checkedCount === $rows.length);
	$chkAll.prop('indeterminate', checkedCount > 0 && checkedCount < $rows.length);
}

function deleteSelectedTables() {
	var grid = window._tableInfoGrid;
	if (!grid) return;

	var ids = ArchiveGrid.getCheckedKeys(grid);
	if (!ids.length) {
		alert('[알림] 삭제할 테이블을 선택하세요');
		return;
	}
	if (!confirm('[알림] 해당 테이블 및 관련 컬럼을 삭제합니다\n진행하시겠습니까?')) {
		return;
	}

	var serverId = $('#C_SERVER_ID').val();
	var failed = 0;
	var done = 0;
	var deletedSelected = false;

	function finish() {
		if (done < ids.length) return;
		ArchiveGrid.clearChecked(grid);
		if (deletedSelected) clearTableSelection();
		ArchiveGrid.load(grid, { keepPage: true });
		if (failed) {
			alert('일부 테이블 삭제에 실패했습니다. (' + failed + '건)');
		} else {
			alert('정상적으로 처리 되었습니다');
		}
	}

	ids.forEach(function(id) {
		if (window._selectedTableRow && String(window._selectedTableRow.TABLE_ID) === String(id)) {
			deletedSelected = true;
		}
		var row = findTableRowById(id) || { TABLE_ID: id };
		$.ajax({
			url: archiveCtx + '/SetTable',
			type: 'post',
			data: {
				param: JSON.stringify({
					table_id: row.TABLE_ID,
					table_cd: row.TABLE_CD || '',
					table_nm: row.TABLE_NM || '',
					crud: 'D',
					serverId: serverId
				})
			},
			dataType: 'json',
			success: function() {},
			error: function(xhr) {
				if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
					return;
				}
				failed++;
			},
			complete: function() {
				done++;
				finish();
			}
		});
	});
}

function deleteSelectedAttrs() {
	var tableRow = window._selectedTableRow;
	if (!tableRow || !tableRow.TABLE_ID) {
		alert('[알림] 테이블을 먼저 선택하세요');
		return;
	}
	if (!gridLocal2) return;

	var ids = ArchiveGrid.getCheckedKeys(gridLocal2);
	if (!ids.length) {
		alert('[알림] 삭제할 컬럼을 선택하세요');
		return;
	}
	if (!confirm('선택한 ' + ids.length + '건의 컬럼을 삭제 하시겠습니까?')) {
		return;
	}

	var failed = 0;
	var done = 0;

	function finish() {
		if (done < ids.length) return;
		ArchiveGrid.clearChecked(gridLocal2);
		reloadColumnList();
		if (failed) {
			alert('일부 컬럼 삭제에 실패했습니다. (' + failed + '건)');
		} else {
			alert('정상적으로 처리 되었습니다');
		}
	}

	ids.forEach(function(attrCd) {
		$.ajax({
			url: archiveCtx + '/SetTable',
			type: 'post',
			data: {
				param: JSON.stringify({
					table_id: tableRow.TABLE_ID,
					attr_cd: attrCd,
					crud: 'AD'
				})
			},
			dataType: 'json',
			success: function() {},
			error: function(xhr) {
				if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
					return;
				}
				failed++;
			},
			complete: function() {
				done++;
				finish();
			}
		});
	});
}

var ATTR_COLS = [
	{name:'TABLE_ID', hidden:true},
	{name:'_CHK', checkbox:true, keyName:'ATTR_CD', align:'center', className:'col-chk'},
	{name:'ATTR_CD', align:'center', className:'col-cd', label:'코드'},
	{name:'ATTR_NM', align:'center', className:'col-nm tal_cut', ellipsis:true, label:'컬럼명'},
	{name:'ATTR_TYPE_CD', hidden:true},
	{name:'ATTR_TYPE_NM', align:'center', className:'col-type', label:'타입'},
	{name:'ATTR_SIZE', align:'center', className:'col-size', label:'길이'},
	{name:'ATTR_ORDER', align:'center', className:'col-order', label:'순서'},
	{name:'WHERE_INDEX', align:'center', className:'col-where', label:'조건'},
	{name:'OUTPUT_INDEX', align:'center', className:'col-output', label:'출력'},
	{name:'ATTR_NULL_YN', align:'center', className:'col-null', label:'Null'},
	{name:'DATE_TYPE_YN', align:'center', className:'col-date', label:'날짜'},
	{name:'TIME_TYPE_YN', align:'center', className:'col-time', label:'시간'},
	{name:'ATTR_USE_YN', hidden:true},
	{name:'DECIMAL_SIZE', hidden:true}
];

var ATTR_EXCEL_HEADER_MAP = {
	'코드': 'ATTR_CD',
	'컬럼명': 'ATTR_NM',
	'타입': 'ATTR_TYPE_NM',
	'길이': 'ATTR_SIZE',
	'순서': 'ATTR_ORDER',
	'조건': 'WHERE_INDEX',
	'출력': 'OUTPUT_INDEX',
	'Null': 'ATTR_NULL_YN',
	'날짜': 'DATE_TYPE_YN',
	'시간': 'TIME_TYPE_YN'
};

function normalizeAttrExcelRow(value) {
	var row = {};
	Object.keys(value || {}).forEach(function(key) {
		var field = ATTR_EXCEL_HEADER_MAP[key] || key;
		row[field] = value[key];
	});
	return row;
}

gridLocal2 = ArchiveGrid.createLocal({
	bodySelector: '#gridBody2',
	pagingSelector: '.wr_page2',
	countSelector: '#totalCnt2',
	pageSize: 10,
	checkboxKeyField: 'ATTR_CD',
	columns: ATTR_COLS,
	onAfterRender: function(inst) { syncAttrChkAll(inst); }
});
ArchiveGrid.register('jqGrid2', gridLocal2);
ArchiveGrid.wireLocal(gridLocal2);

$(function() {
	var grid = ArchiveGrid.create({
		url: 'GetTableList',
		pageSize: 10,
		checkboxKeyField: 'TABLE_ID',
		columns: [
			{name:'_CHK', checkbox:true, keyName:'TABLE_ID', align:'center', className:'col-chk'},
			{name:'SERVER_ID', hidden:true},
			{name:'TABLE_ID', align:'center', className:'col-id'},
			{name:'TABLE_CD', align:'center', className:'col-cd'},
			{name:'TABLE_NM', align:'center', className:'col-nm tal_cut', ellipsis:true},
			{name:'TABLE_JOIN_NM', hidden:true},
			{name:'SAVE_PREQ_CD', hidden:true},
			{name:'SAVE_PREQ_NM', hidden:true},
			{name:'SAVE_PREQ', hidden:true},
			{name:'EXP_PREQ_CD', hidden:true},
			{name:'EXP_PREQ_NM', hidden:true},
			{name:'EXP_PREQ', hidden:true},
			{name:'DESCRIPTION', hidden:true},
			{name:'USE_YN', align:'center', className:'col-use'}
		],
		getPostData: function() { return buildSearchParam(); },
		onRowClick: function(row) { onTableRowClick(row); },
		onAfterRender: function(inst) { syncTableChkAll(inst); }
	});
	ArchiveGrid.wirePage(grid);
	window._tableInfoGrid = grid;
});

$(document).ready(function() {
	loadServerCombo(function() {
		loadTableGrid();
	});
	$('input[name="C_USE_YN"]').val(['Y']);

	gridLocal2.onRowDblClick = function(row) {
		openTableAttrPop(row);
	};

	$('#btnQuery').off('click.archive').on('click', function(e) {
		e.preventDefault();
		loadTableGrid({ clearSelection: true });
	});

	$('#C_SERVER_ID').on('change', function() {
		loadTableGrid({ clearSelection: true });
	});

	$('#C_TABLE_NM').on('keydown', function(e) {
		if (e.key === 'Enter' || e.keyCode === 13) {
			e.preventDefault();
			loadTableGrid({ clearSelection: true });
		}
	});

	$('#btnAdd').on('click', function(e) {
		e.preventDefault();
		resetTableDetailForAdd();
	});

	$('#btnTableSave').on('click', function(e) {
		e.preventDefault();
		saveTableDetail();
	});

	$('#btnDelete').on('click', function(e) {
		e.preventDefault();
		deleteSelectedTables();
	});

	$('#btnAttrAdd').on('click', function(e) {
		e.preventDefault();
		openTableAttrPop();
	});

	$('#btnAttrDelete').on('click', function(e) {
		e.preventDefault();
		deleteSelectedAttrs();
	});

	$('#chkAllTable').on('change', function() {
		var grid = window._tableInfoGrid;
		if (!grid) return;
		ArchiveGrid.togglePageChecked(grid, this.checked);
		syncTableChkAll(grid);
	});

	$('#chkAllAttr').on('change', function() {
		if (!gridLocal2) return;
		ArchiveGrid.togglePageChecked(gridLocal2, this.checked);
		syncAttrChkAll(gridLocal2);
	});

	$(document).on('change', '#gridBody .archive-grid-chk', function(e) {
		e.stopPropagation();
		var grid = window._tableInfoGrid;
		if (!grid) return;
		ArchiveGrid.setRowChecked(grid, $(this).data('row-key'), this.checked);
		syncTableChkAll(grid);
	});

	$(document).on('change', '#gridBody2 .archive-grid-chk', function(e) {
		e.stopPropagation();
		if (!gridLocal2) return;
		ArchiveGrid.setRowChecked(gridLocal2, $(this).data('row-key'), this.checked);
		syncAttrChkAll(gridLocal2);
	});

	$('#btnExpExcel').on('click', function(e) {
		e.preventDefault();
		var tableRow = window._selectedTableRow;
		var rows = ArchiveGrid.getRows(gridLocal2);
		if (!rows.length) {
			alert('다운로드할 데이터가 없습니다.');
			return;
		}
		if (!tableRow || !tableRow.TABLE_ID) {
			alert('테이블이 선택되지 않았습니다');
			return;
		}
		var saveFileName = $('select#C_SERVER_ID option:selected').text() + '.' + (tableRow.TABLE_CD || 'table');
		ArchiveGrid.exportToExcel(gridLocal2, {
			includeLabels: true,
			includeGroupHeader: true,
			includeFooter: true,
			fileName: saveFileName + '.xlsx',
			maxlength: 40
		});
	});

	$('#btnImpExcel').on('click', function(e) {
		e.preventDefault();
		if (!window._selectedTableRow || !window._selectedTableRow.TABLE_ID) {
			alert('테이블을 먼저 선택하세요');
			return;
		}
		$('#btnImpExcelFile').val('').trigger('click');
	});

	$('#btnAttrRenum').on('click', function(e) {
		e.preventDefault();
		var tableRow = window._selectedTableRow;
		if (!tableRow || !tableRow.TABLE_ID) {
			alert('[알림] 테이블을 먼저 선택하세요');
			return;
		}
		var rows = ArchiveGrid.getRows(gridLocal2);
		if (!rows.length) {
			alert('재정렬할 컬럼이 없습니다.');
			return;
		}
		rows.forEach(function(rowData, index) {
			$.ajax({
				url: archiveCtx + '/SetTable',
				type: 'post',
				dataType: 'json',
				data: {
					param: JSON.stringify({
						table_id: tableRow.TABLE_ID,
						attr_cd: rowData.ATTR_CD,
						attr_seq: String(index + 1),
						crud: 'RN'
					})
				}
			});
		});
		alert('정상적으로 처리 되었습니다');
	});

});

var reader = new FileReader();
reader.onload = function(e) {
	ArchiveGrid.clearRows(gridLocal2);

	var data = e.target.result;
	var workbook = XLSX.read(data, { type: 'array', cellDates: true, dateNF: 'YYYY-MM-DD' });
	var sheet = workbook.Sheets[workbook.SheetNames[0]];
	if (!sheet) return;

	var excelFileData = XLSX.utils.sheet_to_json(sheet, { raw: false, defval: '' });
	var jsonGetData = JSON.parse(JSON.stringify(excelFileData));

	jsonGetData.forEach(function(value) {
		value = normalizeAttrExcelRow(value);
		var newValue = JSON.stringify(value.ATTR_CD || '').replaceAll('"', '');
		if (newValue.length > 0) {
			var idsLen = ArchiveGrid.getRows(gridLocal2).length + 1;
			ArchiveGrid.addRowData(gridLocal2, {});
			var rowData = ArchiveGrid.getRowData(gridLocal2, idsLen) || {};
			rowData.ATTR_CD = JSON.stringify(value.ATTR_CD).replaceAll('"', '');
			rowData.ATTR_NM = JSON.stringify(value.ATTR_NM).replaceAll('"', '');
			rowData.ATTR_SIZE = JSON.stringify(value.ATTR_SIZE).replaceAll('"', '');
			rowData.DECIMAL_SIZE = JSON.stringify(value.DECIMAL_SIZE).replaceAll('"', '');
			rowData.ATTR_TYPE_NM = JSON.stringify(value.ATTR_TYPE_NM).replaceAll('"', '');
			rowData.ATTR_NULL_YN = JSON.stringify(value.ATTR_NULL_YN || 'N').replaceAll('"', '');
			rowData.DATE_TYPE_YN = JSON.stringify(value.DATE_TYPE_YN || 'N').replaceAll('"', '');
			rowData.TIME_TYPE_YN = JSON.stringify(value.TIME_TYPE_YN || 'N').replaceAll('"', '');
			rowData.ATTR_ORDER = JSON.stringify(value.ATTR_ORDER).replaceAll('"', '');
			rowData.WHERE_INDEX = JSON.stringify(value.WHERE_INDEX).replaceAll('"', '');
			rowData.OUTPUT_INDEX = JSON.stringify(value.OUTPUT_INDEX).replaceAll('"', '');
			ArchiveGrid.setRowData(gridLocal2, idsLen, rowData);
		}
	});
	syncAttrChkAll(gridLocal2);
};

$('#btnImpExcelFile').on('change', function() {
	if (!this.files || !this.files[0]) return;
	reader.readAsArrayBuffer(this.files[0]);
});
