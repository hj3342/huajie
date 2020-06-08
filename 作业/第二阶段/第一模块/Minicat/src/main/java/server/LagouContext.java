package server;

import java.util.ArrayList;
import java.util.List;

public class LagouContext {

    private String path;

    private List<LagouWrapper> lagouWrappers = new ArrayList<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<LagouWrapper> getLagouWrappers() {
        return lagouWrappers;
    }

    public void setLagouWrappers(List<LagouWrapper> lagouWrappers) {
        this.lagouWrappers = lagouWrappers;
    }
}
