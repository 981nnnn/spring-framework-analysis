

## 1. xml文件配置bean

​        我们先从`xml配置bean`开始，它是spring最早支持的方式。后来，随着`springboot`越来越受欢迎，该方法目前已经用得很少了，但我建议我们还是有必要了解一下。

### 1.1 构造器

如果你之前有在bean.xml文件中配置过bean的经历，那么对如下的配置肯定不会陌生：

```xml
<bean id="personService" class="com.sue.cache.service.test7.PersonService">
</bean>
```

这种方式是以前使用最多的方式，它默认使用了无参构造器创建bean。

当然我们还可以使用有参的构造器，通过`<constructor-arg>`标签来完成配置。

```xml
<bean id="personService" class="com.sue.cache.service.test7.PersonService">
   <constructor-arg index="0" value="susan"></constructor-arg>
   <constructor-arg index="1" ref="baseInfo"></constructor-arg>
</bean>
```

其中：

- `index`表示下标，从0开始。
- `value`表示常量值
- `ref`表示引用另一个bean

### 1.2 setter方法

除此之外，spring还提供了另外一种思路：通过setter方法设置bean所需参数，这种方式耦合性相对较低，比有参构造器使用更为广泛。

先定义Person实体：

```java
@Data
public class Person {
    private String name;
    private int age;
}
```

它里面包含：成员变量name和age，getter/setter方法。

然后在bean.xml文件中配置bean时，加上`<property>`标签设置bean所需参数。

```xml
<bean id="person" class="com.sue.cache.service.test7.Person">
   <property name="name" value="susan"></constructor-arg>
   <property name="age" value="18"></constructor-arg>
</bean>
```

### 1.3 静态工厂

这种方式的关键是需要定义一个工厂类，它里面包含一个创建bean的静态方法。例如：

```java
public class SusanBeanFactory {
    public static Person createPerson(String name, int age) {
        return new Person(name, age);
    }
}
```

接下来定义Person类如下：

```java
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    private String name;
    private int age;
}
```

它里面包含：成员变量name和age，getter/setter方法，无参构造器和全参构造器。

然后在bean.xml文件中配置bean时，通过`factory-method`参数指定静态工厂方法，同时通过`<constructor-arg>`设置相关参数。

```xml
<bean class="com.sue.cache.service.test7.SusanBeanFactory" factory-method="createPerson">
   <constructor-arg index="0" value="susan"></constructor-arg>
   <constructor-arg index="1" value="18"></constructor-arg>
</bean>
```

### 1.4 实例工厂方法

这种方式也需要定义一个工厂类，但里面包含非静态的创建bean的方法。

```java
public class SusanBeanFactory {
    public Person createPerson(String name, int age) {
        return new Person(name, age);
    }
}
```

Person类跟上面一样，就不多说了。

然后bean.xml文件中配置bean时，需要先配置工厂bean。然后在配置实例bean时，通过`factory-bean`参数指定该工厂bean的引用。

```xml
<bean id="susanBeanFactory" class="com.sue.cache.service.test7.SusanBeanFactory">
</bean>
<!-- 非静态工厂方法 -->
<bean factory-bean="susanBeanFactory" factory-method="createPerson">
   <constructor-arg index="0" value="susan"></constructor-arg>
   <constructor-arg index="1" value="18"></constructor-arg>
</bean>
```

### 1.5 FactoryBean

不知道大家有没有发现，上面的实例工厂方法每次都需要创建一个工厂类，不方面统一管理。

这时我们可以使用`FactoryBean`接口。

```java
public class UserFactoryBean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
```

在它的`getObject`方法中可以实现我们自己的逻辑创建对象，并且在`getObjectType`方法中我们可以定义对象的类型。

然后在bean.xml文件中配置bean时，只需像普通的bean一样配置即可。

```xml
<bean id="userFactoryBean" class="com.sue.async.service.UserFactoryBean">
</bean>
```

轻松搞定，so easy。

> 注意：getBean("userFactoryBean");获取的是getObject方法中返回的对象。而getBean("&userFactoryBean");获取的才是真正的UserFactoryBean对象。

