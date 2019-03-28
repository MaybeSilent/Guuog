# Guuog
> 简易版本服务器
- 由maven构建的小项目，webroot目录为需要部署的http网页内容，可以进行替换，src目录下为源码部分
- 目前仅仅支持servlet和html页面内容的展示
- 其中webroot目录结构与tomcat下的目录结构类似
```
-- WEB-INF
---- web.xml
---- classes
-- *.html
```
- 其中web.xml可以定义相应的servlet-mapping与servlet-name

> 项目运行
- 获取源码后直接利用maven下载相应的依赖包进行构建即可运行。
- 运行直接调用server包下的main方法即可，该模块主要启动了socket监听线程，请求处理线程与内容写入线程
- 运行后如果不更改webroot下的部署网站目录
- 直接在浏览器输入 http://127.0.0.1:8888/index.html 即可访问相应index.html中的内容
```
GUUOG SIMPLE SERVER
welcome

project address : https://github.com/MaybeSilent/Guuog

This is a simple web server based on nio
```
- 输入 http://127.0.0.1:8888/GuuogServlet
则可以在浏览器中看到 Servlet example class main.simpleServlet
- 运行日志部分展示如下所示
```
Thu Mar 28 23:00:16 CST 2019 INFO: Server Thread Start
Thu Mar 28 23:00:16 CST 2019 INFO: BootStrap Of Server Finished
Thu Mar 28 23:00:24 CST 2019 INFO: Register A Request
Thu Mar 28 23:00:24 CST 2019 INFO: Register A Request
Thu Mar 28 23:00:24 CST 2019 INFO: Begin To Read From Request
Thu Mar 28 23:00:24 CST 2019 INFO: Begin Parsing A Request
Thu Mar 28 23:00:24 CST 2019 INFO: Kind: GET Uri: /index.html HttpKind: HTTP/1.1
Thu Mar 28 23:00:24 CST 2019 INFO: Host : 127.0.0.1:8888
Thu Mar 28 23:00:24 CST 2019 INFO: Connection : keep-alive
Thu Mar 28 23:00:24 CST 2019 INFO: Upgrade-Insecure-Requests : 1
Thu Mar 28 23:00:24 CST 2019 INFO: User-Agent : Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36
Thu Mar 28 23:00:24 CST 2019 INFO: Accept : text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3
Thu Mar 28 23:00:24 CST 2019 INFO: Accept-Encoding : gzip, deflate, br
Thu Mar 28 23:00:24 CST 2019 INFO: Accept-Language : zh-CN,zh;q=0.9,en;q=0.8
Thu Mar 28 23:00:24 CST 2019 INFO: FINISH PARSE A REQUEST
Thu Mar 28 23:00:24 CST 2019 INFO: Writing Message Into Response
Thu Mar 28 23:00:24 CST 2019 INFO: Writing Finished
Thu Mar 28 23:00:24 CST 2019 INFO: 0 of samllBuffer Is Recycled
Thu Mar 28 23:00:46 CST 2019 INFO: Begin To Read From Request
Thu Mar 28 23:00:46 CST 2019 INFO: Request Finished
Thu Mar 28 23:00:46 CST 2019 INFO: Begin Parsing A Request
Thu Mar 28 23:00:46 CST 2019 INFO: FINISH PARSE A REQUEST
Thu Mar 28 23:00:46 CST 2019 INFO: A CAHNNEL IS CANCELED
```

> 项目原理
- 主要为了熟悉JavaNIO的使用而编写的项目，利用NIO进行数据的读写。
- NIO主要利用了缓冲区来提高IO效率，在缓冲区上，自定义了Buffer和BufferPool类来进行内存的管理
