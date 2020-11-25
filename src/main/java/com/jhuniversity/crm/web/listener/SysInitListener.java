package com.jhuniversity.crm.web.listener;

import com.jhuniversity.crm.settings.domian.DicValue;
import com.jhuniversity.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Component
public class SysInitListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private DicService dicService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("数据字典执行开始");
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
        ServletContext application = webApplicationContext.getServletContext();
        dicService = (DicService) applicationContext.getBean("dicService");
        Map<String, List<DicValue>> map = dicService.getAll();
        //将map解析为上下文对象中保存的键值对
        Set<String> sets = map.keySet();
        for (String key : sets) {
            application.setAttribute(key, map.get(key));
        }
        System.out.println("数据字典结束");
    }
}
//@Component
//@WebListener
//public class SysInitListener implements ServletContextListener{
//
//
//    @Autowired
//    private static DicService dicService;
//
//    @Override
//    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        System.out.println("数据字典处理开始");
//        ServletContext application=servletContextEvent.getServletContext();
//                /*
//                跟业务层要7个list
//                    业务层应该式这样保存数据的
//                       map.put("appellationList",dvList1);
//                       map.put("clueStateList",dvList2);
//                       map.put("stageList",dvList3);
//                 */
//        Map<String,List<DicValue>>map =dicService.getAll();
//        //将map解析为上下文对象中保存的键值对
//        Set<String>sets=map.keySet();
//        for (String key:sets){
//            application.setAttribute(key,map.get(key));
//        }
//        System.out.println("数据字典结束");
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//
//    }
//}
