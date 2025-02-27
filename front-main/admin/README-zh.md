# 代码框架

- [vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)

## 构建

```bash
# 安装依赖
npm install

# 构建本地测试环境
npm run dev

# 构建生产环境
npm run build:prod
```

浏览器访问 [http://localhost:8080](http://localhost:8080)

测试环境接口地址 vue.config.js文件中修改 devServer.proxy
生产环境接口地址 src/utils/request.js文件中修改 baseURL


## 框架目录结构
```bash
├── src                        // 源代码
│   ├── assets                 // 主题 字体等静态资源
│   ├── components             // 全局公用组件
│   ├── icons                  // 项目所有 svg icons
│   ├── router                 // 路由
│   ├── store                  // 全局 store管理
│   ├── styles                 // 全局样式
│   ├── utils                  // 全局公用方法
│   ├── views                  // view
│   ├── App.vue                // 入口页面
│   ├── main.js                // 入口 加载组件 初始化等
│   └── permission.js          // 权限管理
|
├── utils                      // 工具方法
│   └── request.js             // 网络请求封装
├── router                     // 路由
│   └── index.js               
|
├── .babelrc                   // babel-loader 配置
├── eslintrc.js                // eslint 配置项
├── .gitignore                 // git 忽略项
├──public                      //静态文件
│  ├── favicon.ico             // favicon图标
│  └── index.html              // html模板
|
└── package.json               // package.json

```
## 业务代码设计

```bash
└── src                        // 源代码
    ├── views                   
    │    ├── login             //登陆页           
    │    ├── admin             //管理员管理
    │    ├── appeal            //健康码申诉  
    │    ├── detect            //核酸检测人员管理
    │    ├── heathcode         //健康码转码管理
    │    ├── index             //首页
    │    ├── place             //场所码管理
    │    └── vac               //疫苗接种管理
    ├──utils                   // 工具方法
    |    ├── villages.js       // 杭州行政区划表
    |    └── request.js        // 网络请求封装
    └── router                 // 路由
         └── index.js               
```

## 部署
拉取nginx镜像

```bash
# 构建生产环境
# 输出目录在vue.config.js中设置
# 本项目打包输出路径为./nginx/wwww/
npm run build:prod

# 拉取nginx镜像
docker pull nginx:1.17

#启动容器 开启nginx服务
# 将容器名设置为 nginxtest
# 将服务端口号设置为 3001
# 将./nginx 路径下的三个文件夹挂载到容器中
# -- www   页面静态资源
# -- conf  nginx服务配置
# -- logs  服务日志

 docker run -d -p 3001:80 --name nginxtest -v {{yourpath}}/nginx/www:/usr/share/nginx/html -v {{yourpath}}/nginx/conf/nginx.conf:/etc/nginx.nginx.conf -v {{yourpath}}/nginx/logs:/var/log/nginx nginx:1.17

```
