var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';

function buildSearchParam() {
	return {
		__server_id: '*',
		user_cd: $('#F_USER_CD').val() || '',
		start_tr_ymd: $('#F_SP_START_TR_YMD').val() || '',
		end_tr_ymd: $('#F_SP_END_TR_YMD').val() || ''
	};
}

$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetUserApproveStatSearchList',
		pageSize: 10,
		columns: [
			{name:'REQ_DATE', align:'center'},
			{name:'APPROVAL_DIV_CD', hidden:true},
			{name:'APPROVAL_DIV_NM', align:'center'},
			{name:'APPROVAL_REQ_ID', align:'center'},
			{name:'APPROVAL_REQ_REASON', align:'left'},
			{name:'USER_CD', align:'center'},
			{name:'BRNM', align:'center'},
			{name:'NAME', align:'center'},
			{name:'FIRST_APPROVAL_LINE_USER_NM', hidden:true},
			{name:'FIRST_APPROVAL_DATE', align:'center'},
			{name:'FIRST_APPROVAL_REJECT_DOCU', hidden:true},
			{name:'FIRST_APPROVAL_YN', hidden:true},
			{name:'FIRST_APPROVAL_YN_NM', align:'center'},
			{name:'SECOND_APPROVAL_LINE_USER_NM', hidden:true},
			{name:'SECOND_APPROVAL_DATE', align:'center'},
			{name:'SECOND_APPROVAL_REJECT_DOCU', hidden:true},
			{name:'SECOND_APPROVAL_YN', hidden:true},
			{name:'SECOND_APPROVAL_YN_NM', align:'center'},
			{name:'THIRD_APPROVAL_LINE_USER_NM', hidden:true},
			{name:'THIRD_APPROVAL_DATE', align:'center'},
			{name:'THIRD_APPROVAL_REJECT_DOCU', hidden:true},
			{name:'THIRD_APPROVAL_YN', hidden:true},
			{name:'THIRD_APPROVAL_YN_NM', align:'center'},
		],
		getPostData: buildSearchParam,
		onRowClick: function(row, idx, inst) { loadInfo(row, idx, inst.currentPage); },
	});
	ArchiveGrid.wirePage(grid, { autoLoad: true });
});

function loadInfo(selectedRow, currentRow, currentPage) {
	var selectdRow = selectedRow;
	$('#F_FIRST_APPROVAL_LINE_USER_NM').val(selectdRow.FIRST_APPROVAL_LINE_USER_NM);
	$('#F_FIRST_APPROVAL_YN_NM').val(selectdRow.FIRST_APPROVAL_YN_NM);
	$('#F_FIRST_APPROVAL_REJECT_DOCU').val(selectdRow.FIRST_APPROVAL_REJECT_DOCU);
	$('#F_FIRST_APPROVAL_DATE').val(selectdRow.FIRST_APPROVAL_DATE);
	$('#F_SECOND_APPROVAL_LINE_USER_NM').val(selectdRow.SECOND_APPROVAL_LINE_USER_NM);
	$('#F_SECOND_APPROVAL_YN_NM').val(selectdRow.SECOND_APPROVAL_YN_NM);
	$('#F_SECOND_APPROVAL_REJECT_DOCU').val(selectdRow.SECOND_APPROVAL_REJECT_DOCU);
	$('#F_SECOND_APPROVAL_DATE').val(selectdRow.SECOND_APPROVAL_DATE);
	$('#F_THIRD_APPROVAL_LINE_USER_NM').val(selectdRow.THIRD_APPROVAL_LINE_USER_NM);
	$('#F_THIRD_APPROVAL_YN_NM').val(selectdRow.THIRD_APPROVAL_YN_NM);
	$('#F_THIRD_APPROVAL_REJECT_DOCU').val(selectdRow.THIRD_APPROVAL_REJECT_DOCU);
	$('#F_THIRD_APPROVAL_DATE').val(selectdRow.THIRD_APPROVAL_DATE);
}

function popUp(reqId) {
		console.log("##### req ID : " + reqId) ;
		
		
		var popWidth = document.body.clientWidth;
	    var popHeight = document.body.clientHeight;
	    
	    var pageName = "";
	    var tempPageName= window.location.href;
	    var strPageName	= tempPageName.split("/");
	    pageName = strPageName[strPageName.length-1].split("?")[0];
	
		openArchivePopup('bulkReqListPop?&reqId=' + reqId, {
			width: popWidth - 850,
			height: popHeight - 420,
			keyboard: false
		});
	}
	
$(function() {
	$('#F_USER_CD').focusout(function(){
		if($(this).val() == ""){
			$('#F_USER_NM').val("");
		}
	});
	
	
	$('#showAjaxModal').click(function (e) {
		showUserSelector();
	});
	
	
	function showUserSelector() {
		
		jQuery('#modal-7').modal({backdrop: 'static', keyboard: false});
		
		console.log('..showAjaxModal...........................');

		var obj = new Object();
		
		obj.__user_cd = $('#F_USER_CD').val();   
		
		oTable = $('#uTable').DataTable({
 			dom: 'Bfrtip',
			select: true,
			processing: true,
			serverSide: false,
			searching: false,
			destroy: true,
			pagingType: "full_numbers",
			pageLength: 10,
			ajax: {
		        url:"GetUserApproveStatSearchList",
				dataType:"json", 
				type: "post",
				data : {param:JSON.stringify(obj)}
			},
			language: {
				"decimal":        "",
			    "emptyTable":     "데이타가 없습니다.",
			    "info":           "_START_ ~ _END_ ( 전체 _TOTAL_ 건)",
			    "infoEmpty":      "전체 0 건",
			    "infoFiltered":   "(filtered from _MAX_ total entries)",
			    "infoPostFix":    "",
			    "thousands":      ",",
			    "lengthMenu":     "Show _MENU_ entries",
			    "loadingRecords": "Loading...",
			    "processing":     "Processing...",
			    "search":         "Search:",
			    "zeroRecords":    "해당 데이터가 없습니다.",
			    "paginate": {
			        "first":      "처음",
			        "last":       "마지막",
			        "next":       "다음",
			        "previous":   "이전"
			    },
			    "aria": {
			        "sortAscending":  ": activate to sort column ascending",
			        "sortDescending": ": activate to sort column descending"
			    }
	        },
	        buttons: [
	            {
	                extend: 'excelHtml5',
	                title: '인증목록'
	            }
	        ],
            columns : [
                {"data":"user_cd"},		 	 
                {"data":"user_nm"}
     	    ]
     	        	    
		});
		
	};
	
	$('#uTable tbody').on('dblclick', 'tr', function () {
	    var data = oTable.row( this ).data();
	    
	    $('#F_USER_CD').val(data.user_cd);
		$('#F_USER_NM').val(data.user_nm);
	    
		jQuery('#modal-7').modal("hide");
	});
	
});
