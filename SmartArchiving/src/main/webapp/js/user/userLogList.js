var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetUserLogList',
		pageSize: 10,
		columns: [
			{name:'LOG_ID', hidden:true},
			{name:'ACTION_TIME', align:'center'},
			{name:'USER_NAME', align:'center'},
			{name:'PROGRAM_NM', align:'left'},
			{name:'ACTION_DATE', hidden:true},
			{name:'GROUP_ID', hidden:true},
			{name:'USER_ID', hidden:true},
			{name:'USER_CD', hidden:true},
			{name:'PROGRAM_ID', hidden:true},
			{name:'PROGRAM_WHERE', align:'left'},
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : { __rows:'50', __page:'1' }; },
	});
	ArchiveGrid.wirePage(grid);
});
$(document).ready(function() {
	if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
	
	$("#export").on("click", function(){
		
		/*var popWidth = document.body.clientWidth;
	    var popHeight = document.body.clientHeight;
	    
		const test =  w2popup.load({       
		      url:'downloadReqPop?&userId=' + sessionUserId + '&userCd=' +  sessionUserCd + '&userNm=' +  sessionUserNm + '&teamNm=' +  sessionGroupId 
		    , showMax:true
	      , width:popWidth - 800              
	      , height: popHeight -650  
	      , onOpen: function(event){            	
	      }                           
	  });*/
		
		var fileName = "사용자로그조회_" + $('#F_SP_START_TR_YMD').val() + ".xlsx";
		
		ArchiveGrid.exportToExcel(_archiveGrid, {
			includeLabels : true,
			includeGroupHeader : true,
			includeFooter: true,
			fileName : fileName,
			maxlength : 40 // maxlength for visible string data 
		})
	});
	
	$('#showAjaxModal').click(function (e) {
		showUserSelector();
	});
	
	
	function showUserSelector() {
		
		jQuery('#modal-7').modal({backdrop: 'static', keyboard: false});
		
		console.log('..showAjaxModal...........................');

		var obj = new Object();
		
		obj.__user_cd = '';
		
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
		        url:"GetUserListPop",
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
