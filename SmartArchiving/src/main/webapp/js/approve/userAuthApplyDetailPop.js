var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(document).ready(function() {
	
	search();
	
});

function search(){
	var obj = new Object();
	obj.APPROVAL_REQ_ID = $("input#reqId").val();
	
	console.log(" ##### reqId#####" + $("input#reqId").val() );
	
	$.ajax({
        url:"GetUserAuthApplyDetail",
        data:{param:JSON.stringify(obj)},
        dataType:"json",
        success: function(json_data) {
        	if(json_data.result == 'OK') {
				$("#F_USER_CD_POP").val(json_data.rows[0].USER_CD); //사용자ID
				$("#F_USER_NM_POP").val(json_data.rows[0].USER_NM); //성명
				$("#F_BRMM_POP").val(json_data.rows[0].BRMM); //팀명
				$("#F_APPROVAL_REQ_ID_POP").val(json_data.rows[0].APPROVAL_REQ_ID); //결재요청 ID
				$("#F_OFT_POP").val(json_data.rows[0].OFT); //직급명
				$("#F_IP_ADDRESS_POP").val(json_data.rows[0].IP_ADDRESS); //등록IP
				$("#F_APPROVAL_REQ_REASON_POP").val(json_data.rows[0].APPROVAL_REQ_REASON); //결재요청사유
				
				//조회법인				
				if(json_data.rows[0].COM_CD == '1') {
					$("#F_COM_CD_POP").val("은행");	
				} else if(json_data.rows[0].COM_CD == '2') {
					$("#F_COM_CD_POP").val("상호");
				} else {
					$("#F_COM_CD_POP").val("은행상호");
				}
				
				$("#F_EXPIRE_DATE_POP").val(toDatePoint2(json_data.rows[0].EXPIRE_DATE)); //만료일자
			} else {
			    alert("[알림] 오류: " + json_data.result);
			}
        },
        error : function(json_data, status){
            alert("[알림] 처리시오류가 발생하였습니다\n" );
            console.log("[알림] 처리시오류가 발생하였습니다\n" + json_data.responseText);
        }
    });
}
