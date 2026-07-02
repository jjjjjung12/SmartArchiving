var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function buildSearchParam() {
	return {
		__use_yn: $('input[name="C_USE_YN"]:checked').val() || 'A',
		__server_id: '*',
		__agent_id: '*',
	};
}

function clearDetail() {
	$('#D_SERVER, #D_AGENT, #D_HEALTH, #D_HEALTH_URL, #D_HEALTH_BODY').val('');
}

function loadInfo(row) {
	if (!row) return;
	$('#D_SERVER').val((row.SERVER_NM || '') + (row.SERVER_IP ? ' (' + row.SERVER_IP + ')' : ''));
	$('#D_AGENT').val([row.AGENT_NM, row.AGENT_ID, row.AGENT_PORT].filter(Boolean).join(' / '));
	$('#D_HEALTH').val((row.HEALTH || '') + (row.HEALTH_CODE ? ' (' + row.HEALTH_CODE + ')' : ''));
	$('#D_HEALTH_URL').val(row.HEALTH_URL || '');
	$('#D_HEALTH_BODY').val(row.HEALTH_BODY || '');
}

$(function() {
	var grid = ArchiveGrid.create({
		url: 'GetAgentMonitorList',
		pageSize: 10,
		columns: [
			{ name: 'SERVER_ID', align: 'center' },
			{ name: 'SERVER_NM', align: 'center' },
			{ name: 'SERVER_IP', align: 'center' },
			{ name: 'AGENT_ID', align: 'center' },
			{ name: 'AGENT_NM', align: 'center' },
			{ name: 'AGENT_PORT', align: 'center' },
			{ name: 'HEALTH', align: 'center' },
			{ name: 'HEALTH_URL', align: 'left' },
			{ name: 'HEALTH_CODE', align: 'center' },
			{ name: 'HEALTH_BODY', hidden: true },
			{ name: 'RUN_NM', align: 'center' },
			{ name: 'USE_YN', align: 'center' },
			{ name: 'DESCRIPTION', align: 'left' }
		],
		getPostData: buildSearchParam,
		onRowClick: function(row) { loadInfo(row); },
		onRowDblClick: function(row) { loadInfo(row); }
	});

	ArchiveGrid.wirePage(grid, { autoLoad: true });

	$('#btnRefresh').on('click', function(e) {
		e.preventDefault();
		ArchiveGrid.load(grid);
	});

	clearDetail();
});
