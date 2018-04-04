package com.jutem.cases.spider.webmagic;

import com.alibaba.fastjson.JSON;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href = http://webmagic.io/docs/zh/posts/ch2-install/first-project.html></a>
 */
public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
//        page.addTargetRequests(page.getHtml().links().regex("(https://leetcode\\.com/\\w+/\\w+)").all());
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        page.putField("name", page.getHtml().xpath("//tbody[@class='reactable-data']/a/text()").toString());
//        page.putField("allLinkes", JSON.toJSONString(page.getHtml().links().all()));
//        page.putField("html", page.getHtml().toString());

//        System.out.println("raw : " + page.getRawText());


        List<String> list = new JsonPathSelector("$.stat_status_pairs").selectList(page.getRawText());
        List<String> titles = new ArrayList<>();
        for(String s : list) {
            String title = new JsonPathSelector("$.stat.question__title").select(s);
            titles.add(title);
        }
        page.putField("titles", titles);

//        if (page.getResultItems().get("name") == null) {
//            skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://leetcode.com/api/problems/all/")
                .addPipeline(new ConsolePipeline()).thread(5).run();

    }
}
