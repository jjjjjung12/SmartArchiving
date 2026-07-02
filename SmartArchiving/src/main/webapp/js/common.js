function initDatePicker(selector, maxDate, onSelectCallback) {

    var options = {

        dateFormat: 'yy-mm-dd',

        prevText: '이전 달',

        nextText: '다음 달',

        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],

        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],

        dayNames: ['일', '월', '화', '수', '목', '금', '토'],

        dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],

        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],

        showMonthAfterYear: true,

        yearSuffix: '년',

        showButtonPanel: true,

        currentText: '오늘 날짜',

        closeText: '닫기',

    };

    

    // maxDate가 있을 경우 옵션에 추가

    if (maxDate) {

        options.maxDate = maxDate;

    } else {

        options.maxDate = "+0D"; // 기본 maxDate 설정

    }



	// onSelect 이벤트 핸들러 추가

    if (onSelectCallback && typeof onSelectCallback === "function") {

        options.onSelect = onSelectCallback;

    }



    $(selector).datepicker(options);

}



function show_date_picker(id){

	$(id).datepicker("show");

	

	if(isMobile()) {

		$("#ui-datepicker-div").css("z-index", "20");

	}

}



//--------------- 페이지 이용통계

function setUseStat(pageName) {

    $.ajax({

        type: "POST",

        url: $('meta[name="_contextPath"]').attr('content')+"/sysAdm/insertUseStat.do",

        data: {

            "pagePath": pageName

        },

        success: function(response) {

            console.log(pageName);

        },

        error: function(xhr, status, error) {

            console.error(error);

 			console.error("Status:", status);

            console.error("Error:", error);

            console.error("Response Text:", xhr.responseText); // 서버 응답 내용 확인

        }

    });

}



function openPop(id){

	$(id).show();

	$(".popup_bg").show();

}



function closePop(id){

	if (typeof w2popup !== 'undefined' && $('#w2ui-popup').length > 0 && w2popup.status !== 'closed') {
		w2popup.close();
		return;
	}

	if (id) {
		$(id).hide();
	}

	$(".popup_bg").hide();

}

/** w2ui 팝업 HTML에서 script[src] 추출 (ajax 로드 시 script 미실행 보완) */
function extractPopupScriptSrcs(html) {
	var srcs = [];
	if (!html) return srcs;
	var re = /<script[^>]*\ssrc=["']([^"']+)["'][^>]*>/gi;
	var m;
	while ((m = re.exec(html)) !== null) {
		var src = m[1];
		if (srcs.indexOf(src) >= 0) continue;
		// 레이아웃 공통 스크립트는 이미 로드됨 — 팝업 전용 JS만 로드
		if (/\/jquery|\/common\.js|w2ui|archiveGrid|jquery-ui|timepicker/i.test(src)) continue;
		srcs.push(src);
	}
	return srcs;
}

function resolveArchivePopupUrl(url) {
	if (!url) return url;
	if (/^https?:\/\//i.test(url)) return url;
	var c = typeof ctx !== 'undefined' ? ctx : '';
	if (!c) {
		var meta = document.querySelector('meta[name="_contextPath"]');
		c = meta ? meta.getAttribute('content') : '';
	}
	if (c && (url === c || url.indexOf(c + '/') === 0)) return url;
	if (url.charAt(0) === '/') return c + url;
	return c ? c + '/' + url.replace(/^\//, '') : url;
}

var _archivePopupScriptsLoaded = {};

function loadArchivePopupScripts(srcs, done) {
	if (!srcs || !srcs.length) {
		if (done) done();
		return;
	}
	var pending = srcs.length;
	srcs.forEach(function(src) {
		var abs = resolveArchivePopupUrl(src);
		if (_archivePopupScriptsLoaded[abs]) {
			if (--pending === 0 && done) done();
			return;
		}
		$.ajax({ url: abs, dataType: 'script', cache: true })
			.done(function() { _archivePopupScriptsLoaded[abs] = true; })
			.always(function() {
				if (--pending === 0 && done) done();
			});
	});
}

/** w2ui 팝업 공통 옵션 (sangju형 popup_wrap + 커스텀 헤더) */
function openArchivePopup(url, options) {
	options = options || {};
	var fullUrl = resolveArchivePopupUrl(url);
	if (typeof w2popup === 'undefined') {
		window.open(fullUrl, 'archivePop', 'width=1200,height=800,scrollbars=yes,resizable=yes');
		return;
	}
	var popupOpts = $.extend({
		url: fullUrl,
		title: '',
		showClose: false,
		showMax: false,
		modal: true
	}, options);

	function showPopup() {
		var opts = $.extend({}, popupOpts);
		var onReady = opts.onReady;
		delete opts.onReady;
		var userOnOpen = opts.onOpen;
		opts.onOpen = function(event) {
			if (typeof userOnOpen === 'function') userOnOpen(event);
			var chained = event.onComplete;
			event.onComplete = function() {
				if (typeof chained === 'function') chained.call(this, event);
				if (typeof onReady === 'function') onReady();
			};
		};
		w2popup.load(opts);
	}

	var cached = $('#w2ui-popup').data(fullUrl);
	if (cached) {
		loadArchivePopupScripts(extractPopupScriptSrcs(cached), showPopup);
		return;
	}

	$.get(fullUrl, function(html) {
		loadArchivePopupScripts(extractPopupScriptSrcs(html), showPopup);
	}).fail(function(xhr) {
		if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
			return;
		}
		alert('팝업을 불러오지 못했습니다.');
	});
}



//--------------- 쿠키 설정 START

// 쿠키를 설정하는 함수

function setCookie(name, value, days) {

    var expires = "";

    if (days) {

        var date = new Date();

        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));

        expires = "; expires=" + date.toUTCString();

    }

    document.cookie = name + "=" + (value || "") + expires + "; path=/";

}