我们通过上面五种方式，在bean.xml文件中把bean配置好之后，spring就会自动扫描和解析相应的标签，并且帮我们创建和实例化bean，然后放入spring容器中。

虽说基于xml文件的方式配置bean，简单而且非常灵活，比较适合一些小项目。但如果遇到比较复杂的项目，则需要配置大量的bean，而且bean之间的关系错综复杂，这样久而久之会导致xml文件迅速膨胀，非常不利于bean的管理。



## 2. Component注解

为了解决bean太多时，xml文件过大，从而导致膨胀不好维护的问题。在spring2.5中开始支持：`@Component`、`@Repository`、`@Service`、`@Controller`等注解定义bean。

如果你有看过这些注解的源码的话，就会惊奇得发现：其实后三种注解也是`@Component`。

`@Repository`注解

```java
package org.springframework.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * Indicates that an annotated class is a "Repository", originally defined by
 * Domain-Driven Design (Evans, 2003) as "a mechanism for encapsulating storage,
 * retrieval, and search behavior which emulates a collection of objects".
 *
 * <p>Teams implementing traditional Java EE patterns such as "Data Access Object"
 * may also apply this stereotype to DAO classes, though care should be taken to
 * understand the distinction between Data Access Object and DDD-style repositories
 * before doing so. This annotation is a general-purpose stereotype and individual teams
 * may narrow their semantics and use as appropriate.
 *
 * <p>A class thus annotated is eligible for Spring
 * {@link org.springframework.dao.DataAccessException DataAccessException} translation
 * when used in conjunction with a {@link
 * org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
 * PersistenceExceptionTranslationPostProcessor}. The annotated class is also clarified as
 * to its role in the overall application architecture for the purpose of tooling,
 * aspects, etc.
 *
 * <p>As of Spring 2.5, this annotation also serves as a specialization of
 * {@link Component @Component}, allowing for implementation classes to be autodetected
 * through classpath scanning.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 2.0
 * @see Component
 * @see Service
 * @see org.springframework.dao.DataAccessException
 * @see org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Repository {

   /**
    * The value may indicate a suggestion for a logical component name,
    * to be turned into a Spring bean in case of an autodetected component.
    * @return the suggested component name, if any (or empty String otherwise)
    */
   @AliasFor(annotation = Component.class)
   String value() default "";

}
```

`@Service`注解

```java
package org.springframework.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * Indicates that an annotated class is a "Service", originally defined by Domain-Driven
 * Design (Evans, 2003) as "an operation offered as an interface that stands alone in the
 * model, with no encapsulated state."
 *
 * <p>May also indicate that a class is a "Business Service Facade" (in the Core J2EE
 * patterns sense), or something similar. This annotation is a general-purpose stereotype
 * and individual teams may narrow their semantics and use as appropriate.
 *
 * <p>This annotation serves as a specialization of {@link Component @Component},
 * allowing for implementation classes to be autodetected through classpath scanning.
 *
 * @author Juergen Hoeller
 * @since 2.5
 * @see Component
 * @see Repository
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {

   /**
    * The value may indicate a suggestion for a logical component name,
    * to be turned into a Spring bean in case of an autodetected component.
    * @return the suggested component name, if any (or empty String otherwise)
    */
   @AliasFor(annotation = Component.class)
   String value() default "";

}
```

`@Controller`注解

```java
package org.springframework.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * Indicates that an annotated class is a "Controller" (e.g. a web controller).
 *
 * <p>This annotation serves as a specialization of {@link Component @Component},
 * allowing for implementation classes to be autodetected through classpath scanning.
 * It is typically used in combination with annotated handler methods based on the
 * {@link org.springframework.web.bind.annotation.RequestMapping} annotation.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @since 2.5
 * @see Component
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see org.springframework.context.annotation.ClassPathBeanDefinitionScanner
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {

   /**
    * The value may indicate a suggestion for a logical component name,
    * to be turned into a Spring bean in case of an autodetected component.
    * @return the suggested component name, if any (or empty String otherwise)
    */
   @AliasFor(annotation = Component.class)
   String value() default "";

}
```

