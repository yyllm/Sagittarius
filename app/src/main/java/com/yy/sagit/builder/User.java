package com.yy.sagit.builder;

/**
 * Created by Administrator on 2017/9/27.
 */

public class User {

//    User类的构造方法是私有的，调用者不能直接创建User对象。
//    User类的属性都是不可变的。所有的属性都添加了final修饰符，并且在构造方法中设置了值。并且，对外只提供getters方法。
//    Builder的内部类构造方法中只接收必传的参数，并且该必传的参数使用了final修饰符。

    private final String name;         //必选
    private final String cardID;       //必选
    private final int age;             //可选
    private final String address;      //可选
    private final String phone;        //可选

    private User(UserBuilder userBuilder){
        this.name=userBuilder.name;
        this.cardID=userBuilder.cardID;
        this.age=userBuilder.age;
        this.address=userBuilder.address;
        this.phone=userBuilder.phone;
    }

    public String getName() {
        return name;
    }

    public String getCardID() {
        return cardID;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public static class UserBuilder{
        private final String name;
        private final String cardID;
        private int age;
        private String address;
        private String phone;

        public UserBuilder(String name,String cardID){
            this.name=name;
            this.cardID=cardID;
        }

        public UserBuilder age(int age){
            this.age=age;
            return this;
        }

        public UserBuilder address(String address){
            this.address=address;
            return this;
        }

        public UserBuilder phone(String phone){
            this.phone=phone;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

}
