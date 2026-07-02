/**
 * ArchiveGrid - sangju tbl02 + wr_page 페이징(MAGICARCHIVE jqGrid 대체)
 */
var ArchiveGrid = (function() {
    'use strict';

    function ctx() {
        var m = document.querySelector('meta[name="_contextPath"]');
        return m ? m.getAttribute('content') : '';
    }

    function esc(s) {
        if (s == null) return '';
        return String(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
    }

    function normalizeRows(data) {
        if (!data) return [];
        if (Array.isArray(data)) return data;
        if (data.rows) return data.rows;
        return [];
    }

    /** sangju: 데이터0건이면.paging 숨김, 1건이상이면 표시 */
    function setPagingVisible(inst, visible) {
        var $pg = $(inst.pagingSelector);
        var $wrap = $pg.closest('.paging');
        ($wrap.length ? $wrap : $pg).toggle(!!visible);
    }

    function pageNavIcon(kind) {
        var icons = {
            first: 'fa-angle-double-left',
            pre: 'fa-angle-left',
            next: 'fa-angle-right',
            last: 'fa-angle-double-right'
        };
        var labels = {
            first: '처음 페이지 이동',
            pre: '이전 페이지 이동',
            next: '다음 페이지 이동',
            last: '끝 페이지 이동'
        };
        return '<i class="fas ' + icons[kind] + '"></i><em>' + labels[kind] + '</em>';
    }

    function renderPaging(inst) {
        var total = inst.allRows.length;
        var pageSize = inst.pageSize;
        var totalPages = total > 0 ? Math.ceil(total / pageSize) : 0;
        if (inst.currentPage > totalPages) inst.currentPage = Math.max(1, totalPages);
        var start = (inst.currentPage - 1) * pageSize;
        var pageRows = inst.allRows.slice(start, start + pageSize);
        var $body = $(inst.bodySelector);
        $body.empty();
        if (total === 0) {
            $body.append('<tr><td colspan="' + inst.visibleCols.length + '" class="archive-grid-empty">조회된 데이터가 없습니다.</td></tr>');
            setPagingVisible(inst, false);
            if (inst.countSelector) $(inst.countSelector).text('0');
            return;
        }
        setPagingVisible(inst, true);
        if (inst.countSelector) $(inst.countSelector).text(total.toLocaleString());
		pageRows.forEach(function(row, idx) {
            var tr = $('<tr class="pointer"></tr>');
            var rowIndex = start + idx + 1;
            inst.visibleCols.forEach(function(col) {
                var val = row[col.name];
                if (col.formatter) val = col.formatter(val, row);
                var raw = val == null ? '' : String(val);
                var cls = col.className ? ' class="' + col.className + '"' : '';
                var style = col.align ? ' style="text-align:' + col.align + '"' : '';
                var title = (col.ellipsis || col.title) && raw ? ' title="' + esc(raw) + '"' : '';
                var inner;
                if (col.checkbox) {
                    var keyField = col.keyName || inst.checkboxKeyField || 'id';
                    var rowKey = row[keyField];
                    var checked = inst.checkedIds[rowKey] ? ' checked' : '';
                    inner = '<input type="checkbox" class="archive-grid-chk" data-row-key="' + esc(rowKey) + '"' + checked + ' />';
                } else if (col.ellipsis) {
                    inner = '<span class="cell-ellipsis">' + esc(val) + '</span>';
                } else {
                    inner = esc(val);
                }
                tr.append('<td' + cls + style + title + '>' + inner + '</td>');
            });
            tr.on('click', function(e) {
                if ($(e.target).is('input[type=checkbox]')) return;
                $body.find('tr').removeClass('selected');
                $(this).addClass('selected');
                if (inst.onRowClick) inst.onRowClick(row, rowIndex, inst);
            });
            tr.on('dblclick', function(e) {
                if ($(e.target).is('input[type=checkbox]')) return;
                if (inst.onRowDblClick) inst.onRowDblClick(row, rowIndex, inst);
            });
            $body.append(tr);
        });
        if (typeof inst.onAfterRender === 'function') inst.onAfterRender(inst);
        renderPageHtml(inst, totalPages);
    }

    /** sangju pagination() / dataMngpagination() 스타일의 10건 번호 블록*/
    function renderPageHtml(inst, totalPages) {
        var cur = inst.currentPage;
        var blockSize = inst.pageBlockSize || 10;
        var firstPageNoOnPageList = Math.floor((cur - 1) / blockSize) * blockSize + 1;
        var lastPageNoOnPageList = Math.min(firstPageNoOnPageList + blockSize - 1, totalPages);
        var html = '';

        html += cur === 1
            ? '<a href="#" class="first disable" title="처음 페이지 이동">' + pageNavIcon('first') + '</a>'
            : '<a href="#" class="first" data-page="1" title="처음 페이지 이동">' + pageNavIcon('first') + '</a>';

        html += cur === 1
            ? '<a href="#" class="pre disable" title="이전 페이지 이동">' + pageNavIcon('pre') + '</a>'
            : '<a href="#" class="pre" data-page="' + (cur - 1) + '" title="이전 페이지 이동">' + pageNavIcon('pre') + '</a>';

        html += '<span class="num">';
        for (var p = firstPageNoOnPageList; p <= lastPageNoOnPageList; p++) {
            html += p === cur
                ? '<a href="#" class="on page-num">' + p + '</a>'
                : '<a href="#" class="page-num" data-page="' + p + '">' + p + '</a>';
        }
        html += '</span>';

        html += cur >= totalPages
            ? '<a href="#" class="next disable" title="다음 페이지 이동">' + pageNavIcon('next') + '</a>'
            : '<a href="#" class="next" data-page="' + (cur + 1) + '" title="다음 페이지 이동">' + pageNavIcon('next') + '</a>';

        html += cur === totalPages
            ? '<a href="#" class="last disable" title="끝 페이지 이동">' + pageNavIcon('last') + '</a>'
            : '<a href="#" class="last" data-page="' + totalPages + '" title="끝 페이지 이동">' + pageNavIcon('last') + '</a>';

        var $pg = $(inst.pagingSelector);
        $pg.html(html);
        $pg.find('a[data-page]').on('click', function(e) {
            e.preventDefault();
            inst.currentPage = parseInt($(this).data('page'), 10);
            renderPaging(inst);
        });
    }

    function resolveUrl(url) {
        if (!url) return url;
        if (/^https?:\/\//i.test(url)) return url;
        var c = ctx();
        if (c && (url.indexOf(c + '/') === 0 || url === c)) return url;
        if (url.charAt(0) === '/') return c + url;
        return c ? c + '/' + url.replace(/^\//, '') : url;
    }

    function create(options) {
        var cols = options.columns || [];
        var inst = {
            url: options.url,
            method: options.method || 'POST',
            getPostData: options.getPostData,
            columns: cols,
            visibleCols: cols.filter(function(c) { return !c.hidden; }),
            bodySelector: options.bodySelector || '#gridBody',
            pagingSelector: options.pagingSelector || '.wr_page',
            countSelector: options.countSelector || '#totalCnt',
            pageSize: options.pageSize || 10,
            pageBlockSize: options.pageBlockSize || 10,
            currentPage: 1,
            allRows: [],
            checkedIds: {},
            checkboxKeyField: options.checkboxKeyField || null,
            onRowClick: options.onRowClick,
            onRowDblClick: options.onRowDblClick,
            onLoadComplete: options.onLoadComplete,
            onError: options.onError
        };
        cols.forEach(function(c) {
            if (c.checkbox && c.keyName && !inst.checkboxKeyField) {
                inst.checkboxKeyField = c.keyName;
            }
        });
        return inst;
    }

    function load(inst, opts) {
        opts = opts || {};
        var keepPage = !!opts.keepPage;
        var postData = inst.getPostData ? inst.getPostData() : {};
        $('.loading_bg').addClass('on');
        $.ajax({
            url: resolveUrl(inst.url),
            type: inst.method,
            dataType: 'json',
            data: { param: JSON.stringify(postData) },
            success: function(data) {
                $('.loading_bg').removeClass('on');
                if (typeof handleAjaxLoginRequiredData === 'function' && handleAjaxLoginRequiredData(data)) {
                    return;
                }
                if (data.result && data.result !== 'OK' && data.result !== 'NOTFOUND') {
                    if (data.result !== 'over') alert(data.result);
                    inst.allRows = [];
                } else {
                    inst.allRows = normalizeRows(data);
                }
                if (!keepPage) {
                    inst.currentPage = 1;
                    inst.checkedIds = {};
                }
                renderPaging(inst);
                if (inst.onLoadComplete) inst.onLoadComplete(data, inst);
            },
            error: function(xhr, status, err) {
                $('.loading_bg').removeClass('on');
                if (typeof redirectToLoginIfSessionExpired === 'function' && redirectToLoginIfSessionExpired(xhr)) {
                    if (inst.onError) inst.onError(xhr, status, err);
                    return;
                }
                alert('조회 중 오류가 발생했습니다.');
                if (inst.onError) inst.onError(xhr, status, err);
            }
        });
    }

    function openPopup(url, opts) {
        opts = opts || {};
        var fullUrl = url.indexOf('/') === 0 ? ctx() + url : ctx() + '/' + url;
        if (typeof openArchivePopup === 'function') {
            openArchivePopup(fullUrl, {
                width: opts.width || (document.body.clientWidth - 250),
                height: opts.height || (document.body.clientHeight - 80)
            });
        } else if (typeof w2popup !== 'undefined') {
            w2popup.load({
                url: fullUrl,
                width: opts.width || (document.body.clientWidth - 250),
                height: opts.height || (document.body.clientHeight - 80),
                title: '',
                showClose: false,
                showMax: false,
                modal: true
            });
        } else {
            window.open(fullUrl, 'archivePop', 'width=1200,height=800,scrollbars=yes,resizable=yes');
        }
    }

    function wirePage(grid, opts) {
        opts = opts || {};
        window._archiveGrid = grid;
        $('#btnQuery').off('click.archive').on('click.archive', function(e) {
            e.preventDefault();
            load(grid);
        });
        if (opts.onRowClick) grid.onRowClick = opts.onRowClick;
        if (opts.onRowDblClick) grid.onRowDblClick = opts.onRowDblClick;
        if (typeof pageInit === 'function') pageInit(grid);
        if (opts.autoLoad) load(grid);
    }

    function setRows(inst, rows) {
        inst.allRows = rows || [];
        inst.currentPage = 1;
        renderPaging(inst);
    }

    function createLocal(options) {
        var inst = create(options);
        inst.allRows = options.data ? options.data.slice() : [];
        return inst;
    }

    function getRows(inst) {
        return inst ? (inst.allRows || []) : [];
    }

    function clearRows(inst) {
        setRows(inst, []);
    }

    function addRowData(inst, row) {
        if (!inst) return 0;
        inst.allRows.push(row || {});
        renderPaging(inst);
        return inst.allRows.length;
    }

    function setRowData(inst, index, data) {
        if (!inst) return;
        var i = parseInt(index, 10) - 1;
        if (i >= 0 && i < inst.allRows.length) {
            $.extend(inst.allRows[i], data);
            renderPaging(inst);
        }
    }

    function setCell(inst, index, col, val) {
        if (!inst) return;
        var i = parseInt(index, 10) - 1;
        if (i >= 0 && i < inst.allRows.length) {
            inst.allRows[i][col] = val;
            renderPaging(inst);
        }
    }

    function getRowData(inst, index) {
        if (!inst) return {};
        if (index === undefined || index === null || index === '') {
            return inst.allRows.slice();
        }
        var i = parseInt(index, 10) - 1;
        return inst.allRows[i] ? $.extend({}, inst.allRows[i]) : {};
    }

    function loadRows(inst, rows) {
        if (!inst) return;
        inst.allRows = rows || [];
        inst.currentPage = 1;
        renderPaging(inst);
    }

    function exportToExcel(inst, opts) {
        opts = opts || {};
        if (!inst) return;
        var cols = (inst.visibleCols && inst.visibleCols.length ? inst.visibleCols : inst.columns.filter(function(c) { return !c.hidden; }))
            .filter(function(c) { return !c.checkbox; });
        var aoa = [];
        if (opts.includeLabels !== false) {
            aoa.push(cols.map(function(c) { return c.label || c.name; }));
        }
        getRows(inst).forEach(function(row) {
            aoa.push(cols.map(function(c) {
                var v = row[c.name];
                return v == null ? '' : v;
            }));
        });

        var fileName = opts.fileName || 'export.csv';
        var canWriteXlsx = typeof XLSX !== 'undefined' && XLSX.utils && typeof XLSX.writeFile === 'function';
        if (/\.xlsx$/i.test(fileName) && canWriteXlsx) {
            var ws = XLSX.utils.aoa_to_sheet(aoa);
            var wb = XLSX.utils.book_new();
            XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
            XLSX.writeFile(wb, fileName);
            return;
        }

        if (/\.xlsx$/i.test(fileName)) {
            fileName = fileName.replace(/\.xlsx$/i, '.csv');
        }
        var lines = aoa.map(function(row) {
            return row.map(function(v) {
                return '"' + String(v).replace(/"/g, '""') + '"';
            }).join(',');
        });
        var blob = new Blob(['\ufeff' + lines.join('\r\n')], { type: 'text/csv;charset=utf-8;' });
        var a = document.createElement('a');
        a.href = URL.createObjectURL(blob);
        a.download = fileName;
        a.click();
        URL.revokeObjectURL(a.href);
    }

    function wireLocal(inst, opts) {
        opts = opts || {};
        if (opts.id) window[opts.id] = inst;
        if (opts.autoRender !== false) renderPaging(inst);
    }

    var gridRegistry = {};
    function register(id, inst) {
        if (id) gridRegistry[id.replace('#', '')] = inst;
    }
    function resolve(idOrInst) {
        if (idOrInst && idOrInst.allRows) return idOrInst;
        if (typeof idOrInst === 'string') return gridRegistry[idOrInst.replace('#', '')];
        return null;
    }

    function getCheckedKeys(inst) {
        if (!inst || !inst.checkedIds) return [];
        return Object.keys(inst.checkedIds).filter(function(k) { return inst.checkedIds[k]; });
    }

    function clearChecked(inst) {
        if (!inst) return;
        inst.checkedIds = {};
    }

    function setRowChecked(inst, key, checked) {
        if (!inst) return;
        if (!inst.checkedIds) inst.checkedIds = {};
        if (checked) inst.checkedIds[key] = true;
        else delete inst.checkedIds[key];
    }

    function togglePageChecked(inst, checked) {
        if (!inst) return;
        var pageSize = inst.pageSize;
        var start = (inst.currentPage - 1) * pageSize;
        var pageRows = inst.allRows.slice(start, start + pageSize);
        pageRows.forEach(function(row) {
            var keyField = inst.checkboxKeyField;
            if (!keyField) return;
            var key = row[keyField];
            if (key == null || key === '') return;
            setRowChecked(inst, key, checked);
        });
        renderPaging(inst);
    }

    return {
        create: create, createLocal: createLocal, load: load, renderPaging: renderPaging,
        setRows: setRows, clearRows: clearRows, getRows: getRows, getRowData: getRowData,
        addRowData: addRowData, setRowData: setRowData, setCell: setCell, loadRows: loadRows,
        exportToExcel: exportToExcel, wireLocal: wireLocal, register: register, resolve: resolve,
        openPopup: openPopup, wirePage: wirePage, ctx: ctx,
        getCheckedKeys: getCheckedKeys, clearChecked: clearChecked, setRowChecked: setRowChecked,
        togglePageChecked: togglePageChecked
    };
})();

var ArchiveApp = ArchiveApp || {};
ArchiveApp.ctx = function() { return ArchiveGrid.ctx(); };
ArchiveApp.logout = function() {
    $.post('GetLogin', { logout: 'Y' }, function() {
        location.href = ArchiveApp.ctx() + '/login';
    });
};
ArchiveApp.initYmdPicker = function(sel, opts) {
    var o = $.extend({ dateFormat: 'yymmdd', changeMonth: true, changeYear: true, showMonthAfterYear: true }, opts || {});
    $(sel).datepicker(o);
};
function fn_timePicker(el) {
    if ($(el).timepicker) $(el).timepicker({ timeFormat: 'HHmm' });
}
