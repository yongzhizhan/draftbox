define(function () {
    return {
        getDemoList: function () {
            return [{
                'name': 'demo',
                'path': 'views/demo/index.html?baseUrl=' + config.pathEncode('../../modules')
            },{
                'name': 'config tool',
                'path': 'views/configtool/index.html?baseUrl=' + config.pathEncode('../../modules')
            }]
        }
    }
});