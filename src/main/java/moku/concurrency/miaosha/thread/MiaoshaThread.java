package moku.concurrency.miaosha.thread;

import moku.concurrency.miaosha.service.IRedPackageService;

public class MiaoshaThread implements Runnable {

    private IRedPackageService redPackageService;

    public MiaoshaThread(IRedPackageService redPackageService) {
        this.redPackageService = redPackageService;
    }

    @Override
    public void run() {
        redPackageService.miaosha();
    }
}
