package org.example;

import java.util.concurrent.CountDownLatch;

public class Foo {
    private final CountDownLatch latch1 = new CountDownLatch(1);
    private final CountDownLatch latch2 = new CountDownLatch(1);

    public void first() {
        System.out.print("first");
        latch1.countDown();
    }

    public void second() {
        try {
            latch1.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print("second");
        latch2.countDown();
    }

    public void third() {
        try {
            latch2.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print("third");
    }


    public static void main(String[] args) {
        Foo foo = new Foo();

        new Thread(() -> foo.third()).start();
        new Thread(() -> foo.first()).start();
        new Thread(() -> foo.second()).start();
    }
}

