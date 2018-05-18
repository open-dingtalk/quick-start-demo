
## 开发工具
INTELLIJ IDEA

## 项目结构
```
├── README.md
├── ding-demo.iml
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       ├── Application.java
    │   │       ├── config
    │   │       │   ├── Constant.java
    │   │       │   └── DingDataSourceConfig.java
    │   │       ├── controller
    │   │       │   └── IndexController.java
    │   │       ├── enums
    │   │       │   └── BizTypeEnum.java
    │   │       ├── mapper
    │   │       │   └── ding
    │   │       │       └── OpenSyncBizDataMapper.java
    │   │       ├── model
    │   │       │   └── OpenSyncBizDataDO.java
    │   │       ├── sdk
    │   │       │   ├── CorpTokenVO.java
    │   │       │   ├── HttpRequestHelper.java
    │   │       │   ├── HttpSDK.java
    │   │       │   ├── HttpUtils.java
    │   │       │   └── SuiteTokenVO.java
    │   │       └── util
    │   │           ├── LogFormatter.java
    │   │           ├── ServiceResult.java
    │   │           └── ServiceResultCode.java
    │   └── resources
    │       ├── application.properties
    │       ├── mybatis
    │       │   └── mapper
    │       │       └── ding
    │       │           └── open_sync_biz_data_mapper.xml
    │       └── static
    │           ├── js
    │           │   └── jquery-3.1.0.js
    │           └── login_demo.html
    └── test
        └── java
            └── com
                ├── ApplicationTests.java
                └── mapper
                    └── OpenSyncBizDataMapperTest.java
```
                    
                
## 项目配置
1.更新Constant.java文件的SUITE_ID，SUITE_KEY，SUITE_SECRET三个属性。  
具体数值值请登录[开发者后台套件列表](http://open-dev.dingtalk.com/#/suite?_k=4j8h05)，查看套件详情中获取      

2.更新application.properties文件的数据连接属性和服务器启动端口。

## 打包命令
mvn clean package  -Dmaven.test.skip=true  
打成的包在工程文件的target目录下。文件为  "工程名"-"版本号".jar。()

## 服务部署    
java -jar  target/"工程名"-"版本号".jar

## 创建微应用，给企业开通微应用，查看微应用。参见文档
https://open-doc.dingtalk.com/microapp/isvh5/  
注意：创建的微应用首页地址请填写http://ip:port/login_demo.html?corpId=$CORPID$

