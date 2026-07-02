var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(document).ready(function() {
	if( $('#corpValue').val() !="BL" ){
		var bl = document.getElementById('F_COM_1');
		if(bl) bl.parentElement.remove();
		var blSb = document.getElementById('F_COM_3');
		if(blSb) blSb.parentElement.remove();
		var sb = document.getElementById('F_COM_2');
		if(sb) sb.checked = true;
	}
	$("#F_APPLY_DIV_CD option[value='02']").remove();
	$("#F_APPLY_DIV_CD option[value='03']").remove();
	search();

	function search(){
		var obj = { user_cd: $("input#F_USER_CD").val() };
        $.ajax({
            url:"GetUserAuthApply",
            data:{param:JSON.stringify(obj)},
            dataType:"json",
            success: function(json_data) {
            	if(json_data.result == 'OK') {
            		$('#F_APPROVAL_REQ_ID').val(json_data.approval_req_id);
            		$('#F_EXPIRE_DATE').val(json_data.expire_date);
            		$('#F_USER_NM').val(json_data.name);
            		$('#F_BRC').val(json_data.brc);
            		$('#F_BRMM').val(json_data.brnm);
            		$('#F_OFT_C').val(json_data.oft_c);
            		$('#F_OFT').val(json_data.oft);
            		$('#F_IP_ADDRESS').val(json_data.ip);
            		if(typeof json_data.rows == "undefined"){
            			$('#btnSave').show();
            			$('#div_text').hide().html("");
            			$('#F_FIRST_LINE,#F_SECOND_LINE').val("");
            			$('#F_FIRST_USER_ID,#F_SECOND_USER_ID').val("");
            			$('#F_APPROVAL_REQ_REASON').val("");
            			$('input[type="radio"]').attr("onclick", "");
            			$('#F_APPLY_DIV_CD').attr("disabled", false);
            			$('#F_APPROVAL_REQ_REASON').attr("readonly", false);
            			$('#F_EXPIRE_DATE').attr("readonly", false);
            			$('#F_FIRST_USER_ID,#F_SECOND_USER_ID').attr("disabled", false);
            		}else{
            			$('#btnSave').hide();
            			$('#div_text').show();
            			$('#F_APPROVAL_REQ_REASON').val(json_data.rows[0].approval_req_reason);
            			if(json_data.rows[0].line_approval_date != "") {
            				$('#F_FIRST_LINE').val(toDatePoint2(json_data.rows[0].line_approval_date)+ (json_data.rows[0].approval_yn == "Y" ? "  결재완료" : "  반려"));
            			}
            			if(json_data.rows[1].line_approval_date != "") {
            				$('#F_SECOND_LINE').val(toDatePoint2(json_data.rows[1].line_approval_date) + (json_data.rows[1].approval_yn == "Y" ? "  결재완료" : "  반려"));
            			}
            			$('#div_text').html(json_data.rows[1].line_approval_date != "" ? "결재 완료 (신청일자: "+ toDatePoint2(json_data.req_date) +")" : "결재 신청중(신청일자: "+ toDatePoint2(json_data.req_date) +")");
            			$('#F_FIRST_USER_ID').val(json_data.rows[0].approval_line_user_id);
            			$('#F_SECOND_USER_ID').val(json_data.rows[1].approval_line_user_id);
            			$('#F_IP_ADDRESS').val(json_data.rows[0].ip_address);
            			$('input[name="F_COM"]').val([json_data.rows[0].com_cd]);
            			$('input[type="radio"]').attr("disabled", true);
            			$('#F_APPLY_DIV_CD,#F_FIRST_USER_ID,#F_SECOND_USER_ID').attr("disabled", true);
            			$('#F_APPROVAL_REQ_REASON,#F_EXPIRE_DATE,#F_IP_ADDRESS').attr("readonly", true);
            		}
				} else if(json_data.result == 'NOTFOUND') {
				    $('#btnSave').show();
				} else {
				    alert("[알림] 오류: " + json_data.result);
				}
            },
            error : function(){ alert("[알림] 처리시오류가 발생하였습니다"); }
        });
	}

	$('#btnSave').click(function () {
		var obj = {
			user_cd: $("#F_USER_CD").val(), user_nm: $("#F_USER_NM").val(),
			brc: $("#F_BRC").val(), brmm: $("#F_BRMM").val(),
			approval_req_id: $("#F_APPROVAL_REQ_ID").val(), oft_c: $("#F_OFT_C").val(), oft: $("#F_OFT").val(),
			apply_div_cd: $("#F_APPLY_DIV_CD").val(), approval_req_reason: $("#F_APPROVAL_REQ_REASON").val(),
			expire_date: $("#F_EXPIRE_DATE").val(),
			first_user_id: $("#F_FIRST_USER_ID").val(), first_user_nm: $("#F_FIRST_USER_ID option:checked").text(),
			second_user_id: $("#F_SECOND_USER_ID").val(), second_user_nm: $("#F_SECOND_USER_ID option:checked").text(),
			com: $('input[name="F_COM"]:checked').val(), ip: $("#F_IP_ADDRESS").val(), crud: "ap"
		};
		if(!obj.approval_req_reason){ alert("[알림] 결재요청사유를입력하세요"); return; }
		if(!obj.first_user_id){ alert("[알림] 1차결재자를 선택하세요"); return; }
		if(!obj.second_user_id){ alert("[알림] 2차결재자를 선택하세요"); return; }
		if(!obj.ip){ alert("[알림] ip를입력하세요"); return; }
		if(!obj.expire_date){ alert("[알림] 만료일자를입력하세요"); return; }
		if(!obj.com){ alert("[알림] 조회법인을선택하세요"); return; }
		$.ajax({
			url: 'SetUserAuthApply', dataType:'json', type: 'post', data:{param:JSON.stringify(obj)},
			success: function(json_data) {
				if(json_data.result == "OK"){ alert("정상적으로신청 되었습니다"); search(); }
				else alert("신청 실패");
			}
		});
	});

	$('#login').click(function (e) {
		e.preventDefault();
		$.ajax({
			url: "GetLogin", dataType:"json", type:"post",
			success: function(json){ if(json.result == "success") location.href = archiveCtx + "/login"; }
		});
	});
});
