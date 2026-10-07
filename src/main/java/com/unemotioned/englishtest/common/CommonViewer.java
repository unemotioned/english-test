package com.unemotioned.englishtest.common;

public class CommonViewer {
    public CommonViewer() {}

    public void promptCancel(String msg) {
        System.out.println("Canceling " + msg + "...");
    }

    public void holdIt(int milSec) {
        try {
            Thread.sleep(milSec);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
