package moku.concurrency.miaosha.controller;

import moku.concurrency.miaosha.queue.Sender;
import moku.concurrency.miaosha.service.IRedPackageService;
import moku.concurrency.miaosha.thread.MiaoshaThread;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/redPackage")
public class RedPackageController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RedPackageController.class);

    @Autowired
    private IRedPackageService redPackageService;

    @Autowired
    private Sender sender;

    @RequestMapping("/index")
    public String index(){
        return "red-package";
    }

    @RequestMapping("/testMiaosha")
    @ResponseBody
    public String testMiaosha(){
        MiaoshaThread miaoshaThread = new MiaoshaThread(redPackageService);
        for(int i=0;i<50;i++){
            Thread thread = new Thread(miaoshaThread,"线程"+i);
            thread.start();
        }
        return "OK";
    }

    @RequestMapping("/")
    @ResponseBody
    public List getAll(){
        List list = new ArrayList();
        list = redPackageService.getAll();
        return list;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map add(String userName, int amount){
        Map result = new HashMap();
        String res = "success";
        Map params = new HashMap();
        params.put("userName",userName);
        params.put("amount",amount);
        try {
            sender.send(Sender.DIRECT_EXCHANGE_MIAOSHA_NAME,Sender.DIRECT_EXCHANGE_MIAOSHA_ROUTING_KEY,params);
        } catch (Exception e) {
            res = "failure";
            logger.error(e.getMessage());
        }
        result.put("result",res);
        return result;
    }
}
