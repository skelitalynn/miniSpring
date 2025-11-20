# 🌲 Spring 的整个设计，其实只解决 **三个问题**

| 问题                       | 如果不用Spring会怎样         | Spring是怎么解决的                                     | 对应模块                               |
| ------------------------ | --------------------- | ------------------------------------------------ | ---------------------------------- |
| **对象怎么创建？**              | 手动 new、全局到处传          | **IOC**：容器替你创建 & 管理对象                            | `BeanFactory/ApplicationContext`   |
| **横切逻辑（日志/事务/权限）怎么统一写？** | 每个方法都手写重复逻辑，改一次改全局    | **AOP**：用“代理”把横切逻辑包在方法执行前后                       | `ProxyFactory/Interceptor`         |
| **请求进来后如何找到要执行哪个方法？**    | 总不能 if-else 匹配 URL 吧？ | **MVC**：DispatcherServlet 统一分发请求 → Controller 方法 | `DispatcherServlet/HandlerMapping` |


```
IOC：我负责“对象怎么来”
AOP：我负责“方法执行前后加点料”
MVC：我负责“请求怎么路由到方法”
```
---

## ① 为什么要有 `BeanFactory` / `ApplicationContext`？（IOC）

### 🌧️ 如果没有 IOC

你写代码会变成：

```java
UserService userService = new UserService(new UserRepository(new DBConnection()));
```

到处 new、依赖硬绑死、想换实现会非常麻烦。

### 🌞 IOC 的核心作用

**把“创建对象的权力”从你手里** → **交给容器。**

于是：

```java
UserService userService = ctx.getBean(UserService.class);
```

这意味着：

* Spring **知道**哪些类要创建（扫描 @Component）
* Spring **知道**这个对象需要依赖谁（@Autowired）
* Spring **负责实例化和组装对象图**

**你只负责写业务，框架负责铺底层。**

> **BeanFactory = 管对象的仓库**
> **ApplicationContext = 多加了一堆扩展服务（事件、国际化、AOP集成...）的 BeanFactory**

---

## ② 为什么 AOP 必须用“代理”实现？

设想你想给所有方法 **加日志 / 权限检查 / 事务**。

### ❌ 如果不用代理

你只能在每个业务方法里重复写：

```java
log();
try{
    doBusiness();
} finally{
    log();
}
```

**重复代码 = 难维护 = 臭。**

### ✅ 用代理的哲学

**不动业务代码，在外面套一层。**

```
真正对象：UserService
包装层：代理对象 UserServiceProxy

Controller 调用 → 调的是代理 → 代理内部再调真实方法
```

伪图：

```
你以为你在调 UserService
其实你调的是 Proxy(UserService)
```

**这就是 AOP 的本质：用代理把横切逻辑“织进去”。**

---

## ③ 为什么 MVC 要有 DispatcherServlet + HandlerMapping？

因为 HTTP 请求是这样的：

```
GET /user/profile?id=1
```

你要做这些事情：

```
解析URL → 找到对应Controller方法 → 准备参数 → 调用方法 → 把结果转成 HTTP Response
```

如果没有 MVC，你要写下面这种一大坨 if-else：

```java
if(path.equals("/user/profile")){
    userController.profile(id);
}
```

### MVC 的作用是“解耦 URL → 方法”

* `@GetMapping("/user/profile")` **只是声明**
* `HandlerMapping` **负责登记 URL 对应哪个方法**
* `DispatcherServlet` **负责根据 URL 找到方法并调用**

**它是一个智能路由器。**

---

| 组件                                     | 它解决的问题           | 为什么必须这样做                    |
| -------------------------------------- | ---------------- | --------------------------- |
| `BeanFactory` / `ApplicationContext`   | 对象如何创建、依赖如何自动注入  | 统一管理对象生命周期，消灭自己 new         |
| `ProxyFactory` / `Interceptor`         | 横切逻辑如何统一         | 不污染业务代码，逻辑可插拔               |
| `DispatcherServlet` + `HandlerMapping` | HTTP 请求如何路由到业务方法 | 解耦 URL 与控制器方法，减少 if/else 地狱 |

# 基础篇：IOC

### 最简单的bean容器

代码分支：simple-bean-container

定义一个简单的bean容器BeanFactory，内部包含一个map用以保存bean，只有注册bean和获取bean两个方法

### BeanDefinition和BeanDefinitionRegistry

代码分支：bean-definition-and-bean-definition-registry
