
##资料
[学习项目地址](https://github.com/codedrinker/community)
[maven仓库配置](https://www.cnblogs.com/winner-0715/p/7493387.html)
[bootstrap资料](https://v3.bootcss.com/getting-started/)
[Spring boot 指导文档](https://spring.io/guides/)
[elasticsearch参照网址](https://elasticsearch.cn/explore)
[github oauth开发](https://developer.github.com/apps/building-oauth-apps/)
[使用okhttp简化调用web服务](https://square.github.io/okhttp/)
[搜索jar包的mven配置写法](https://mvnrepository.com/)
[数据库迁移管理脚本](https://flywaydb.org/getstarted/firststeps/maven)
[thymeleaf官方地址](https://www.thymeleaf.org)
[简化get/set方法的lombok插件](https://projectlombok.org)
[mybatis中文文档](https://mybatis.org/mybatis-3/zh/index.html)
###IDEA操作快捷键
####编辑器最大化：Ctrl+Shift+F12
####竖行输入：alt+鼠标左键拖动
####生成代码(如get,set方法,构造函数等): Alt+Insert 或者右键（Generate）
####代码标签 生成循环、System.out、main方法:   fori/sout/psvm+Tab
例如要输入for(User user : users)只需输入user.for+Tab
####查找类:Ctrl+N, 查找文件：ctrl+shift+N
####自动生成变量定义:Ctrl+Alt+v
####快速新创建一行：shift+enter
####加try catch:ctrl+alt+t
####注释(// 或者/*...*/ ): Ctrl+/ 或 Ctrl+Shift+/  
####返沪上次操作的位置：ctrl+E
####删除无效的引入,选择后：ctrl+alt+o
###热加载
####页面热加载
修改application.properties 中加入
```properties
spring.thymeleaf.cache=false
```
禁止leymleaf 缓存html
在idea中修改html后 ctrl+f9 重新编译页面，既可以不用重启应用获得新的页面
[参考：IDEA SpringBoot Thymeleaf热部署实现更改页面不重启](https://blog.csdn.net/CoderBruis/article/details/89705013)
[thymeleaf模板框架用法](https://blog.csdn.net/u013399093/article/details/51768506)

###添加H2数据库
[参考h2 官方网页](http://www.h2database.com/html/quickstart.html)
添加依赖到pom.xml, 加入
```xml
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.200</version>
        </dependency>
```
然后在idea database panel 中添加h2数据源，选择嵌入模式
无需用户名和密码   路径：jdbc:h2:~/community
第一次连接时报错，要到/用户/zhi.wengang目录下把community.mv.db和community.trace文件删除
然后选择数据源，右击选择console,执行创建h2数据库用户sa的操作
```sql
create user if not exists sa password '123';
alter user sa admin true;
```

###启用mybatis
[参考mybatis官方配置](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
[参考springboot 官方配置数据库的说明](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/html/spring-boot-features.html#boot-features-sql)
首先加入依赖pom.xml, 加入spring-boot-starter-jdbc会添加一个数据源连接池，添加mybatis-spring-boot-starter 会加入mybatis依赖
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.1</version>
        </dependency>
```
修改application.properties,加入h2数据库的连接定义
```properties
spring.datasource.url=jdbc:h2:~/community
spring.datasource.username=sa
spring.datasource.password=123
spring.datasource.driver-class-name=org.h2.Driver
```
##启用flywaydb 可以自动执行脚本保证开发和生产数据库保持一致
把修改数据库的脚本按照版本号方式命名，并存放到resource/db/migration目录下
文件名为 V1__create_user_table.sql, V2__alter_user_add_bio.sql等
这样执行mvn flyway:migrate 就会自动执行这些SQL脚本
[中文flywaydb使用介绍](https://www.cnblogs.com/ywjy/p/10959475.html)

##git 操作
```shell script
echo "# community" >> README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin https://github.com/richardzhi/community.git
git add .
git push -u origin master

#用bash git，在用户目录下创建文件，修改内容
touch .git-credentials
vim .git-credentials #加入下面内容
https://{username}:{password}@github.com
#执行命令
git config --global credential.helper store

##分支管理的例子
#创建分支
git branch test
# 切换到test分支
git checkout test
# 查看分支，当前已经在test分支上面
git branch
* test
  master
# 在当前分支上进行一些修改，把内容‘123123’添加到hello.txt文件中
echo "123123" >> hello.txt
## 添加并提交修改到本地
git add hello.txt
git commit -m "add hello.txt"
#推送到远程，因为现在远程还没有test分支，所以需要set-upstream
git push --set-upstream origin test
#test解决后，把修改合并会master，并删除test分支
#切换到主分支
git checkout master
Switched to branch 'master'
Your branch is up to date with 'origin/master'.
#把test分支的内容合并到主分支master
git merge test
Updating 2d0f8f9..bc9a5ca
Fast-forward
 qwe | 1 +
 1 file changed, 1 insertion(+)
#删除分支test
git branch -d test
Deleted branch wy (was bc9a5ca). create mode 100644 qwe
## push 到主分支
$ git push
Total 0 (delta 0), reused 0 (delta 0)
To https://github.com/xxxx/xxxx.git
   2d0f8f9..bc9a5ca  master -> master
# 删除远程分支
git push origin :test
To https://github.com/xxxx/xxxx.git
 - [deleted]         test


git checkout -b 本地分支名 远程分支名　　#如：git checkout -b monitor origin/monitor
git push 远程别名 本地分支名 #　如：git push zlj_origin drug-supervision-monitor
```
查看.gitconfig，出现以下内容：
[credential] helper = store
以后再git push就不会输入用户
 
 ###lombok 简化set/get 方法
在程序中加入@Data注解 可以避免因增加或修改字段而重复set/get方法 
 
 ###项目工程的目录设计
 controller:响应用户访问请求
 dto: 封装调用API的参数
 provider: 封装调用API的方法
 mapper: 封装mybatis用来读写数据的方法，方法中需要调用Model中对象
 model:对应数据表的bean对象
 service:组装多个数据库对象到一起
 调用外部API的次序： controller->provider->dto
 调用数据库访问的次序: controller->service->mapper->model  或者 controller->mapper->model
 
 ##配置自动化
![图片测试](https://raw.githubusercontent.com/richardzhi/pic/master/%E6%9D%80%E6%89%8B47.jfif?token=AAQOG7LT77BGEKCABMP5SGC6KH2TC)
 