// 쿠키를 읽는 함수

function getCookie(name) {

    var nameEQ = name + "=";

    var ca = document.cookie.split(';');

    for (var i = 0; i < ca.length; i++) {

        var c = ca[i];

        while (c.charAt(0) === ' ') c = c.substring(1, c.length);

        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);

    }

    return null;

}



// 쿠키에서 팝업 숨김 상태를 가져오는 함수

function getHiddenPopups() {

    var hiddenPopups = getCookie('hiddenPopups');

    if (hiddenPopups) {

        return hiddenPopups.split(',');

    }

    return [];

}



// 쿠키에 팝업 숨김 상태를 저장하는 함수

function setHiddenPopups(hiddenPopups) {

    setCookie('hiddenPopups', hiddenPopups.join(','), 7); // 쿠키를 7일 동안 유효하게 설정

}



//--------------- 쿠키 설정 END



//모바일 체크

function isMobile() {

  const userAgent = navigator.userAgent.toLowerCase();

  return /android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(userAgent);

}



//날짜객체 문자?로 변경
function getDateStr(myDate){

	var year = myDate.getFullYear();

	var month = (myDate.getMonth() + 1);

	var day = myDate.getDate();

	

	month = (month < 10) ? "0" + String(month) : month;

	day = (day < 10) ? "0" + String(day) : day;

	

	return  year + '-' + month + '-' + day;

}



//빈값 체크

function isNull(value) {

	if(value != null && value != '' && value != undefined && value != "undefined") {

		return false;

	} else {

		return true;

	}

}



// 주소 좌표 변환
function transAddressToCoordinates(address, callback) {

	$.ajax({

		type: "POST",

		url: $('meta[name="_contextPath"]').attr('content')+"/member/transAddressToCoordinates.do",

		data: {"address":address},

		success: function(result) {

			callback(result);

		},

		error: function(request, status, error) {

			console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);

			if (typeof callback === "function") {

                callback("오류가 발생했습니다.");

            }

		}

	});

}



// 좌표 주소 변환
function transCoordinatesToAddress(lng, lat, callback) {

	$.ajax({

		type: "POST",

		url: $('meta[name="_contextPath"]').attr('content')+"/member/transCoordinatesToAddress.do",

		data: {"lng":lng, "lat":lat},

		success: function(result) {

			callback(result.address);

		},

		error: function(request, status, error) {

			console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);

			if (typeof callback === "function") {

                callback("오류가 발생했습니다.");

            }

		}

	});

}



