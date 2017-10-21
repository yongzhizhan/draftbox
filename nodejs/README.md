# nodejs学习

## 学习的内容：

* 网络请求
* 单元测试
* 文件读写
* url解析
* express的路由优先级

## node开发环境

开发环境，依赖的基础组件：

        {
          "dependencies": {
            "@types/mocha": "^2.2.43",
            "@types/node": "^8.0.34",
            "mocha": "^4.0.1",
            "ts-node": "^3.3.0",
            "typescript": "^2.5.3"
          },
          "devDependencies": {}
        }

tsconfig的基础配置：

    {
      "compilerOptions": {
        "module": "commonjs",
        "target": "es6",
        "experimentalDecorators": true,
        "allowJs": true,
        "typeRoots": [
          "./node_modules/@types"
        ],
        "sourceMap": true
      },
      "exclude": [
        "node_modules"
      ]
    }

## TODO