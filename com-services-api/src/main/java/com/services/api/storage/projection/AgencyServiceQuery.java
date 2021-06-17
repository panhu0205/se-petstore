package com.services.api.storage.projection;

public interface AgencyServiceQuery {

    public Long getId();

    public String getServiceName();

    public Long getServiceId();

    public String getServiceImageUrl();

    public Integer getServicePrice();

    public Integer getServiceRatioShare();

    public Integer getStatus();
}
