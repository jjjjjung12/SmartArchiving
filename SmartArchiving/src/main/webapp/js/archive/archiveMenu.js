var ArchiveMenu = (function($) {
	'use strict';

	var $menu, $wrapper;

	function appCtx() {
		if (typeof window.ctx !== 'undefined' && window.ctx) {
			return window.ctx;
		}
		return $('meta[name="_contextPath"]').attr('content') || '';
	}

	function escapeHtml(text) {
		return String(text || '')
			.replace(/&/g, '&amp;')
			.replace(/</g, '&lt;')
			.replace(/>/g, '&gt;')
			.replace(/"/g, '&quot;');
	}

	function menuIcon(iconClass) {
		var raw = (iconClass && String(iconClass).trim()) ? String(iconClass).trim() : '';
		if (!raw) {
			return '<i class="entypo-doc-text menu-icon"></i>';
		}
		return '<i class="' + raw + ' menu-icon"></i>';
	}

	function buildMenuHtml(menuData) {
		var html = '';
		var inSub = false;

		for (var i = 0; i < menuData.length; i++) {
			var n = menuData[i];
			var href = n.MENU_URL || '#';
			var label = escapeHtml(n.MENU_NM);
			var allowed = n.MENU_YN === 'Y';

			if (href === '#') {
				if (inSub) {
					html += '</ul></div></li>';
				}
				html += '<li class="depth1 has-sub">';
				html += '<a href="javascript:void(0);">' + menuIcon(n.ICON_CLASS_ID);
				html += '<span class="menu-label">' + label + '</span>';
				html += '<i class="fa-solid fa-angle-down menu-arrow"></i></a>';
				html += '<div class="depth2"><ul>';
				inSub = true;
				continue;
			}

			if (!allowed) {
				href = appCtx() + '/noAuth';
			}

			if (inSub) {
				html += '<li><a href="' + href + '">' + menuIcon(n.ICON_CLASS_ID);
				html += '<span class="menu-label">' + label + '</span></a></li>';
			} else {
				html += '<li class="depth1"><a href="' + href + '">';
				html += menuIcon(n.ICON_CLASS_ID);
				html += '<span class="menu-label">' + label + '</span></a></li>';
			}
		}

		if (inSub) {
			html += '</ul></div></li>';
		}
		return html;
	}

	function isFolded() {
		return $wrapper.hasClass('menu-folded');
	}

	function fold(fold) {
		if (fold) {
			$wrapper.addClass('menu-folded');
			$menu.find('.depth1.on').removeClass('on');
			$menu.find('.depth2').slideUp(150);
		} else {
			$wrapper.removeClass('menu-folded');
		}
	}

	function toggleFold() {
		fold(!isFolded());
	}

	function closeOtherSubmenus($currentLi) {
		$menu.find('> li.depth1.has-sub').not($currentLi).removeClass('on').find('.depth2').slideUp(180);
	}

	function bindEvents() {
		$(document).on('click', '.menu_toggle', function(e) {
			e.preventDefault();
			toggleFold();
		});

		$(document).on('click', '#archive-main-menu > li.depth1.has-sub > a', function(e) {
			e.preventDefault();
			var $li = $(this).parent();

			if (isFolded()) {
				fold(false);
			}

			if ($li.hasClass('on')) {
				$li.removeClass('on');
				$li.find('.depth2').slideUp(180);
				return;
			}

			closeOtherSubmenus($li);
			$li.addClass('on');
			$li.find('.depth2').slideDown(180);
		});

		$(document).on('click', '#archive-main-menu .depth2 li a', function(e) {
			e.stopPropagation();
			$(this).closest('li').addClass('on').siblings().removeClass('on');
		});
	}

	function normalizeMenuHref(url) {
		if (!url) return '';
		try {
			var a = document.createElement('a');
			a.href = url;
			var path = (a.pathname || '').replace(/\/+$/, '') || '/';
			return path + (a.search || '');
		} catch (err) {
			return (url.split('#')[0] || '').replace(/\/+$/, '');
		}
	}

	function currentMenuKey() {
		return normalizeMenuHref(window.location.href);
	}

	function menuHrefMatches(currentKey, menuKey) {
		if (!menuKey || menuKey === '#' || menuKey.indexOf('/noAuth') !== -1) return false;
		return currentKey === menuKey;
	}

	function labelText($a) {
		if (!$a || !$a.length) return '';
		var $l = $a.children('.menu-label');
		return (($l.length ? $l.text() : $a.text()) || '').trim();
	}

	function fallbackTitle() {
		var t = (document.title || '').trim();
		if (t && t !== 'SmartArchiving') return t;
		return '';
	}

	function renderBreadcrumb(parentName, currentName) {
		var $bc = $('#archive-breadcrumb');
		if (!$bc.length) return;
		var html = '<ol class="breadcrumb">';
		html += '<li><a href="' + appCtx() + '/"><i class="fa-solid fa-house"></i> Home</a></li>';
		if (parentName) {
			html += '<li>' + escapeHtml(parentName) + '</li>';
		}
		if (currentName) {
			html += '<li class="active"><strong>' + escapeHtml(currentName) + '</strong></li>';
		}
		html += '</ol>';
		$bc.html(html);
	}

	function markActiveMenu() {
		var currentKey = currentMenuKey();
		var $best = null;
		var bestLen = -1;

		$menu.find('.depth2 li a').each(function() {
			var key = normalizeMenuHref(this.href);
			if (!menuHrefMatches(currentKey, key)) return;
			if (key.length > bestLen) {
				bestLen = key.length;
				$best = $(this);
			}
		});

		$menu.find('.depth2 li').removeClass('on');

		if ($best && $best.length) {
			$best.parent('li').addClass('on');
			var $parent = $best.closest('li.depth1.has-sub');
			$parent.addClass('on');
			$parent.find('.depth2').show();
			renderBreadcrumb(labelText($parent.children('a')), labelText($best));
			return;
		}

		var $leaf = null;
		$menu.find('> li.depth1 > a').each(function() {
			if ($(this).closest('.has-sub').length) return;
			var key = normalizeMenuHref(this.href);
			if (menuHrefMatches(currentKey, key)) {
				$(this).closest('li.depth1').addClass('on');
				$leaf = $(this);
			}
		});

		renderBreadcrumb(null, $leaf ? labelText($leaf) : fallbackTitle());
	}

	function applyMenuFoldFromPage() {
		if ($('.sub_contarea [data-menu-folded="true"]').length) {
			fold(true);
		}
	}

	function loadMenu() {
		$menu = $('#archive-main-menu');
		$wrapper = $('.wrapper_cont.disflex');
		if (!$menu.length || !$wrapper.length) return;

		var param = {
			user_id: $menu.data('user-id') || '',
			user_cd: $menu.data('user-cd') || '',
			sel_cd: 'M'
		};

		$.ajax({
			url: 'GetMenuList',
			dataType: 'json',
			data: { param: JSON.stringify(param) },
			success: function(res) {
				if (!res || res.result !== 'OK') return;
				$menu.html(buildMenuHtml(res.menuData || []));
				$menu.find('.depth2').hide();
				markActiveMenu();
				applyMenuFoldFromPage();
				$(document).trigger('archiveMenuReady');
			}
		});
	}

	$(function() {
		bindEvents();
		renderBreadcrumb(null, fallbackTitle());
		loadMenu();
	});

	return {
		fold: fold,
		toggleFold: toggleFold,
		reload: loadMenu
	};
})(jQuery);
