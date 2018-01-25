package com.lorne.tx.service.impl;


import com.lorne.tx.Constants;
import com.lorne.tx.config.ConfigReader;
import com.lorne.tx.service.DiscoveryService;
import com.lorne.tx.service.TxManagerService;
import com.lorne.tx.service.TxService;
import com.lorne.tx.service.model.TxServer;
import com.lorne.tx.service.model.TxState;
import com.lorne.tx.utils.SocketManager;
import com.lorne.tx.utils.SocketUtils;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.eureka.EurekaServerContextHolder;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author  by lorne on 2017/7/1.
 */
@Service
public class TxServiceImpl implements TxService {

    @Autowired
    private TxManagerService managerService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryService discoveryService;
    @Autowired
    private ConfigReader configReader;

    @Override
    public TxServer getServer() {
        List<String> urls= getServices();
        List<TxState> states = new ArrayList<>();
        for(String url:urls){
            TxState state = restTemplate.getForObject(url+"/tx/manager/state",TxState.class);
            states.add(state);
        }
        if(states.size()<=1) {
            TxState state = getState();
            if (state.getMaxConnection() > state.getNowConnection()) {
                return TxServer.format(state);
            } else {
                return null;
            }
        }else{
            //找默认数据
            TxState state = getDefault(states,0);
            if (state == null) {
                //没有满足的默认数据
                return null;
            }
            int minNowConnection = state.getNowConnection();
            for (TxState s : states) {
                if (s.getMaxConnection() > s.getNowConnection()) {
                    if (s.getNowConnection() < minNowConnection) {
                        state = s;
                    }
                }
            }
            return TxServer.format(state);
        }
    }

    private TxState getDefault(List<TxState> states, int index) {
        TxState state = states.get(index);
        if (state.getMaxConnection() == state.getNowConnection()) {
            index++;
            if (states.size() - 1 >= index) {
                return getDefault(states, index);
            } else {
                return null;
            }
        } else {
            return state;
        }
    }


    @Override
    public TxState getState() {
        TxState state = new TxState();
        String ipAddress = EurekaServerContextHolder.getInstance().getServerContext().getApplicationInfoManager().getEurekaInstanceConfig().getIpAddress();
        if(!isIp(ipAddress)){
            ipAddress = "127.0.0.1";
        }
        state.setIp(ipAddress);
        state.setPort(Constants.socketPort);
        state.setMaxConnection(SocketManager.getInstance().getMaxConnection());
        state.setNowConnection(SocketManager.getInstance().getNowConnection());
        state.setRedisSaveMaxTime(configReader.getRedisSaveMaxTime());
        state.setTransactionNettyDelayTime(configReader.getTransactionNettyDelayTime());
        state.setTransactionNettyHeartTime(configReader.getTransactionNettyHeartTime());
        state.setNotifyUrl(configReader.getCompensateNotifyUrl());
        state.setCompensate(configReader.isCompensateAuto());
        state.setCompensateTryTime(configReader.getCompensateTryTime());
        state.setAutoCompensateLimit(configReader.getAutoCompensateLimit());
        state.setSlbList(getServices());
        return state;
    }

    public static boolean isIp(String ipAddress) {
        String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }




    private List<String> getServices(){
        List<String> urls = new ArrayList<>();
        List<InstanceInfo> instanceInfos =discoveryService.getConfigServiceInstances();
        for (InstanceInfo instanceInfo : instanceInfos) {
            String url = instanceInfo.getHomePageUrl();
            String address = instanceInfo.getIPAddr();
            if (isIp(address)) {
                urls.add(url);
            }else{
                url = url.replace(address,"127.0.0.1");
                urls.add(url);
            }
        }
        return urls;
    }


    @Override
    public boolean checkClearGroup(String groupId, String taskId, int isGroup) {
        return managerService.checkClearGroup(groupId,taskId,isGroup);
    }

    @Override
    public int checkGroup(String groupId, String taskId) {
        return managerService.checkTransactionGroup(groupId, taskId);
    }

    @Override
    public boolean sendMsg(String model,String msg) {
        Channel channel = SocketManager.getInstance().getChannelByModelName(model);
        if (channel != null &&channel.isActive()) {
            SocketUtils.sendMsg(channel,msg);
            return true;
        }
        return false;
    }
}
