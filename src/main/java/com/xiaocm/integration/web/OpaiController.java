package com.xiaocm.integration.web;

import com.xiaocm.integration.config.DingConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiUserListsimpleRequest;
import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;

@RestController
@RequestMapping("/opai")
public class OpaiController {

    @Autowired
    private DingConfigProperties dingConfigProperties;


    @GetMapping("/access-token")
    public Object accessToken() throws ApiException {
        return getAccessToken();
    }


    @GetMapping("/user-info")
    public Object userInfo() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/listsimple");
        OapiUserListsimpleRequest req = new OapiUserListsimpleRequest();
        req.setDeptId(1L);
        req.setCursor(0L);
        req.setSize(10L);
        req.setOrderField("modify_desc");
        req.setContainAccessLimit(false);
        req.setLanguage("zh_CN");
        return client.execute(req, getAccessToken().getAccessToken());
    }

    /**
     * 获取部门列表
     * @return
     * @throws ApiException
     */
    @GetMapping("/dept-list")
    public Object deptList() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub");
        OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
        req.setDeptId(1L);
        req.setLanguage("zh_CN");
        return client.execute(req, getAccessToken().getAccessToken());
    }



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