`@Component`系列注解的出现，给我们带来了极大的便利。我们不需要像以前那样在bean.xml文件中配置bean了，现在只用在类上加Component、Repository、Service、Controller，这四种注解中的任意一种，就能轻松完成bean的定义。

```java
@Service
public class PersonService {
    public String get() {
        return "data";
    }
}
```

其实，这四种注解在功能上没有特别的区别，不过在业界有个不成文的约定：

- Controller 一般用在控制层
- Service 一般用在业务层
- Repository 一般用在数据层
- Component 一般用在公共组件上

太棒了，简直一下子解放了我们的双手。

不过，需要特别注意的是，通过这种`@Component`扫描注解的方式定义bean的前提是：**需要先配置扫描路径**。

**【SpringBoot中@SpringBootApplication注解默认扫描所在子包的全部路径】**

目前常用的配置扫描路径的方式如下：

1. 在applicationContext.xml文件中使用`<context:component-scan>`标签，这种方式在很早的SSM中常使用。例如：

```xml
<context:component-scan base-package="com.gqzdev.beans" />
```

1. 在springboot的启动类上加上`@ComponentScan`注解，例如：

```java
@ComponentScan(basePackages = "com.gqzdev.beans")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
    }
}
```

1. 直接在`@SpringBootApplication`注解上加，它支持ComponentScan功能：

```java
@SpringBootApplication(scanBasePackages = "com.gqzdev.beans")
public class Application {
    
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
    }
}
```

当然，如果你需要扫描的类跟springboot的入口类，在同一级或者子级的包下面，无需指定`scanBasePackages`参数，**spring默认会从入口类的同一级或者子级的包去找。**

```java
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
    }
}
```

此外，除了上述四种`@Component`注解之外，springboot还增加了`@RestController`注解，它是一种特殊的`@Controller`注解，所以也是`@Component`注解。

​	**`@RestController`还支持`@ResponseBody`注解的功能，即将接口响应数据的格式自动转换成json。**

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface RestController {

   /**
    * The value may indicate a suggestion for a logical component name,
    * to be turned into a Spring bean in case of an autodetected component.
    * @return the suggested component name, if any (or empty String otherwise)
    * @since 4.0.1
    */
   @AliasFor(annotation = Controller.class)
   String value() default "";

}
```

`@Component`系列注解已经让我们爱不释手了，它目前是我们日常工作中最多的定义bean的方式。



## 3. JavaConfig

`@Component`系列注解虽说使用起来非常方便，但是bean的创建过程完全交给spring容器来完成，我们没办法自己控制。

spring从3.0以后，开始支持**`JavaConfig`的方式定义bean**。它可以看做spring的配置文件，但并非真正的配置文件，我们需要通过编码java代码的方式创建bean。例如：

```java
@Configuration
public class MyConfiguration {

    @Bean
    public Person person() {
        return new Person();
    }
}
```

**在JavaConfig类上加`@Configuration`注解，相当于配置了`<beans>`标签。而在方法上加`@Bean`注解，相当于配置了`<bean>`标签。**

此外，springboot还引入了一系列的`@Conditional`注解，用来控制bean的创建。

```java
@Configuration
public class MyConfiguration {

