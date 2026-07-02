var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function trimAttrVal(v) {
	if (v == null) return '';
	return String(v).trim();
}

function sanitizePopNumericVal(v) {
	return String(v == null ? '' : v).replace(/\D/g, '');
}

var TABLE_ATTR_NUM_IDS = ['#F_ATTR_SIZE_POP', '#F_ATTR_ORDER_POP', '#F_WHERE_INDEX_POP', '#F_OUTPUT_INDEX_POP'];
var TABLE_ATTR_NUM_SELECTOR = TABLE_ATTR_NUM_IDS.map(function(id) {
	return '#w2ui-popup #SetAttrFormPop ' + id;
}).join(',');

function setPopNumericField($pop, selector, value) {
	$pop.find(selector).val(sanitizePopNumericVal(trimAttrVal(value)));
}

function ensureTableAttrPopFilled() {
	var $pop = $('#w2ui-popup');
	if (!$pop.find('#SetAttrFormPop').length) return;

	var row = window._attrPopRow || null;
	var tableId = window._attrPopTableId || '';

	$pop.find('#F_TABLE_ID_POP').val(tableId);

	if (row) {
		$pop.find('.tit_pop').text('컬럼 상세');
		$pop.find('#CRUD_POP').val('AU');
		$pop.find('#F_ATTR_CD_POP').val(trimAttrVal(row.ATTR_CD)).prop('readonly', true);
		$pop.find('#F_ATTR_NM_POP').val(trimAttrVal(row.ATTR_NM));
		$pop.find('#F_ATTR_TYPE_CD_POP').val(trimAttrVal(row.ATTR_TYPE_CD));
		setPopNumericField($pop, '#F_ATTR_SIZE_POP', row.ATTR_SIZE);
		setPopNumericField($pop, '#F_ATTR_ORDER_POP', row.ATTR_ORDER);
		setPopNumericField($pop, '#F_WHERE_INDEX_POP', row.WHERE_INDEX);
		setPopNumericField($pop, '#F_OUTPUT_INDEX_POP', row.OUTPUT_INDEX);
		$pop.find('input[name="F_ATTR_NULL_YN_POP"]').val([trimAttrVal(row.ATTR_NULL_YN) || 'N']);
		$pop.find('input[name="F_DATE_TYPE_YN_POP"]').val([trimAttrVal(row.DATE_TYPE_YN) || 'N']);
		$pop.find('input[name="F_TIME_TYPE_YN_POP"]').val([trimAttrVal(row.TIME_TYPE_YN) || 'N']);
	} else {
		$pop.find('.tit_pop').text('컬럼 등록');
		$pop.find('#CRUD_POP').val('AC');
		$pop.find('#F_ATTR_CD_POP').val('').prop('readonly', false);
		$pop.find('#F_ATTR_NM_POP').val('');
		$pop.find('#F_ATTR_TYPE_CD_POP option:eq(0)').prop('selected', true);
		$pop.find('#F_ATTR_SIZE_POP').val('');
		$pop.find('#F_ATTR_ORDER_POP').val('');
		$pop.find('#F_WHERE_INDEX_POP').val('');
		$pop.find('#F_OUTPUT_INDEX_POP').val('');
		$pop.find('input[name="F_ATTR_NULL_YN_POP"]').val(['N']);
		$pop.find('input[name="F_DATE_TYPE_YN_POP"]').val(['N']);
		$pop.find('input[name="F_TIME_TYPE_YN_POP"]').val(['N']);
	}
	$pop.find('#F_ATTR_CD_POP').focus();
}

function saveTableAttrPop() {
	var $pop = $('#w2ui-popup');
	if (!$pop.find('#SetAttrFormPop').length) return;

	var obj = {
		table_id: trimAttrVal($pop.find('#F_TABLE_ID_POP').val()),
		attr_cd: trimAttrVal($pop.find('#F_ATTR_CD_POP').val()),
		attr_nm: trimAttrVal($pop.find('#F_ATTR_NM_POP').val()),
		attr_size: trimAttrVal($pop.find('#F_ATTR_SIZE_POP').val()),
		attr_type_cd: trimAttrVal($pop.find('#F_ATTR_TYPE_CD_POP').val()),
		attr_type_nm: $pop.find('#F_ATTR_TYPE_CD_POP option:selected').text(),
		attr_null_yn: $pop.find('input[name="F_ATTR_NULL_YN_POP"]:checked').val(),
		attr_order: trimAttrVal($pop.find('#F_ATTR_ORDER_POP').val()),
		where_index: trimAttrVal($pop.find('#F_WHERE_INDEX_POP').val()),
		output_index: trimAttrVal($pop.find('#F_OUTPUT_INDEX_POP').val()),
		date_type_yn: $pop.find('input[name="F_DATE_TYPE_YN_POP"]:checked').val(),
		time_type_yn: $pop.find('input[name="F_TIME_TYPE_YN_POP"]:checked').val(),
		crud: $pop.find('#CRUD_POP').val()
	};

	if (!obj.table_id) {
		alert('[알림] 테이블을 먼저 선택하세요');
		return;
	}
	if (!obj.attr_nm) {
		alert('[알림] 컬럼명을 입력하세요');
		$pop.find('#F_ATTR_NM_POP').focus();
		return;
	}
	if (obj.attr_size && Number(obj.attr_size) <= 0) {
		alert('[알림] 길이가 너무 작습니다.');
		$pop.find('#F_ATTR_SIZE_POP').focus();
		return;
	}
	if (obj.attr_size && Number(obj.attr_size) > 999) {
		alert('[알림] 길이가 너무 큽니다');
		$pop.find('#F_ATTR_SIZE_POP').focus();
		return;
	}

	$.ajax({
		url: archiveCtx + '/SetTable',
		type: 'post',
		data: { param: JSON.stringify(obj) },
		dataType: 'json',
		success: function() {
			closePop();
			if (typeof reloadColumnList === 'function') reloadColumnList();
			alert('정상적으로 처리 되었습니다');
		},
		error: function(xhr) {
			if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
				return;
			}
			if (xhr && xhr.responseJSON && xhr.responseJSON.error === 2) {
				alert('이미 등록되어 있는 데이터입니다');
			} else {
				alert('처리 시 오류가 발생하였습니다');
			}
		}
	});
}

(function bindTableAttrPop() {
	if (window._tableAttrPopBound) return;
	window._tableAttrPopBound = true;

	$(document).on('input', TABLE_ATTR_NUM_SELECTOR, function() {
		var $el = $(this);
		var v = sanitizePopNumericVal($el.val());
		if ($el.val() !== v) $el.val(v);
	});

	$(document).on('paste', TABLE_ATTR_NUM_SELECTOR, function() {
		var $el = $(this);
		setTimeout(function() {
			$el.val(sanitizePopNumericVal($el.val()));
		}, 0);
	});

	$(document).on('click', '#w2ui-popup #btnSavePop', function(e) {
		if (!$('#w2ui-popup #SetAttrFormPop').length) return;
		e.preventDefault();
		saveTableAttrPop();
	});

	$(document).on('click', '#w2ui-popup #pop_close', function(e) {
		if (!$('#w2ui-popup #SetAttrFormPop').length) return;
		e.preventDefault();
		closePop();
	});
})();
