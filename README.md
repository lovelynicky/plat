## 说明 ##

_**一个后台服务框架，目前功能比较简单 基于`spring`，`jersey`，`mybatis`和`velocity`，集成了`shiro`的登录（暂未启用权限）
自定义了`mysql`数据库的数据库分页插件，集成`quartz`定时任务（持久化数据库）提供定时任务的新增，简单整合了`redis`，通过注解的方式新增和一致化缓存，
后面会继续整合`dubbo`服务以及定时任务的动态访问和操作等**_

## `module`说明 ##

+ _**`plat-common`**_
    >通用模块，用来提供通用方法，类和实现,分页插件也此定义，同时包含`dataSource`、`shiro`、`mybatis`、`quartz`、`redis`的配置
   
+ _**`plat-domain`**_
    >实体对象类，该模块只定义业务相关的数据实体类型

+ _**`plat-repository`**_
    >数据操作接口，均继承自`BaseRepository`
   
+ _**`plat-service`**_
    >业务逻辑`service`的实现均在于此，`redis`缓存也主要集中在这一层
    
+ _**`plat-restful`**_
    >对外提供各种`restful`风格的服务接口，基于`jersey实现`

+ _**`plat-web`**_
    >web工程，`controller`和`velocity`页面

## 运行 ##

1. 在*parent* *module*`plat`下面执行`mvn clean install`
2. 在*module*`plat-restful`下面执行`mvn clean jetty:run`
    >默认端口是*8088*，可在`pom.xml`文件中修改
3. 在*module*`plat-web`下面执行`mvn clean jetty:run`
    >默认端口是*8080*，可在`pom.xml`文件中修改