    @ConditionalOnClass(Country.class)
    @Bean
    public Person person() {
        return new Person();
    }
}
```

`@ConditionalOnClass`注解的功能是当项目中存在Country类时，才实例化Person类。换句话说就是，如果项目中不存在Country类，就不实例化Person类。

这个功能非常有用，相当于一个开关控制着Person类，只有满足一定条件才能实例化。

spring中使用比较多的Conditional还有：

- ConditionalOnBean
- ConditionalOnProperty
- ConditionalOnMissingClass
- ConditionalOnMissingBean
- ConditionalOnWebApplication

如果你对这些功能比较感兴趣，可以看看《[spring中那些让你爱不释手的代码技巧（续集）](http://mp.weixin.qq.com/s?__biz=MzUxODkzNTQ3Nw==&mid=2247485964&idx=1&sn=6b228b315461498aa3e2ac241341f72a&chksm=f9800cd6cef785c07022577cec282c3f990e35d80ee5cb07219681ed4db71cddabb9fcbeabd2&scene=21#wechat_redirect)》，这是我之前写的一篇文章，里面做了更详细的介绍。

> Conditional家族
>
> - ConditionalOnBean
> - ConditionalOnClass
> - ConditionalOnCloudPlatform
> - ConditionalOnExpression
> - ConditionalOnJava
> - ConditionalOnJndi
> - ConditionalOnMissingBean
> - ConditionalOnMissingClass
> - ConditionalOnNotWebApplication
> - ConditionalOnProperty
> - ConditionalOnResource
> - ConditionalOnSingleCandidate
> - ConditionalOnWebApplication

nice，有了这些功能，我们终于可以告别麻烦的xml时代了。



## 4. Import注解

通过前面介绍的**@Configuration和@Bean相结合的方式，我们可以通过代码定义bean**。但这种方式有一定的局限性，它只能创建该类中定义的bean实例，不能创建其他类的bean实例，如果我们想创建其他类的bean实例该怎么办呢？

这时可以使用`@Import`注解导入。SpringBoot中自动配置的基础。



### 4.1 普通类(实例化普通bean实例)

spring4.2之后`@Import`注解可以**实例化普通类的bean实例**。例如：

先定义了Role类：

```java
@Data
public class Role {
    private Long id;
    private String name;
}
```

接下来使用@Import注解导入Role类：

```java
@Import(Role.class)
@Configuration
public class MyConfig {
    
}
```

然后在调用的地方通过`@Autowired`注解注入所需的bean。

```java
@RequestMapping("/")
@RestController
public class TestController {

    @Autowired
    private Role role;

    @GetMapping("/test")
    public String test() {
        System.out.println(role);
        return "test";
    }
}
```

聪明的你可能会发现，我没有在任何地方定义过Role的bean，但spring却能自动创建该类的bean实例，这是为什么呢？

这也许正是`@Import`注解的强大之处。

此时，有些朋友可能会问：`@Import`注解能定义单个类的bean，但如果有多个类需要定义bean该怎么办呢？

恭喜你，这是个好问题，因为`@Import`注解也支持。

```java
/**
 * 使用Import注解导入其他bean
 * @author gqzdev
 * @date 2021/07/10 10:19
 **/

@Import({Role.class, User.class})
@Configuration
public class MyConfig {
}
```

甚至，如果你想偷懒，不想写这种`MyConfig`类，springboot也欢迎。

```java
@Import({Role.class, User.class})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
    }
}
```

可以将@Import加到springboot的启动类上。

这样也能生效？

springboot的启动类一般都会加@SpringBootApplication注解，该注解上加了@SpringBootConfiguration注解。

而@SpringBootConfiguration中包含了@Configuration注解。

所以，springboot启动类本身带有@Configuration注解的功能。



### 4.2 Configuration类

上面介绍了@Import注解导入普通类的方法，它同时也支持导入Configuration类。

先定义一个Configuration类：

```java
@Configuration
public class MyConfig2 {

    @Bean
    public User user() {
        return  new User();
    }

