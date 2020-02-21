
##资料
[bootstrap资料](https://v3.bootcss.com/getting-started/)
[Spring boot 指导文档](https://spring.io/guides/)
[elasticsearch参照网址](https://elasticsearch.cn/explore)
[github oauth开发](https://developer.github.com/apps/building-oauth-apps/)
[使用okhttp简化调用web服务](https://square.github.io/okhttp/)
[搜索jar包的mven配置写法](https://mvnrepository.com/)

###IDEA操作快捷键
####编辑器最大化：Ctrl+Shift+F12
####竖行输入：alt+鼠标左键拖动
####生成代码(如get,set方法,构造函数等): Alt+Insert 或者右键（Generate）
####代码标签 生成循环、System.out、main方法:   fori/sout/psvm+Tab
例如要输入for(User user : users)只需输入user.for+Tab
####查找文件，类，符号:Ctrl+N
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
vim .git-credentials
https://{username}:{password}@github.com

git config --global credential.helper store

```
查看.gitconfig，出现以下内容：
[credential] helper = store
以后再git push就不会输入用户
 