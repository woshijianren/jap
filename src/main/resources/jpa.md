###### 疑问：第一个：为什么exteds CrudRepository后的delete是select语句

```
long aa = repository.deleteByName("南京办");
System.out.println(aa);
```

Hibernate: 
    select
        dept0_.dept_id as dept_id1_0_,
        dept0_.name as name2_0_ 
    from
        sys_dept dept0_ 
    where
        dept0_.name=?
1

###### 记住：@NoRepositoryBean是一定要加在中间的Repository上的，方法名也不可以随便乱变。

至于为什么可以写一个save()在中间层的Repository上，主要看官方文档的标题是“有选择地公开CRUD方法”，那不就可能是说和CrudRepository差不多吗？

###### 记住：设置了ID的GeneratedValue的（strategy的自增策略后，save主键是没用的），调用save保存的时候可以把id不写。

###### 记住：Sort和Pageable都有一个Sort.unsorted()和Pageable.unpaged()方法。定义排序表达式：

1. Sort sort = Sort.by("firstname").ascending()  .and(Sort.by("lastname").descending()); 

2.  TypedSort<Person> person = Sort.sort(Person.class); 
	TypedSort<Person> sort = person.by(Person::getFirstname).ascending()  .and(person.by(Person::getLastname).descending()); 
	
3. 第二种比第一种好的地方就是确保了类型安全，第一种什么类型都没有，就是直接的字段名写了上去。

   ###### 记住：只有返回类型为Slice的限制查询结果才能在结果之后进行分页。但是——官方文档上说的是分页和切割都可以，Pageable和Slice。

###### 记住：Streamable的返回结果可以用and合并

###### 记住：Stream并非对所有Spring Data模块都可以作为返回结果接收，并且使用Stream作为返回结果，最后需要调用close关闭。

###### 记住： @DomainEvents 可以在save()方法后执行被注解的方法，好像也可以是在save()执行成功前，具体还是看看官方文档吧。还有 @**AfterDomainEventPublication** 

###### 记住：findBy默认是IgnoreCase的

###### 记住：Dynamic Sort它所说的指向别名函数的有效表达式，是为函数取别名，而不是单单为改函数所用的字段取别名——如果Sort.by(LENGTH(xx))，那么这个LENGTH(XX)直接就是作为select语句的一个字段取别名yy，然后Sort.by(yy)。

###### 记住：参数绑定@Param只是因为参数名称和:xx不一致，如果参数也是xx，那么就不需要用@Param

###### 疑问：为什么我的Spel表达式使用不了

###### 记住：Modifying queries can only use void or int/Integer    除此之外只可以用void

###### 记住：@Transaction要放在repository接口的方法上，放在调用方是没有用的


