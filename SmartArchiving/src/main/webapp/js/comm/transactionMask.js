var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetTranMaskList',
		pageSize: 10,
		columns: [
			{name:'TGRM_NM_ENG', align:'left'},
			{name:'TGRM_NM_KOR', align:'left'},
			{name:'MSK_PTTRN', align:'left'},
			{name:'CHG_DTM', align:'Center'},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
		onRowClick: function(row, idx, inst) { loadInfo(row, idx, inst.currentPage); },
	});
	ArchiveGrid.wirePage(grid);
});
function buildSearchParam() {
	var Obj = {};

		
		Obj.__tgrm_nm_eng = '';
	return Obj;
}

$(document).ready(function() {
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
});

function loadInfo(selectedRow, currentRow, currentPage) {

			var selectdRow = selectedRow;

			$('#F_TGRM_NM_ENG').val(selectdRow.TGRM_NM_ENG);
			$('#F_TGRM_NM_KOR').val(selectdRow.TGRM_NM_KOR);
			$('#F_MSK_PTTRN').val(selectdRow.MSK_PTTRN);
		}
