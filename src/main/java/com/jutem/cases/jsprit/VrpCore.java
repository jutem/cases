package com.jutem.cases.jsprit;

import com.graphhopper.jsprit.analysis.toolbox.GraphStreamViewer;
import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.algorithm.state.StateManager;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.constraint.ConstraintManager;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.vehicle.Vehicle;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Coordinate;
import com.graphhopper.jsprit.core.util.Solutions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.jutem.cases.jsprit.constants.Constants.*;

public class VrpCore {

    public void bootstrap() {
        List<Vehicle> vehicles = buildFleet();
        List<Service> services = buildService();
        VehicleRoutingProblem vrp = buildVrp(vehicles, services);
        VehicleRoutingAlgorithm vra = buildAlgorithm(vrp);
        Collection<VehicleRoutingProblemSolution> solutions = vra.searchSolutions();
        VehicleRoutingProblemSolution best = Solutions.bestOf(solutions);
        SolutionPrinter.print(vrp, best, SolutionPrinter.Print.VERBOSE);
        new GraphStreamViewer(vrp, best).display();
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建algorihm
     */
    private VehicleRoutingAlgorithm buildAlgorithm(VehicleRoutingProblem vrp) {

        StateManager stateManager = new StateManager(vrp); //状态manage
        ConstraintManager constraintManage = new ConstraintManager(vrp, stateManager); //限制manage
        //constraintManage.addConstraint(new HardWeightConstraint()); //增加一些限制条件

        //CloseActivityInsertionCostsCalculator actCalculator = new CloseActivityInsertionCostsCalculator(vrp.getTransportCosts(), vrp.getActivityCosts(), stateManager);

        VehicleRoutingAlgorithm vra = Jsprit.Builder.newInstance(vrp)
                //.setActivityInsertionCalculator(actCalculator) //计算动态尝试插入点时候新增点相对于原始点新增的代价
                //.setObjectiveFunction(new ServiceNumCalculator()) //计算每个solution总开销的方法
                .setStateAndConstraintManager(stateManager, constraintManage)
                .buildAlgorithm();
        //增加一些监听，比如当某个点未排进结果的时候监听失败原因
        //vra.addListener(new UnassignedJobListener());
        return vra;
    }


    /**
     * 构建problem
     */
    private VehicleRoutingProblem buildVrp(List<Vehicle> vehicles, List<Service> services) {

        VehicleRoutingProblem vrp = VehicleRoutingProblem.Builder.newInstance()
                .setFleetSize(VehicleRoutingProblem.FleetSize.INFINITE) //可设置车队规模
                //.setRoutingCost() //设置有向图，可以包括距离和时间.当车辆和服务位置使用id标识时起作用
                .addAllVehicles(vehicles)
                .addAllJobs(services)
                .build();
        return vrp;
    }

    /**
     * 构造fleet
     */
    private List<Vehicle> buildFleet() {

        List<Vehicle> fleets = new ArrayList<>();
        //定义车辆类型
        VehicleTypeImpl type = VehicleTypeImpl.Builder.newInstance("车辆类型A") //定义id
                .addCapacityDimension(WEIGHT, 100) //增加自定义维度 tips:只支持整数型
                .addCapacityDimension(VOLUME, 100)
                .setCostPerDistance(1) //每单位距离的开销
                .setCostPerTransportTime(0) //路上,每单位时间的开销
                .setCostPerServiceTime(0) //服务时,每单位时间的开销
                .setFixedCost(0) //固定开销
                .build();

        //可以定义坐标,坐标和id二择其一
        Location location = Location.Builder.newInstance()
                //.setId("车辆起始坐标") //定义全局有向图的时候用到
                .setCoordinate(Coordinate.newInstance(1, 1)) //设置坐标
                .build();
        //定义具体车辆
        Vehicle vehicle = VehicleImpl.Builder.newInstance("车辆A") //定义id
                .setType(type) //设置车辆类型
                .setStartLocation(location) //设置起始点
                .addSkill(COLD) //增加车辆技能,例如冷冻，冷藏
                .addSkill(HOT)
                .setEarliestStart(60) //设置发车时间,逻辑上可以认为60等于凌晨1点
                .setLatestArrival(120) //最晚回车时间
                .build();

        fleets.add(vehicle);
        return fleets;
    }

    /**
     * 构建服务
     */
    private List<Service> buildService() {

        //可以定义坐标,坐标和id二择其一
        Location location = Location.Builder.newInstance()
                //.setId("车辆起始坐标") //定义全局有向图的时候用到
                .setCoordinate(Coordinate.newInstance(2, 2)) //设置坐标
                .build();
        Service service = Service.Builder.newInstance("服务点A")
                .addSizeDimension(WEIGHT, 1) //增加自定义维度 tips:只支持整数型
                .addSizeDimension(VOLUME, 1)
                .addRequiredSkill(HOT) //增加需求的技能
                .addTimeWindow(0, 1000) //窗口时间
                .setServiceTime(10) //服务时间
                .setLocation(location)
                .build();

        List<Service> services = new ArrayList<>();
        services.add(service);
        return services;
    }
}