    @Bean
    public Role role() {
        return new Role();
    }
}
```

然后在另外一个Configuration类中引入前面的Configuration类：

```java
@Import({MyConfig2.class})
@Configuration
public class MyConfig {
}
```

这种方式，如果MyConfig2类已经在spring指定的扫描目录或者子目录下，则MyConfig类会显得有点多余。因为MyConfig2类本身就是一个配置类，它里面就能定义bean。

但如果MyConfig2类不在指定的spring扫描目录或者子目录下，则通过MyConfig类的导入功能，也能把MyConfig2类识别成配置类。这就有点厉害了喔。

**其实下面还有更高端的玩法**。

swagger作为一个优秀的文档生成框架，在spring项目中越来越受欢迎。接下来，我们以swagger2为例，介绍一下它是如何导入相关类的。

众所周知，我们引入swagger相关jar包之后，只需要在springboot的启动类上加上`@EnableSwagger2`注解，就能开启swagger的功能。

其中@EnableSwagger2注解中导入了Swagger2DocumentationConfiguration类。

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhhAz5vXXTffj5N2LBN31fLK1uNHxWMia1sGWdNcPdXdwRZzrZ8XAGqtMw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

该类是一个Configuration类，它又导入了另外两个类：

- SpringfoxWebMvcConfiguration
- SwaggerCommonConfiguration

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhhnu9on6HGzoto4C6cLG0YJvgWs3vYkpkVObdEOocM1sibt5sOF7IBNpw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

SpringfoxWebMvcConfiguration类又会导入新的Configuration类【ModelsConfiguration.class】，并且通过@ComponentScan注解扫描了一些其他的路径。

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhhPwxbiaR9pVhr64A1j6a6Hj7do1ibcOtvtAexH25TzHd2NciapWggszrNQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

SwaggerCommonConfiguration同样也通过@ComponentScan注解扫描了一些额外的路径。

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhhLKLJeE0AZsf7w26iaST1zpPotp3WphfYy9LWRR9QRx2Brbohzjc49eA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

如此一来，我们通过一个简单的`@EnableSwagger2`注解，就能轻松的导入swagger所需的一系列bean，并且拥有swagger的功能。

还有什么好说的，狂起点赞，简直完美。

### 4.3 ImportSelector

上面提到的Configuration类，它的功能非常强大。但怎么说呢，它不太适合加复杂的判断条件，根据某些条件定义这些bean，根据另外的条件定义那些bean。

那么，这种需求该怎么实现呢？

这时就可以使用`ImportSelector`接口了。

首先定义一个类实现`ImportSelector`接口：

```java
//定义一个类，实现ImportSelector接口，
public class DataImportSelector implements ImportSelector {
    
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.gqzdev.async.service.User", "com.gqzdev.async.service.Role"};
    }
}
```

重写`selectImports`方法，在该方法中指定需要定义bean的类名，注意要包含完整路径，而非相对路径。

然后在MyConfig类上@Import导入这个类即可：

```java
@Import({DataImportSelector.class})
@Configuration
public class MyConfig {
    
}
```

朋友们是不是又发现了一个新大陆？

不过，这个注解还有更牛逼的用途。

@EnableAutoConfiguration注解中导入了AutoConfigurationImportSelector类，并且里面包含系统参数名称：`spring.boot.enableautoconfiguration`。

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {

   /**
    * Environment property that can be used to override when auto-configuration is
    * enabled.
    */
   String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

   /**
    * Exclude specific auto-configuration classes such that they will never be applied.
    * @return the classes to exclude
    */
   Class<?>[] exclude() default {};

   /**
    * Exclude specific auto-configuration class names such that they will never be
    * applied.
    * @return the class names to exclude
    * @since 1.3.0
    */
   String[] excludeName() default {};

}
```

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhhvqCpMJaEmG5FuXK0HoWy6jKzUllyoEvNaSXWEC7BWicoP3teavQWOJA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

AutoConfigurationImportSelector类实现了`ImportSelector`接口。

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhhA2nxyKfJTC3MB0ItHKO8Y18KX7RuMjoViafoF2klvLGMgtFSI6D0rmg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

并且重写了`selectImports`方法，该方法会根据某些注解去找所有需要创建bean的类名，然后返回这些类名。其中在查找这些类名之前，先调用isEnabled方法，判断是否需要继续查找。

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhhfiaLHkkSePWVmUlVL1pF4obLDPUqQBQqUDyWpBvSwgJgy0UDWtfbZhg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

该方法根据ENABLED_OVERRIDE_PROPERTY的值来作为判断条件。

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhhib8XibXCzAgqaTkJyVQkt5icdmqqBLI6Vic12aLDZOGjbM9muOuNZVgG9g/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

而这个值就是`spring.boot.enableautoconfiguration`。

换句话说，这里能根据系统参数控制bean是否需要被实例化，优秀。

我个人认为实现ImportSelector接口的好处主要有以下两点：

1. 把某个功能的相关类，可以放到一起，方面管理和维护。
2. 重写selectImports方法时，能够根据条件判断某些类是否需要被实例化，或者某个条件实例化这些bean，其他的条件实例化那些bean等。我们能够非常灵活的定制化bean的实例化。



