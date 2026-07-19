package com.logitics.erp.transport.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class KakaoRouteResponse {

    @JsonProperty("trans_id")
    private String transId;

    private List<Route> routes;

    @Getter
    @NoArgsConstructor
    public static class Route {

        @JsonProperty("result_code")
        private int resultCode;

        @JsonProperty("result_msg")
        private String resultMsg;

        private Summary summary;

        private List<Section> sections;
    }

    @Getter
    @NoArgsConstructor
    public static class Summary {

        private Point origin;

        private Point destination;

        private List<Point> waypoints;

        private String priority;

        private Bound bound;

        private Fare fare;

        private int distance;

        private int duration;
    }

    @Getter
    @NoArgsConstructor
    public static class Point {

        private String name;

        private double x;

        private double y;
    }

    @Getter
    @NoArgsConstructor
    public static class Bound {

        @JsonProperty("min_x")
        private double minX;

        @JsonProperty("min_y")
        private double minY;

        @JsonProperty("max_x")
        private double maxX;

        @JsonProperty("max_y")
        private double maxY;
    }

    @Getter
    @NoArgsConstructor
    public static class Fare {

        private int taxi;

        private int toll;
    }

    @Getter
    @NoArgsConstructor
    public static class Section {

        private int distance;

        private int duration;

        private Bound bound;

        private List<Road> roads;

        private List<Guide> guides;
    }

    @Getter
    @NoArgsConstructor
    public static class Road {

        private String name;

        private int distance;

        private int duration;

        @JsonProperty("traffic_speed")
        private double trafficSpeed;

        @JsonProperty("traffic_state")
        private int trafficState;

        // [경도, 위도, 경도, 위도 ...]
        private List<Double> vertexes;
    }

    @Getter
    @NoArgsConstructor
    public static class Guide {

        private String name;

        private double x;

        private double y;

        private int distance;

        private int duration;

        private int type;

        private String guidance;

        @JsonProperty("road_index")
        private int roadIndex;
    }
}