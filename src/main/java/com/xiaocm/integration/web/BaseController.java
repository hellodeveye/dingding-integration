package com.xiaocm.integration.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import com.xiaocm.integration.config.DingConfigProperties;

public class BaseController {

    @Autowired
    private DingConfigProperties dingConfigProperties;

    public OapiGettokenResponse getAccessToken() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest req = new OapiGettokenRequest();
        req.setAppkey(dingConfigProperties.getClientId());
        req.setAppsecret(dingConfigProperties.getClientSecret());
        req.setHttpMethod("GET");
        OapiGettokenResponse rsp = client.execute(req);
        return rsp;
    }
}
