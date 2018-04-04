/*
 * Licensed to GraphHopper GmbH under one or more contributor
 * license agreements. See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 *
 * GraphHopper GmbH licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jutem.cases.jsprit.calculator;

import com.graphhopper.jsprit.core.algorithm.recreate.ActivityInsertionCostsCalculator;
import com.graphhopper.jsprit.core.algorithm.state.InternalStates;
import com.graphhopper.jsprit.core.problem.cost.VehicleRoutingActivityCosts;
import com.graphhopper.jsprit.core.problem.cost.VehicleRoutingTransportCosts;
import com.graphhopper.jsprit.core.problem.misc.JobInsertionContext;
import com.graphhopper.jsprit.core.problem.solution.route.activity.DeliverShipment;
import com.graphhopper.jsprit.core.problem.solution.route.activity.End;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.problem.solution.route.state.RouteAndActivityStateGetter;
import com.graphhopper.jsprit.core.problem.vehicle.Vehicle;

/**
 * 影响某个服务加入某段路径中的消耗
 * 源码中的case:
 * LocalActivityInsertionCostsCalculator(计算新增点增加的开销)
 */
public class CloseActivityInsertionCostsCalculator implements ActivityInsertionCostsCalculator {

    private VehicleRoutingTransportCosts routingCosts;

    private VehicleRoutingActivityCosts activityCosts;

    private RouteAndActivityStateGetter stateManager;


    public CloseActivityInsertionCostsCalculator(VehicleRoutingTransportCosts routingCosts, VehicleRoutingActivityCosts actCosts, RouteAndActivityStateGetter stateManager) {
        super();
        this.routingCosts = routingCosts;
        this.activityCosts = actCosts;
        this.stateManager = stateManager;
    }

    /**
     * 简单实现一个只根据新增点相对于前一个服务点产生的距离
     */
    @Override
    public double getCosts(JobInsertionContext iFacts, TourActivity prevAct, TourActivity nextAct, TourActivity newAct, double depTimeAtPrevAct) {
        return routingCosts.getTransportCost(prevAct.getLocation(), newAct.getLocation(), depTimeAtPrevAct, iFacts.getNewDriver(), iFacts.getNewVehicle());

    }
}
