package moku.concurrency.miaosha.service.impl;

import moku.concurrency.miaosha.dao.RedPackageMapper;
import moku.concurrency.miaosha.queue.Sender;
import moku.concurrency.miaosha.service.IRedPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RedPackageServiceImpl implements IRedPackageService {

    @Autowired
    private RedPackageMapper redPackageMapper;

    @Autowired
    private Sender sender;

    @Override
    public List<Map> getAll() {
        return redPackageMapper.getAll();
    }

    @Override
    public void add(Map params) throws Exception{
        sender.sendMiaosha(params);
//        redPackageMapper.add(params);
    }
}