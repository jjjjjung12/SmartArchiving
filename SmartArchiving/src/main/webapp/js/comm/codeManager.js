var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function trimCodeVal(v) {
	if (v == null) return '';
	return String(v).trim();
}

function buildSearchParam() {
	return {
		__crud: 'R',
		__group_nm: trimCodeVal($('#C_GROUP_NM').val()),
		__use_yn: $('input[name="C_USE_YN"]:checked').val() || 'Y'
	};
}

function buildDetailSearchParam() {
	return {
		__crud: 'RD',
		__group_cd: window._selectedGroupCd || '',
		__use_yn: 'A'
	};
}

function showGroupDetailForm(show) {
	var $panel = $('.code-group-detail-panel');
	if (show) {
		$panel.addClass('table-info-detail-panel--active');
	} else {
		$panel.removeClass('table-info-detail-panel--active');
	}
}

function showCodeDetailForm(show) {
	var $panel = $('.code-detail-detail-panel');
	if (show) {
		$panel.addClass('table-info-detail-panel--active');
	} else {
		$panel.removeClass('table-info-detail-panel--active');
	}
}

function syncDetailPanelVisibility() {
	var show = !!(window._selectedGroupCd);
	$('#codeDetailPanel').toggleClass('table-info-section--hidden', !show);
}

function clearGroupDetail() {
	window._selectedGroupCd = '';
	$('#CRUD1').val('C');
	$('#F_GROUP_CD').val('').prop('readonly', false);
	$('#F_GROUP_NM').val('');
	$('input[name="F_USE_YN"]').val(['Y']);
	showGroupDetailForm(false);
	clearDetailForm();
	clearDetailGrid();
	syncDetailPanelVisibility();
}

function clearDetailForm() {
	$('#CRUD2').val('CD');
	$('#F_DETAIL_CD').val('').prop('readonly', false);
	$('#F_DETAIL_NM').val('');
	$('input[name="F_USE_SUB_YN"]').val(['Y']);
	showCodeDetailForm(false);
}

function clearDetailGrid() {
	if (window._detailGrid) {
		ArchiveGrid.clearRows(window._detailGrid);
		ArchiveGrid.clearChecked(window._detailGrid);
	}
	syncDetailChkAll(window._detailGrid);
}

function loadGroupDetail(row) {
	if (!row || !row.GROUP_CD) {
		clearGroupDetail();
		return;
	}
	window._selectedGroupCd = row.GROUP_CD;
	showGroupDetailForm(true);
	$('#CRUD1').val('U');
	$('#F_GROUP_CD').val(trimCodeVal(row.GROUP_CD)).prop('readonly', true);
	$('#F_GROUP_NM').val(trimCodeVal(row.GROUP_NM));
	$('input[name="F_USE_YN"]').val([trimCodeVal(row.USE_YN) || 'Y']);
	clearDetailForm();
	syncDetailPanelVisibility();
	loadDetailGrid();
}

function resetGroupDetailForAdd() {
	clearDetailGrid();
	window._selectedGroupCd = '';
	showGroupDetailForm(true);
	$('#CRUD1').val('C');
	$('#F_GROUP_CD').val('').prop('readonly', false);
	$('#F_GROUP_NM').val('');
	$('input[name="F_USE_YN"]').val(['Y']);
	clearDetailForm();
	syncDetailPanelVisibility();
	$('#F_GROUP_CD').focus();
}

function loadDetailGrid() {
	if (!window._detailGrid || !window._selectedGroupCd) {
		clearDetailGrid();
		return;
	}
	ArchiveGrid.load(window._detailGrid);
}

function loadDetailForm(row) {
	if (!row) return;
	showCodeDetailForm(true);
	$('#CRUD2').val('UD');
	$('#F_DETAIL_CD').val(trimCodeVal(row.DETAIL_CD)).prop('readonly', true);
	$('#F_DETAIL_NM').val(trimCodeVal(row.DETAIL_NM));
	$('input[name="F_USE_SUB_YN"]').val([trimCodeVal(row.USE_YN) || 'Y']);
}

function resetDetailFormForAdd() {
	if (!window._selectedGroupCd) {
		alert('[알림] 그룹코드를 먼저 선택하세요');
		return;
	}
	showCodeDetailForm(true);
	$('#CRUD2').val('CD');
	$('#F_DETAIL_CD').val('').prop('readonly', false);
	$('#F_DETAIL_NM').val('');
	$('input[name="F_USE_SUB_YN"]').val(['Y']);
	$('#F_DETAIL_CD').focus();
}

function loadGroupGrid(opts) {
	opts = opts || {};
	if (opts.clearSelection) clearGroupDetail();
	if (window._groupGrid) ArchiveGrid.load(window._groupGrid);
}

