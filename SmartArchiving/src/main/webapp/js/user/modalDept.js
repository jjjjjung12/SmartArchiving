var archiveCtx = (typeof ctx !== 'undefined') ? ctx : '';
$(document).ready(function() {

	loadDeptList();

	function loadDeptList() {
		var obj = { sabun: '1' };
		$.ajax({
			url: 'GetDeptList',
			type: 'get',
			dataType: 'json',
			data: { param: JSON.stringify(obj) },
			success: function(list) {
				renderDeptList(list || []);
			},
			error: function() {
				$('#dynatreeDept').html('<div class="list-group2-empty">부서목록을 불러오지 못했습니다.</div>');
			}
		});
	}

	function renderDeptList(list) {
		if (!list.length) {
			$('#dynatreeDept').html('<div class="list-group2-empty">조회된 부서가 없습니다.</div>');
			return;
		}
		var html = '';
		$.each(list, function(i, n) {
			var title = n.title || '';
			var key = n.key || '';
			html += "<div class='list-group2-item dept-item' data-key='" + key + "'>"
				+ "<p class='list-group2-item-text'>" + title + "</p></div>";
		});
		$('#dynatreeDept').addClass('list-group2').html(html);
	}

	$('#dynatreeDept').on('click', '.dept-item', function() {
		$('#dynatreeDept .dept-item').removeClass('on');
		$(this).addClass('on');
		$('#echoActive').text($(this).find('p').text());
	});

	$('#dynatreeDept').on('dblclick', '.dept-item', function() {
		$('#echoActive').text($(this).find('p').text());
		$('#f_orgCd').val($(this).data('key'));
		$('#f_orgNm').val($(this).find('p').text());
		$("button[name='close']").click();
	});

});