### 4.4 ImportBeanDefinitionRegistrar

我们通过上面的这种方式，确实能够非常灵活的自定义bean。

但它的自定义能力，还是有限的，**它没法自定义bean的名称和作用域等属性**。

有需求，就有解决方案。

接下来，我们一起看看`ImportBeanDefinitionRegistrar`接口的神奇之处。

```java
package org.springframework.context.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Interface to be implemented by types that register additional bean definitions when
 * processing @{@link Configuration} classes. Useful when operating at the bean definition
 * level (as opposed to {@code @Bean} method/instance level) is desired or necessary.
 *
 * <p>Along with {@code @Configuration} and {@link ImportSelector}, classes of this type
 * may be provided to the @{@link Import} annotation (or may also be returned from an
 * {@code ImportSelector}).
 *
 * <p>An {@link ImportBeanDefinitionRegistrar} may implement any of the following
 * {@link org.springframework.beans.factory.Aware Aware} interfaces, and their respective
 * methods will be called prior to {@link #registerBeanDefinitions}:
 * <ul>
 * <li>{@link org.springframework.context.EnvironmentAware EnvironmentAware}</li>
 * <li>{@link org.springframework.beans.factory.BeanFactoryAware BeanFactoryAware}
 * <li>{@link org.springframework.beans.factory.BeanClassLoaderAware BeanClassLoaderAware}
 * <li>{@link org.springframework.context.ResourceLoaderAware ResourceLoaderAware}
 * </ul>
 *
 * <p>See implementations and associated unit tests for usage examples.
 *
 * @author Chris Beams
 * @since 3.1
 * @see Import
 * @see ImportSelector
 * @see Configuration
 */
public interface ImportBeanDefinitionRegistrar {

   /**
    * Register bean definitions as necessary based on the given annotation metadata of
    * the importing {@code @Configuration} class.
    * <p>Note that {@link BeanDefinitionRegistryPostProcessor} types may <em>not</em> be
    * registered here, due to lifecycle constraints related to {@code @Configuration}
    * class processing.
    * @param importingClassMetadata annotation metadata of the importing class
    * @param registry current bean definition registry
    */
   void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry);

}
```

先定义CustomImportSelector类实现ImportBeanDefinitionRegistrar接口：

```java
public class CustomImportSelector implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition roleBeanDefinition = new RootBeanDefinition(Role.class);
        registry.registerBeanDefinition("role", roleBeanDefinition);

        RootBeanDefinition userBeanDefinition = new RootBeanDefinition(User.class);
        userBeanDefinition.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
        registry.registerBeanDefinition("user", userBeanDefinition);
    }
}
```

重写`registerBeanDefinitions`方法，在该方法中我们可以获取`BeanDefinitionRegistry`对象，通过它去注册bean。不过在注册bean之前，我们先要创建BeanDefinition对象，它里面可以自定义bean的名称、作用域等很多参数。

然后在MyConfig类上导入上面的类：

```java
@Import({CustomImportSelector.class})
@Configuration
public class MyConfig {
    
}
```

我们所熟悉的fegin功能，就是使用ImportBeanDefinitionRegistrar接口实现的：

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhh4xGb6icoZcZicobzBKl4wibJB1D62XMMU7p3eY7JsxsRV9zdxAC4reu1A/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

具体细节就不多说了，有兴趣的朋友可以加我微信找我私聊。





## 5. PostProcessor

除此之外，spring还提供了专门注册bean的接口：`BeanDefinitionRegistryPostProcessor`。

该接口的方法postProcessBeanDefinitionRegistry上有这样一段描述：

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhhcsRfRiaqYZGTsaqdsV4l0fGakUlnIcG4JyP8rV3aibForMI2pEN2PqEQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

修改应用程序上下文的内部bean定义注册表标准初始化。所有常规bean定义都将被加载，但是还没有bean被实例化。这允许进一步添加在下一个后处理阶段开始之前定义bean。

如果用这个接口来定义bean，我们要做的事情就变得非常简单了。只需定义一个类实现`BeanDefinitionRegistryPostProcessor`接口。

