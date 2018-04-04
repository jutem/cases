package com.jutem.cases.jsprit.listener;

import com.graphhopper.jsprit.core.algorithm.recreate.listener.JobUnassignedListener;
import com.graphhopper.jsprit.core.algorithm.state.StateUpdater;
import com.graphhopper.jsprit.core.problem.job.Job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 获取服务点未插入路径原因
 * tips:这里有一个需要改进的地方是这里只会收集到全部的失败原因，而不是针对某一次solution的
 */
public class UnassignedJobListener implements JobUnassignedListener, StateUpdater {

    private List<String> reasons = new ArrayList<>();

    @Override
    public void informJobUnassigned(Job job, Collection<String> collection) {
        reasons.addAll(collection);
    }

    public List<String> getReasons() {
        return reasons;
    }
}
