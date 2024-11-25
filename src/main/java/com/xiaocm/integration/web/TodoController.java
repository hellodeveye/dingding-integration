package com.xiaocm.integration.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taobao.api.ApiException;

import shade.com.alibaba.fastjson2.JSONObject;

@RestController
@RequestMapping("/todos")
public class TodoController extends BaseController {

    /**
     * POST /v1.0/todo/users/{unionId}/org/tasks/query HTTP/1.1
     * Host:api.dingtalk.com
     * x-acs-dingtalk-access-token:String
     * Content-Type:application/json
     * 
     * {
     * "nextToken" : "String",
     * "isDone" : Boolean
     * }
     */

    // 获取用户任务列表
    @PostMapping("/users/{unionId}/tasks/query")
    public Object queryTasks(@PathVariable("unionId") String unionId, @RequestBody Map<String, Object> request)
            throws ApiException {

        HttpClient client = HttpClient.newHttpClient();
        String url = "https://api.dingtalk.com/v1.0/todo/users/" + unionId + "/org/tasks/query";

        // Create the request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("nextToken", request.get("nextToken"));
        requestBody.put("isDone", request.get("isDone"));

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("x-acs-dingtalk-access-token", getAccessToken().getAccessToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        // Send the request and get the response
        HttpResponse<String> response;
        try {
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * POST /v1.0/todo/users/{unionId}/tasks?operatorId=String HTTP/1.1
     * Host:api.dingtalk.com
     * x-acs-dingtalk-access-token:String
     * Content-Type:application/json
     * 
     * {
     * "sourceId" : "String",
     * "subject" : "String",
     * "creatorId" : "String",
     * "description" : "String",
     * "dueTime" : Long,
     * "executorIds" : [ "String" ],
     * "participantIds" : [ "String" ],
     * "detailUrl" : {
     * "appUrl" : "String",
     * "pcUrl" : "String"
     * },
     * "isOnlyShowExecutor" : Boolean,
     * "priority" : Integer,
     * "notifyConfigs" : {
     * "dingNotify" : "String"
     * }
     * }
     */
    @PostMapping("/users/{unionId}/tasks")
    public ResponseEntity<String> createTask(@PathVariable String unionId, @RequestBody Map<String, Object> request)
            throws ApiException {
        String url = "https://api.dingtalk.com/v1.0/todo/users/" + unionId + "/tasks?operatorId="
                + request.get("creatorId");
        HttpClient client = HttpClient.newHttpClient();
        // Create the request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("sourceId", request.get("sourceId"));
        requestBody.put("subject", request.get("subject"));
        requestBody.put("creatorId", request.get("creatorId"));
        requestBody.put("description", request.get("description"));
        requestBody.put("dueTime", request.get("dueTime"));
        requestBody.put("executorIds", request.get("executorIds"));
        requestBody.put("participantIds", request.get("participantIds"));

        JSONObject detailUrl = new JSONObject();
        detailUrl.put("appUrl", "https://www.xiaocm.com");
        detailUrl.put("pcUrl", "https://www.xiaocm.com");
        requestBody.put("detailUrl", detailUrl);

        // requestBody.put("isOnlyShowExecutor", request.get("isOnlyShowExecutor"));
        // requestBody.put("priority", request.get("priority"));

        // JSONObject notifyConfigs = new JSONObject();
        // notifyConfigs.put("dingNotify",
        // request.get("notifyConfigs").get("dingNotify"));
        // requestBody.put("notifyConfigs", notifyConfigs);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("x-acs-dingtalk-access-token", getAccessToken().getAccessToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        // Send the request and get the response
        HttpResponse<String> response;
        try {
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return ResponseEntity.ok(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating task");
        }
    }
}
