package com.jutem.cases.jsprit;


import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.cost.VehicleRoutingTransportCosts;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.vehicle.Vehicle;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * 用来解决vrp问题的工具
 * 使用jsprit工具，git:https://github.com/graphhopper/jsprit/
 */
@Component
public class JspritUtil {
    private Logger logger = LoggerFactory.getLogger(JspritUtil.class);

    private static final Integer WEIGHT = 0;
    private static final Integer VOLUME = 1;

    public void Demon() {

        Integer WEIGHT = 0;
        Integer VOLUME = 1;
        Location startLocation = Location.newInstance("CC");

        VehicleType tenTonType = VehicleTypeImpl.Builder.newInstance("tenTon").addCapacityDimension(WEIGHT, 10).addCapacityDimension(VOLUME, 100).setCostPerDistance(1).build();
        VehicleImpl tenTonVehicle = VehicleImpl.Builder.newInstance("tenTon").setStartLocation(startLocation).setType(tenTonType).build();

        VehicleType fiveTonType = VehicleTypeImpl.Builder.newInstance("fiveTon").addCapacityDimension(WEIGHT, 5).addCapacityDimension(VOLUME, 50).setCostPerDistance(1).build();
        VehicleImpl fiveTonVehicle = VehicleImpl.Builder.newInstance("fiveTon").setStartLocation(startLocation).setType(fiveTonType).build();

        VehicleType threeTonType = VehicleTypeImpl.Builder.newInstance("threeTon").addCapacityDimension(WEIGHT, 3).addCapacityDimension(VOLUME, 30).setCostPerDistance(1).build();
        VehicleImpl threeTonVehicle = VehicleImpl.Builder.newInstance("threeTon").setStartLocation(startLocation).setType(threeTonType).build();

        Service sb = Service.Builder.newInstance("sb").addSizeDimension(WEIGHT, 3).addSizeDimension(VOLUME, 5).setLocation(Location.newInstance("B")).build();
        Service sd = Service.Builder.newInstance("sd").addSizeDimension(WEIGHT, 2).addSizeDimension(VOLUME, 10).setLocation(Location.newInstance("D")).build();
        Service sf = Service.Builder.newInstance("sf").addSizeDimension(WEIGHT, 2).addSizeDimension(VOLUME, 2).setLocation(Location.newInstance("F")).build();
        Service sg = Service.Builder.newInstance("sg").addSizeDimension(WEIGHT, 9).addSizeDimension(VOLUME, 10).setLocation(Location.newInstance("G")).build();

        VehicleRoutingTransportCostsMatrix.Builder costMatrixBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(true);
        costMatrixBuilder.addTransportDistance("CC", "B", 3.0);
        costMatrixBuilder.addTransportDistance("CC", "D", 7.0);
        costMatrixBuilder.addTransportDistance("CC", "F", 10.0);
        costMatrixBuilder.addTransportDistance("CC", "G", 10.0);
        costMatrixBuilder.addTransportDistance("B", "D", 6.0);
        costMatrixBuilder.addTransportDistance("B", "F", 4.0);
        costMatrixBuilder.addTransportDistance("B", "G", 10.0);
        costMatrixBuilder.addTransportDistance("D", "F", 5.0);
        costMatrixBuilder.addTransportDistance("D", "G", 10000.0);
        costMatrixBuilder.addTransportDistance("F", "G", 3.0);

        VehicleRoutingTransportCosts costMatrix = costMatrixBuilder.build();

        VehicleRoutingProblem vrp = VehicleRoutingProblem.Builder.newInstance().setFleetSize(VehicleRoutingProblem.FleetSize.FINITE).setRoutingCost(costMatrix)
                .addVehicle(tenTonVehicle).addVehicle(fiveTonVehicle).addVehicle(threeTonVehicle)
                .addJob(sb).addJob(sd).addJob(sf).addJob(sg).build();

        VehicleRoutingAlgorithm vra = Jsprit.createAlgorithm(vrp);

        Collection<VehicleRoutingProblemSolution> solutions = vra.searchSolutions();

        SolutionPrinter.print(vrp, Solutions.bestOf(solutions), SolutionPrinter.Print.VERBOSE);
    }

