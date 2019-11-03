# edu
![](https://github.com/0xcaffebabe/edu/workflows/Java%20CI/badge.svg)
 
 ## 项目背景
 
 - 垂直性在线教育平台
 
 - B2B2C
 
 ## 功能模块
 
 - 门户
 - 学习
 - 学习管理
 - 系统管理
 
 ## 技术架构
 
 - 前后端分离
 - 前端：VUE.JS技术栈
    - node.js 
    - vue.js 
    - npm/cnpm 
    - webpack 
    - axios 
    - nuxt.js
 - 服务端：Spring Cloud微服务架构
 
 ### 接口定义规范
 
 - restful接口
 - 统一返回：是否成功、操作代码、提示信息及自定义数据JSON数据
 
 ### 前后端分离开发具体流程
 
 - 前端与后端人员讨论确定接口
 - 并行开发
 - 前后端联调
 
 #### 单元测试
 
- 前端：mock数据

### 业务相关模块分析

- 使用GridFS存储文件
- 使用MQ来对上下游解耦
- 因业务逻辑比较复杂并为了日后可扩展，对一个实体进行分表设计
- 通过前端对文件分块实现断点续传
- 使用FFmpeg处理视频文件
- 搜索功能使用elasticsearch
- 使用jwt完成认真授权功能


 
 