// 페이징
function pagination(paginationInfo){

	let pageHtml = "";

	

	if(paginationInfo.currentPageNo == '1') {

		pageHtml += '<a href="#" class="first disable" title="처음 페이지 이동"><i class="fas fa-angle-double-left"></i><em>처음 페이지 이동</em></a>';

	} else {

		pageHtml += '<a href="#" class="first" title="처음 페이지 이동" onclick="navigateToPage(); return false;"><i class="fas fa-angle-double-left"></i><em>처음 페이지 이동</em></a>';

	}

	

	if(paginationInfo.firstPageNoOnPageList == '1') {

		pageHtml += '<a href="#" class="pre disable" title="이전 페이지 이동"><i class="fas fa-angle-left"></i><em>이전 페이지 이동</em></a>';

	} else {

		pageHtml += '<a href="#" class="pre" title="이전 페이지 이동" onclick="navigateToPage('+ Number(paginationInfo.firstPageNoOnPageList - 1) +'); return false;"><i class="fas fa-angle-left"></i><em>이전 페이지 이동</em></a>';

	}

	pageHtml += '<span class="num">';

	

	for(var i = paginationInfo.firstPageNoOnPageList; i <= paginationInfo.lastPageNoOnPageList; i++) {

		if(paginationInfo.currentPageNo == i) {

			pageHtml += '<a class="on">'+i+'</a>';

		} else {

			pageHtml += '<a href="" onclick="navigateToPage('+i+'); return false;">'+i+'</a>';

		}

	}

	

	pageHtml += '</span>';

	if(paginationInfo.totalPageCount <= paginationInfo.lastPageNoOnPageList) {

		pageHtml += '<a href="#" class="next" title="다음 페이지 이동"><i class="fas fa-angle-right"></i><em>다음 페이지 이동</em></a>';

	} else {

		pageHtml += '<a href="#" class="next" title="다음 페이지 이동" onclick="navigateToPage('+ Number(paginationInfo.lastPageNoOnPageList + 1) +'); return false;"><i class="fas fa-angle-right"></i><em>다음 페이지 이동</em></a>';		

	}

	if((paginationInfo.lastPageNoOnPageList == paginationInfo.totalPageCount) || (paginationInfo.totalPageCount == 0)) {

		pageHtml += '<a href="#" class="last disable" title="끝 페이지 이동"><i class="fas fa-angle-double-right"></i><em>끝 페이지 이동</em></a>';

	} else {

		pageHtml += '<a href="#" class="last" title="끝 페이지 이동" onclick="navigateToPage('+ paginationInfo.lastPageNo +'); return false;"><i class="fas fa-angle-double-right"></i><em>끝 페이지 이동</em></a>';		

	}

	

	$(".wr_page").html(pageHtml);

}
var ArchiveApp = ArchiveApp || {};

function archiveAppCtx() {
	if (typeof ArchiveApp.ctx === 'function') {
		return ArchiveApp.ctx();
	}
	var meta = document.querySelector('meta[name="_contextPath"]');
	return meta ? meta.getAttribute('content') : '';
}

function parseAjaxLoginRequired(xhr) {
	if (!xhr) {
		return null;
	}
	if (xhr.responseJSON && xhr.responseJSON.result === 'loginRequired') {
		return xhr.responseJSON.message || '로그인이 만료되었습니다. 다시 로그인해 주세요.';
	}
	if (xhr.responseText) {
		try {
			var body = JSON.parse(xhr.responseText);
			if (body && body.result === 'loginRequired') {
				return body.message || '로그인이 만료되었습니다. 다시 로그인해 주세요.';
			}
		} catch (e) { /* non-JSON */ }
	}
	return null;
}

function redirectToLoginIfSessionExpired(xhr) {
	var msg = parseAjaxLoginRequired(xhr);
	if (!msg) {
		return false;
	}
	if (window._archiveLoginRedirecting) {
		return true;
	}
	window._archiveLoginRedirecting = true;
	alert(msg);
	location.href = archiveAppCtx() + '/login';
	return true;
}

function handleAjaxLoginRequiredData(data) {
	if (!data || data.result !== 'loginRequired') {
		return false;
	}
	if (window._archiveLoginRedirecting) {
		return true;
	}
	window._archiveLoginRedirecting = true;
	alert(data.message || '로그인이 만료되었습니다. 다시 로그인해 주세요.');
	location.href = archiveAppCtx() + '/login';
	return true;
}

window.redirectToLoginIfSessionExpired = redirectToLoginIfSessionExpired;
window.handleAjaxLoginRequiredData = handleAjaxLoginRequiredData;

ArchiveApp.ctx = function() {
	var meta = document.querySelector('meta[name="_contextPath"]');
	return meta ? meta.getAttribute('content') : '';
};
ArchiveApp.logout = function() {
	location.href = ArchiveApp.ctx() + '/logout';
};

// CSRF: Spring Security(CookieCsrfTokenRepository)가 발급한 XSRF-TOKEN 쿠키를
// 변경요청(POST/PUT/DELETE/PATCH)의 X-XSRF-TOKEN 헤더로 자동 첨부 (레거시 AJAX 공통 처리)
(function() {
	if (typeof $ === 'undefined' || typeof $.ajaxPrefilter !== 'function') {
		return;
	}
	var SAFE_METHOD = /^(GET|HEAD|OPTIONS|TRACE)$/i;
	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		var method = (options.type || 'GET').toUpperCase();
		if (SAFE_METHOD.test(method) || options.crossDomain) {
			return;
		}
		var token = getCookie('XSRF-TOKEN');
		if (token) {
			jqXHR.setRequestHeader('X-XSRF-TOKEN', decodeURIComponent(token));
		}
	});

	$(document).ajaxError(function(event, xhr) {
		redirectToLoginIfSessionExpired(xhr);
	});
})();