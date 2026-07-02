var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function refreshLicenseStatus() {
	var Obj = {
		__server_id: '',
		__job_id: '*',
		__job_nm: '',
		__job_tm_from: '',
		__job_tm_to: '',
		__stat: 'B',
	};

	$.ajax({
		url: 'GetJobMonitor',
		data: { param: JSON.stringify(Obj) },
		type: 'post',
		dataType: 'json',
		success: function(json_data) {
			if (json_data.result !== 'OK' || !json_data.rows || !json_data.rows.length) {
				return;
			}
			var row = json_data.rows[0];
			$('#F_TOTAL_AMT').text(typeof toMoneyPoint === 'function' ? toMoneyPoint(row.LICENSE) : row.LICENSE);
			$('#F_USED_AMT').text(typeof toMoneyPoint === 'function' ? toMoneyPoint(row.SOURCE_SIZE) : row.SOURCE_SIZE);
			$('#F_FREE_AMT').text(typeof toMoneyPoint === 'function' ? toMoneyPoint(row.FREE_SIZE) : row.FREE_SIZE);

			var rate = parseInt(row.RATE, 10);
			var $status = $('#F_STATUS');
			$status.removeClass('license-panel__status--ok license-panel__status--warn license-panel__status--danger');
			if (rate < 70) {
				$status.text('양호').addClass('license-panel__status--ok');
			} else if (rate < 80) {
				$status.text('주의').addClass('license-panel__status--warn');
			} else {
				$status.text('위험').addClass('license-panel__status--danger');
			}
		}
	});
}

$(function() {
	refreshLicenseStatus();
});
