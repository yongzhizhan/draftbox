var path = require('path');
var webpack = require('webpack');
module.exports = {
    devtool: "source-map",
    entry: {
        'tab': './custom/tab/test-tab.ts',
        'input-search': './custom/input-search/test-input-search.ts'
    },
    output:{
        path:path.resolve('./custom/'),
        filename:'[name]/[name]-pack.js' //输入文件
    },
    module:{
        loaders:[
            {
                test:/\.ts$/,//支持正则
                loader:'ts-loader'
            }
        ]
    },
    resolve: {
        extensions: ['', '.webpack.js', '.web.js', '.ts', '.js']
    }
}