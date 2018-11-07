package moku.concurrency.miaosha.controller;

import moku.concurrency.miaosha.queue.Sender;
import moku.concurrency.miaosha.service.IRedPackageService;
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
            sender.send(Sender.ROUTING_KEY_MIAOSHA_ADD,params);
        } catch (Exception e) {
            res = "failure";
            logger.error(e.getMessage());
        }
        result.put("result",res);
        return result;
    }
}
