package com.jutem.cases.jsprit.constraint;

import com.graphhopper.jsprit.core.problem.Capacity;
import com.graphhopper.jsprit.core.problem.constraint.HardRouteConstraint;
import com.graphhopper.jsprit.core.problem.misc.JobInsertionContext;
import com.graphhopper.jsprit.core.problem.vehicle.Vehicle;
import com.jutem.cases.jsprit.constants.Constants;
import org.springframework.stereotype.Component;

/**
 * 硬性限制类
 * 只要返回false这个服务点就不会加入当前尝试的路径中
 * 源码中实现的case:
 * ServiceLoadRouteLevelConstraint(计算capacityDimensions是否满足需求)
 * HardSkillConstraint(判断是否有对应的skill)
 */
@Component
public class HardWeightConstraint implements HardRouteConstraint {
    /**
     * 新车载重小于100,则返回false
     */
    @Override
    public boolean fulfilled(JobInsertionContext jobInsertionContext) {
        Vehicle vehicle = jobInsertionContext.getNewVehicle();
        Capacity capacity = vehicle.getType().getCapacityDimensions();
        if (capacity.get(Constants.WEIGHT) < 100)
            return false;
        else
            return true;
    }
}
