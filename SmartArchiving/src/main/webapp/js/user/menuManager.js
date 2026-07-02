var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetMenuList',
		pageSize: 10,
		columns: [
		],
		getPostData: function(){ return typeof buildSearchParam === 'function' ? buildSearchParam() : {}; },
	});
	ArchiveGrid.wirePage(grid);
});
var $gridPrj;

var jsonObj = new Object();

function fn_popup(id) {
    var popup = "#" + id;
    $(id).modal("show");
}

var objPrj = {
		width:'flex', 		
		roundCorners:true,
		collapsible: false,
		pageModel: { type: "local", rPP: 16, strRpp: "{0}", strDisplay: "{0} to {1} of {2}", strPage: "Page {0} / {1}", layout: ['first','prev', 'next','last', "|", "strPage"]   },
		wrap: false,
		hwrap: true,
		autofill: false,
		editable:true,
//		editable:false,
		numberCell: { show: true, resizable: true, title: "#" },
		resizable: true,
		sortModel: {
             single: false,
             sorter: [{ dataIndx: 'MENU_ORDER', dir: 'up' }],
             space: true,
             multiKey: null
        },
	rowDblClick : function (e,i) {
		$('#F_MENU_ID').val(i.rowData.MENU_ID);
		$('#F_MENU_CD').val(i.rowData.MENU_CD);
		$('#F_MENU_NM').val(i.rowData.MENU_NM);
		$('#F_MENU_DESC').val(i.rowData.MENU_DESC);
		$('#F_MENU_URL').val(i.rowData.MENU_URL);
		$('#F_MENU_ORDER').val(i.rowData.MENU_ORDER);
		$('input[name=F_USE_YN][value="' + i.rowData.USE_YN + '"]').prop("checked", true);
		$('#F_ICON_CLASS_ID').val(i.rowData.ICON_CLASS_ID); //20240904 ICON_CLASS_ID 추가 
		$('#CRUD').val("U");
	}
};
objPrj.colModel = [
    { 
        dataIndx: "SELL",
        align: "center",
        cb: { header: true, select: true, all: true },
        type: 'checkbox',
        cls: 'ui-state-default', 
        dataType: 'bool',
        width: 50,
        editor: false
    },
    { title: "ID"		, width: 100, dataType: "string", dataIndx: "MENU_ID"   	 , align:"center"     , hidden:true, editable: false          },
    { title: "메뉴Code" 	, width: 100, dataType: "string", dataIndx: "MENU_CD"   	 , align:"center", editable: false         },
    { title: "메뉴명"	    , width: 200, dataType: "string", dataIndx: "MENU_NM"   	 , align:"left", editable: false         },
    { title: "URL"	 	, width: 200, dataType: "string", dataIndx: "MENU_URL"  	 , align:"left", editable: false         },
    { title: "설명"	 	, width: 100, dataType: "string", dataIndx: "MENU_DESC" 	 , align:"left" , editable: false        },
    { title: "순서"	 	, width: 100, dataType: "integer", dataIndx: "MENU_ORDER"	 , align:"center", editable: false         },
    { title: "사용(Y,N)"	, width: 100, dataType: "string", dataIndx: "USE_YN"    	 , align:"center", editable: false         },
    { title: "아이콘 클래스", width: 150, dataType: "string", dataIndx: "ICON_CLASS_ID"  , align:"left", editable: false         } //20240904 ICON_CLASS_ID 추가
];
objPrj.height = '100%';
if ($.fn && $.fn.pqGrid) { $gridPrj = $("#grid_json").pqGrid(objPrj); }


function bodyload( sData ) {
	
	if ($gridPrj && $gridPrj.pqGrid) $gridPrj.pqGrid('option', 'dataModel.data', sData).pqGrid('refreshDataAndView');
}
function arrOrder(key) {
    return function(a, b) {
        if (a[key] > b[key]) {    
            return 1;    
        } else if (a[key] < b[key]) {    
            return -1;    
        } 
        
        return 0;    
    }    
}


$('#btnDelete').click(function (e) {

	var jobj = new Object();
	var jobjArray = new Array();
	
	var rowData2 = ($gridPrj && $gridPrj.pqGrid) ? $gridPrj.pqGrid( "getData" ) : [];

	
	$.each(rowData2, function (index, value){
		if(value.SELL) {
			var jobjmenu = new Object();
			jobjmenu.menu_id = value.MENU_ID;
			jobjArray.push(jobjmenu);
		}
	});

	console.log("MENU_ID:" + JSON.stringify(jobjArray));

	jobj.menu_id_list = jobjArray;
	jobj.crud    = "DS";
	
	if(jobj.menu_id_list.length != 0){
		console.log('['+jobj.menu_id_list+"]");	
	} else {
		alert("[알림] 메뉴를선택하세요");
		$("input#F_MENU_NM").focus();
	    return;
	}	
	
	if(confirm("선택한메뉴를삭제 하시겠습니까?")) {
		try {
			$("#SetServerForm").ajaxForm({
				url : 'SetMenuList',
				dataType:'json',
				type: 'post',
				data : {param:JSON.stringify(jobj)},
				success: function(json_data) {
					console.log(json_data.result); 
			        if(json_data.result == 'OK') {					
						if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
						$('#btnAdd').click();
						alert("정상적으로처리 되었습니다");
						$(e.target).attr("disabled", false);
					} else {
						console.log(json_data.result); 
						alert("[알림] 처리시오류가 발생하였습니다\n" + json_data.message);
					}
				},
				error : function(data, status){
			    	if (data != null){
		    			alert("[알림] 처리시오류가 발생하였습니다\n" + data.responseText);
			    	}
			    	$(e.target).attr("disabled", false);
				}
			});	
			$("#SetServerForm").submit() ;
			$('#F_USER_CD').attr('readonly', false);
			
		} catch (error){
			alert("[알림] 처리시오류가 발생하였습니다\n" + error.message);
			$(e.target).attr("disabled", false);
		}
	}

		
});

if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