function syncGroupChkAll(grid) {
	var $chkAll = $('#chkAllGroup');
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

function syncDetailChkAll(grid) {
	var $chkAll = $('#chkAllDetail');
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

function saveGroupDetail() {
	var obj = {
		__group_cd: trimCodeVal($('#F_GROUP_CD').val()),
		__group_nm: trimCodeVal($('#F_GROUP_NM').val()),
		__use_yn: $('input[name="F_USE_YN"]:checked').val(),
		__crud: $('#CRUD1').val()
	};

	if (!obj.__group_cd) {
		alert('[알림] 그룹코드를 입력하세요');
		$('#F_GROUP_CD').focus();
		return;
	}
	if (!obj.__group_nm) {
		alert('[알림] 그룹코드명을 입력하세요');
		$('#F_GROUP_NM').focus();
		return;
	}

	$.ajax({
		url: archiveCtx + '/SetCodeManager',
		type: 'post',
		dataType: 'json',
		data: { param: JSON.stringify(obj) },
		success: function(json) {
			if (json.result !== 'OK') {
				alert('저장이 실패했습니다.');
				return;
			}
			var editedCd = obj.__group_cd;
			var wasCreate = obj.__crud === 'C';
			ArchiveGrid.load(window._groupGrid, { keepPage: true });
			if (wasCreate) {
				resetGroupDetailForAdd();
			} else if (String(window._selectedGroupCd) === String(editedCd)) {
				loadDetailGrid();
			}
			alert('정상적으로 처리 되었습니다');
		},
		error: function(xhr) {
			if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
				return;
			}
			alert('처리 시 오류가 발생하였습니다');
		}
	});
}

function deleteSelectedGroups() {
	var grid = window._groupGrid;
	if (!grid) return;

	var ids = ArchiveGrid.getCheckedKeys(grid);
	if (!ids.length) {
		alert('[알림] 삭제할 그룹을 선택하세요');
		return;
	}
	if (!confirm('삭제 후 복구할 수 없습니다.\n선택한 ' + ids.length + '건의 그룹을 삭제하시겠습니까?')) {
		return;
	}

	var failed = 0;
	var done = 0;
	var deletedSelected = false;

	function finish() {
		if (done < ids.length) return;
		ArchiveGrid.clearChecked(grid);
		if (deletedSelected) clearGroupDetail();
		ArchiveGrid.load(grid, { keepPage: true });
		if (failed) {
			alert('일부 그룹 삭제에 실패했습니다. (' + failed + '건)');
		} else {
			alert('정상적으로 처리 되었습니다');
		}
	}

	ids.forEach(function(groupCd) {
		if (String(window._selectedGroupCd) === String(groupCd)) {
			deletedSelected = true;
		}
		$.ajax({
			url: archiveCtx + '/SetCodeManager',
			type: 'post',
			dataType: 'json',
			data: {
				param: JSON.stringify({
					__group_cd: groupCd,
					__crud: 'D'
				})
			},
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

function saveDetailCode() {
	if (!window._selectedGroupCd) {
		alert('[알림] 그룹코드를 먼저 선택하세요');
		return;
	}

	var obj = {
		__group_cd: window._selectedGroupCd,
		__detail_cd: trimCodeVal($('#F_DETAIL_CD').val()),
		__detail_nm: trimCodeVal($('#F_DETAIL_NM').val()),
		__use_yn: $('input[name="F_USE_SUB_YN"]:checked').val(),
		__crud: $('#CRUD2').val()
	};

	if (!obj.__detail_cd) {
		alert('[알림] 코드를 입력하세요');
		$('#F_DETAIL_CD').focus();
		return;
	}
	if (!obj.__detail_nm) {
		alert('[알림] 코드명을 입력하세요');
		$('#F_DETAIL_NM').focus();
		return;
	}

	$.ajax({
		url: archiveCtx + '/SetCodeManager',
		type: 'post',
		dataType: 'json',
		data: { param: JSON.stringify(obj) },
		success: function(json) {
			if (json.result !== 'OK') {
				alert('처리 중 오류가 발생했습니다.\n' + (json.msg || ''));
				return;
			}
			loadDetailGrid();
			if (obj.__crud === 'CD') {
				resetDetailFormForAdd();
			} else {
				loadDetailForm({
					DETAIL_CD: obj.__detail_cd,
					DETAIL_NM: obj.__detail_nm,
					USE_YN: obj.__use_yn
				});
			}
			alert('정상적으로 처리 되었습니다');
		},
		error: function(xhr) {
			if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
				return;
			}
			alert('처리 시 오류가 발생하였습니다');
		}
	});
}

function deleteSelectedDetails() {
	if (!window._selectedGroupCd) {
		alert('[알림] 그룹코드를 먼저 선택하세요');
		return;
	}
	var grid = window._detailGrid;
	if (!grid) return;

	var ids = ArchiveGrid.getCheckedKeys(grid);
	if (!ids.length) {
		alert('[알림] 삭제할 상세코드를 선택하세요');
		return;
	}
	if (!confirm('삭제 후 복구할 수 없습니다.\n선택한 ' + ids.length + '건을 삭제하시겠습니까?')) {
		return;
	}

	var failed = 0;
	var done = 0;
	var currentCd = trimCodeVal($('#F_DETAIL_CD').val());
	var clearedForm = false;

	function finish() {
		if (done < ids.length) return;
		ArchiveGrid.clearChecked(grid);
		loadDetailGrid();
		if (!clearedForm && ids.indexOf(currentCd) >= 0) {
			clearDetailForm();
			clearedForm = true;
		} else if (clearedForm) {
			showCodeDetailForm(false);
		}
		if (failed) {
			alert('일부 상세코드 삭제에 실패했습니다. (' + failed + '건)');
		} else {
			alert('정상적으로 처리 되었습니다');
		}
	}

	ids.forEach(function(detailCd) {
		if (String(detailCd) === String(currentCd)) {
			clearedForm = true;
		}
		$.ajax({
			url: archiveCtx + '/SetCodeManager',
			type: 'post',
			dataType: 'json',
			data: {
				param: JSON.stringify({
					__group_cd: window._selectedGroupCd,
					__detail_cd: detailCd,
					__crud: 'DD'
				})
			},
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

$(function() {
	var groupGrid = ArchiveGrid.create({
		url: 'GetCodeManager',
		pageSize: 10,
		checkboxKeyField: 'GROUP_CD',
		columns: [
			{ name: '_CHK', checkbox: true, keyName: 'GROUP_CD', align: 'center', className: 'col-chk' },
			{ name: 'GROUP_CD', align: 'center', className: 'col-cd', label: '코드' },
			{ name: 'GROUP_NM', align: 'left', className: 'col-nm tal_cut', ellipsis: true, label: '이름' },
			{ name: 'USE_YN', align: 'center', className: 'col-use', label: '사용' }
		],
		getPostData: buildSearchParam,
		onRowClick: function(row) { loadGroupDetail(row); },
		onAfterRender: function(inst) { syncGroupChkAll(inst); }
	});

	var detailGrid = ArchiveGrid.create({
		url: 'GetCodeManager',
		bodySelector: '#gridBody2',
		pagingSelector: '.wr_page2',
		countSelector: '#totalCnt2',
		pageSize: 10,
		checkboxKeyField: 'DETAIL_CD',
		columns: [
			{ name: '_CHK', checkbox: true, keyName: 'DETAIL_CD', align: 'center', className: 'col-chk' },
			{ name: 'DETAIL_CD', align: 'center', className: 'col-cd', label: '코드' },
			{ name: 'DETAIL_NM', align: 'left', className: 'col-nm tal_cut', ellipsis: true, label: '코드명' },
			{ name: 'USE_YN', align: 'center', className: 'col-use', label: '사용' }
		],
		getPostData: buildDetailSearchParam,
		onRowClick: function(row) { loadDetailForm(row); },
		onAfterRender: function(inst) { syncDetailChkAll(inst); }
	});

	window._groupGrid = groupGrid;
	window._detailGrid = detailGrid;
	window._archiveGrid = groupGrid;

	ArchiveGrid.wirePage(groupGrid, { autoLoad: true });
});

$(document).ready(function() {
	$('input[name="C_USE_YN"]').val(['Y']);

	$('#btnQuery').off('click.archive').on('click', function(e) {
		e.preventDefault();
		loadGroupGrid({ clearSelection: true });
	});

	$('#C_GROUP_NM').on('keydown', function(e) {
		if (e.key === 'Enter' || e.keyCode === 13) {
			e.preventDefault();
			loadGroupGrid({ clearSelection: true });
		}
	});

	$('#btnAdd').on('click', function(e) {
		e.preventDefault();
		resetGroupDetailForAdd();
	});

	$('#btnGroupSave').on('click', function(e) {
		e.preventDefault();
		saveGroupDetail();
	});

	$('#btnDelete').on('click', function(e) {
		e.preventDefault();
		deleteSelectedGroups();
	});

	$('#btnAttrAdd').on('click', function(e) {
		e.preventDefault();
		resetDetailFormForAdd();
	});

	$('#btnDetailSave').on('click', function(e) {
		e.preventDefault();
		saveDetailCode();
	});

	$('#btnAttrDelete').on('click', function(e) {
		e.preventDefault();
		deleteSelectedDetails();
	});

	$('#chkAllGroup').on('change', function() {
		var grid = window._groupGrid;
		if (!grid) return;
		ArchiveGrid.togglePageChecked(grid, this.checked);
		syncGroupChkAll(grid);
	});

	$('#chkAllDetail').on('change', function() {
		var grid = window._detailGrid;
		if (!grid) return;
		ArchiveGrid.togglePageChecked(grid, this.checked);
		syncDetailChkAll(grid);
	});

	$(document).on('change', '#gridBody .archive-grid-chk', function(e) {
		e.stopPropagation();
		var grid = window._groupGrid;
		if (!grid) return;
		ArchiveGrid.setRowChecked(grid, $(this).data('row-key'), this.checked);
		syncGroupChkAll(grid);
	});

	$(document).on('change', '#gridBody2 .archive-grid-chk', function(e) {
		e.stopPropagation();
		var grid = window._detailGrid;
		if (!grid) return;
		ArchiveGrid.setRowChecked(grid, $(this).data('row-key'), this.checked);
		syncDetailChkAll(grid);
	});
});
