# Tab模块

## 思路
通过a标签的src与content关联，实现切换

## 使用

    declare let $: any;
    
    import Tab from "./tab";
    
    $(function(){
        let tab = new Tab("#testTab");
    });
