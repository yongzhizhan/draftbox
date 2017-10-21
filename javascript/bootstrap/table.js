$('#gravidaTable').bootstrapTable({
    contentType: "application/x-www-form-urlencoded",
    method: 'post',
    dataType: "json",
    url: "/data/page-data.json",
    striped: true,
    pagination: true,
    singleSelect: false,
    clickToSelect:true,
    showColumns: false,
    search: false,
    silent: true,
    pageSize: 10,
    pageNumber:1,
    sidePagination: "server",
    queryParamsType:"limit",
    pageList:[10, 25, 50, 100],
    queryParams: gravidaTableParams,
    onDblClickRow:onDblClickRow
});

function onDblClickRow(row)
{
    $('#myModal').modal('show')

}

//表格查询条件
function gravidaTableParams(params) {
    alert('a');
    return {
        pageSize: params.limit,
        pageNumber: params.pageNumber
    };
}