    public void MultiVehicleAndJob(int jobNum, int vBigNum, int vSmallNum) {
        List<Location> locations = new ArrayList<>(jobNum + 1);
        Location startLocation = Location.newInstance("CC");
        locations.add(startLocation);
        //设置车辆属相
        VehicleType bigType = buildType(10, 100, "tenTon");
        VehicleType smallType = buildType(5, 50, "fiveTon");

        List<Vehicle> vList = new ArrayList<>(vBigNum);
        for(int i = 0; i < vBigNum; i++) {
            Vehicle v = buildVehicle(startLocation, bigType, "v_big_" + i);
            vList.add(v);
        }
        for(int i = 0; i < vSmallNum; i++) {
            Vehicle v = buildVehicle(startLocation, smallType, "v_small_" + i);
            vList.add(v);
        }

        //随机生成服务
        Random random = new Random();
        List<Service> services = new ArrayList<>(jobNum);
        for(int i = 0; i < jobNum; i++) {
            Location location = Location.newInstance("service_location_" + i);
            locations.add(location);
            int weight =  random.nextInt(10) + 1;
            int volume = random.nextInt(100) + 1;
            Service service = buildService(location, weight, volume, "service_" + i);
            logger.debug(service.getId() + ":" + weight + ":" + volume);
            services.add(service);
        }

        //生成距离矩阵
        VehicleRoutingTransportCosts costMatrix = buildMatrix(locations);

        VehicleRoutingProblem vrp = VehicleRoutingProblem.Builder.newInstance().setFleetSize(VehicleRoutingProblem.FleetSize.INFINITE).setRoutingCost(costMatrix)
                .addAllVehicles(vList).addAllJobs(services).build();

        long start = System.currentTimeMillis();
        VehicleRoutingAlgorithm vra = Jsprit.createAlgorithm(vrp);
        long end = System.currentTimeMillis();
        logger.info("createAlgorithm time : " + (end - start));

        start = System.currentTimeMillis();
        Collection<VehicleRoutingProblemSolution> solutions = vra.searchSolutions();
        end = System.currentTimeMillis();
        logger.info("search time : " + (end - start));

        SolutionPrinter.print(vrp, Solutions.bestOf(solutions), SolutionPrinter.Print.VERBOSE);
    }

    private VehicleImpl buildVehicle(Location location, VehicleType type, String id) {
        return VehicleImpl.Builder.newInstance(id).setStartLocation(location).setType(type).build();
    }

    private VehicleType buildType(int weight, int volume, String id) {
        return VehicleTypeImpl.Builder.newInstance(id).addCapacityDimension(WEIGHT, weight).addCapacityDimension(VOLUME, volume).build();
    }

    private Service buildService(Location location, int needWeight, int needVolume, String id) {
        return Service.Builder.newInstance(id).addSizeDimension(WEIGHT, needWeight).addSizeDimension(VOLUME, needVolume).setLocation(location).build();
    }

    private VehicleRoutingTransportCosts buildMatrix(List<Location> locations) {
        VehicleRoutingTransportCostsMatrix.Builder costMatrixBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(true);
        Random random = new Random();
        for(int i = 0; i < locations.size() - 1; i ++) {
            Location from = locations.get(i);
            for(int j = i + 1; j < locations.size(); j++) {
                Location to = locations.get(j);
                double distance = random.nextDouble() * 80 + 1.0;
                costMatrixBuilder.addTransportDistance(from.getId(), to.getId(), distance);
                logger.debug(from.getId() + ":" + to.getId() + ":" + distance);
            }
        }

        return costMatrixBuilder.build();
    }

}
