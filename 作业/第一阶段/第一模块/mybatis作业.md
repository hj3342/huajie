1、Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？
（1）Mybatis动态SQL可以让我们在XML映射文件内以XML标签的形式编写动态SQL，完成逻辑判断和动态拼接SQL的功能。动态sql用来支持复杂业务逻辑下根据动态生成sql。
（2）Mybatis提供的动态SQL标签有：<if/>、<choose/>、<when/>、<otherwise/>、<trim/>、<when/>、<set/>、<foreach/>、<bind/>。
（3）执行原理：使用OGNL的表达式，从SQL参数对象中计算表达式的值,根据表达式的值动态拼接SQL，以此来完成动态SQL的功能。

2、Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？
支持延迟加载，Mybatis仅支持association关联对象和collection关联集合对象的延迟加载，association指的就是一对一，collection指的就是一对多查询。
mybatis 默认延迟加载是关闭的，在配置文件中可以配置是否启用延迟加载lazyLoadingEnabled=true|false。
原理：使用CGLIB创建目标对象的代理对象，当调用目标方法时，进入拦截器方法，比如调用a.getB().getName()，拦截器invoke()方法发现a.getB()是null值，
那么就会单独发送事先保存好的查询关联B对象的sql，把B查询上来，然后调用a.setB(b)，于是a的对象b属性就有值了，接着完成a.getB().getName()方法的调用。这就是延迟加载的基本原理

3、Mybatis都有哪些Executor执行器？它们之间的区别是什么？
SimpleExecutor：每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象。
ReuseExecutor：执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，用完后，不关闭Statement对象，而是放置于Map内，供下一次使用。简言之，就是重复使用Statement对象。
BatchExecutor：执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批处理中（addBatch()），等待统一执行（executeBatch()），它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，等待逐一执行executeBatch()批处理。与JDBC批处理相同。

4、简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？
一级缓存：
一级缓存是用HashMap缓存数据。
一级缓存是sqlsession级别的缓存，不同的sqlsession之间的缓存数据区域是互不影响的。
一级缓存在sqlsession改变，或者在sqlsession不管的情况下，改变查询条件、发生了增删改操作、手动清楚缓存一级缓存会失效。

二级缓存：
二级缓存默认实现类PerpetualCache的缓存数据结构是HashMap.
二级缓存是mapper级别的缓存，多个sqlsession可以公用二级缓存，二级缓存是跨sqlSession的。
二级缓存无法在分布式场景下使用。

5、简述Mybatis的插件运行原理，以及如何编写一个插件？
mybatis插件本质上就是拦截器，本质上就是借助于底层的jdk动态代理实现的，mybatis在创建四大核心对象时，创建出对象后不直接返回，而是通过interceptorChain.pluginAll(parameterHandler)方法对原对象封装，interceptorChain中保存了所有的拦截器(interceptors），获取到所有的拦截器后分别调用interceptor.plugin(target）返回原对象的代理对象，代理对象就可以拦截四大核心对象的每一个行为。
编写插件：
创建一个插件类实现Interceptor 接口，重写intercept方法（插件的核心方法）、plugin方法（生成target的代理对象）、setProperties方法（传递插件所需参数），通过@Intercepts和@Signature注解配置拦截的接口、方法和方法的参数，在sqlMapConfig.xml中<plugins>配置插件参数。