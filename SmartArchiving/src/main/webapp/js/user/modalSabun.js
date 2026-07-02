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
				$('#dynatreeSabun').html('<div class="list-group2-empty">부서목록을 불러오지 못했습니다.</div>');
			}
		});
	}

	function renderDeptList(list) {
		if (!list.length) {
			$('#dynatreeSabun').html('<div class="list-group2-empty">조회된 부서가 없습니다.</div>');
			return;
		}
		var html = '';
		$.each(list, function(i, n) {
			var title = n.title || '';
			var key = n.key || '';
			html += "<div class='list-group2-item dept-item' data-key='" + key + "'>"
				+ "<p class='list-group2-item-text'>" + title + "</p></div>";
		});
		$('#dynatreeSabun').addClass('list-group2').html(html);
	}

	$('#dynatreeSabun').on('click', '.dept-item', function() {
		$('#dynatreeSabun .dept-item').removeClass('on');
		$(this).addClass('on');
		$('#echoActive').text($(this).find('p').text());
		loadEmpList('*', $(this).data('key'));
	});

	function loadEmpList(name, orgcd) {
		var obj = { name: name, sabun: '*', orgcd: orgcd };
		$.ajax({
			url: 'GetEmpList',
			data: { param: JSON.stringify(obj) },
			dataType: 'json',
			success: function(json_data) {
				if (json_data.result == 'OK') {
					var html = '';
					$.each(json_data.data, function(i, n) {
						html += "<div class='list-group2-item' id='sabun_list_item" + i + "'>"
							+ "<p class='list-group2-item-text'>" + n.name + "(" + n.sabun + ")</p></div>";
					});
					$('#sabun_list').html(html);
				} else {
					$('#sabun_list').html('<div class="list-group2-empty">조회된 직원이 없습니다.</div>');
				}
			},
			error: function() {
				$('#sabun_list').html('<div class="list-group2-empty">직원 목록을 불러오지 못했습니다.</div>');
			}
		});
	}

	$('#sabun_list').on('dblclick', 'p', function() {
		if (sPocus == 0) {
			$('#searchVal').val($(this).text());
		} else if (sPocus == 1) {
			$('#f_secondApro').val($(this).text());
		} else if (sPocus == 2) {
			$('#f_lastApro').val($(this).text());
		}
		$("button[name='close']").click();
	});

});
