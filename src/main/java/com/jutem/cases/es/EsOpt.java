package com.jutem.cases.es;

import org.apache.commons.collections.map.HashedMap;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesAction;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesRequest;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesRequestBuilder;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesResponse;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateAction;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequestBuilder;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class EsOpt {
    @Autowired
    private TransportClient esClient;

    private String index = "ord_order";
    private String type = "logs";

    public String templateSearch(Date startTime, Date endTime) {

        Map<String, Object> templateParams = new HashedMap();
        templateParams.put("startTime", startTime);
        templateParams.put("endTime", endTime);

        SearchResponse sr = new SearchTemplateRequestBuilder(esClient)
                .setScript("template_gender")
                .setScriptType(ScriptType.FILE)
                .setScriptParams(templateParams)
                .setRequest(new SearchRequest().indices(index).types(type))
                .get().getResponse();

        return sr.toString();
    }

    public String putTemplate(String name, String source) {
        PutIndexTemplateResponse resp = new PutIndexTemplateRequestBuilder(esClient, PutIndexTemplateAction.INSTANCE, name)
                .setTemplate(name)
                .setSource(source)
                .get();
        return resp.toString();
    }

    public String getTemplate(String name) {
        GetIndexTemplatesResponse resp = new GetIndexTemplatesRequestBuilder(esClient, GetIndexTemplatesAction.INSTANCE, name)
                .get();
        return resp.toString();
    }

}
