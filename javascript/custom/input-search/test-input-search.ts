import {InputSearch, MenuDataItem} from "./input-search";

declare let $:any;

$(function(){
    let id = "#input-search-box";
    let inputSearch = new InputSearch(id, getMenuData);

    function getMenuData(){
        let menuDataItem: MenuDataItem = new MenuDataItem();
        menuDataItem.name = "name";
        menuDataItem.data = "data";

        let result = [];
        result.push(menuDataItem);

        return result;
    }
});