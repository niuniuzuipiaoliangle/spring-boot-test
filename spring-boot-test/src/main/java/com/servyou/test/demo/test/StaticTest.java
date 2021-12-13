package com.servyou.test.demo.test;


public class StaticTest
{
    static class  A{
        static  int  a=1; //类变量
        static B b;
        String name;
        int  id;
        //构造代码块
        {
            id=11;
            System.out.println( "这是父类的构造代码块id:" +name);
        }
        //静态代码块
        static {
            a=10;
            System.out.println( "这是父类的静态代码块" );
        }

        A(){
            System.out.println( "这是父类的无参构造函数" );
        }
        A(String name){
            System.out.println( "这是父类的name" +name);
        }
        public static void setA(int a){
            A.a=a;
        }
    }
    static class  B extends StaticTest.A {                          // 父类静态代码块  子类静态代码块  父类构造代码块  父类无参构造函数
        String name;
        static  int  b;
        static {
            b=12;
            System.out.println( "这是子类的静态代码块" +b);
            a=100;
        }
        //构造代码块
        {
            b=13;
            System.out.println( "这是子类的构造代码块id:" +id);
        }
        B(String name) {
            super(name);
            this .name = name;
            System.out.println( "这是子类的name:" +name);
        }
        B(){
            super();
            System.out.println("这是子类无参构造");
        }
    }
    public static class  Test666 {
        public  static  void  main(String[] args) {
            StaticTest.B aa= new StaticTest.B( );
            System.out.println("---------------------------------");
            StaticTest.B bb= new StaticTest.B("GG" );

            System.out.println(StaticTest.B.a);
        }
    }


    /*
    * 总结：
    *       1.静态代码
    *           1.1 在类初始化阶段被初始化
    *           1.2 顺序，父类静态代码块 -> 子类静态代码块
    *       2.非静态代码
    *           2.1 在使用阶段使用，即在实例化一个类的时候被初始化
    *
    *       3.常量final一旦被赋值，不能再次被赋值
    *
    *
    * */
}