```java
@Component
public class MyRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        RootBeanDefinition roleBeanDefinition = new RootBeanDefinition(Role.class);
        registry.registerBeanDefinition("role", roleBeanDefinition);

        RootBeanDefinition userBeanDefinition = new RootBeanDefinition(User.class);
        userBeanDefinition.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
        registry.registerBeanDefinition("user", userBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}
```

重写`postProcessBeanDefinitionRegistry`方法，在该方法中能够获取`BeanDefinitionRegistry`对象，它负责bean的注册工作。

不过细心的朋友可能会发现，里面还多了一个`postProcessBeanFactory`方法，没有做任何实现。

这个方法其实是它的父接口：`BeanFactoryPostProcessor`里的方法。

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhhJE1RAAK7g0wc7TZ6fucUiaaeIoEKohtpPoeGzwLrVE9dXPXb4mNYVOQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

在应用程序上下文的标准bean工厂之后修改其内部bean工厂初始化。所有bean定义都已加载，但没有bean将被实例化。这允许重写或添加属性甚至可以初始化bean。

```java
@Component
public class MyPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory registry = (DefaultListableBeanFactory)beanFactory;
        RootBeanDefinition roleBeanDefinition = new RootBeanDefinition(Role.class);
        registry.registerBeanDefinition("role", roleBeanDefinition);

        RootBeanDefinition userBeanDefinition = new RootBeanDefinition(User.class);
        userBeanDefinition.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
        registry.registerBeanDefinition("user", userBeanDefinition);
    }
}
```

既然这两个接口都能注册bean，那么他们有什么区别？

- BeanDefinitionRegistryPostProcessor 更侧重于bean的注册
- BeanFactoryPostProcessor 更侧重于**对已经注册的bean的属性进行修改**，虽然也可以注册bean。

此时，有些朋友可能会问：既然拿到BeanDefinitionRegistry对象就能注册bean，那通过BeanFactoryAware的方式是不是也能注册bean呢？

从下面这张图能够看出DefaultListableBeanFactory就实现了BeanDefinitionRegistry接口。

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhhD2SFCiakicKE72xbqIb5Z0ibWcgc5tNkWdZQTFdnsRYURKic6Eia1AAuvSg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

这样一来，我们如果能够获取DefaultListableBeanFactory对象的实例，然后调用它的注册方法，不就可以注册bean了？

说时迟那时快，定义一个类实现`BeanFactoryAware`接口：

```java
@Component
public class BeanFactoryRegistry implements BeanFactoryAware {
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory registry = (DefaultListableBeanFactory) beanFactory;
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(User.class);
        registry.registerBeanDefinition("user", rootBeanDefinition);

        RootBeanDefinition userBeanDefinition = new RootBeanDefinition(User.class);
        userBeanDefinition.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
        registry.registerBeanDefinition("user", userBeanDefinition);
    }
}
```

重写`setBeanFactory`方法，在该方法中能够获取BeanFactory对象，它能够强制转换成DefaultListableBeanFactory对象，然后通过该对象的实例注册bean。

当你满怀喜悦的运行项目时，发现竟然报错了：

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhhduLuSZLdBUVUkrpWFSU5mcCEvF6DaIB6ic1kypNQ2eYKxyPPJnAkguA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

为什么会报错？

spring中bean的创建过程顺序大致如下：

![图片](https://mmbiz.qpic.cn/mmbiz_png/uL371281oDF3eclmVcRAt9Ribqd3SaGhh5vCcbXezk84CzoVKpNevjLFaT5mR6ZAwwcc5ViaBGPzhHxr7O3PibOuA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

`BeanFactoryAware`接口是在bean创建成功，并且完成依赖注入之后，在真正初始化之前才被调用的。在这个时候去注册bean意义不大，因为这个接口是给我们获取bean的，并不建议去注册bean，会引发很多问题。



> 此外，ApplicationContextRegistry和ApplicationListener接口也有类似的问题，我们可以用他们获取bean，但不建议用它们注册bean。



https://mp.weixin.qq.com/s/wLV02Y6xGaQe1t0N3YUfvg

