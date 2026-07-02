var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(function(){
	var grid = ArchiveGrid.create({
		url: 'GetBulkInquiryJobResultList',
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
		numberCell: { show: true, resizable: true, title: "#" },
		resizable: true,
		sortModel: {
             single: false,
             sorter: [{ dataIndx: 'MENU_ORDER', dir: 'up' }],
             space: true,
             multiKey: null
        }
};
objPrj.colModel = [
    { title: "ID"		, width: 100, dataType: "string", dataIndx: "MENU_ID"   	 , align:"center"     , hidden:true          },
    { title: "순서"	 	, width: 100, dataType: "integer", dataIndx: "MENU_ORDER"	 , align:"center"         },    
    { title: "메뉴명"	    , width: 200, dataType: "string", dataIndx: "MENU_NM"   	 , align:"left"         },
    { title: "메뉴코드" 	, width: 100, dataType: "string", dataIndx: "MENU_CD"   	 , align:"center"         },
    { title: "URL"	 	, width: 200, dataType: "string", dataIndx: "MENU_URL"  	 , align:"left"         },
    { title: "설명"	 	, width: 100, dataType: "string", dataIndx: "MENU_DESC" 	 , align:"left"         },
    { title: "사용"	 	, width: 100, dataType: "string", dataIndx: "USE_YN"    	 , align:"center"         },
    { title: "아이콘 클래스", width: 150, dataType: "string", dataIndx: "ICON_CLASS_ID"  , align:"left"         } //20240904 ICON_CLASS_ID 추가
];
objPrj.height = '500';
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
function refreshData() {

    try {
		jsonObj.user_cd = 'ALL__';
		jsonObj.sel_cd  = '*';		
        jsonObj.__rows    = '100';
        jsonObj.__page    = '1';

        $.ajax({
            url:"GetBulkInquiryJobResultList",
            data:{param:JSON.stringify(jsonObj)},
            type:"post",
            dataType:"json",
            success: function(json_data) {
            	
            	console.log(json_data.rows);
            	let data = json_data.rows;
            	data.sort(arrOrder("MENU_ORDER"));
            	
                if(json_data.result == 'OK') {
                		bodyload(json_data.rows);
                } else {
                    console.log('NOTFOUND');
                    bodyload(json_data.rows);
                }
            },
            error : function(json_data, status){
                alert("[알림] 처리시오류가 발생하였습니다\n" );
                console.log("[알림] 처리시오류가 발생하였습니다\n" + json_data.responseText);
            }
        });
    } catch (error){
        alert("[알림] 처리시오류가 발생하였습니다\n" + error.message);
        //$('#btnQuery').attr("disabled", false);
    }
    

};





$('#btnAdd').click(function (e) {
	$('#F_MENU_ID'  ).val("");
	$('#F_MENU_NM'  ).val("");
	$('#F_MENU_CD'  ).val("");
	$('#F_MENU_DESC').val("");
	$('#F_MENU_ORDER').val("");
	$('#F_MENU_URL' ).val("");
	$('#CRUD'       ).val("C");
	$('input[name="F_USE_YN"]').val(["N"]);
	$('#F_MENU_ID'  ).attr("readonly", true); //설정
	$("input#F_MENU_NM").focus();
	$('#F_ICON_CLASS_ID' ).val(""); //20240904 ICON_CLASS_ID 추가
});


if(window._archiveGrid) ArchiveGrid.load(window._archiveGrid);
