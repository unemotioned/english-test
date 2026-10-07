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

    public void sortingMsg(String file) {
        System.out.println("Sorting " + file);
    }

    public void terminate() {
        System.out.println("Terminated");
    }
}
