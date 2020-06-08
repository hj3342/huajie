package server;

import java.util.ArrayList;
import java.util.List;

public class LagouHost {
    private String name;
    private List<LagouContext> lagouContexts = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LagouContext> getLagouContexts() {
        return lagouContexts;
    }

    public void setLagouContexts(List<LagouContext> lagouContexts) {
        this.lagouContexts = lagouContexts;
    }
}
