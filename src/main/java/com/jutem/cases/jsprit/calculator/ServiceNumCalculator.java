package com.jutem.cases.jsprit.calculator;

import com.graphhopper.jsprit.core.problem.solution.SolutionCostCalculator;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;

/**
 * 计算每一个solution的最终开销
 * 默认实现是在jsprit对象中getObjectiveFunction方法里有一个默认实现
 */
public class ServiceNumCalculator implements SolutionCostCalculator {

    /**
     * 简单实现一个根据solution中的路径数量*100当做solution的开销
     */
    @Override
    public double getCosts(VehicleRoutingProblemSolution solution) {
        return solution.getRoutes().size() * 100;
    }

}
