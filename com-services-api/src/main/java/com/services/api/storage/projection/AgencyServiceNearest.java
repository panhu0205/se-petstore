package com.services.api.storage.projection;

public interface AgencyServiceNearest {

    public Long getAgencyId();

    public String getAgencyName();

    public String getAgencyPhone();

    public Long getServiceId();

    public Double getLongitude();

    public Double getLatitude();

    public Double getDistanceInKm();

    public Long getState();

}
