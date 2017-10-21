declare let $: any;

export class MenuDataItem{
    public name: string;
    public data: string;
}

export class InputSearch{
    menuList: any;
    input: any;
    private inputSearchItem;

    constructor(inputSearchId, getMenuData){
        this.inputSearchItem = $(inputSearchId);
        this.input = this.inputSearchItem.find('input');
        this.menuList = this.inputSearchItem.find("ul");

        this.init(getMenuData);
    }

    init(getMenuData){
        const self = this;
        this.input.blur(function(){
            self.menuList.hide();
        });

        this.input.keypress(function(){
            let val = $(this).val();
            let menuData: MenuDataItem[] = [];

            if(!getMenuData){
                console.log("getMenuData is empty");
            }else{
                menuData = getMenuData(val);
            }

            //清空内容
            self.menuList.html("");

            for(let item of menuData){
                let menuItem = $("<li><a href='#' data='{1}'>{2}</a></li>"
                    .replace('{1}', item.data)
                    .replace('{2}', item.name));

                menuItem.appendTo(self.menuList);
                menuItem.one('mousedown', function(){
                    self.itemSelect(item);
                });
            }

            self.menuList.show();
        });
    }

    itemSelect(item){
        this.input.val(item.name);
        this.input.attr("data", item.data);

        this.menuList.hide();
    }
}