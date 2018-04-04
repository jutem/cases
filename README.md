# 说明
测试代码在同包名的test下

# 1.令牌桶 
##### 1.1 guava中的rateLimiter实现
package: com.jutem.cases.traffic.guava 

参考:

1. <https://blog.jamespan.me/2015/10/19/traffic-shaping-with-token-bucket/>

2. <http://ifeve.com/guava-ratelimiter/>

#2.Spring
##### 2.1 aop（aspectj）
package：com.jutem.cases.spring.aop

说明：
使用注释进行AOP（被自定义的annotation注释的方法才被AOP）

参考：

1.<https://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html>

####2.2 cache manager
pakcage: com.jutem.cases.spring.cache

说明：
使用cache manager和注解进行缓存操作

#3.RPC框架
package:com.jutem.cases.rpc

说明：
手动抄写了一次参考的博文，这篇博文很强大，链接如下

启动方式：
先启动本地zookeeper，然后启动RpcBootstrap类，然后通过HelloServiceTest（junit）调用。

遗留问题：
现在rpcClient中78行（future.channel().closeFuture().sync()）会有block，但是主动让server断开（关闭），rpcClient这里也能执行下去。
如果只是测试可以先将这行注释。

参考：

1.<https://my.oschina.net/huangyong/blog/361751?p=1&temp=1481708812211>


#4.数据结构
##### 4.1 trie树（字典树）
package: com.jutem.cases.structure.trieTree

class:TrieTree,TrieNode

说明：
字典树可以参考维基百科：https://zh.wikipedia.org/wiki/Trie

典型应用场景：字符串检索，词频统计，字符串前缀匹配

参考：

1.http://www.programcreek.com/2014/05/leetcode-implement-trie-prefix-tree-java/

#5.jsprit
pacakge: com.jutem.cases.jsprit

class: JspritUtil

说明：
jsprit是一个解决VRP（Vehicle Routing Problem, 车辆路径问题）问题工具
功能很强大，包括时间窗口，服务时间，各种维度的约束条件等。
更详细的内容以及例子在原git地址都能找到：https://github.com/graphhopper/jsprit