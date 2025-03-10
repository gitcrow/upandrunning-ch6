package com.springbootexam.upandrunning_ch6.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash // 템플릿에서 repository로 변경하면서 추가되는 부분
@JsonIgnoreProperties(ignoreUnknown = true)
public class Aircraft {
    @Id
    private Long id;
    private String callsign;
    private String squawk;
    private String reg;
    private String flightno;
    private String route;
    private String type;
    private String category;
    private int altitude;
    private int heading;
    private int speed;
    @JsonProperty("vert_rate")
    private int vertRate;
    @JsonProperty("selected_altitude")
    private int selectedAltitude;
    private double lat, lon, barometer;
    @JsonProperty("polar_distance")
    private double polarDistance;
    @JsonProperty("polar_bearing")
    private double polarBearing;
    @JsonProperty("is_adsb")
    private boolean isADSB;
    @JsonProperty("is_on_ground")
    private boolean isOnGround;
    @JsonProperty("last_seen_time")
    private Instant lastSeenTime;
    @JsonProperty("pos_update_time")
    private Instant posUpdateTime;
    @JsonProperty("bds40_seen_time")
    private Instant bds40SeenTime;

    //템플릿에서 repository로 변경하면서 제거되는 부분
    //repository 지원에 있는 변환기가 복잡한 타입 변환을 쉽게 처리하기때문에 명시적 getter, setter는 필요없어짐
    //다만, 스프링데이터 템플릿의 하위수준 기능에 직접 액세스할 필요가 있다면, 템플릿 기반 데이터베이스 지원은 필수
    /* 
    public String getLastSeenTime(){
        return lastSeenTime.toString();
    }

    public void setLastSeenTime(String lastSeenTime){
        if(null != lastSeenTime){
            this.lastSeenTime = Instant.parse(lastSeenTime);
        }else{
            this.lastSeenTime = Instant.ofEpochSecond(0);
        }
    }

    public String getPosUpdateTime() {
        return this.posUpdateTime.toString();
    }

    public void setPosUpdateTime(String posUpdateTime) {
        if(null != posUpdateTime){
            this.posUpdateTime = Instant.parse(posUpdateTime);
        }else{
            this.posUpdateTime = Instant.ofEpochSecond(0);
        }
    }

    public String getBds40SeenTime() {
        return this.bds40SeenTime.toString();
    }

    public void setBds40SeenTime(String bds40SeenTime) {
        if(null != bds40SeenTime){
            this.bds40SeenTime = Instant.parse(bds40SeenTime);
        }else{
            this.bds40SeenTime = Instant.ofEpochSecond(0);
        }
    }
    */
}
