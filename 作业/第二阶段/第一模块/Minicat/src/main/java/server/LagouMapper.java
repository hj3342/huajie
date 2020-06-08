package server;

import java.util.ArrayList;
import java.util.List;

public class LagouMapper {

    private List<LagouHost> lagouHosts = new ArrayList<>();

    public List<LagouHost> getLagouHosts() {
        return lagouHosts;
    }

    public void setLagouHosts(List<LagouHost> lagouHosts) {
        this.lagouHosts = lagouHosts;
    }
}
