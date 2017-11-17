$(function () {
    console.log("content script");
    //
    var index = 0;

    var updateStatus = function(){
        $(".phoneNum").each(function(){
            var item = $(this);
            var id = item.attr("data-id");
            var url = "http://123.56.168.19:8083/getStatus/" + id;
            $.get(url, function(data){
                var json = JSON.parse(data);
                var status = json.status;
                item.attr("data-status", status);

                if(0 == status){
                    item.text("未拨打");
                    item.attr("style", "");
                }else{
                    item.text("已拨打");
                    item.attr("style", "color:red");
                }
            });
        });
    };

    var doWork = function(){
        updateStatus();

        var markObj = $("#hotelContainer .h_item").first();

        if(markObj.attr("data-xx") == 1){
            return;
        }

        markObj.attr("data-xx", 1);
        $(".h_info_base .h_info_b1 a").each(function(){

            console.log($(this).attr("href"));
            var item = $(this).parent().parent();
            var detailPage = $(this).attr("href");
            var id = detailPage.split("/")[1];
            if(0 == index) {
                $.get(detailPage, function (data) {
                    var content = $($.parseHTML(data));
                    var phoneItem = content.find("#hotelContent .dview_info .dview_info_item dd").first();

                    console.log(phoneItem);
                    console.log(phoneItem.text());

                    var phoneNum = $.trim(phoneItem.text());
                    console.log(phoneNum);

                    item.prepend('<p style="font-size: 16px; font-weight: bold;padding: 15px;">酒店电话：' + phoneNum
                        + '<a class="phoneNum" id="phoneNum_{id}" href="#" data-id="{id}" data-status="0">未拨打</a></p>'.replace("{id}", id).replace("{id}", id));

                    $("#phoneNum_"+id).click(function(){
                        var item = $(this);
                        var status = item.attr("data-status");
                        var newStatus = (1==status) ? 0 : 1;
                        var url = "http://123.56.168.19:8083/setStatus/" + id + "/" + newStatus;
                        $.get(url, function(data){
                            item.attr("data-status", newStatus);
                            if(0 == newStatus){
                                item.text("未拨打");
                                item.attr("style", "");
                            }else{
                                item.text("已拨打");
                                item.attr("style", "color:red");
                            }
                        });

                        return false;
                    });
                });

                //index++;
            }
        });
    };

    var timeoutFunc = function() {
        doWork();
        setTimeout(timeoutFunc, 2000);
    };

    setTimeout(timeoutFunc, 2000);
});
