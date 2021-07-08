1.串行GC: 年轻代使用Serial，老年代使用Serial old，只有一个gc线程，gc线程与用户线程串行执行，暂停时间长；升级版的年轻代GC是ParNew，
是多线程版本的serial收集器，多个gc线程并行回收，可以减少STW时长；
2.并行GC: 年轻代是Parallel Scavenge，老年代使用Parallel Old，使用多线程 + 标记整理算法，以提高系统吞吐量为目标；
3 CMS GC: 老年代的GC，可以搭配新生代的ParNew，可以与用户线程并发执行，STW时间短；
4 G1 GC: 基于region的设计，整体采用标记整理算法,记录维护各个region的回收价值，优先收集回收价值高的region,从而可以实现STW可控，和CMS类似可以
和用户线程并发执行。