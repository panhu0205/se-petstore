package com.services.api.storage.projection;


import io.swagger.models.auth.In;

public interface AgencyAdminOnline {
    Long getAgencyId();
    String getAgencyName();
    String getAgencyPhone();
    Double getLatitude();
    Double getLongitude();
    Integer getState();

    Long getServiceId();
    String getServiceName();
    String getServiceImageUrl();
}
