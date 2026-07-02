var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function noticeFieldVal(v) {
	return v && v !== 'null' ? v : '';
}

function fillNoticeDetailPop(row) {
	var $pop = $('#w2ui-popup');
	if (!$pop.find('#F_SUBJECT').length || !row) return;

	$pop.find('#F_SUBJECT').val(noticeFieldVal(row.SUBJECT));
	$pop.find('#F_SERIAL_NUMBER').val(noticeFieldVal(row.SERIAL_NUMBER));
	$pop.find('#F_REG_DATE').val(noticeFieldVal(row.REG_DATE));
	$pop.find('#F_USER_NM').val(noticeFieldVal(row.USER_NM));
	$pop.find('#F_REG_USER_CD').val(noticeFieldVal(row.REG_USER_CD));
	$pop.find('#F_SUBJECT_DETAIL').val(noticeFieldVal(row.SUBJECT_DETAIL));
	$pop.find('#F_FILE_NM').val(noticeFieldVal(row.FILE_NM));
	$pop.find('#F_FILE_URL').val(noticeFieldVal(row.FILE_URL));

	var hasFile = noticeFieldVal(row.FILE_NM) && noticeFieldVal(row.FILE_URL);
	$pop.find('#btnDownPop').toggle(!!hasFile);

	var canDelete = window._noticeDetailCanDelete === true;
	$pop.find('#btnDeletePop').toggle(canDelete);
}

function ensureNoticeDetailPopFilled(attempt) {
	attempt = attempt || 0;
	if ($('#w2ui-popup #F_SUBJECT').length && window._noticeDetailRow) {
		fillNoticeDetailPop(window._noticeDetailRow);
		return;
	}
	if (attempt < 40) {
		setTimeout(function() { ensureNoticeDetailPopFilled(attempt + 1); }, 50);
	}
}

function downloadNoticeFilePop() {
	var $pop = $('#w2ui-popup');
	var fileUrl = $pop.find('#F_FILE_URL').val();
	var fileNm = $pop.find('#F_FILE_NM').val();
	if (!fileUrl || !fileNm) return;
	var ele = document.createElement('a');
	ele.setAttribute('href', fileUrl + '/' + encodeURIComponent(fileNm));
	ele.setAttribute('download', fileNm);
	ele.click();
}

function deleteNoticeFromPop() {
	var serial = $('#w2ui-popup #F_SERIAL_NUMBER').val();
	if (!serial) {
		alert('[알림] 삭제할 공지사항을 선택하세요');
		return;
	}
	if (!confirm('선택한 공지사항을 삭제하시겠습니까?')) return;

	$.ajax({
		url: archiveCtx + '/GetNoticeManageList',
		dataType: 'json',
		type: 'get',
		data: { param: JSON.stringify({ serial_number: serial, crud: 'D' }) },
		success: function(json_data) {
			if (json_data && json_data.result === 'OK') {
				alert('삭제가 완료되었습니다');
				closePop();
				if (window._archiveGrid) ArchiveGrid.load(window._archiveGrid, { keepPage: true });
			} else {
				alert('삭제에 실패하였습니다');
			}
		},
		error: function(xhr) {
			if (xhr && xhr.responseText) alert('Error ' + xhr.responseText);
		}
	});
}

window.fillNoticeDetailPop = fillNoticeDetailPop;
window.ensureNoticeDetailPopFilled = ensureNoticeDetailPopFilled;

(function bindNoticeDetailPop() {
	if (window._noticeDetailPopBound) return;
	window._noticeDetailPopBound = true;

	$(document).on('click', '#w2ui-popup #pop_close', function(e) {
		e.preventDefault();
		closePop();
	});

	$(document).on('click', '#w2ui-popup #btnDownPop', function(e) {
		e.preventDefault();
		downloadNoticeFilePop();
	});

	$(document).on('click', '#w2ui-popup #btnDeletePop', function(e) {
		e.preventDefault();
		deleteNoticeFromPop();
	});

	if (typeof w2popup !== 'undefined') {
		w2popup.on('open', function(event) {
			event.onComplete = function() {
				ensureNoticeDetailPopFilled(0);
			};
		});
	}
})();
