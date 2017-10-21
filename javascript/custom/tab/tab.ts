declare let $: any;

class Tab{
    private tab;

    constructor(tabId){
        this.tab = $(tabId);
        this.init();
    }

    init(){
        let self = this;
        let index = 0;

        this.tab.children("a").each(function(){
            let tabItem = $(this);

            tabItem.attr("tab-index", index);

            if(0 != index){
                let curContentId = tabItem.attr("href");
                $(curContentId).hide();

                console.log("hide id:" + curContentId);
            }

            index++;
            tabItem.on('click', function () {
                let contentId = tabItem.attr("href");
                let index = tabItem.attr("tab-index");

                self.showContent(index, contentId);
            });
        });
    }

    showContent(index, contentId){
        this.tab.children("a").each(function(){
            let tabItem = $(this);
            let curIndex = tabItem.attr("tab-index");
            if(curIndex == index)
                return;

            let curContentId = tabItem.attr("href");
            $(curContentId).hide();
        });

        let contentObj = $(contentId);
        contentObj.show();
    }
}

export default Tab;