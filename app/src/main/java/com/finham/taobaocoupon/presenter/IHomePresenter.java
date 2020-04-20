package com.finham.taobaocoupon.presenter;

import com.finham.taobaocoupon.base.IBasePresenter;
import com.finham.taobaocoupon.view.IHomeCallback;

/**
 * User: Fin
 * Date: 2020/4/17
 * Time: 11:24
 */
//编写需要的接口，定义方法。
public interface IHomePresenter extends IBasePresenter<IHomeCallback> {
    /*获取商品分类*/
    void getCategories();

//    /*因为是异步的，你需要去网络访问去取数据，所以你需要等，在view包中创建接口通知UI更新*/
//    //注册UI回调
//    void registerViewCallback(IHomeCallback callback);
//    //取消UI回调
//    void unregisterCallback(IHomeCallback callback);

    /**
     * 子类拥有父类非 private 的属性、方法。
     * 子类可以拥有自己的属性和方法，即子类可以对父类进行扩展。
     * 子类可以用自己的方式实现父类的方法。
     * Java 的继承是单继承，但是可以多重继承，单继承就是一个子类只能继承一个父类，多重继承就是，
     * 例如 A 类继承 B 类，B 类继承 C 类，所以按照关系就是 C 类是 B 类的父类，B 类是 A 类的父类，这是 Java 继承区别于 C++ 继承的一个特性。
     * 提高了类之间的耦合性（继承的缺点，耦合度高就会造成代码之间的联系越紧密，代码独立性越差）。
     *
     * super关键字：我们可以通过super关键字来实现对父类成员的访问，用来引用当前对象的父类。
     * this关键字：指向自己的引用。
     * final 关键字声明类可以把类定义为不能继承的，即最终类；或者用于修饰方法，该方法不能被子类重写：
     *
     * 子类是不继承父类的构造器（构造方法或者构造函数）的，它只是调用（隐式或显式）。
     * 如果父类的构造器带有参数，则必须在子类的构造器中显式地通过 super 关键字调用父类的构造器并配以适当的参数列表。
     * 如果父类构造器没有参数，则在子类的构造器中不需要使用 super 关键字调用父类构造器，系统会自动调用父类的无参构造器。
     */
}
