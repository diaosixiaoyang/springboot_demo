jQuery.extend(jQuery.validator.messages, {
    required: "此处必填！",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入正确的网址",
    date: "请输入正确的日期",
    dateISO: "请输入正确的日期 (ISO).",
    number: "请输入正确的数字",
    digits: "只能输入整数",
    creditcard: "请输入正确的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
    minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
    rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
    range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
    min: jQuery.validator.format("请输入一个最小为 {0} 的值")
});

var pickerLocal = "zh-CN";

var dataTableLanguage = {
    "processing": "处理中...",
    "lengthMenu": "显示 _MENU_ 项结果",
    "zeroRecords": "没有匹配结果",
    "info": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
    "infoEmpty": "显示第 0 至 0 项结果，共 0 项",
    "infoFiltered": "(由 _MAX_ 项结果过滤)",
    "infoPostFix": "",
    "search": "搜索:",
    "searchPlaceholder": "搜索...",
    "url": "",
    "emptyTable": "对不起，没有满足条件的记录！",
    "loadingRecords": "载入中...",
    "infoThousands": ",",
    "paginate": {
        "first": "首页",
        "previous": "上页",
        "next": "下页",
        "last": "末页"
    },
    "aria": {
        paginate: {
            first: '首页',
            previous: '上页',
            next: '下页',
            last: '末页'
        },
        "sortAscending": ": 以升序排列此列",
        "sortDescending": ": 以降序排列此列"
    },
    "decimal": "-",
    "thousands": ".",
    buttons: {
        copy: "复制", excel: "导出到excel", csv: "导出到csv", pdf: "导出到pdf", print: "打印", copyTitle: "复制到剪贴板",
        copySuccess: "复制了 %d 行到剪贴板"
    }
};