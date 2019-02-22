package moku.concurrency.miaosha.service.impl;

import moku.concurrency.miaosha.dao.RedPackageMapper;
import moku.concurrency.miaosha.service.IDistributeLockService;
import moku.concurrency.miaosha.service.IRedPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RedPackageServiceImpl implements IRedPackageService {

    int n = 500;

    @Autowired
    private RedPackageMapper redPackageMapper;

    @Autowired
    private IDistributeLockService distributeLockService;

    @Override
    public List<Map> getAll() {
        return redPackageMapper.getAll();
    }

    @Override
    public void add(Map params) throws Exception {
        redPackageMapper.add(params);
    }

    @Override
    public void miaosha() {
        String identifier = "";
        identifier = distributeLockService.getLock("1234567890", 10);
        if (identifier != null) {
            System.out.println(Thread.currentThread().getName() + "获得了锁");
            System.out.println(--n);
//            distributeLockService.releaseLock("1234567890", identifier);
        }
    }


}
